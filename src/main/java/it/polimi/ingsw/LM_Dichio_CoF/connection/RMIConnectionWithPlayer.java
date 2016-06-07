package it.polimi.ingsw.LM_Dichio_CoF.connection;

import it.polimi.ingsw.LM_Dichio_CoF.work.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIPlayerSideInterface;

public class RMIConnectionWithPlayer extends Thread{

	GameSide gameSide;
	Player player;
	
	public RMIConnectionWithPlayer(RMIPlayerSideInterface playerSide, GameSide gameSide){
	
		this.gameSide=gameSide;
		
		player = new Player('r');
		player.setPlayerSide(playerSide);
		
		start();
		
	}
	
	public void run(){
		
		gameSide.handlePlayer(player);
		
	}
	
	
}