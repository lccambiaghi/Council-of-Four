package it.polimi.ingsw.server.model;

/**
 * This class represents the Politic Cards of the match. The cards have a color so we generate it randomly
 * and the we call the constructor of Politic Card.
 */

public class PoliticCard {
	
	private Color cardColor;

	/**
	 * The constructor assigns a random color to the newly created politic card
	 * @param cardColor
	 */
	public PoliticCard (Color cardColor){
		this.cardColor=cardColor; 
	}

	public Color getCardColor() {
		return cardColor;
	}

}
