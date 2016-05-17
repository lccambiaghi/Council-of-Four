package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.Field;


public class Game {

	private Configurations config;
	private Player arrayPlayer[];
	private Field field;
	private Market market;
	private PoliticCard politicCard;
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
	public Game(Configurations config, Player[] arrayPlayer) {

		this.config = config;
		this.arrayPlayer = arrayPlayer;
		
		/*
		 *  Bisogna implementare l'accettazione di parametro "config" nel campo
		 */
		//field = new Field(this.config);
		
		/*
		 *  Restituisce un array di Integer casuali, costituente l'ordine di gioco 
		 */
		orderPlayers = randomizeOrderPlayer(arrayPlayer);
		
		startGame();
		
	}
	
	private Integer[] randomizeOrderPlayer(Player[] arrayPlayer){
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		int numberOfPlayer = arrayPlayer.length;
		
		for(int i=0; i<numberOfPlayer; i++){
			numbers.add(i);
		}
		Collections.shuffle(numbers);
	
		Integer orderInteger[] = new Integer[numbers.size()];
		orderInteger = numbers.toArray(orderInteger);
		return orderInteger;
	}	
	
	private void startGame() {	
		
		
		
		
	}

		
	
	
}
