package it.polimi.ingsw.server.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

import it.polimi.ingsw.server.control.InfoMatch;
import it.polimi.ingsw.server.control.ControlMarket;
import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.utils.Constant;

/**
 * It is the body of a game. It creates the field based on the configurations that the first player
 * chose, the market and the info of the match. In addition it sets the initially attributes of the players
 * as for example the initial politics cards, number of assistants and the positions on the routes.
 */

public class Match {

	private ArrayList<Player> arrayListPlayer;

	private Field field;

	private ControlMarket market;

	private InfoMatch infoMatch;
	
	/**
	 * It assigns the initially attributes of the players, that it receives as parameter.
	 * @param arrayListPlayer
	 */
	
	public Match(ArrayList<Player> arrayListPlayer) {
		
		this.arrayListPlayer = arrayListPlayer;

		Configurations config = readFileConfigurations();
		
		giveInitialPoliticCards();

		giveInitialAssistants();
		
		giveInitialRichness();
		
		field = new Field(config, arrayListPlayer);

		market = new ControlMarket (arrayListPlayer, this);

		infoMatch = new InfoMatch(this);
		
	}

	/**
	 * This method read the configurations that are stored into a .txt file, and then it returns the 
	 * Configurations read.
	 * @return
	 */
	private Configurations readFileConfigurations(){

		FileInputStream fileInputStream = null;
		
		try {
			
			fileInputStream = new FileInputStream(Constant.PATH_FILE_CONFIGURATIONS);
			
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
		
	/**
	 * For each player of the match it assigns 6 Politic Cards with random colors.
	 */
	private void giveInitialPoliticCards(){
		for (Player player : arrayListPlayer)
			for (int itCard = 0; itCard < Constant.POLITIC_CARDS_INITIAL_NUMBER; itCard++)
				player.addPoliticCard(new PoliticCard(Color.getRandomColor()));
	}
	
	/**
	 * For each player of the match it assigns the number of assistants. The first player of the turn
	 * will have only one assistant, the second two, and so on.
	 */
	
	private void giveInitialAssistants(){
		for(int itPlayer = 0, numberAssistants = Constant.ASSISTANT_INITIAL_FIRST_PLAYER;
			itPlayer<arrayListPlayer.size(); itPlayer++, numberAssistants++)
				arrayListPlayer.get(itPlayer).setAssistant(numberAssistants);
	}
	
	/**
	 * For each player of the match it assigns the richness points. The first player of the turn
	 * will have ten coins, the second eleven, and so on.
	 */	
	
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

	public ControlMarket getMarket() { return market; }

}
