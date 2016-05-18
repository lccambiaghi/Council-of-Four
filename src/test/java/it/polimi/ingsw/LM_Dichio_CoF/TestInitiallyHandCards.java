package it.polimi.ingsw.LM_Dichio_CoF;

import static org.junit.Assert.*;

import java.util.ArrayList;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.PoliticCard;
import org.junit.Test;

public class TestInitiallyHandCards {

	@Test
	public void test() {
		
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
