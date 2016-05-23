package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class FaceUpPermitCardArea {
	
	PermitCard[] arrayPermitCard;
	City[] arrayCity;

	
	/*
	 * Constructor
	 * It takes the array of cities that the permitCard belonging to 
	 * a region can have.
	 * It initializes the arrayPermitCard with NUMBER_FACE_UP_PERMIT_CARD_PER_REGION
	 * permit cards.
	 */
	public FaceUpPermitCardArea(City[] arrayCity){
		
		this.arrayCity = new City[arrayCity.length];
		this.arrayCity=arrayCity;
		
		arrayPermitCard = new PermitCard[Constant.NUMBER_FACE_UP_PERMIT_CARD_PER_REGION];
		
		for(int i=0; i< arrayPermitCard.length; i++){
			arrayPermitCard[i]= new PermitCard(arrayCity);
		}
		
	}

	/*
	 * This method permit to take a permit card from the available ones.
	 * It takes as parameters the index of the permit card (for example
	 * "1" for the one in position "0" and so on)
	 * It returns the wanted permit card, creating a new one in the place of the
	 * taken one
	 */
	public PermitCard takePermitCard(int index){
		PermitCard permitCardTmp = arrayPermitCard[index-1];
		arrayPermitCard[index-1]=new PermitCard(arrayCity);
		return permitCardTmp;
	}
	
	
	/*
	 * This method returns the permit card 
	 * It takes as parameters the index of the permit card (for example
	 * "1" for the one in position "0" and so on)
	 */
	public PermitCard getPermitCard(int index) {
		return arrayPermitCard[index-1];
	}
	
	public PermitCard[] getArrayPermitCard(){
		return arrayPermitCard;
	}
	
}
