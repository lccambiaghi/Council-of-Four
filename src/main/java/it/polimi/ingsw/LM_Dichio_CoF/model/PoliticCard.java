package it.polimi.ingsw.LM_Dichio_CoF.model;

public class PoliticCard {
	
	private Color cardColor;

	/* The constructor assigns a random color to the newly created politic card */
	public PoliticCard (){
		cardColor = Color.getRandomColor(); 
	}

	public Color getCardColor() {
		return cardColor;
	}

}
