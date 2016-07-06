package it.polimi.ingsw.LM_Dichio_CoF.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.ControlMatch;
import it.polimi.ingsw.LM_Dichio_CoF.control.InfoMatch;
import it.polimi.ingsw.LM_Dichio_CoF.control.Market;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;


public class Match {

	private ArrayList<Player> arrayListPlayer;
	private Field field;
	private Market market;

	private InfoMatch infoMatch;
	
	public Match(ArrayList<Player> arrayListPlayer) {
		
		this.arrayListPlayer = arrayListPlayer;

		Configurations config = readFileConfigurations();
		
		giveInitialPoliticCards(this.arrayListPlayer);

		giveInitialAssistants(this.arrayListPlayer);
		
		field = new Field(config, arrayListPlayer);

		market = new Market (arrayListPlayer,field);

		infoMatch = new InfoMatch(this);
		
	}

	public static Match MatchFactory(ArrayList<Player> arrayListPlayer){

		return new Match(arrayListPlayer);

	}

	private Configurations readFileConfigurations(){

		FileInputStream fileInputStream = null;
		
		try {
			
			//il salvataggio per ora è in locale, dovrà essere inviato al server quando ci sarà la connessione
			fileInputStream = new FileInputStream("./src/configurations/config");
			
			// create an ObjectInputStream for the file we created before
	         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	         return (Configurations) objectInputStream.readObject();
	         
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			// close the stream
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
		
	private void giveInitialPoliticCards(ArrayList<Player> arrayListPlayer){
		for (Player player : arrayListPlayer)
			for (int itCard = 0; itCard < Constant.POLITIC_CARDS_INITIAL_NUMBER; itCard++)
				player.addPoliticCard(new PoliticCard(Color.getRandomColor()));
	}
	
	private void giveInitialAssistants(ArrayList<Player> arrayListPlayer){
		for(int itPlayer = 0, numberAssistants = Constant.ASSISTANT_INITIAL_FIRST_PLAYER;
			itPlayer<arrayListPlayer.size(); itPlayer++, numberAssistants++)
				arrayListPlayer.get(itPlayer).setAssistant(numberAssistants);
	}

	public Field getField(){
		return field;
	}

	public ArrayList<Player> getArrayListPlayer(){return arrayListPlayer;}
	
	public InfoMatch getInfoMatch() {return infoMatch;}

	public Market getMarket() { return market; }
}
