package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

import java.util.ArrayList;

public class PoliticCard {
	
	Color cardColor;
	
	public PoliticCard (){
		cardColor = Color.getRandomColor(); 
	}
	
	public Color getCardColor() {
		return cardColor;
	}

	public ArrayList <PoliticCard> InitiallyHandCards(){
		
		ArrayList <PoliticCard> InitiallyHandCards = new ArrayList <PoliticCard> ();
		
		for (int i = 0; i<Constant.POLITIC_CARDS_INITIAL_NUMBER; i++){
			PoliticCard card = new PoliticCard();
			InitiallyHandCards.add(card);
		}
		return InitiallyHandCards;
	}
}
