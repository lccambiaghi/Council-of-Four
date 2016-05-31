package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.IOException;
import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Connection;

/* 
 * 	Questa classe rappresenta il nucleo del lato gioco, da qui si inizializzano
 *  le variabili ecc
 *  
 *  
 *  
 *  
 */


public class GameSide {

	Connection handleConnections;
	static ArrayList <Player> arrayListAvailablePlayer;
	static ArrayList <Player> arrayListPlayingPlayer;
	
	public GameSide() throws IOException{
		
		/*
		 *	Lancia Connection per gestire le connessioni
		 *	Passaggio alla parte di Connessione
		 */
		arrayListAvailablePlayer = new ArrayList <Player>();
		handleConnections = new Connection();
		
	}
	
	public static void handlePlayer(Player player){
		
		arrayListAvailablePlayer.add(player);
		
		System.out.println("I am managing a player through a thread");
		
		/* If the player is the first one not playing yet */
		if(player.equals(arrayListAvailablePlayer.get(0))){
			
		}
		
		System.out.println(arrayListAvailablePlayer.size());
		
		
	}
	
	
}
