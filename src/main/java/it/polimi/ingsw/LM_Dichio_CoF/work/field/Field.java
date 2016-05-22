package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.*;

public class Field {

	Region arrayRegion[];
	Balcony arrayBalcony[];
	AvailableCouncilor availableCouncilor;
	Route route;
	
	
	public Field(Configurations config, int numberPlayers, Player[] orderPlayers) {
		
		/*
		 * GESTIONE REGIONI -- C'è da creare una random che distribuisca i colori tra le regioni,
		 * inoltre bisogna trovare un modo (matrice di inceidenza o liste) per creare le città vicine
		 */
		
		
		int numberCitiesPerRegion = (config.getNumberCities())/3;

		arrayRegion[0] = new Region (numberCitiesPerRegion,NameRegion.Sea, 0);
		arrayRegion[1] = new Region (numberCitiesPerRegion,NameRegion.Hill, numberCitiesPerRegion);
		arrayRegion[2] = new Region (numberCitiesPerRegion,NameRegion.Mountain, numberCitiesPerRegion*2);

		
		
		//GESTIONE BALCONATE
		
		
		/*
		 * Create one (AND ONLY FOR ALL THE GAME) instance for the Available Councilor
		 */
		availableCouncilor = new AvailableCouncilor();
		
		arrayBalcony[0] = new Balcony (availableCouncilor, NameRegion.Sea.toString()+"Balcony");
		arrayBalcony[1] = new Balcony (availableCouncilor, NameRegion.Hill.toString()+"Balcony");
		arrayBalcony[2] = new Balcony (availableCouncilor, NameRegion.Mountain.toString()+"Balcony");
		arrayBalcony[3] = new KingBalcony (availableCouncilor, "KingBalcony");
		
		
		
		//GESTIONE PERCORSI
		
		
	}
	
}
