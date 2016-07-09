package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestPoliticCard {

	ArrayList <PoliticCard> initiallyHandCards;
	
	@Before
	public void init(){
		initiallyHandCards = new ArrayList <> ();
	}
	
	@Test
	public void InitiallyHandCards() {
		
		for (int i=0; i<6; i++){
			PoliticCard card = new PoliticCard(Color.getRandomColor());
			assertNotNull(card);
			initiallyHandCards.add(card);
		}
		
		assertEquals(6, initiallyHandCards.size());
	}
}
