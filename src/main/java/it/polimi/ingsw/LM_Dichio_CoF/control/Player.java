package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.connection.ConnectionWithPlayerInterface;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.PermitCard;

import java.util.ArrayList;


public class Player{
	
	private String nickname;
	
	private char typeOfConnection;
	
	private ConnectionWithPlayerInterface connectionWithPlayer;

	// variables of the game
	private boolean playing;
	
	private int richness;
	private int assistant;

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
	
	public ConnectionWithPlayerInterface getConnectionWithPlayer() {
		return connectionWithPlayer;
	}

	public void setConnectionWithPlayer(ConnectionWithPlayerInterface connectionWithPlayer) {
		this.connectionWithPlayer = connectionWithPlayer;
	}
	
	public String getNickname() {return nickname;}
	public void setNickname(String nickname) {this.nickname = nickname;}
	
	public char getTypeOfConnection() {return typeOfConnection;}
	public void setTypeOfConnection(char typeOfConnection) {this.typeOfConnection = typeOfConnection;}
	
	public boolean isPlaying() {return playing;}
	public void setPlaying(boolean playing) {this.playing = playing;}
	
	public int getRichness() {return richness;}
	public void setRichness(int richness) {this.richness = richness;}

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
	public void setMainActionsLeft(int mainActionsLeft) {this.mainActionsLeft = mainActionsLeft;}

	public boolean isQuickActionDone() {return quickActionDone;}
	public void setQuickActionDone(boolean quickActionDone) {this.quickActionDone = quickActionDone;}
	
	
}
