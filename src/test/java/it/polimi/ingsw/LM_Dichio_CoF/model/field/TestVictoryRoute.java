package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.VictoryRoute;

import org.junit.Test;

public class TestVictoryRoute {

	@Test
	public void initialVictory() {
		
		TestCases testCases = new TestCases();
		
		ArrayList <Player> arrayListPlayer;
		arrayListPlayer=testCases.arrayListPlayer();
		
		VictoryRoute victoryRoute = new VictoryRoute(arrayListPlayer);
		
		for(Player player : arrayListPlayer)
			System.out.println(player.getNickname()+ " " + victoryRoute.getPosition(player));
		
		assertEquals(3, arrayListPlayer.size()); // da cambiare, vedi richness
	
	}

	public void movePlayer() {
		TestCases testCases = new TestCases();

		ArrayList <Player> arrayListPlayer;
		arrayListPlayer=testCases.arrayListPlayer();
		int[] arrayPosition = new int[arrayListPlayer.size()];

		VictoryRoute victoryRoute = new VictoryRoute(arrayListPlayer);

		victoryRoute.movePlayer(18, arrayListPlayer.get(0));
		victoryRoute.movePlayer(44, arrayListPlayer.get(0));
		victoryRoute.movePlayer(60, arrayListPlayer.get(0));

		victoryRoute.movePlayer(23, arrayListPlayer.get(1));

		victoryRoute.movePlayer(1, arrayListPlayer.get(2));

		int i=0;
		for(Player player : arrayListPlayer){
			System.out.println(player.getNickname()+ " " + victoryRoute.getPosition(player));
			arrayPosition[i] = victoryRoute.getPosition(player);
			i++;
		}

		assertArrayEquals(arrayPosition, new int[]{100,23,1});
	}

}
