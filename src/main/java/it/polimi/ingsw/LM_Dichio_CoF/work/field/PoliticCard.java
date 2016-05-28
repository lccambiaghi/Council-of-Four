package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

import java.util.ArrayList;

public class PoliticCard {
	
	private Color cardColor;

	/* The constructor assigns a random color to the newly created politic card */
	public PoliticCard (){
		cardColor = Color.getRandomColor(); 
	}

	/* This method creates an arrayList of cards initially held by player*/
	public ArrayList <PoliticCard> initiallyHeldPoliticCards(){
		
		ArrayList <PoliticCard> InitiallyHandCards = new ArrayList <> ();
		
		for (int i = 0; i<Constant.POLITIC_CARDS_INITIAL_NUMBER; i++){
			PoliticCard card = new PoliticCard();
			InitiallyHandCards.add(card);
		}
		return InitiallyHandCards;
	}

	public Color getCardColor() {
		return cardColor;
	}

}
