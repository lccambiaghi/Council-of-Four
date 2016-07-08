package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestPoliticCard {

	@Test
	public void InitiallyHandCards() {
		
		ArrayList <PoliticCard> initiallyHandCards = new ArrayList <PoliticCard> ();
		for (int i=0; i<6; i++){
			PoliticCard card = new PoliticCard(Color.getRandomColor());
			initiallyHandCards.add(card);
		}
		
		for(PoliticCard card : initiallyHandCards){
			System.out.println(card.getCardColor());
		}
		
		assertEquals(6, initiallyHandCards.size());
	}
}
