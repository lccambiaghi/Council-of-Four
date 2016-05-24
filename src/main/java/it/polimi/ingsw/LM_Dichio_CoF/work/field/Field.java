package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import it.polimi.ingsw.LM_Dichio_CoF.work.*;

public class Field {

	Region[] arrayRegion;
	City[] arrayCity;
	Balcony[] arrayBalcony;
	AvailableCouncilor availableCouncilor;
	Route route;
	King king;
	
	public Field(Configurations config, ArrayList<Player> arrayListPlayer) {
		
		/*
		 * GESTIONE REGIONI -- C'è da creare una random che distribuisca i colori tra le regioni,
		 * inoltre bisogna trovare un modo (matrice di inceidenza o liste) per creare le città vicine
		 */
		
		createCitiesAndRegions(config);
		
		
		assignNearbyCities(config.getDifficulty(), arrayCity);
		
		
		//GESTIONE BALCONATE
		
		
		/*
		 * Create one (AND ONLY FOR ALL THE GAME) instance for the Available Councilor
		 */
		
		
		availableCouncilor = new AvailableCouncilor();
		
		arrayBalcony = new Balcony[Constant.NUMBER_BALCONIES];
		
		arrayBalcony[0] = new Balcony (availableCouncilor, NameRegion.Sea.toString()+"Balcony");
		arrayBalcony[1] = new Balcony (availableCouncilor, NameRegion.Hill.toString()+"Balcony");
		arrayBalcony[2] = new Balcony (availableCouncilor, NameRegion.Mountain.toString()+"Balcony");
		arrayBalcony[3] = new KingBalcony (availableCouncilor, "KingBalcony");
		
		
		
		//GESTIONE PERCORSI
		
		
	}

	private void createCitiesAndRegions(Configurations config){
		/*
		 * Creation of the cities that are assigned to the specified Region
		 */
		int numberCities = config.getNumberCities();
		int numberCitiesPerRegion = numberCities/Constant.NUMBER_OF_REGIONS;

		arrayCity = new City[numberCities];
		arrayRegion = new Region[Constant.NUMBER_OF_REGIONS];

		City[] arrayCityPerRegion = new City[numberCitiesPerRegion];

		CityColor[] arrayCityColor = new CityColor[numberCities];

		switch (numberCities) {

			case Constant.CITIES_NUMBER_LOW:
				arrayCityColor= Constant.CITIES_COLOR[0].clone();
				break;

			case Constant.CITIES_NUMBER_MEDIUM:
				arrayCityColor= Constant.CITIES_COLOR[1].clone();
				break;

			case Constant.CITIES_NUMBER_HIGH:
				arrayCityColor= Constant.CITIES_COLOR[2].clone();
				break;

		}

		Collections.shuffle(Arrays.asList(arrayCityColor));

		int itColor=0;

		for(int itRegion=0; itRegion<Constant.NUMBER_OF_REGIONS ; itRegion++){
			NameRegion nameRegion = NameRegion.getNameRegion(itRegion);

			for(int itCity=0; itCity<numberCitiesPerRegion; itCity++){

				NameCity nameCity = NameCity.getNameCity(itCity + itRegion*numberCitiesPerRegion);

				if (nameCity.equals(Constant.INITIAL_KING_CITY)) {
					arrayCity[itCity + itRegion * numberCitiesPerRegion] = new City(config, nameCity, nameRegion, CityColor.Purple);
				}
				else {
					arrayCity[itCity + itRegion * numberCitiesPerRegion] = new City(config, nameCity, nameRegion, arrayCityColor[itColor]);
					itColor++;
				}
				arrayCityPerRegion[itCity] = arrayCity[itRegion*numberCitiesPerRegion];
			}

			arrayRegion[itRegion] = new Region (nameRegion, arrayCityPerRegion);

		}



	}

	
	/*
	 * This method assigns to every city of the arrayCity the cities that are connected to it
	 * It uses an arrayList to make the creation of the array of cities connected to every city simpler
	 * 
	 */
	private void assignNearbyCities(char difficulty,City[] arrayCity){
		
		int[][] matrix = importMatrix(difficulty, arrayCity.length);
		
		for(int row=0; row<arrayCity.length; row++){
			
        	ArrayList<City> arrayListCityConnected = new ArrayList<City>();
			for (int column=0; column<arrayCity.length; column++){
        		if(matrix[row][column]==1){
        			arrayListCityConnected.add(arrayCity[column]);
        		}	
        	}
			/*
			 * ArrayList to simplify the procedure
			 */
			City[] arrayCityConnected = new City[arrayListCityConnected.size()];
			arrayListCityConnected.toArray(arrayCityConnected);
			arrayCity[row].setNearbyCity(arrayCityConnected);
			
		}  

	}
	
	/*
	 * This method is used to import the matrix (txt file) corresponding to the parameters passed:
	 * "difficulty" is the first letter of the file, "numberCities" the second one
	 */
	private int[][] importMatrix(char difficulty , int numberCities){
		
		int[][]matrix = new int[numberCities][numberCities];
		
		try {
			
			
			FileReader inFile = new FileReader("./src/nearbyCities/"+ difficulty + numberCities+".txt");
			Scanner in = new Scanner(inFile);
			
			
			for(int i=0; i<numberCities; i++) {
	            for(int j=0; j<numberCities; j++){
	            	String read = in.next();
	            	if (read.equals("1")){
	            		matrix[i][j]=1;
	            		/*
	            		 * This second assignment is for making the matrix specular,
	            		 * because in the txt file it is only upper triangular set
	            		 */
	            		matrix[j][i]=1;
	            	}
	            }
	            	
	        }
			
	        in.close();
	        
	        try {
				inFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		return matrix;
		
	}

	public City[] getArrayCity(){
		return arrayCity;
	}
	
}
