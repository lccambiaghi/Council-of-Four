package it.polimi.ingsw.LM_Dichio_CoF.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.connection.DisconnectedException;
import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIConnectionWithPlayer;
import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIGameSide;
import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIGameSideInterface;
import it.polimi.ingsw.LM_Dichio_CoF.connection.SocketConnectionWithPlayer;
import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIConnection;

public class GameSide {

	public static void main( String[] args ){
		new GameSide();
    }
	
	
	private static ArrayList<Player> arrayListPlayer = new ArrayList<Player>();
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
				
				player.getBroker().login(gameSide);
				
				putPlayerInArrayLists();
				
				putPlayerInWaitingRoom();
				
			}catch (DisconnectedException e){
				System.out.println("The player trying to login has disconnected!");
			}
		}
		
		private void putPlayerInArrayLists(){
			synchronized (lockArrayListPlayer) {
				player.setConnected(true);
				arrayListPlayer.add(player);
				arrayListAllPlayer.add(player);
			}
		}
		
		private void putPlayerInWaitingRoom(){
			synchronized (lockWaitingRoomFromGameSide) {
				
				if(!waitingRoomAvailable){
					
					waitingRoom = new WaitingRoom(gameSide, player);
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
