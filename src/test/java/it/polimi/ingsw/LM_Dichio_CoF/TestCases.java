package it.polimi.ingsw.LM_Dichio_CoF;

import static org.junit.Assert.*;

import java.util.ArrayList;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.*;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public class TestCases {
	
	public City[] arrayCity(){
		
		int n=5;
		City[] arrayCity = new City[n];
		for(int i=0; i<n; i++){
			arrayCity[i] = new City(NameCity.getNameCity(i));
		}
		
		return arrayCity;
	}
	
	
	
	public ArrayList <Player> arrayListPlayer() {
	
		ArrayList <Player> arrayListPlayer = new ArrayList <Player> ();

		Player player = new Player("s");
		player.setNickname("A");
		arrayListPlayer.add(player);
		Player player2 = new Player("s");
		player2.setNickname("B");
		arrayListPlayer.add(player2);
		Player player3 = new Player("s");
		player3.setNickname("C");
		arrayListPlayer.add(player3);
		
		return arrayListPlayer;
	}

}
