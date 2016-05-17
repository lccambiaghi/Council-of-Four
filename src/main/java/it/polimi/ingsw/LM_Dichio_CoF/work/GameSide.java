package it.polimi.ingsw.LM_Dichio_CoF.work;

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

	private int id=0;
	Connection handleConnection;
	
	public GameSide(){
		
		/*
		 *	Lancia Connection per gestire le connessioni
		 *	Passaggio alla parte di Connessione
		 *	
		 */
		while(true){
			handleConnection = new Connection();
		}
		
	}
	
	public static void handlePlayer(Player player){
		
		//DI PROVA PER IL TEST
		for(int i=0; i<5; i++){
			printIt(player);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0; i<5; i++){
			printIt(player);
		}
		
	}
	
	//DI PROVA PER IL TEST
	public static synchronized void printIt(Player player){
		System.out.println("carattere: "+ player.getTypeOfConnection());
	}
	
	//Attesa scelta CLI o GUI

	// Accettazione richiesta -> salvataggio scelta parametro nella classe Giocatore
	
	// Attesa username 
	
	// Accettazione username -> salvataggio parametro nella classe Giocatore
				
			// Verifica partite esistenti
			
			// Crea partita o aggiunge giocatore a partita
	
	
}
