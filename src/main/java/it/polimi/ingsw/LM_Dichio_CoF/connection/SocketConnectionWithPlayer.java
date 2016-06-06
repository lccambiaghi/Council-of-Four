package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.net.Socket;

public class SocketConnectionWithPlayer extends Thread {
	
	GameSide gameSide;
	Player player;
	
	SocketConnectionWithPlayer(Socket clientSocket, GameSide gameSide){
		
		this.gameSide=gameSide;
		
		player = new Player("s".charAt(0));
		player.setPlayerSocket(clientSocket);
		player.openSocketStream();
		
		start();
		
	}
	
	@Override
	public void run(){
		
		gameSide.handlePlayer(player);
		
	}
}
