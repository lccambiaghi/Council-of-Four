package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIConnectionWithPlayer;
import it.polimi.ingsw.LM_Dichio_CoF.connection.SocketConnectionWithPlayer;
import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIConnection;

public class GameSide {

	private static ArrayList <Player> arrayListPlayer;
	
	private ServerSocket serverSocket;
	
	public static final Object lockArrayListPlayer = new Object();


	private boolean firstAvailablePlayer=true;
	
	public GameSide() {
		
		arrayListPlayer = new ArrayList <Player>();
		
		try {
			//new RMIConnectionWithPlayer(this);
			serverSocket = new ServerSocket(Constant.SOCKET_PORT);
			while(true){
				Socket socket = serverSocket.accept();
				new SocketConnectionWithPlayer(socket, this);		
			}
		} catch (IOException e) {
			e.printStackTrace();
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
		while(!logged){
			String message = player.receiveString();
			if(message.equals("login")){
				String nickname = player.receiveString();
				if(isNicknameInUse(nickname)){
					player.sendString("false");
				}else{
					player.setNickname(nickname);
					player.sendString("true");
					logged=true;
				}
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
