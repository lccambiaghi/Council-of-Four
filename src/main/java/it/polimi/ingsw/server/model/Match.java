package it.polimi.ingsw.server.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

import it.polimi.ingsw.server.control.Constant;
import it.polimi.ingsw.server.control.InfoMatch;
import it.polimi.ingsw.server.control.MarketHandler;
import it.polimi.ingsw.server.control.Player;


public class Match {

	private ArrayList<Player> arrayListPlayer;

	private Field field;

	private MarketHandler market;

	private InfoMatch infoMatch;
	
	public Match(ArrayList<Player> arrayListPlayer) {
		
		this.arrayListPlayer = arrayListPlayer;

		Configurations config = readFileConfigurations();
		
		giveInitialPoliticCards();

		giveInitialAssistants();
		
		giveInitialRichness();
		
		field = new Field(config, arrayListPlayer);

		market = new MarketHandler (arrayListPlayer, this);

		infoMatch = new InfoMatch(this);
		
	}

	public static Match MatchFactory(ArrayList<Player> arrayListPlayer){

		return new Match(arrayListPlayer);

	}

	private Configurations readFileConfigurations(){

		FileInputStream fileInputStream = null;
		
		try {
			
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
		
	private void giveInitialPoliticCards(){
		for (Player player : arrayListPlayer)
			for (int itCard = 0; itCard < Constant.POLITIC_CARDS_INITIAL_NUMBER; itCard++)
				player.addPoliticCard(new PoliticCard(Color.getRandomColor()));
	}
	
	private void giveInitialAssistants(){
		for(int itPlayer = 0, numberAssistants = Constant.ASSISTANT_INITIAL_FIRST_PLAYER;
			itPlayer<arrayListPlayer.size(); itPlayer++, numberAssistants++)
				arrayListPlayer.get(itPlayer).setAssistant(numberAssistants);
	}
	
	private void giveInitialRichness(){
		for(int itPlayer = 0, numberRichness = Constant.RICHNESS_INITIAL_FIRST_PLAYER;
				itPlayer<arrayListPlayer.size(); itPlayer++, numberRichness++)
					arrayListPlayer.get(itPlayer).setRichness(numberRichness);
	}

	public Field getField(){
		return field;
	}

	public ArrayList<Player> getArrayListPlayer(){return arrayListPlayer;}
	
	public InfoMatch getInfoMatch() {return infoMatch;}

	public MarketHandler getMarket() { return market; }

}
