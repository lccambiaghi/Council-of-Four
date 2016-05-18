package it.polimi.ingsw.LM_Dichio_CoF.work.field;

public class Region {

	NameRegion name;
	int numberCities;
	City[] arrayCity;
	
	/*
	 * The constructor of Region takes as parameters the number of cities he has to have,
	 * the name of the Region, the index to star creating cities: creates an array of cities.
	 * The point is that the constructor itself passes to each city the name of it, selecting 
	 * the right ones.
	 */
	public Region(int numberCities, NameRegion name, int indexStartingCity){
		
		this.numberCities=numberCities;
		this.name = name;
		
		arrayCity = new City[numberCities];
		
		for(int i = 0; i<numberCities; i++){
			NameCity nameCity = NameCity.getNameCity(i + indexStartingCity);
			arrayCity[i] = new City(nameCity);
		}
		
	}

	
	public City[] getArrayCity() {
		return arrayCity;
	}


	FaceUpPermitCardArea faceUpPermitCardArea;
	
}
