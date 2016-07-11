package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.connection.RMIGameSide;
import it.polimi.ingsw.server.connection.RMIGameSideInterface;
import it.polimi.ingsw.server.connection.SocketConnectionWithPlayer;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.DisconnectedException;
import it.polimi.ingsw.utils.Message;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.util.ArrayList;

/**
 * This class contains the main that launches the server.
 * 
 * initializeRMI: creates the registry and allocate the class rmiGameSide
 * 
 * ListenSocket: class that extends thread and permit to listen through socket
 * 
 * The whole class is designed to handle the clients who successfully connect to the server:
 * it permits them to login, it adds them to the "arrayListAllPlayer" and put them in
 * the first waiting room available.
 * 
 * It also handle the players that finish a match and asks them to play again.
 * 
 * There is a Thread for every player through all of these processes, so concurrent variables
 * and methods are synchronized with locks.
 * 
 * Moreover there are locks in common with the class WaitingRoom to avoid concurrent accesses to
 * common variables.
 *
 */
public class GameSide implements Serializable{
	
	private final ArrayList<Player> arrayListAllPlayer = new ArrayList<>();
	private final Object lockArrayListPlayer = new Object();
	private final Object lockWaitingRoomFromGameSide = new Object();

	private WaitingRoom waitingRoom;

	private boolean waitingRoomAvailable=false;
	
	/**
	 * Constructor of the class.
	 * Initializes and locate the RMI registry and starts listening through Socket
	 * launching a new thread
	 */
	private void startGameSide() {
		
		initializeRMI();

		ListenSocket listenSocket = new ListenSocket(this);
		listenSocket.start();
		
	}
	
	private void initializeRMI(){
		try {
		
			System.out.println("Creating RMI registry...");
			java.rmi.registry.LocateRegistry.createRegistry(Constant.RMI_PORT);
			
			System.out.println("Implementing RMIGameSide...");
			RMIGameSideInterface rmiGameSide = new RMIGameSide(this);
			
			System.out.println("Rebinding...");
			Naming.rebind("rmi://" + Constant.RMI_REGISTRY_ADDRESS +":" + Constant.RMI_PORT+"/CoF", rmiGameSide);
			
			System.out.println("RMI initialized!");

		}catch (Exception e) {
				System.out.println("RMI failed " + e);
		}
	}
	
	/**
	 *  This class creates the ServerSocket and, when the "start" method is invoked,
	 *  it starts listening through socket.
	 *  
	 *  Whenever a client connects it calls the method "startHandlePlayer".
	 *  
	 *  The "run" method catches InputOutput exceptions due to lack of connection.
	 *
	 */
	class ListenSocket extends Thread{
		
		private final GameSide gameSide;
		public ListenSocket(GameSide gameSide){this.gameSide=gameSide;}
		
