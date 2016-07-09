package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.server.control.Player;

public class TestSellingAssistants {

	ArrayList <SellingObject> arrayListSellingObject;
	ArrayList <Player> arrayListPlayers;
	SellingAssistants sellingAssistants;
	Player player;
	
	@Before
	public void init(){
		arrayListPlayers = createArrayListPlayer();
		player = arrayListPlayers.get(0);
		sellingAssistants = new SellingAssistants(player.getAssistant(), player, 4);
		
	}
	
	@Test
	public void sellingPolitic (){
		assertNotNull(sellingAssistants);
		assertEquals(arrayListPlayers.get(0),sellingAssistants.getOwner());
		assertEquals(4,sellingAssistants.getPrice());
	}
	
	@Test
	public void add (){
		sellingAssistants.addToPlayer(arrayListPlayers.get(1));
		assertEquals(3, arrayListPlayers.get(1).getAssistant());		
	}

	private ArrayList <Player> createArrayListPlayer() {
		ArrayList <Player> arrayListPlayer = new ArrayList <>();		
		
		Player player = new Player();
		player.setNickname("A");
		player.setAssistant(3);
		player.setRichness(10);
		player.getArrayListPoliticCard().add(new PoliticCard(Color.getRandomColor()));
		arrayListPlayer.add(player);

		Player player2 = new Player();
		player2.setNickname("B");
		player.setAssistant(0);
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
