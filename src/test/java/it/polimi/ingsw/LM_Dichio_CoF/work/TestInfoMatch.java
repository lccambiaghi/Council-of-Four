package it.polimi.ingsw.LM_Dichio_CoF.work;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class TestInfoMatch {

	TestCases testCases = new TestCases();
	Match match = testCases.matchMock();
	ArrayList<Player> arrayListPlayer = match.getArrayListPlayer();
	
	InfoMatch infoMatch = new InfoMatch(match);
	
	@Test
	public void infoField() {
		infoMatch.printInfoField();
	}
	
	@Test
	public void infoPlayer(){
		infoMatch.printInfoPlayer(arrayListPlayer.get(0));
	}

}
