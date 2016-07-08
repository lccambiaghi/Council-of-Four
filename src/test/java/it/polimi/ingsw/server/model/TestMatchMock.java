package it.polimi.ingsw.server.model;

import java.io.*;
import java.util.ArrayList;

import it.polimi.ingsw.server.TestCases;
import it.polimi.ingsw.server.control.Player;

public class TestMatchMock{

	private TestCases testCases = new TestCases();
    private ArrayList <Player> arrayListPlayer= testCases.arrayListPlayer();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	/*@Ignore
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
*/
    }

