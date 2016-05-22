package it.polimi.ingsw.LM_Dichio_CoF;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.NobilityRoute;

public class TestNobilityRoute {

	@Test
	public void test() {
		
		TestCases testCases = new TestCases();
		
		ArrayList <Player> arrayListPlayer;
		arrayListPlayer=testCases.arrayListPlayer();
		
		NobilityRoute nobilityRoute = new NobilityRoute(arrayListPlayer);
		
		for(Player player : arrayListPlayer)
			System.out.println(player.getNickname()+ " " + nobilityRoute.getPosition(player));
		
		assertEquals(3, arrayListPlayer.size());
	}

}
