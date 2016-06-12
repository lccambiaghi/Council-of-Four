package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;
import org.junit.Ignore;
import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.work.MatchMock;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public class TestSellingObject {
	
	private TestCases testCases = new TestCases();
    private ArrayList <Player> arrayListPlayer= testCases.arrayListPlayer();
    private MatchMock match = testCases.matchMock();

    @Ignore
	public void constructor() {

		//ByteArrayInputStream in=new ByteArrayInputStream("1\n5\n".getBytes());
		//System.setIn(in);

		assertEquals(Constant.POLITIC_CARDS_INITIAL_NUMBER, arrayListPlayer.get(0).getArrayListPoliticCard().size());

		SellingObject sellingObject=new SellingObject (arrayListPlayer.get(0), "PoliticCard");

		assertEquals("PoliticCard",sellingObject.getObject().getClass().getSimpleName());
		assertEquals(5,sellingObject.getPrice());

		assertEquals(Constant.POLITIC_CARDS_INITIAL_NUMBER-1, arrayListPlayer.get(0).getArrayListPoliticCard().size());

		System.setIn(System.in);
	}

}
