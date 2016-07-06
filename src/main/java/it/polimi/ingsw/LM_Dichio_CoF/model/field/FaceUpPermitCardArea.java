package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class FaceUpPermitCardArea {
	
	private PermitCard[] arrayPermitCard;
	private City[] arrayBuildableCities;
	private Configurations config;

	/* The constructor receives the arrayBuildableCities belonging to a region and
	  initializes the arrayPermitCard with FACE_UP_PERMIT_CARD_PER_REGION_NUMBER
	  permit cards */
	public FaceUpPermitCardArea(City[] arrayBuildableCities, Configurations config){
		
		this.arrayBuildableCities = arrayBuildableCities;
		this.config=config;
		
		arrayPermitCard = new PermitCard[Constant.FACE_UP_PERMIT_CARD_PER_REGION_NUMBER];
		
		for(int i=0; i< arrayPermitCard.length; i++){
			arrayPermitCard[i]= new PermitCard(arrayBuildableCities, config);
		}
		
	}

	/* This method is to take a permit card from the area.
	 * It takes as parameters the index of the permit card
	 * ("0" for arrayPermitCard[0] and so on)
	 * It returns the wanted permit card, creating a new one in the place of the
	 * taken one */
	public PermitCard acquirePermitCard(int index){
		PermitCard chosenPermitCard = arrayPermitCard[index];
		replacePermitCard(index);
		return chosenPermitCard;
	}

	public void replacePermitCard(int index){
		arrayPermitCard[index]=new PermitCard(arrayBuildableCities, config);
	}

	public PermitCard[] getArrayPermitCard(){
		return arrayPermitCard;
	}
	
}
