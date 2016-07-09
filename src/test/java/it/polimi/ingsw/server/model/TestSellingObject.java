package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.server.control.Player;

public class TestSellingObject {
	
	ArrayList <SellingObject> arrayListSellingObject;
	ArrayList <Player> arrayListPlayers;
	SellingPoliticCard sellingPoliticCard;
	
	@Before
	public void init(){
		arrayListPlayers = createArrayListPlayer();
		arrayListSellingObject = new ArrayList <> ();
		Player player = arrayListPlayers.get(0);
		sellingPoliticCard = new SellingPoliticCard(player.getArrayListPoliticCard().get(0), player, 10);
		arrayListSellingObject.add(sellingPoliticCard);
		
	}
	
	@Test
	public void sellingElements (){
		assertNotNull(arrayListSellingObject);
		assertEquals(1, arrayListSellingObject.size());
		assertEquals(arrayListPlayers.get(0),arrayListSellingObject.get(0).getOwner());
		assertEquals(10,arrayListSellingObject.get(0).getPrice());
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
