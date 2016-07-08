package it.polimi.ingsw.server.model;

public class PoliticCard {
	
	private Color cardColor;

	/* The constructor assigns a random color to the newly created politic card */
	public PoliticCard (Color cardColor){
		this.cardColor=cardColor; 
	}

	public Color getCardColor() {
		return cardColor;
	}

}
