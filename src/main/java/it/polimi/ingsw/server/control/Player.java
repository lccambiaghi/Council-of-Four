package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.connection.Broker;
import it.polimi.ingsw.server.connection.ConnectionWithPlayerInterface;
import it.polimi.ingsw.server.model.PoliticCard;
import it.polimi.ingsw.server.model.City;
import it.polimi.ingsw.server.model.PermitCard;

import java.util.ArrayList;


public class Player{
	
	private String nickname;

	private char typeOfConnection;
	
	private ConnectionWithPlayerInterface connectionWithPlayer;
	
	private Broker broker;
	
	// variables of the game
	private boolean connected;
	
	private int richness;
	private int assistant;
	private int victory;
	
	private ArrayList <PoliticCard> arrayListPoliticCard= new ArrayList<>();

	private ArrayList <PermitCard> arrayListPermitCard = new ArrayList<>();
	private ArrayList <PermitCard> arrayListUsedPermitCard = new ArrayList<>();

	private int mainActionsLeft;
	private boolean quickActionDone;
	
	private ArrayList <City>  arrayListEmporiumBuilt = new ArrayList<>();

	/* The constructor assigns to the player the type of connection */
	public Player(char typeOfConnection){
		this.typeOfConnection=typeOfConnection;
	}
	
	public Broker getBroker() {
		return broker;
	}
	
	public void setBroker(Broker broker) {
		this.broker=broker;
	}

	public void addVictory(int increment){victory+=increment;}

	public String getNickname() {return nickname;}
	public void setNickname(String nickname) {this.nickname = nickname;}
	
	public boolean isConnected() {return connected;}
	public void setConnected(boolean connected) {this.connected = connected;}
	
	public int getRichness() {return richness;}
	public void setRichness(int richness) {this.richness = richness;}
	public void addRichness(int increment){
		if (this.getRichness()+increment>Constant.RICHNESS_MAX){
			this.setRichness(Constant.RICHNESS_MAX);
		}
		else
			this.setRichness(this.getRichness()+increment);
	}
	public void decrementRichness(int decrement) {
		this.setRichness(this.getRichness()-decrement);
	}
	
	public int getAssistant() {	return assistant;}
	public void setAssistant(int assistant) {this.assistant = assistant;}
	public void addAssistant(int increment)	{assistant +=increment;}
	public void decrementAssistant(int decrement)	{assistant -=decrement;}
	
	public ArrayList<PoliticCard> getArrayListPoliticCard() {return arrayListPoliticCard;}
	public void addPoliticCard(PoliticCard politicCard) {arrayListPoliticCard.add(politicCard);}
	public void discardPoliticCard(PoliticCard politicCard){arrayListPoliticCard.remove(politicCard);}

	public ArrayList<PermitCard> getArrayListPermitCard(){return arrayListPermitCard;}
	public ArrayList<PermitCard> getArrayListUsedPermitCard(){return arrayListUsedPermitCard;}
	public void acquirePermitCard(PermitCard permitCard){arrayListPermitCard.add(permitCard);}
	public void usePermitCard (PermitCard permitCard){
		int i=arrayListPermitCard.indexOf(permitCard);
		arrayListUsedPermitCard.add(arrayListPermitCard.remove(i));
	}
	
	public ArrayList<City> getArrayListEmporiumBuilt(){return arrayListEmporiumBuilt;}
	public void addCityEmporiumBuilt (City city){arrayListEmporiumBuilt.add(city);}

	public int getMainActionsLeft() {return mainActionsLeft;}
	public void setMainActionsLeft(int mainActionsLeft) {this.mainActionsLeft=mainActionsLeft;}
	public void addMainActionLeft(int increment) {mainActionsLeft += increment;}
	public void decrementMainActionLeft(int decrement) {mainActionsLeft -= decrement;}

	public boolean isQuickActionDone() {return quickActionDone;}
	public void setQuickActionDone(boolean quickActionDone) {this.quickActionDone = quickActionDone;}


	public int getVictory() {
		return victory;
	}
	public void setVictory(int victory) {
		this.victory = victory;
	}

}
