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
	
	private final Object lockArrayListPlayer = new Object();
	private final Object lockWaitingRoomAvailable = new Object();
	
	private boolean waitingRoomAvailable=false;
	
	public GameSide() {
		
		/**
		 * Initialize and locate the RMI registry
		 */
		//initializeRMI();
		
		/**
		 * Start listening with Socket with a thread
		 */
		listenSocket = new ListenSocket(this);
		listenSocket.start();
		
	}
	
	private void initializeRMI(){
		try {
		
			System.out.println("Creating RMI registry...");
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			
			System.out.println("Implementing RMIGameSide...");
			rmiGameSide=new RMIGameSide(this);
			
			System.out.println("Rebinding...");
			Naming.rebind("rmi://127.0.0.1:1099/CoF", rmiGameSide);
			
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
		hp.start();
	}
	
	class HandlePlayer extends Thread{
		
		private GameSide gameSide;
		private Player player; 
		
		public HandlePlayer(GameSide gameSide, Player player){this.gameSide=gameSide; this.player=player;}
		
		public void run(){
			
			Broker.login(player, gameSide);
			
			synchronized (lockArrayListPlayer) {
				player.setPlaying(false);
				arrayListPlayer.add(player);
				arrayListAllPlayer.add(player);
			}
			
			synchronized (lockWaitingRoomAvailable) {
				if(!waitingRoomAvailable){
					waitingRoomAvailable=true;
					waitingRoom = new WaitingRoom(gameSide, player);
					waitingRoom.start();
				}else{
					waitingRoom.addPlayerToWaitingRoom(player);
					try {
						sleep(1000);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
			
		}
	
	}
	
	public boolean isNicknameInUse(String nickname){
		for(Player player: getArrayListAllPlayer()){
			if(player.getNickname().equals(nickname))
				return true;
		}
		return false;
	}
	
	public ArrayList<Player> getArrayListPlayer() {
		synchronized(lockArrayListPlayer){
			return arrayListPlayer;
		}
	}
	
	public ArrayList<Player> getArrayListAllPlayer() {
		return arrayListAllPlayer;
	}
	
	public void setWaitingRoomAvailable(boolean waitingRoomAvailable) {
		this.waitingRoomAvailable = waitingRoomAvailable;
	}
	
}
