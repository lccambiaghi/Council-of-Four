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
	
	public static final Object lockArrayListPlayer = new Object();
	public static final Object lockWaitingRoomAvailable = new Object();
	
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
		
		/**
		 * Start checking (while true)
		 * if a new player has connected and (if so) create a Waiting Room if there's none already available
		 */
		arrayListPlayerHandler();
		
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
					new SocketConnectionWithPlayer(socket, gameSide);		
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void handlePlayer(Player player){
		
		Broker.login(player, this);
		
		synchronized (lockArrayListPlayer) {
			player.setPlaying(false);
			arrayListPlayer.add(player);
			arrayListAllPlayer.add(player);
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
		synchronized (lockWaitingRoomAvailable) {
			this.waitingRoomAvailable = waitingRoomAvailable;
		}
	}
	
	public boolean getWaitingRoomAvailable() {
		synchronized (lockWaitingRoomAvailable) {
			return waitingRoomAvailable;
		}
	}
	
	private void arrayListPlayerHandler(){
		
		Player player = null;
		Boolean startWaitingRoom = false;
		while(true){
			synchronized(lockArrayListPlayer){
				if(arrayListPlayer.size()!=0){
					player = arrayListPlayer.remove(0);
				}
			}
			if(player!=null){
				synchronized (lockWaitingRoomAvailable) {
					if(!waitingRoomAvailable){
						waitingRoomAvailable=true;
						startWaitingRoom=true;
						waitingRoom = new WaitingRoom(this, player);
					}else{
						waitingRoom.addPlayerToWaitingRoom(player);
					}
					player=null;
				}
				if(startWaitingRoom){
					waitingRoom.start();
					startWaitingRoom=false;
				}
			}
		}
	}
	
}
