package it.polimi.ingsw.LM_Dichio_CoF.work;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class TestMarket {

	private TestCases testCases = new TestCases();
    private ArrayList <Player> arrayListPlayer= testCases.arrayListPlayer();
    private MatchMock match = testCases.matchMock();
	private Market market= new Market (arrayListPlayer);
	
	@Test
	public void startMarketAssistant() {
		market.startMarket();
		
		assertEquals(1, market.arrayListSellingObjects.size());
		assertEquals("Integer", market.arrayListSellingObjects.get(0).getObject().getClass().getSimpleName());
		assertEquals(4, market.arrayListSellingObjects.get(0).getPrice());
		assertEquals(0, arrayListPlayer.get(0).getAssistant());
		
	}
	@Test
	public void startMarketPermitCard() {
		market.startMarket();
		
		assertEquals(1, market.arrayListSellingObjects.size());
		assertEquals("PermitCard", market.arrayListSellingObjects.get(0).getObject().getClass().getSimpleName());
		assertEquals(4, market.arrayListSellingObjects.get(0).getPrice());
		assertEquals(0, arrayListPlayer.get(0).getArrayListPermitCard().size());
		
	}
	@Test
	public void startMarketPoliticCard() {
		market.startMarket();
		
		assertEquals(1, market.arrayListSellingObjects.size());
		assertEquals("PoliticCard", market.arrayListSellingObjects.get(0).getObject().getClass().getSimpleName());
		assertEquals(4, market.arrayListSellingObjects.get(0).getPrice());
		assertEquals(5, arrayListPlayer.get(0).getArrayListPoliticCard().size());
		
	}

}
