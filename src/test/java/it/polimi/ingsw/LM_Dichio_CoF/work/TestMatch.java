package it.polimi.ingsw.LM_Dichio_CoF.work;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.jar.Attributes.Name;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class TestMatch {

	private TestCases testCases = new TestCases();

	@Ignore
	public void giveInitialPoliticCards() {
		
		ArrayList <Player> arrayListPlayer = testCases.arrayListPlayer();
	
		Match match = new Match(arrayListPlayer);

		for (Player anArrayListPlayer : arrayListPlayer) {
			ArrayList<PoliticCard> arrayListPoliticCard = anArrayListPlayer.getArrayListPoliticCard();
			for (PoliticCard anArrayListPoliticCard : arrayListPoliticCard) {
				System.out.println(anArrayListPoliticCard.getCardColor().toString());
			}
			System.out.println();
		}
		
	}
	
	@Ignore
	public void giveInitialAssistants() {
		
		ArrayList <Player> arrayListPlayer = testCases.arrayListPlayer();
	
		Match match = new Match(arrayListPlayer);

		for (Player anArrayListPlayer : arrayListPlayer) {
			System.out.println(anArrayListPlayer.getAssistant());
			System.out.println();
		}
	}

	@Ignore
	public void electCouncillor(){

		testCases.configurations();
		ArrayList<Player> arrayListPlayer = testCases.arrayListPlayer();

		Match match = new Match(arrayListPlayer);

		// asks if quick action : 2=no

		// asks which main action : 1=Election

		// asks which balcony : 1=Sea

		// asks which color : 1=White

		assertEquals(14, arrayListPlayer.get(0).getRichness());
	}

	@Test
	public void engageAnAssistant(){

		testCases.configurations();
		ArrayList<Player> arrayListPlayer = testCases.arrayListPlayer();

		Match match = new Match(arrayListPlayer);
		assertEquals(7, arrayListPlayer.get(0).getRichness());
	}
}
