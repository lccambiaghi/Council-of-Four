package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.connection.Broker;
import it.polimi.ingsw.server.model.City;
import it.polimi.ingsw.server.model.PermitCard;
import it.polimi.ingsw.server.model.PoliticCard;
import it.polimi.ingsw.utils.Constant;

import java.util.ArrayList;

/**
 * This class represents the client connected to the server and offers both the methods of the connection
 * and the ones of the match.
 */
public class Player{
	
	
	private ArrayList <PoliticCard> arrayListPoliticCard= new ArrayList<>();
	private ArrayList <PermitCard> arrayListPermitCard = new ArrayList<>();
	private ArrayList <PermitCard> arrayListUsedPermitCard = new ArrayList<>();
	private ArrayList <City>  arrayListEmporiumBuilt = new ArrayList<>();
	
	
	
	// ************ CONNECTION VARIABLES ************ //

	private String nickname;
	private Broker broker;
	
	private boolean connected = true;
	private boolean logged = false;
	private boolean messageDisconnectedSent = false;
	
	// ********************************************** //

	
	
	// ************ MATCH VARIABLES ************ //
	
	private int richness;
	private int assistant;
	private int victory;

	private int mainActionsLeft;
	private boolean quickActionDone;
	
	private boolean lastTurnDone;

	// ***************************************** //
	
	
	
	// ************ CONNECTION METHODS ************ //

	public String getNickname() {return nickname;}
	public void setNickname(String nickname) {this.nickname = nickname;}
	
	public Broker getBroker() {return broker;}
	public void setBroker(Broker broker) {this.broker=broker;}
	
	public boolean isConnected() {return connected;}
	public void setConnected(boolean connected) {this.connected = connected;}
	
	public boolean isLogged() {return logged;}
	public void setLogged(boolean logged) {this.logged = logged;}
	
	public boolean isMessageDisconnectedSent() {return messageDisconnectedSent;}
	public void setMessageDisconnectedSent(boolean messageDisconnectedSent) {this.messageDisconnectedSent = messageDisconnectedSent;	}

	// ******************************************** //
	
	
	
	// ************ MATCH METHODS ************ //
	
	// VICTORY 
	
	public int getVictory() {return victory;}
	
	/**
	 * @param increment to be applied
	 */
	public void addVictory(int increment){victory+=increment;}
	
	
	// RICHNESS
	
	public int getRichness() {return richness;}
	
	public void setRichness(int richness) {this.richness = richness;}
	
	/**
	 * @param increment to be applied
	 */
	public void addRichness(int increment){
		if (this.getRichness()+increment>Constant.RICHNESS_MAX){
			this.setRichness(Constant.RICHNESS_MAX);
		}
		else
			this.setRichness(this.getRichness()+increment);
	}
	
	/**
	 * @param decrement to be applied (decrement>0)
	 */
	public void decrementRichness(int decrement) {
		this.setRichness(this.getRichness()-decrement);
	}
	
	
	// ASSISTANTS
	
	public int getAssistant() {	return assistant;}
	public void setAssistant(int assistant) {this.assistant = assistant;}
	
	/**
	 * @param increment to be applied
	 */
	public void addAssistant(int increment)	{assistant +=increment;}
	
	/**
	 * @param decrement to be applied (decrement>0)
	 */
	public void decrementAssistant(int decrement)	{assistant -=decrement;}

	
	// POLITICS CARDS
	
	public ArrayList<PoliticCard> getArrayListPoliticCard() {return arrayListPoliticCard;}
	
	/**
	 * @param politicCard to be applied
	 */
	public void addPoliticCard(PoliticCard politicCard) {arrayListPoliticCard.add(politicCard);}
	
	/**
	 * @param politicCard used
	 */
	public void discardPoliticCard(PoliticCard politicCard){arrayListPoliticCard.remove(politicCard);}

	
	// PERMIT CARDS
	
	public ArrayList<PermitCard> getArrayListPermitCard(){return arrayListPermitCard;}
	public ArrayList<PermitCard> getArrayListUsedPermitCard(){return arrayListUsedPermitCard;}
	
	/**
	 * @param permitCard to be applied
	 */
	public void acquirePermitCard(PermitCard permitCard){arrayListPermitCard.add(permitCard);}
	
	/**
	 * @param permitCard to be used
	 */
	public void usePermitCard (PermitCard permitCard){
		int i=arrayListPermitCard.indexOf(permitCard);
		arrayListUsedPermitCard.add(arrayListPermitCard.remove(i));
	}
	
	
	// EMPORIUMS BUILT
	
	public ArrayList<City> getArrayListEmporiumBuilt(){return arrayListEmporiumBuilt;}
	
	/**
	 * @param city where an emporium has been built
	 */
	public void addCityEmporiumBuilt (City city){arrayListEmporiumBuilt.add(city);}

	
	// ACTIONS
	
	public int getMainActionsLeft() {return mainActionsLeft;}
	public void setMainActionsLeft(int mainActionsLeft) {this.mainActionsLeft=mainActionsLeft;}
	
	/**
	 * @param increment to be applied
	 */
	public void addMainActionLeft(int increment) {mainActionsLeft += increment;}
	
	/**
	 * @param decrement to be applied (decrement>0)
	 */
	public void decrementMainActionLeft(int decrement) {mainActionsLeft -= decrement;}

	public boolean isQuickActionDone() {return quickActionDone;}
	public void setQuickActionDone(boolean quickActionDone) {this.quickActionDone = quickActionDone;}

	
	// TURN
	
	public boolean isLastTurnDone() {return lastTurnDone;}
	public void setLastTurnDone(boolean lastTurnDone) {this.lastTurnDone = lastTurnDone;}
	

	// RESET MATCH STATUS
	
	public void resetMatchStatus(){
		
		arrayListPoliticCard= new ArrayList<>();
		arrayListPermitCard = new ArrayList<>();
		arrayListUsedPermitCard = new ArrayList<>();
		arrayListEmporiumBuilt = new ArrayList<>();
		richness = 0;
		assistant = 0;
		victory = 0;
		lastTurnDone=false;
		
	}
	
	// *************************************** //
	
}
