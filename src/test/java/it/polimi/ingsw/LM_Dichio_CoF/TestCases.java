package it.polimi.ingsw.LM_Dichio_CoF;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.*;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public class TestCases {
	
	
	public Configurations configurations(){
		
		// Parameters constructor: numberCities, minNumberBonusPerCity,
		// maxNumberBonusPerCity
		Configurations config = new Configurations(15,"n".charAt(0), 0,3);
		return config;
		
	}
	
	
	public City[] arrayCity(){
		
		Configurations config = configurations();
		
		int n = config.getNumberCities()/3;
		City[] arrayCity = new City[n];
		for(int i=0; i<n; i++){
			arrayCity[i] = new City(config, NameCity.getNameCity(i), NameRegion.Sea);
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
