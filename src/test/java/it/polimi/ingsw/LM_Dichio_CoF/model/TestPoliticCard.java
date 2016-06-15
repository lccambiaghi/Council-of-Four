package it.polimi.ingsw.LM_Dichio_CoF.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;

public class TestPoliticCard {

	@Test
	public void InitiallyHandCards() {
		
		ArrayList <PoliticCard> initiallyHandCards = new ArrayList <PoliticCard> ();
		for (int i=0; i<6; i++){
			PoliticCard card = new PoliticCard();
			initiallyHandCards.add(card);
		}
		
		for(PoliticCard card : initiallyHandCards){
			System.out.println(card.getCardColor());
		}
		
		assertEquals(6, initiallyHandCards.size());
	}
}
