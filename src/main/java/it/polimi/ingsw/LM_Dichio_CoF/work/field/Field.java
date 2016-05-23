package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;


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
		
		
		/*
		 * Creation of the cities that are assigned to the specified Region
		 */
		int numberCities = config.getNumberCities();
		int numberCitiesPerRegion = numberCities/Constant.NUMBER_OF_REGIONS;
		
		arrayCity = new City[numberCities];
		arrayRegion = new Region[Constant.NUMBER_OF_REGIONS];
		
		City[] arrayCityPerRegion = new City[numberCitiesPerRegion];

		for(int itRegion=0; itRegion<Constant.NUMBER_OF_REGIONS ; itRegion++){
			NameRegion nameRegion = NameRegion.getNameRegion(itRegion);
			
			for(int itCity=0; itCity<numberCitiesPerRegion; itCity++){
				
				NameCity nameCity = NameCity.getNameCity(itCity + itRegion*numberCitiesPerRegion);
				arrayCity[itCity + itRegion*numberCitiesPerRegion] = new City(config, nameCity, nameRegion);
				
				arrayCityPerRegion[itCity] = arrayCity[itRegion*numberCitiesPerRegion];
			}
			
			arrayRegion[itRegion] = new Region (nameRegion, arrayCityPerRegion);
			
		}
		
		
		
		
		//GESTIONE BALCONATE
		
		
		/*
		 * Create one (AND ONLY FOR ALL THE GAME) instance for the Available Councilor
		 */
		
		/*
		availableCouncilor = new AvailableCouncilor();
		
		arrayBalcony[0] = new Balcony (availableCouncilor, NameRegion.Sea.toString()+"Balcony");
		arrayBalcony[1] = new Balcony (availableCouncilor, NameRegion.Hill.toString()+"Balcony");
		arrayBalcony[2] = new Balcony (availableCouncilor, NameRegion.Mountain.toString()+"Balcony");
		arrayBalcony[3] = new KingBalcony (availableCouncilor, "KingBalcony");
		*/
		
		
		//GESTIONE PERCORSI
		
		
	}
	
	public City[] getArrayCity(){
		return arrayCity;
	}
	
	
}
