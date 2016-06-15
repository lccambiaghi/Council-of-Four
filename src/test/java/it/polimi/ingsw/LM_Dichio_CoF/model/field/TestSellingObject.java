package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.InfoMatch;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.MatchMock;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.SellingObject;

public class TestSellingObject {
	
	private TestCases testCases = new TestCases();
    private ArrayList <Player> arrayListPlayer= testCases.arrayListPlayer();
    private MatchMock match = testCases.matchMock();
    
    @Test
	public void constructorPolitic() {

		//ByteArrayInputStream in=new ByteArrayInputStream("1\n5\n".getBytes());
		//System.setIn(in);

		assertEquals(Constant.POLITIC_CARDS_INITIAL_NUMBER, arrayListPlayer.get(0).getArrayListPoliticCard().size());

		SellingObject sellingObject=new SellingObject (arrayListPlayer.get(0), "PoliticCard");

		assertEquals("PoliticCard",sellingObject.getObject().getClass().getSimpleName());
		assertEquals(5,sellingObject.getPrice());

		assertEquals(Constant.POLITIC_CARDS_INITIAL_NUMBER-1, arrayListPlayer.get(0).getArrayListPoliticCard().size());
	    
		InfoMatch info = new InfoMatch(match);

		//System.setIn(System.in);
	}
    @Ignore
    public void constructorAssistant() {

		//ByteArrayInputStream in=new ByteArrayInputStream("1\n5\n".getBytes());
		//System.setIn(in);

    	assertEquals(Constant.ASSISTANT_INITIAL_FIRST_PLAYER, arrayListPlayer.get(0).getAssistant());
    	
		SellingObject sellingObject=new SellingObject (arrayListPlayer.get(0), "Assistants");

		assertEquals("Integer",sellingObject.getObject().getClass().getSimpleName());
		assertEquals(5,sellingObject.getPrice());

		assertEquals(Constant.ASSISTANT_INITIAL_FIRST_PLAYER-1, arrayListPlayer.get(0).getAssistant());

		//System.setIn(System.in);
	}
    @Ignore
    public void constructorPermitCard() {

		//ByteArrayInputStream in=new ByteArrayInputStream("1\n5\n".getBytes());
		//System.setIn(in);

    	assertEquals(1, arrayListPlayer.get(0).getArrayListPermitCard().size());
    	
		SellingObject sellingObject=new SellingObject (arrayListPlayer.get(0), "PermitCard");

		assertEquals("PermitCard",sellingObject.getObject().getClass().getSimpleName());
		assertEquals(5,sellingObject.getPrice());

		assertEquals(0, arrayListPlayer.get(0).getArrayListPermitCard().size());

		//System.setIn(System.in);
	}

}
