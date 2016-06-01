package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.ArrayList;


public class Player extends Client{
	
	// variables of the game
	private int richness;
	private int assistant;
	private ArrayList <PoliticCard> arrayListPoliticCard= new ArrayList<>();
	
	public Player(){};
	
	public int getRichness() {return richness;}
	public void setRichness(int richness) {this.richness = richness;}

	public int getAssistant() {	return assistant;}
	public void setAssistant(int assistant) {this.assistant = assistant;}
	public void addAssistant(int increment)	{this.assistant +=increment;}

	public ArrayList<PoliticCard> getArrayListPoliticCard() {return arrayListPoliticCard;}
	public void addPoliticCard(PoliticCard politicCard) {this.arrayListPoliticCard.add(politicCard);}
	
	
}
