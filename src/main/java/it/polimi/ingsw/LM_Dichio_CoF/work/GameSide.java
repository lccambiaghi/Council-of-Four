package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.IOException;
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
				Socket clientSocket = serverSocket.accept();
				new SocketConnectionWithClient(clientSocket, this);		
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void handlePlayer(Player player){
		
		arrayListAvailablePlayer.add(player);
	
		System.out.println("I am managing a player through a thread");
		
		player.send("ciao");
		
		if(arrayListAvailablePlayer.size()==1){
			//method that asks for configurations
		}
			
	}
	
	
}
