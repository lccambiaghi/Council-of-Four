package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class Region {

	NameRegion nameRegion;
	int numberCitiesPerRegion;
	City[] arrayCity;
	FaceUpPermitCardArea faceUpPermitCardArea;
	
	/*
	 * The constructor of Region takes as parameters the number of cities he has to have,
	 * the name of the Region, the index to start creating cities: creates an array of cities.
	 * The point is that the constructor itself passes to each city the name of it, selecting 
	 * the right ones.
	 * It also creates the faceUpPermitCardArea, calling his constructor
	 */
	public Region(Configurations config, NameRegion nameRegion){
		
		this.numberCitiesPerRegion=config.getNumberCities()/Constant.NUMBER_OF_REGIONS;
		this.nameRegion = nameRegion;
		
		this.arrayCity = new City[numberCitiesPerRegion];
		
		int indexStartingCity = NameRegion.getIndex(nameRegion)*numberCitiesPerRegion;
		
		for(int i = 0; i<numberCitiesPerRegion; i++){
			NameCity nameCity = NameCity.getNameCity(i + indexStartingCity);
			arrayCity[i] = new City(config, nameCity);
		}
		
		
		faceUpPermitCardArea = new FaceUpPermitCardArea(arrayCity);
		
	}

	
	public City[] getArrayCity() {
		return arrayCity;
	}
	
}
