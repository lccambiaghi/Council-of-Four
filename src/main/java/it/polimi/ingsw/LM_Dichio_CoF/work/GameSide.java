package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameSide {

	ArrayList <Player> arrayListAvailablePlayer;
	ArrayList <Player> arrayListPlayingPlayer;
	ServerSocket serverSocket;
	
	public GameSide() {
		
		arrayListAvailablePlayer = new ArrayList <Player>();
		
		try {
			serverSocket = new ServerSocket(Constant.SOCKET_PORT);
			while(true){
				Socket socket = serverSocket.accept();
				new SocketConnectionWithClient(socket, this);		
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void handlePlayer(Player player){
		
		arrayListAvailablePlayer.add(player);
	
		System.out.println("I am managing a player through a thread");
		
		if(arrayListAvailablePlayer.size()==1){
			askForConfigurations(player);
		}else{
			player.sendString("Sei arrivato tardi");
		}
			
	}
	
	private synchronized/*??*/ void askForConfigurations(Player player){
		
		player.sendString("configRequest");
		
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
	
	
}
