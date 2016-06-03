package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.net.Socket;

public class SocketConnectionWithClient extends Thread {
	
	GameSide gameSide;
	Client client;
	
	SocketConnectionWithClient(Socket clientSocket, GameSide gameSide){
		
		this.gameSide=gameSide;
		
		client = new Client("s".charAt(0));
		client.setPlayerSocket(clientSocket);
		client.openSocketStream();
		
		start();
		
	}
	
	@Override
	public void run(){
		
		gameSide.handleClient(client);
		
	}
}
