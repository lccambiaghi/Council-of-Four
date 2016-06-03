package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.util.ArrayList;

public class GameSide {

	private ArrayList <Player> arrayListPlayer;
	
	private ServerSocket serverSocket;
	
	boolean matchStarterGoingOn;
	
	public GameSide() {
		
		arrayListPlayer = new ArrayList <Player>();
		
		try {
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
		
		arrayListPlayer.add(player);
		
		matchStarterGoingOn = false;
		
		if(arrayListPlayer.size()==1&&!matchStarterGoingOn){
			matchStarterGoingOn=true;
			player.sendString("config");
			int playersMaxNumber = getPlayersMaxNumberFromPlayer(player);
			setConfigurationsFromPlayer(player);
			new MatchStarter(this, playersMaxNumber);
		}else{
			player.sendString("wait");
		}
			
	}
	
	public ArrayList<Player> getArrayListPlayer() {return arrayListPlayer;}


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
	
	
	private int getPlayersMaxNumberFromPlayer(Player player){
		int playersMaxNumber;
		playersMaxNumber = Integer.parseInt(player.receiveString());
		return playersMaxNumber;
	}
	
	private void setConfigurationsFromPlayer(Player player){
		
		Object object = player.receiveObject();
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("./src/configurations/config");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();	
		}finally{
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void canCreateNewMatch(){ boolean matchStarterGoingOn = false; }
	
}
