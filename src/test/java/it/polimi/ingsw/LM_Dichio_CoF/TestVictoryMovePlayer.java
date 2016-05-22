package it.polimi.ingsw.LM_Dichio_CoF;

import static org.junit.Assert.*;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.VictoryRoute; 

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.VictoryRoute;

public class TestVictoryMovePlayer {

	@Test
	public void test() {
		TestCases testCases = new TestCases();
		
		ArrayList <Player> arrayListPlayer;
		arrayListPlayer=testCases.arrayListPlayer();
		
		VictoryRoute victoryRoute = new VictoryRoute(arrayListPlayer);
		
		victoryRoute.movePlayer(18, arrayListPlayer.get(0));
		victoryRoute.movePlayer(44, arrayListPlayer.get(0));
		victoryRoute.movePlayer(60, arrayListPlayer.get(0));
		
		for(Player player : arrayListPlayer)
			System.out.println(player.getNickname()+ " " + victoryRoute.getPosition(player));
		
		assertEquals(3, arrayListPlayer.size());
	}

}
