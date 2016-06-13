package it.polimi.ingsw.LM_Dichio_CoF.work;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.Balcony;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Councillor;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class TestMatchMock{

	private TestCases testCases = new TestCases();
    private ArrayList <Player> arrayListPlayer= testCases.arrayListPlayer();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup(){
		System.setOut(new PrintStream(outContent));
    }

	@After
	public void cleanup(){
		System.setOut(System.out);
	}

	@Test
	public void initialPoliticCardAndAssistants() {
		new MatchMock(arrayListPlayer);
		for (Player player : arrayListPlayer)
		    assertEquals(Constant.POLITIC_CARDS_INITIAL_NUMBER, player.getArrayListPoliticCard().size());
		for (int i = 0; i < arrayListPlayer.size(); i++)
			assertEquals(Constant.ASSISTANT_INITIAL_FIRST_PLAYER +i, arrayListPlayer.get(i).getAssistant());
	}

    @Test
    public void inputNumber() {

		ByteArrayInputStream in;

		in=new ByteArrayInputStream("1\n2\n".getBytes());
        System.setIn(in);
		MatchMock match = new MatchMock(arrayListPlayer);
        assertEquals(1, match.inputNumber(1,2));
		assertEquals(2, match.inputNumber(1,2));

		in=new ByteArrayInputStream("a\nb\n2\n".getBytes());
		System.setIn(in);
		match = new MatchMock(arrayListPlayer);
		int output= match.inputNumber(1,2);
		assertEquals("Insert an integer value!\nInsert an integer value!\n", outContent.toString());
		assertEquals(2,output);
		outContent.reset();

		in=new ByteArrayInputStream("4\n0\n3\n".getBytes());
		System.setIn(in);
		match = new MatchMock(arrayListPlayer);
		output= match.inputNumber(1,3);
		assertEquals("Insert a value between 1 and 3\n" +
					"Insert a value between 1 and 3\n", outContent.toString());
		assertEquals(3,output);

		//cleanup input stream
        System.setIn(System.in);

    }

	/**
	 * Ask if quick action first: 1.Yes 2. No
	 * Ask which main action: 1. Election 2.PermitCard 3.Emporium 4.With King
	 * (Ask if quick action: 1.Yes 2. No
	 *  Ask which quick action: 1. Assistant 2. ChangePermit 3. Election 4. AdditionalMain)
	 */

	@Test
	public void electCouncillor(){

		/** ask which balcony : 1=Sea
		ask which color : 1=Whatever
		*/
		ByteArrayInputStream in=new ByteArrayInputStream("2\n1\n1\n1\n2\n".getBytes());
		System.setIn(in);

		MatchMock match = new MatchMock(arrayListPlayer);

		ArrayList<Councillor> oldCouncillors=new ArrayList<>(match.getField().getBalconyFromIndex(0).getArrayListCouncillor());

		match.turn(arrayListPlayer.get(0));

		ArrayList<Councillor> newCouncillors = match.getField().getBalconyFromIndex(0).getArrayListCouncillor();

		assertEquals(oldCouncillors.subList(0,oldCouncillors.size()-2), newCouncillors.subList(1,newCouncillors.size()-1));

		assertEquals(14, arrayListPlayer.get(0).getRichness());
	}

}
