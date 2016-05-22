package it.polimi.ingsw.LM_Dichio_CoF;

import static org.junit.Assert.*;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.VictoryRoute;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

import org.junit.Test;

public class TestVictoryRoute {

	@Test
	public void test() {
		
		TestCases testCases = new TestCases();
		
		ArrayList <Player> arrayListPlayer;
		arrayListPlayer=testCases.arrayListPlayer();
		
		VictoryRoute victoryRoute = new VictoryRoute(arrayListPlayer);
		
		for(Player player : arrayListPlayer)
			System.out.println(player.getNickname()+ " " + victoryRoute.getPosition(player));
		
		assertEquals(3, arrayListPlayer.size());
	
	}

}
