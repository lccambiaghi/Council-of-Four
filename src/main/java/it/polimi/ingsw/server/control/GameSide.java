package it.polimi.ingsw.server.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.util.ArrayList;

import it.polimi.ingsw.server.connection.RMIGameSide;
import it.polimi.ingsw.server.connection.RMIGameSideInterface;
import it.polimi.ingsw.server.connection.SocketConnectionWithPlayer;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.DisconnectedException;
import it.polimi.ingsw.utils.Message;

public class GameSide {

	public static void main( String[] args ){
		new GameSide();
    }
	
	private static ArrayList<Player> arrayListAllPlayer = new ArrayList<Player>();

	private RMIGameSideInterface rmiGameSide;
	
	private ServerSocket serverSocket;
	private ListenSocket listenSocket;
	
	private WaitingRoom waitingRoom;
	
	private Object lockWaitingRoom;
	
	private final Object lockArrayListPlayer = new Object();
	public final Object lockWaitingRoomFromGameSide = new Object();

	private boolean waitingRoomAvailable=false;
	
	public GameSide() {
		
		/**
		 * Initialize and locate the RMI registry
		 */
		initializeRMI();
		
		/**
		 * Start listening with Socket with a thread
		 */
		listenSocket = new ListenSocket(this);
		listenSocket.start();
		
	}
	
	private void initializeRMI(){
		try {
		
			System.out.println("Creating RMI registry...");
			java.rmi.registry.LocateRegistry.createRegistry(Constant.RMI_PORT);
			
			System.out.println("Implementing RMIGameSide...");
			rmiGameSide=new RMIGameSide(this);
			
			System.out.println("Rebinding...");
			Naming.rebind("rmi://" + Constant.RMI_REGISTRY_ADDRESS +":" + Constant.RMI_PORT+"/CoF", rmiGameSide);
			
			System.out.println("RMI initialized!");

		}catch (Exception e) {
				System.out.println("RMI failed " + e);
		}
	}
	
	class ListenSocket extends Thread{
		
		private GameSide gameSide;
		public ListenSocket(GameSide gameSide){this.gameSide=gameSide;}
		public void run(){
			try {
				serverSocket = new ServerSocket(Constant.SOCKET_PORT);
				System.out.println("Socket listening on port "+Constant.SOCKET_PORT);
				while(true){
					Socket socket = serverSocket.accept();
					SocketConnectionWithPlayer s = new SocketConnectionWithPlayer(socket, gameSide);
					startHandlePlayer(gameSide, s.getPlayer());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startHandlePlayer(GameSide gameSide, Player player){
		
		HandlePlayer hp = new HandlePlayer(gameSide, player);
		
		if(!player.isLogged())
			System.out.println("A new player has connected");
		
		hp.start();
	}
	
	class HandlePlayer extends Thread{
		
		private GameSide gameSide;
		private Player player; 
		
		public HandlePlayer(GameSide gameSide, Player player){
			this.gameSide=gameSide;
			this.player=player;
		}
		
		public void run(){
			
			try {
				
				if(!player.isLogged()){
					
					player.getBroker().login(gameSide);
					player.setLogged(true);
				
					putPlayerInArrayList(player);
					
				}else{
					
					if(askToPlayAgain(player) == false){
						removePlayerFromArrayList(player);
						return;
					}
				}
				
				putPlayerInWaitingRoom(player);
				
			}catch (DisconnectedException e){
				System.out.println("The player trying to login has disconnected!");
			}
		}
	
	}
	
	public void putPlayerInArrayList(Player player){
		synchronized (lockArrayListPlayer) {
			player.setConnected(true);
			arrayListAllPlayer.add(player);
		}
	}
	
	private boolean askToPlayAgain(Player player){
		player.getBroker().println(Message.askToPlayAgain());
		player.getBroker().println(Message.chooseYesOrNo_1_2());
		int choice = 2;
		try {
			choice = player.getBroker().askInputNumber(1, 2);
		} catch (InterruptedException e) {}
		if(choice==1)
			return true;
		else
			return false;
	}
	
	public void removePlayerFromArrayList(Player player){
		synchronized (lockArrayListPlayer) {
			for(int i = 0; i<arrayListAllPlayer.size(); i++){
				if(arrayListAllPlayer.get(i).equals(player))
					arrayListAllPlayer.remove(i);
			}
		}
	}
	
	private void putPlayerInWaitingRoom(Player player){
		synchronized (lockWaitingRoomFromGameSide) {
			
			if(!waitingRoomAvailable){
				
				waitingRoom = new WaitingRoom(this, player);
				waitingRoom.start();
				
				lockWaitingRoom=waitingRoom.getLockWaitingRoom();
				
				waitingRoomAvailable=true;
				
				try {
					
					synchronized (lockWaitingRoom) {
						lockWaitingRoom.wait();
					}
					
				} catch (InterruptedException e) {}
				
				
			}else{
				
				waitingRoom.addPlayerToWaitingRoom(player);
				
			}
			
		}
	}
	
	
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

	
}
