package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.NobilityRoute;
import it.polimi.ingsw.server.model.NobilityRouteCell;
import it.polimi.ingsw.utils.Constant;

import org.junit.Before;
import org.junit.Test;

public class TestNobilityRoute {
	
	private NobilityRoute nobilityRoute;
	private NobilityRoute customNobilityRoute;
	private ArrayList <Player> arrayListPlayer;
	
	@Before
	public void init (){		
		arrayListPlayer = createArrayListPlayer();
		
		nobilityRoute = new NobilityRoute(arrayListPlayer);
		customNobilityRoute = new NobilityRoute (arrayListPlayer, 5);
	}
	
	@Test
	public void nobilityInitialize (){
		assertNotNull(nobilityRoute);
		assertNotNull(customNobilityRoute);
		
		assertEquals(Constant.NOBILITY_MAX+1, nobilityRoute.getArrayNobilityRouteCell().length);
	}
	
	@Test
	public void initialNobility() {
		
		for(Player player : arrayListPlayer)
			assertEquals(0, nobilityRoute.getPosition(player));
	}

	@Test
	public void movePlayer() {

		int[] arrayPosition = new int[arrayListPlayer.size()];

		int i=10;
		for(Player player : arrayListPlayer){
			nobilityRoute.movePlayer(i, player);
			i+=8;
		}

		int j=0;
		for(Player player : arrayListPlayer){
			arrayPosition[j] = nobilityRoute.getPosition(player);
			j++;
		}

		assertArrayEquals(arrayPosition, new int[]{10,18,20});
	}
	
	@Test
	public void standardBonuses(){
		NobilityRouteCell[] arrayNobilityRouteCell = nobilityRoute.getArrayNobilityRouteCell();
		
		int counter=0;
		
		for (NobilityRouteCell a : arrayNobilityRouteCell){
			if (a.hasBonus()){
				counter++;
			}
		}
		assertEquals(Constant.NOBILITY_CELL_BONUSES.length, counter);
	}

	@Test
	public void randomBonuses(){
		NobilityRouteCell[] arrayNobilityRouteCell = customNobilityRoute.getArrayNobilityRouteCell();
		
		int counter=0;
		int counterNoBonus=0;
		for (NobilityRouteCell a : arrayNobilityRouteCell){
			if (a.hasBonus()){
				counter++;
			}
			else
				counterNoBonus++;
		}
		assertEquals(5, counter);
		assertEquals(Constant.NOBILITY_MAX+1-5, counterNoBonus);
	
	}
	
	private ArrayList <Player> createArrayListPlayer() {
		ArrayList <Player> arrayListPlayer = new ArrayList <>();		
		
		Player player = new Player();
		player.setNickname("A");
		player.setAssistant(3);
		player.setRichness(10);
		arrayListPlayer.add(player);

		Player player2 = new Player();
		player2.setNickname("B");
		player.setAssistant(2);
		player.setRichness(11);
		arrayListPlayer.add(player2);

		Player player3 = new Player();
		player3.setNickname("C");
		player.setAssistant(3);
		player.setRichness(12);
		arrayListPlayer.add(player3);
		
		return arrayListPlayer;
	}

}
