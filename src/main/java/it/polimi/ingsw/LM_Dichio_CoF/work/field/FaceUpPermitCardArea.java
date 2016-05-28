package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class FaceUpPermitCardArea {
	
	private PermitCard[] arrayPermitCard;
	private City[] arrayCity;
	private Configurations config;

	/* The constructor receives the arrayCity belonging to a region and
	  initializes the arrayPermitCard with FACE_UP_PERMIT_CARD_PER_REGION_NUMBER
	  permit cards */
	public FaceUpPermitCardArea(City[] arrayCity, Configurations config){
		
		this.arrayCity=arrayCity;
		this.config=config;
		
		arrayPermitCard = new PermitCard[Constant.FACE_UP_PERMIT_CARD_PER_REGION_NUMBER];
		
		for(int i=0; i< arrayPermitCard.length; i++){
			arrayPermitCard[i]= new PermitCard(arrayCity, config);
		}
		
	}

	/* This method is to take a permit card from the area.
	 * It takes as parameters the index of the permit card
	 * ("1" for arrayPermitCard[0] and so on)
	 * It returns the wanted permit card, creating a new one in the place of the
	 * taken one */
	public PermitCard takePermitCard(int index){
		PermitCard permitCardTmp = arrayPermitCard[index-1];
		arrayPermitCard[index-1]=new PermitCard(arrayCity, config);
		return permitCardTmp;
	}
	
	/* This method returns the permit card at the specified index
	  (("1" for arrayPermitCard[0] and so on)) */
	public PermitCard getPermitCardFromIndex(int index) {
		return arrayPermitCard[index-1];
	}
	
	public PermitCard[] getArrayPermitCard(){
		return arrayPermitCard;
	}
	
}
