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
		
		arrayRegion = new Region[Constant.NUMBER_OF_REGIONS];
		
		arrayRegion[0] = new Region (config, NameRegion.Sea);
		arrayRegion[1] = new Region (config, NameRegion.Hill);
		arrayRegion[2] = new Region (config, NameRegion.Mountain);
		
		/*
		 * This array will contain the cities created by every region
		 */
		arrayCity = new City[config.getNumberCities()];
		
		/*
		 * k=iterator in arrayCity
		 * j=iterator in arrayRegion
		 * i=iterator in arrayCity(of every region)
		 */
		for(int k=0,j=0; j<Constant.NUMBER_OF_REGIONS; j++){
			for(int i=0; i<arrayRegion[j].getArrayCity().length; i++, k++){
				arrayCity[k]=(arrayRegion[j].getArrayCity())[i];
				
				/*
				 * if the City is the king one it initializes the king
				 */
				if(arrayCity[k].getNameCity() == Constant.INITIAL_KING_CITY){
					king = new King(arrayCity[k]);
				}
			}
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
