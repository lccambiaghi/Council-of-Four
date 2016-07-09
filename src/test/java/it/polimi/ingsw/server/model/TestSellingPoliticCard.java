package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.server.control.Player;

public class TestSellingPoliticCard {

	ArrayList <SellingObject> arrayListSellingObject;
	ArrayList <Player> arrayListPlayers;
	SellingPoliticCard sellingPoliticCard;
	
	@Before
	public void init(){
		arrayListPlayers = createArrayListPlayer();
		Player player = arrayListPlayers.get(0);
		sellingPoliticCard = new SellingPoliticCard(player.getArrayListPoliticCard().get(0), player, 1);
		
	}
	
	@Test
	public void sellingPolitic (){
		assertNotNull(sellingPoliticCard);
		assertEquals(arrayListPlayers.get(0),sellingPoliticCard.getOwner());
		assertEquals(1,sellingPoliticCard.getPrice());
	}
	
	@Test
	public void add (){
		sellingPoliticCard.addToPlayer(arrayListPlayers.get(1));
		assertTrue(arrayListPlayers.get(1).getArrayListPoliticCard().size()==1);		
	}

	private ArrayList <Player> createArrayListPlayer() {
		ArrayList <Player> arrayListPlayer = new ArrayList <>();		
		
		Player player = new Player('s');
		player.setNickname("A");
		player.setAssistant(3);
		player.setRichness(10);
		player.getArrayListPoliticCard().add(new PoliticCard(Color.getRandomColor()));
		arrayListPlayer.add(player);

		Player player2 = new Player('s');
		player2.setNickname("B");
		player.setAssistant(2);
		player.setRichness(11);
		arrayListPlayer.add(player2);

		Player player3 = new Player('s');
		player3.setNickname("C");
		player.setAssistant(3);
		player.setRichness(12);
		arrayListPlayer.add(player3);
		
		return arrayListPlayer;
	}
	
}
