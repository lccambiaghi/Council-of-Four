package it.polimi.ingsw.LM_Dichio_CoF.connection;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.work.GameSide;

public class Connection extends Thread{

	char typeOfConnection;
	String IP;
	
	//DI PROVA PER IL TEST
	Scanner input = new Scanner(System.in);
	
	public Connection(){
		
	/*	
	 * 	ATTENDE connessione con un client (RMI o SOCKET)
	 * 
	 * 
	 *	Connessione stabilita con un client
	 *
	 * 	MEMORIZZA L'IP DEL CLIENT NELLA VARIABILE "IP"
	 * 	MEMORIZZA ANCHE IL TIPO DI CONNESSIONE
	 */
		
			//SEMPRE DI PROVA PER IL TEST
			String s = input.nextLine(); 
			typeOfConnection=s.charAt(0);
			
			
			
			IP="127.0.0.1";
			
			
	
	/*
	 * 	CREA UN THREAD PER GESTIRE LA CONNESSIONE
	 */
		
			start();
	
	}
	
	public void run(){
		
		Player player = new Player(typeOfConnection,IP); 
		GameSide.handlePlayer(player);
		
	}
}

/*
 * 	Salvataggio indirizzo IP come parametro nella classe Giocatore
 * 
 * 	Passa al package work, classe GameSide 
 * 
 * 
 */

//Attesa scelta CLI o GUI

			// Accettazione richiesta -> salvataggio scelta parametro nella classe Giocatore
			
			// Attesa username 
			
			// Accettazione username -> salvataggio parametro nella classe Giocatore