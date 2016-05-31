package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import it.polimi.ingsw.LM_Dichio_CoF.work.*;

/* The constructor creates cities, assign them to regions */
public class Field {

	private Configurations config;
	private City[] arrayCity;
	private ArrayList<Player> arrayListPlayer;
	private Route richnessRoute;
	private Route victoryRoute;
	private Route nobilityRoute;
	
	King king;
	
	public Field(Configurations config, ArrayList<Player> arrayListPlayer) {
		
		this.config=config;
		this.arrayListPlayer = arrayListPlayer;
		
		createCitiesAndRegions();
		
		assignNearbyCities();


		createBalconies();
		
		//GESTIONE PERCORSI
		createRoutes();
		
	}

	/* This method creates arrayCity[config.numberCities] assigning cities their color,
	 	then assigns them to the respective region  */
	private void createCitiesAndRegions(){

		int numberCities = config.getNumberCities();
		arrayCity = new City[numberCities];
		Region[] arrayRegion = new Region[Constant.REGIONS_NUMBER];

		int numberCitiesPerRegion = numberCities/Constant.REGIONS_NUMBER;
		City[] arrayCityPerRegion = new City[numberCitiesPerRegion];

		CityColor[] arrayCityColor = createArrayCityColor(numberCities);

		// ristruttura ciclo: king fuori e guarda design patter proxy
		// proxy: crea city prima e inizializza i parametri nel ciclo(??)
		for(int itRegion = 0, itColor=0; itRegion<Constant.REGIONS_NUMBER; itRegion++){

			RegionName regionName = RegionName.getRegionNameFromIndex(itRegion);

			for(int itCityPerRegion=0; itCityPerRegion<numberCitiesPerRegion; itCityPerRegion++){

				int cityIndex = itCityPerRegion + itRegion*numberCitiesPerRegion;

				CityName cityName = CityName.getCityNameFromIndex(cityIndex);

				if (cityName.equals(Constant.KING_CITY_INITIAL)) {
					if (config.isCityBonusRandom())
						arrayCity[cityIndex] = new City(config, cityName, regionName, CityColor.Purple);
					else
						arrayCity[cityIndex] = new City(config.getCityBonusArrayMaps()[cityIndex], cityName, regionName, CityColor.Purple);
				}
				else {
					if (config.isCityBonusRandom())
						arrayCity[cityIndex] = new City(config, cityName, regionName, arrayCityColor[itColor]);
					else
						arrayCity[cityIndex] = new City(config.getCityBonusArrayMaps()[cityIndex], cityName, regionName, arrayCityColor[itColor]);
					itColor++;
				}
				arrayCityPerRegion[itCityPerRegion] = arrayCity[cityIndex];
			}

			arrayRegion[itRegion] = new Region (regionName, arrayCityPerRegion, config);

		}

	}

	/* This method returns an array of randomly orderered CityColors */
	private CityColor[] createArrayCityColor(int numberCities){
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

		return arrayCityColor;
	}

	/* This method assigns to every city in arrayCity the cities that are connected to it
	  according to (cases):
	  A. the matrix contained in "config" (case config.isCityLinksPreconfigured()=false)  
	  B. the matrix contained in the .txt file (selected through difficulty and number of cities)
	  This second case is managed in "importCityLinksMatrix"
	  */
	private void assignNearbyCities(){
		
		int[][] cityLinksMatrix;
		if(config.isCityLinksPreconfigured()){
			cityLinksMatrix = importCityLinksMatrix(config.getDifficulty(), arrayCity.length);
		}else{
			cityLinksMatrix = config.getCityLinksMatrix();
		}
		
		for(int row=0; row<arrayCity.length; row++){
			
        	ArrayList<City> arrayListCityConnected = new ArrayList<>();
			for (int column=0; column<arrayCity.length; column++){
        		if(cityLinksMatrix[row][column]==1){
        			arrayListCityConnected.add(arrayCity[column]);
        		}	
        	}

			/* ArrayList to simplify the procedure*/
			City[] arrayCityConnected = new City[arrayListCityConnected.size()];
			arrayListCityConnected.toArray(arrayCityConnected);
			arrayCity[row].setNearbyCity(arrayCityConnected);
			
		}  

	}
	
	/* This method imports cityLinksMatrix from the respective .txt file according to parameters passed:
	  "difficulty" is the first letter of the file, "numberCities" the second one */
	private int[][] importCityLinksMatrix(char difficulty , int numberCities){
		
		int[][]cityLinksMatrix = new int[numberCities][numberCities];
		
		try {

			FileReader inFile = new FileReader("./src/nearbyCities/"+ difficulty + numberCities+".txt");
			Scanner in = new Scanner(inFile);
			
			for(int i=0; i<numberCities; i++) {
	            for(int j=0; j<numberCities; j++){
	            	String read = in.next();
	            	if (read.equals("1")){
	            		cityLinksMatrix[i][j]=1;
	            		/*
	            		 * This second assignment is for making the matrix specular,
	            		 * because in the txt file it is only upper triangular set
	            		 */
	            		cityLinksMatrix[j][i]=1;
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
	
		return cityLinksMatrix;
		
	}

	/* This method creates four balconies and fills them with councillors */
	private void createBalconies() {
		//Create one (AND ONLY FOR ALL THE GAME) instance for the Available Councillor
		AvailableCouncillor availableCouncillor = new AvailableCouncillor();

		Balcony[] arrayBalcony = new Balcony[Constant.BALCONIES_NUMBER];

		arrayBalcony[0] = new Balcony (availableCouncillor, RegionName.Sea.toString()+"Balcony");
		arrayBalcony[1] = new Balcony (availableCouncillor, RegionName.Hill.toString()+"Balcony");
		arrayBalcony[2] = new Balcony (availableCouncillor, RegionName.Mountain.toString()+"Balcony");
		arrayBalcony[3] = new KingBalcony (availableCouncillor, "KingBalcony");
	}
	
	private void createRoutes(){
		
		this.richnessRoute = new RichnessRoute(arrayListPlayer);
		this.victoryRoute = new VictoryRoute(arrayListPlayer);
		if(config.isNobilityBonusRandom()){
			this.nobilityRoute = new NobilityRoute(arrayListPlayer, config.getNobilityBonusNumber());
		}else{
			this.nobilityRoute = new NobilityRoute(arrayListPlayer);
		}
		
	}
	
	public City[] getArrayCity(){
		return arrayCity;
	}
	
}
