package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIConnectionWithPlayer;
import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIGameSide;
import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIGameSideInterface;
import it.polimi.ingsw.LM_Dichio_CoF.connection.SocketConnectionWithPlayer;
import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIConnection;

public class GameSide {

	private static ArrayList <Player> arrayListPlayer;
	
	RMIGameSideInterface rmiGameSide;
	
	private ServerSocket serverSocket;
	
	public static final Object lockArrayListPlayer = new Object();


	private boolean firstAvailablePlayer=true;
	
	public GameSide() {
		
		arrayListPlayer = new ArrayList <Player>();
		
		/*
		 * Initialize and locate the RMI registry
		 */
		initializeRMI();
		
		try {
			serverSocket = new ServerSocket(Constant.SOCKET_PORT);
			//while(true){
				//Socket socket = serverSocket.accept();
				//new SocketConnectionWithPlayer(socket, this);		
			//}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	
	
	public void handlePlayer(Player player){
		
		System.out.println("I am managing a player through a thread");
		login(player);
		System.out.println("The player has successfully connected with nickname: "+player.getNickname());
		
		addPlayerToArrayList(player);
		
		if(firstAvailablePlayer){
			firstAvailablePlayer=false;
			new HandlerArrayListPlayer(this).start();
		}else{
			player.sendString("wait");
		}
	}
	
	private void addPlayerToArrayList(Player player){
		synchronized(lockArrayListPlayer){
			arrayListPlayer.add(player);
		}
	}
	
	public ArrayList<Player> getArrayListPlayer() {
		synchronized(lockArrayListPlayer){
			return arrayListPlayer;
		}
	}
	
	
	public void removeArrayListPlayer(int playersNumber){
		synchronized (lockArrayListPlayer) {
			for(int i=0; i<playersNumber; i++){
				arrayListPlayer.remove(0);
			}
		}
	}
	
	public Player getFirstPlayer(){
		synchronized (lockArrayListPlayer) {
			return(arrayListPlayer.get(0));
		}
	}

	private void login(Player player){
		
		boolean logged = false;
		player.sendString("Enter a nickname");
		while(!logged){
			String nickname = player.receiveString();
			if(!isNicknameInUse(nickname)){
				logged=true;
				player.sendString("Logged");
			}else{
				player.sendString("Nickname already in use, enter another one");
			}
		}
		
	}
	
	private boolean isNicknameInUse(String nickname){
		for(Player player: arrayListPlayer){
			if(player.getNickname().equals(nickname))
				return true;
		}
		return false;
	}
	
	public void setFirstAvailablePlayer(boolean firstAvailablePlayer) {this.firstAvailablePlayer = firstAvailablePlayer;}
	

}
