package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.work.MatchMock;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public class TestSellingObject {
	
	private TestCases testCases = new TestCases();
    private ArrayList <Player> arrayListPlayer= testCases.arrayListPlayer();
    private MatchMock match = testCases.matchMock();
    
    @Test
	public void constructor() {
		SellingObject sellingObject=new SellingObject (arrayListPlayer.get(0), "PoliticCard");
		assertEquals(sellingObject.getObject().getClass().getSimpleName(), "PoliticCard");
	}

}
