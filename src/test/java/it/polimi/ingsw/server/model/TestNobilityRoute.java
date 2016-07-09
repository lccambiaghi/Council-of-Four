package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.polimi.ingsw.server.TestCases;
import it.polimi.ingsw.server.control.Player;

import it.polimi.ingsw.server.model.Bonus;
import it.polimi.ingsw.server.model.NobilityRoute;
import it.polimi.ingsw.server.model.NobilityRouteCell;
import org.junit.Test;

public class TestNobilityRoute {

	@Test
	public void initialNobility() {
		
		TestCases testCases = new TestCases();
		
		ArrayList <Player> arrayListPlayer;
		arrayListPlayer=testCases.arrayListPlayer();
		
		NobilityRoute nobilityRoute = new NobilityRoute(arrayListPlayer);
		
		for(Player player : arrayListPlayer)
			System.out.println(player.getNickname()+ " " + nobilityRoute.getPosition(player));
		
		assertEquals(3, arrayListPlayer.size());
	}

	@Test
	public void movePlayer() {

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
	
	@Test
	public void standardBonuses(){
		
		TestCases testCases = new TestCases();
		ArrayList <Player> arrayListPlayer =testCases.arrayListPlayer();
		NobilityRoute nobilityRoute = new NobilityRoute(arrayListPlayer);
		
		NobilityRouteCell[] arrayNobilityRouteCell = nobilityRoute.getArrayNobilityRouteCell();
		
		for (NobilityRouteCell a : arrayNobilityRouteCell){
			if (a.hasBonus()){
				for(Bonus bonus : a.getArrayBonus()){
					System.out.print(bonus.getBonusName()+" "+bonus.getIncrement()+" ");
				}
				System.out.println();
				System.out.println();
			}
			else {
				System.out.println(0);
				System.out.println();
			}
		}
		
		System.out.println("\n\n\n");
	
	}

	@Test
	public void randomBonuses(){
		
		TestCases testCases = new TestCases();
		ArrayList <Player> arrayListPlayer =testCases.arrayListPlayer();
		NobilityRoute nobilityRoute = new NobilityRoute(arrayListPlayer, 5);
		
		NobilityRouteCell[] arrayNobilityRouteCell = nobilityRoute.getArrayNobilityRouteCell();
		
		for (NobilityRouteCell a : arrayNobilityRouteCell){
			if (a.hasBonus()){
				for(Bonus bonus : a.getArrayBonus()){
					System.out.print(bonus.getBonusName()+" "+bonus.getIncrement()+" ");
				}
				System.out.println();
				System.out.println();
			}
			else {
				System.out.println(0);
				System.out.println();
			}
		}
	
	}
}
