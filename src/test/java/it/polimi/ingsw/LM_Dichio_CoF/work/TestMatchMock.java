package it.polimi.ingsw.LM_Dichio_CoF.work;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.util.ArrayList;

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
	public void giveInitialPoliticCards() {
		new MatchMock(arrayListPlayer);
		for (Player player : arrayListPlayer)
		    assertEquals(Constant.POLITIC_CARDS_INITIAL_NUMBER, player.getArrayListPoliticCard().size());
	}
	
	@Test
	public void giveInitialAssistants() {
		new MatchMock(arrayListPlayer);
		for (int i = 0; i < arrayListPlayer.size(); i++)
            assertEquals(Constant.ASSISTANT_INITIAL_FIRST_PLAYER +i, arrayListPlayer.get(i).getAssistant());
	}

    @Test
    public void inputNumberTest() {

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

	@Ignore
	public void electCouncillor(){

		ByteArrayInputStream in;

		in=new ByteArrayInputStream("2\n1\n".getBytes());
		System.setIn(in);
		MatchMock match = new MatchMock(arrayListPlayer);


		// asks if quick action : 2=no

		// asks which main action : 1=Election

		// asks which balcony : 1=Sea

		// asks which color : 1=White

		assertEquals(14, arrayListPlayer.get(0).getRichness());
	}

	@Ignore
	public void engageAnAssistant(){

		testCases.configurations();
		ArrayList<Player> arrayListPlayer = testCases.arrayListPlayer();

		Match match = new Match(arrayListPlayer);
		assertEquals(7, arrayListPlayer.get(0).getRichness());
	}
}
