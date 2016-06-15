package it.polimi.ingsw.LM_Dichio_CoF.connection;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIPlayerSideInterface;

public class RMIConnectionWithPlayer extends Thread{

	GameSide gameSide;
	Player player;
	
	public RMIConnectionWithPlayer(RMIPlayerSideInterface rmiPlayerSide, GameSide gameSide){
	
		this.gameSide=gameSide;
		
		System.out.println("A new player has connected");
		
		player = new Player('r');
		player.setRmiPlayerSide(rmiPlayerSide);
		
		start();
		
	}
	
	public void run(){
		
		gameSide.handlePlayer(player);
		
	}
	
	
}