		@Override
		public void run(){
			try {
				ServerSocket serverSocket = new ServerSocket(Constant.SOCKET_PORT);
				System.out.println("Socket listening on port "+Constant.SOCKET_PORT);
				while(true){
					Socket socket = serverSocket.accept();
					SocketConnectionWithPlayer s = new SocketConnectionWithPlayer(socket);
					startHandlePlayer(gameSide, s.getPlayer());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method can be called by both the classes of connection:
	 * "SocketConnectionWithPlayer" and "RMIConnectionWithPlayer"
	 * 
	 * It starts a new Thread creating the object "HandlePlayer".
	 * 
	 * It also prints in the console if the player has just connected
	 * 
	 * @param gameSide : the caller
	 * @param player : the player to be handled
	 */
	public void startHandlePlayer(GameSide gameSide, Player player){
		
		HandlePlayer hp = new HandlePlayer(gameSide, player);
		
		if(!player.isLogged())
			System.out.println("A new player has connected");
		
		hp.start();
	}
	
	
	/**
	 * This class handles the player through a Thread.
	 * 
	 * putPlayerInArrayList : put the player into the arrayList
	 *  
	 * askToPlayAgain : asks the player whether he wants to play again or not
	 * 
	 * putPlayerInWaitingRoom : it puts the player into the a WaitingRoom available
	 *
	 */
	class HandlePlayer extends Thread{
		
		private final GameSide gameSide;
		private final Player player;
		
		public HandlePlayer(GameSide gameSide, Player player){
			this.gameSide=gameSide;
			this.player=player;
		}
		
		@Override
		public void run(){
			
			try {
				
				/**
				 * If the player is not connected yet
				 */
				if(!player.isLogged()){
					
					player.getBroker().login(gameSide);
					player.setLogged(true);
				
					putPlayerInArrayList(player);
					
				}
				
				/**
				 * If the player exit from a match
				 */
				else{
					
					if(!askToPlayAgain(player)){
						removePlayerFromArrayList(player);
						player.getBroker().println(Message.sayByeBye());
						return;
					}
				}
				
				putPlayerInWaitingRoom(player);
				
			}catch (DisconnectedException e){
				System.out.println("The player trying to login has disconnected!");
			}
		}
		
		private void putPlayerInArrayList(Player player){
			synchronized (lockArrayListPlayer) {
				arrayListAllPlayer.add(player);
			}
		}
		
		/**
		 * The method asks the player if he wants to play again
		 * 
		 * @param player 
		 * @return true if he plays again, otherwise false
		 */
		private boolean askToPlayAgain(Player player){
			player.getBroker().println(Message.askToPlayAgain());
			player.getBroker().println(Message.chooseYesOrNo_1_2());
			int choice = 0;
			try {
				choice = player.getBroker().askInputNumber(1, 2);
			} catch (InterruptedException e){
				Thread.currentThread().interrupt();
			}
			return choice==1;
		}
		
		/**
		 * This method creates a new WaitingRoom if there's none available, otherwise
		 * it puts the player in the available one.
		 * 
		 * In order to terminate, the method waits for a notify on the "lockWaitingRoom"
		 * 
		 * @param player : the player handled
		 */
		private void putPlayerInWaitingRoom(Player player){
			synchronized (lockWaitingRoomFromGameSide) {
				
				if(!waitingRoomAvailable){
					
					waitingRoom = new WaitingRoom(gameSide, player);
					waitingRoom.start();

					Object lockWaitingRoom = waitingRoom.getLockWaitingRoom();
					
					waitingRoomAvailable=true;
					
					try {
						
						synchronized (lockWaitingRoom) {
							lockWaitingRoom.wait();
						}
						
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					
					
				}else{
					
					waitingRoom.addPlayerToWaitingRoom(player);
					
				}
				
			}
		}
		
	}	
	
	/**
	 * The method removes the player from the "arrayListAllPlayer".
	 * It checks by itself if the player exists
	 * 
	 * @param player : the player to be removed
	 */
	public void removePlayerFromArrayList(Player player){
		synchronized (lockArrayListPlayer) {
			for(int i = 0; i<arrayListAllPlayer.size(); i++){
				if(arrayListAllPlayer.get(i).equals(player))
					arrayListAllPlayer.remove(i);
			}
		}
	}
	
	
	/**
	 * The method checks if the nickname already belongs to a player in the arrayList.
	 * If so it returns true, otherwise false
	 * 
	 * @param nickname : the nickname to be checked
	 * @return true if it already exists, otherwise false
	 */
	public boolean isNicknameInUse(String nickname){
		synchronized (lockArrayListPlayer) {
			for(Player player: arrayListAllPlayer){
				if(player.getNickname().equals(nickname))
					return true;
			}
			return false;
		}
	}

	public void setWaitingRoomAvailable(boolean waitingRoomAvailable) {
		this.waitingRoomAvailable = waitingRoomAvailable;
	}
	
	public Object getLockWaitingRoomFromGameSide() {
		return lockWaitingRoomFromGameSide;
	}
	
	/**
	 * The main launches the gameSide
	 * @param args
	 */
	public static void main( String[] args ){
		GameSide gm = new GameSide();
		gm.startGameSide();
    }

	
}
