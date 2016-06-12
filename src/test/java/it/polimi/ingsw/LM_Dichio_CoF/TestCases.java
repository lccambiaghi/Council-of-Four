package it.polimi.ingsw.LM_Dichio_CoF;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.work.MatchMock;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.*;

public class TestCases {

	private Configurations config = new Configurations();
	private City[] arrayCity;
	private ArrayList <Player> arrayListPlayer = new ArrayList<>();

	/* Cities of Sea Region */
	public City[] arrayCity(){

		Map <String, Integer> bonusMap =new HashMap <> ();

		bonusMap.put("Assistant", 2);
		bonusMap.put("Nobility", 1);
		bonusMap.put("Richness", 0);

		Configurations config=configurations();

		int n = config.getNumberCities()/3;
		arrayCity= new City[config.getNumberCities()/3];
		for(int i=0; i<n; i++){
			
			//arrayCity[i] = new City(configTest, CityName.getCityNameFromIndex(i), RegionName.Sea, CityColor.Blue);
			arrayCity[i] = new City(bonusMap, CityName.getCityNameFromIndex(i), RegionName.Sea, CityColor.Blue);

		}
		System.out.println();
		return arrayCity;
	}
	
	
	public MatchMock matchMock(){
		MatchMock matchMock = new MatchMock(arrayListPlayer());
		return matchMock;
	}
	
	public ArrayList <Player> arrayListPlayer() {

		Player player = new Player('s');
		player.setNickname("A");
		player.setAssistant(3);
		player.setRichness(10);
		//City[] arrayCity = arrayCity();
		//Configurations config = configurations();
		//player.acquirePermitCard(new PermitCard(arrayCity, config));

		arrayListPlayer.add(player);
		Player player2 = new Player('s');
		player2.setNickname("B");
		player.setAssistant(3);
		player.setRichness(10);
		arrayListPlayer.add(player2);

		Player player3 = new Player('s');
		player3.setNickname("C");
		player.setAssistant(3);
		player.setRichness(10);
		arrayListPlayer.add(player3);
		
		return arrayListPlayer;
	}

	public Configurations configurations(){

		/*
		 * These 3 methods permit to create the configurations, write them in a file
		 * and read them. They substitute the action of the player (player side),
		 * that creates the file, and the
		 * class "Match" that reads the file
		 * If someone wants to change the configurations to see the different test cases
		 * he can do it in the method "createConfigurations"
		 */
		createConfigurations();
		createFileConfigurations();
		readFileConfigurations();

		return config;
	}


	private void createConfigurations(){

		/*
		 * Do not change this parameter and the difficulty one until we haven't create 
		 * new maps for those combination missing
		 */
		config.setCitiesNumber(15);
		
		config.setPermitCardBonusNumberMin(2);
		config.setPermitCardBonusNumberMax(3);
		
		config.setNobilityBonusRandom(false);
		if(config.isNobilityBonusRandom()==false){
			config.setNobilityBonusNumber(7);
		}
		
		config.setCityLinksPreconfigured(false);
		if(config.isCityLinksPreconfigured()==false){
			int[][]cityLinksMatrix =  new int[][]{
				{0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	1,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0}
			};
			/*
    		 * This fir cycle is for making the matrix specular,
    		 * because in the txt file it is only upper triangular set
    		 */
			for(int i = 0; i< config.getCitiesNumber(); i++){
				for(int j = i; j< config.getCitiesNumber(); j++){
					cityLinksMatrix[j][i]=cityLinksMatrix[i][j];
				}
			}
			config.setCityLinksMatrix(cityLinksMatrix);
			
		}else{
			config.setDifficulty("n".charAt(0));
		}
		
		config.setCityBonusRandom(false);
        if(config.isCityBonusRandom()==false){
			HashMap[] cityBonusArrayMap = new HashMap[config.getCitiesNumber()];
            for(int i = 0; i< config.getCitiesNumber(); i++){
                HashMap <String, Integer> bonusMap =new HashMap <> ();
                bonusMap.put("Assistant", 2);
                bonusMap.put("Nobility", 1);
                cityBonusArrayMap[i]=bonusMap;
            }
			config.setCityBonusArrayMaps(cityBonusArrayMap);
        }else{
			config.setCityBonusNumberMin(2);
			config.setCityBonusNumberMax(3);
		}
		
	}
	
	private void createFileConfigurations(){
		
		FileOutputStream fileOutputStream = null;
		
		try {
			
			//il salvataggio per ora è in locale, dovrà essere inviato al server quando ci sarà la connessione
			fileOutputStream = new FileOutputStream("./src/configurations/configTest");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			// write something in the file
			objectOutputStream.writeObject(config);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			// close the stream
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void readFileConfigurations(){
		
		FileInputStream fileInputStream = null;
		
		try {
			
			//il salvataggio per ora è in locale, dovrà essere inviato al server quando ci sarà la connessione
			fileInputStream = new FileInputStream("./src/configurations/configTest");
			
			// create an ObjectInputStream for the file we created before
	         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	         this.config = (Configurations) objectInputStream.readObject();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// close the stream
			try {
				fileInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
