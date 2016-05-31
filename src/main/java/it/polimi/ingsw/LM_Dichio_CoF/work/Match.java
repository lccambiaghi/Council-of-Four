package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.Field;


public class Match {

	private Configurations config;
	private ArrayList<Player> arrayListPlayer;
	private int numberPlayers;
	
	private Player playerTurn;
	
	private Field field;
	private Market market;
	
	
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
	public Match(ArrayList<Player> arrayListPlayer) {
		
		this.arrayListPlayer = arrayListPlayer;
		this.numberPlayers=arrayListPlayer.size();
		
		Collections.shuffle(arrayListPlayer);
		
		readFileConfigurations();
		
		giveInitialCards();
		
		giveInitialAssistants();
		
		//this.field = new Field(config, arrayListPlayer);
		
		startGame();
		
	}
	
	
	private void startGame() {	
		
		int turn=0;
		playerTurn = arrayListPlayer.get(turn);
		
		/* Draw a card */
		playerTurn.addPoliticCard(new PoliticCard());
		
		
		
	}

	private void readFileConfigurations(){
		
		FileInputStream fileInputStream;
		
		try {
			
			//il salvataggio per ora è in locale, dovrà essere inviato al server quando ci sarà la connessione
			fileInputStream = new FileInputStream("./src/configurations/config");
			
			// create an ObjectInputStream for the file we created before
	         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	         this.config = (Configurations) objectInputStream.readObject();
	         
	         fileInputStream.close();
	         
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	private void giveInitialCards(){	
		for(int itPlayer=0; itPlayer<arrayListPlayer.size(); itPlayer++){
			for(int itCard=0; itCard<Constant.POLITIC_CARDS_INITIAL_NUMBER; itCard++){
				arrayListPlayer.get(itPlayer).addPoliticCard(new PoliticCard());
			}
		}
	}
	
	private void giveInitialAssistants(){
		for(int itPlayer=0, numberAssistants=1; itPlayer<arrayListPlayer.size(); itPlayer++, numberAssistants++){
			arrayListPlayer.get(itPlayer).setAssistant(numberAssistants);
		}
	}
}
