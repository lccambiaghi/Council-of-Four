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
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.*;

public class TestCases {

	public Configurations configTest = configurations();
	public ArrayList <Player> arrayListPlayerTest= arrayListPlayer();
	public City[] arrayCityTest = arrayCity();
	
	/* Cities of Sea Region */
	public City[] arrayCity(){

		Map <String, Integer> bonusMap =new HashMap <> ();

		bonusMap.put("Assistant", 2);
		bonusMap.put("Nobility", 1);
		bonusMap.put("Richness", 0);

		int n = configTest.getNumberCities()/3;
		City[] arrayCity = new City[n];
		for(int i=0; i<n; i++){
			
			//arrayCity[i] = new City(configTest, CityName.getCityNameFromIndex(i), RegionName.Sea, CityColor.Blue);
			arrayCity[i] = new City(bonusMap, CityName.getCityNameFromIndex(i), RegionName.Sea, CityColor.Blue);

		}
		System.out.println();
		return arrayCity;
	}
	
	public ArrayList <Player> arrayListPlayer() {
	
		ArrayList <Player> arrayListPlayer = new ArrayList <Player> ();

		Player player = new Player("s");
		player.setNickname("A");

		City[] arrayCity = arrayCityTest;
		player.acquirePermitCard(new PermitCard(arrayCityTest, configTest));

		arrayListPlayer.add(player);
		Player player2 = new Player("s");
		player2.setNickname("B");
		arrayListPlayer.add(player2);
		Player player3 = new Player("s");
		player3.setNickname("C");
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

		return configTest;
	}

	private void createConfigurations(){
		
		configTest = new Configurations();
		
		/*
		 * Do not change this parameter and the difficulty one until we haven't create 
		 * new maps for those combination missing
		 */
		configTest.setCitiesNumber(15);
		
		configTest.setPermitCardBonusNumberMin(2);
		configTest.setPermitCardBonusNumberMax(3);
		
		configTest.setNobilityBonusRandom(false);
		if(configTest.isNobilityBonusRandom()==false){
			configTest.setNobilityBonusNumber(7);
		}
		
		configTest.setCityLinksPreconfigured(false);
		if(configTest.isCityLinksPreconfigured()==false){
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
			for(int i = 0; i< configTest.getCitiesNumber(); i++){
				for(int j = i; j< configTest.getCitiesNumber(); j++){
					cityLinksMatrix[j][i]=cityLinksMatrix[i][j];
				}
			}
			configTest.setCityLinksMatrix(cityLinksMatrix);
			
		}else{
			configTest.setDifficulty("n".charAt(0));
		}
		
		configTest.setCityBonusRandom(false);
        if(configTest.isCityBonusRandom()==false){
			HashMap[] cityBonusArrayMap = new HashMap[configTest.getCitiesNumber()];
            for(int i = 0; i< configTest.getCitiesNumber(); i++){
                HashMap <String, Integer> bonusMap =new HashMap <> ();
                bonusMap.put("Assistant", 2);
                bonusMap.put("Nobility", 1);
                cityBonusArrayMap[i]=bonusMap;
            }
			configTest.setCityBonusArrayMaps(cityBonusArrayMap);
        }else{
			configTest.setCityBonusNumberMin(2);
			configTest.setCityBonusNumberMax(3);
		}
		
	}
	
	private void createFileConfigurations(){
		
		FileOutputStream fileOutputStream = null;
		
		try {
			
			//il salvataggio per ora è in locale, dovrà essere inviato al server quando ci sarà la connessione
			fileOutputStream = new FileOutputStream("./src/configurations/configTest");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			// write something in the file
			objectOutputStream.writeObject(configTest);
		
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
	         this.configTest = (Configurations) objectInputStream.readObject();
		
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
