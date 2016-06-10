package it.polimi.ingsw.LM_Dichio_CoF.work;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
import org.mockito.Mockito;

public class TestMatch {

	private TestCases testCases = new TestCases();


	@Ignore
	public void giveInitialPoliticCards() {
		
		ArrayList <Player> arrayListPlayer = testCases.arrayListPlayer();

		Match match = Mockito.mock(Match.class);
		doNothing().when(match).startGame();

		match = new Match(arrayListPlayer);

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

		/*Match match = Mockito.mock(Match.class);
		when(match.inputNumber(anyInt(), anyInt())).thenReturn(2).thenReturn(1).thenReturn(1).thenReturn(1);
		match = new Match(arrayListPlayer);*/

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
