package it.polimi.ingsw.LM_Dichio_CoF.work;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.jar.Attributes.Name;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class TestMatch {

	@Test
	public void giveInitialCards() {
		
		TestCases testCases = new TestCases();
		
		ArrayList <Player> arrayListPlayer = testCases.arrayListPlayer();
	
		Match match = new Match(arrayListPlayer);
		
		for(int itPlayer=0; itPlayer<arrayListPlayer.size(); itPlayer++){
			ArrayList<PoliticCard> arrayListPoliticCard = arrayListPlayer.get(itPlayer).getArrayListPoliticCard();
			for(int itCard=0; itCard<arrayListPoliticCard.size(); itCard++){
				System.out.println(arrayListPoliticCard.get(itCard).getCardColor().toString());
			}
			System.out.println();
		}
		
	}
	
	@Test
	public void giveInitialAssistants() {
		TestCases testCases = new TestCases();
		
		ArrayList <Player> arrayListPlayer = testCases.arrayListPlayer();
	
		Match match = new Match(arrayListPlayer);
		
		for(int itPlayer=0; itPlayer<arrayListPlayer.size(); itPlayer++){
			System.out.println(arrayListPlayer.get(itPlayer).getAssistant());
			System.out.println();
		}
	}

}
