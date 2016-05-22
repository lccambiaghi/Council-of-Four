package it.polimi.ingsw.LM_Dichio_CoF;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.NobilityRoute;

public class TestNobilityMovePlayer {

	@Test
	public void test() {
		
		TestCases testCases = new TestCases();
		
		ArrayList <Player> arrayListPlayer =testCases.arrayListPlayer();
		
		int[] arrayPosition = new int[arrayListPlayer.size()];
		
		NobilityRoute nobilityRoute = new NobilityRoute(arrayListPlayer);
		
		int i=10;
		for(Player player : arrayListPlayer){
			nobilityRoute.movePlayer(i, player);
			i+=8;
		}
		
		int j=0;
		for(Player player : arrayListPlayer){
			arrayPosition[j] = nobilityRoute.getPosition(player);
			System.out.println(arrayPosition[j]);
			j++;
		}
		
		assertArrayEquals(arrayPosition, new int[]{10,18,20});
	}

}
