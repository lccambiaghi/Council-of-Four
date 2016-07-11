package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.City;
import it.polimi.ingsw.server.model.Region;
import it.polimi.ingsw.server.model.RegionName;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestRegion {

	private Configurations config;
	private Region seaRegion;
	private Region hillRegion;
	private Region mountainRegion;
	
	@Before
	public void init() {
		config = configurations();
		
		int n = config.getNumberCities()/3;
		City[] arrayCity= new City[config.getNumberCities()/3];
		for(int i=0; i<n; i++){
			arrayCity[i] = new City(config, CityName.getCityNameFromIndex(i), RegionName.Sea, CityColor.Blue);
		}
		
		seaRegion = new Region(RegionName.Sea, arrayCity, config);
		hillRegion = new Region(RegionName.Hill, arrayCity, config);
		mountainRegion = new Region(RegionName.Mountain, arrayCity, config);
	}
	
	@Test
	public void creations (){
		assertNotNull(seaRegion);
		assertNotNull(hillRegion);
		assertNotNull(mountainRegion);
		
		assertEquals(RegionName.Sea, seaRegion.getRegionName());
		assertEquals(RegionName.Hill, hillRegion.getRegionName());
		assertEquals(RegionName.Mountain, mountainRegion.getRegionName());	
	}
	
	@Test
	public void faceUpPermitCardArea (){
		FaceUpPermitCardArea faceUpPermitCardArea = seaRegion.getFaceUpPermitCardArea();
		assertNotNull(faceUpPermitCardArea);
		assertEquals(config.getNumberCities(), seaRegion.getArrayCity().length+hillRegion.getArrayCity().length
				+mountainRegion.getArrayCity().length);
	}
	
	
	private Configurations configurations(){
		Configurations config = new Configurations();
		/*
		 * These 3 methods permit to create the configurations, write them in a file
		 * and read them. They substitute the action of the player (player side),
		 * that creates the file, and the
		 * class "Match" that reads the file
		 * If someone wants to change the configurations to see the different test cases
		 * he can do it in the method "createConfigurations"
		 */
		createConfigurations(config);
		createFileConfigurations(config);
		readFileConfigurations(config);

		return config;
	}
	
	private void createConfigurations(Configurations config){		/*
		 * Do not change this parameter and the difficulty one until we haven't create 
		 * new maps for those combination missing
		 */
		config.setCitiesNumber(18);
		
		config.setPermitCardBonusNumberMin(2);
		config.setPermitCardBonusNumberMax(3);
		
		config.setNobilityBonusRandom(false);
		if(config.isNobilityBonusRandom()==false){
			config.setNobilityBonusNumber(7);
		}
		
		config.setCityLinksPreconfigured(true);
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
			config.setDifficulty("h".charAt(0));
		}
		
		config.setCityBonusRandom(false);
        if(config.isCityBonusRandom()==false){
    		ArrayList<CityBonus> arrayListCityBonus[]= new ArrayList[config.getCitiesNumber()];
    		
    		for (int i=0; i<arrayListCityBonus.length; i++){
    			arrayListCityBonus[i] = new ArrayList<>();
    	    	arrayListCityBonus[i].add(new CityBonus(BonusName.Assistant, 2));
    	    	arrayListCityBonus[i].add(new CityBonus(BonusName.Richness, 1));
    		}
			config.setArrayListCityBonus(arrayListCityBonus);
        }else{
			config.setCityBonusNumberMin(2);
			config.setCityBonusNumberMax(3);
		}
		
	}
	
	private void createFileConfigurations(Configurations config){
		
		FileOutputStream fileOutputStream = null;
		
		try {
			
			//il salvataggio per ora è in locale, dovrà essere inviato al server quando ci sarà la connessione
			fileOutputStream = new FileOutputStream("./src/configurations/configTest");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			// write something in the file
			objectOutputStream.writeObject(config);
		
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			// close the stream
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void readFileConfigurations(Configurations config){
		
		FileInputStream fileInputStream = null;
		
		try {
			
			//il salvataggio per ora è in locale, dovrà essere inviato al server quando ci sarà la connessione
			fileInputStream = new FileInputStream("./src/configurations/configTest");
			
			// create an ObjectInputStream for the file we created before
	         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	         this.config = (Configurations) objectInputStream.readObject();
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			// close the stream
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
