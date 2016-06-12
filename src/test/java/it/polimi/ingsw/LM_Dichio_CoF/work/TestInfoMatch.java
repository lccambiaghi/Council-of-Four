package it.polimi.ingsw.LM_Dichio_CoF.work;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class TestInfoMatch {

	TestCases testCases = new TestCases();
	Match match = testCases.matchMock();
	
	InfoMatch infoMatch = new InfoMatch(match);
	
	@Test
	public void test() {
		infoMatch.printInfoField();
	}

}
