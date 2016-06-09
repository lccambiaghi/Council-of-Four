package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIGameSideInterface;

public class PlayMatch {

	PlayerSide playerSide;
	RMIGameSideInterface rmiGameSide;
	
	String message;
	boolean inMatch;
	
	public PlayMatch(PlayerSide playerSide, RMIGameSideInterface rmiGameSide){
		this.playerSide=playerSide;
		this.rmiGameSide=rmiGameSide;
	}
	
	public void startPlayMatch(){
		
		inMatch=true;
		/*
		 * If SOCKET is chosen
		 */
		if(playerSide.getTypeOfConnection()=='s'){
			while(inMatch){
				message = playerSide.receiveStringFS();
				switch(message){
				
				case	"SOCKETwaitTurn":
					waitTurn();
					break;
				
				case	"SOCKETplay":
					play();
					break;
				}
			}
		
		}
	
	}
	
	public void waitTurn(){
		playerSide.printString("It's not your turn yet, wait...");
	}
	
	public void play(){
		playerSide.printString("IT'S YOUR TURN!");
	}
	
	
}
