package it.polimi.ingsw.LM_Dichio_CoF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.*;

public class TestCases {
	
	
	public Configurations configurations(){
		
		// Parameters constructor: numberCities, minNumberBonusPerCity,
		// maxNumberBonusPerCity
		Configurations config = new Configurations(15,"n".charAt(0), 0,3,1,2);
		return config;
		
	}
	
	
	public City[] arrayCity(){
		
		Configurations config = configurations();
		Map <String, Integer> bonusMap =new HashMap <> ();
		
		
		bonusMap.put("Assistant", 2);
		bonusMap.put("Nobility", 1);
		bonusMap.put("Richness", 0);
		
		
		int n = config.getNumberCities()/3;
		City[] arrayCity = new City[n];
		for(int i=0; i<n; i++){
			
			//arrayCity[i] = new City(config, CityName.getCityName(i), NameRegion.Sea, CityColor.Blue);
			arrayCity[i] = new City(bonusMap, CityName.getNameCity(i), NameRegion.Sea, CityColor.Blue);
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
