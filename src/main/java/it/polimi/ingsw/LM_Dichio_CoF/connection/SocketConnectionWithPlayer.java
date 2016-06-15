package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.net.Socket;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;

public class SocketConnectionWithPlayer extends Thread {
	
	GameSide gameSide;
	Player player;
	
	public SocketConnectionWithPlayer(Socket clientSocket, GameSide gameSide){
		
		this.gameSide=gameSide;
		
		player = new Player('s');
		player.setPlayerSocket(clientSocket);
		player.openSocketStream();
		
		start();
		
	}
	
	@Override
	public void run(){
		
		gameSide.handlePlayer(player);
		
	}
}
