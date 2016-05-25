package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.net.Socket;

import it.polimi.ingsw.LM_Dichio_CoF.work.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public class SocketConnectionWithClient extends Thread {

	Socket clientSocket;
	Player player;
	
	SocketConnectionWithClient(Socket clientSocket){
		
		this.clientSocket=clientSocket;
		
		player = new Player("s");
		
		player.setPlayerSocket(clientSocket);
		
		start();
		
	}
	
	@Override
	public void run(){
		
		GameSide.handlePlayer(player);
		
	}
}
