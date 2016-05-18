package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.*;

public class Field {

	Region arrayRegion[];
	Balcony balcony[];
	Route route;
	
	public Field(Configurations config, int numberPlayers, Player[] orderPlayers) {
		
		//GESTIONE REGIONI
		int numberCitiesPerRegion = (config.getNumberCities())/3;

		arrayRegion[0] = new Region (numberCitiesPerRegion,NameRegion.Sea, 0);
		arrayRegion[1] = new Region (numberCitiesPerRegion,NameRegion.Hill, numberCitiesPerRegion);
		arrayRegion[2] = new Region (numberCitiesPerRegion,NameRegion.Mountain, numberCitiesPerRegion*2);

		
		
		
		
		//GESTIONE BALCONATE
		
		//GESTIONE PERCORSI
		
		
	}
	
}
