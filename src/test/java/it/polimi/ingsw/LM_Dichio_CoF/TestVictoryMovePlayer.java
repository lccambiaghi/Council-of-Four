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
