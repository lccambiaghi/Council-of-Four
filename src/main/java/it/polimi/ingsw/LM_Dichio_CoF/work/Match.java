package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.Field;


public class Match {

	private Configurations config;
	private ArrayList<Player> arrayListPlayer;
	private int numberPlayers;
	
	private Field field;
	private Market market;
	private Integer orderPlayers[];
	
	
	/*
	 *  Costruttore della partita.
	 *  
	 *  I parametri d'ingresso sono le configurazioni e i giocatori presenti
	 *  
	 *  Inizializza il campo, passandogli
	 *  come parametro le configurazioni
	 *  Inizializza l'ordine dei giocatori
	 *  
	 *  Lancia il gioco tramite il metodo startGame()
	 *  
	 */
	public Match(Configurations config, ArrayList<Player> arrayListPlayer) {

		this.config = config;
		this.arrayListPlayer = arrayListPlayer;
		
		numberPlayers=arrayListPlayer.size();
		
		Collections.shuffle(arrayListPlayer);
		
		
		/*
		 *  Bisogna implementare l'accettazione di parametro "config" nel campo
		 */
		//field = new Field(config, numberPlayers, orderPlayers);
		
		startGame();
		
	}
	
	
	
	
	
	
	private void startGame() {	
		
		
		
		
	}

		
	
	
}
