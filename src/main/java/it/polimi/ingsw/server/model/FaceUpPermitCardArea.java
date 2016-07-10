package it.polimi.ingsw.server.model;

import it.polimi.ingsw.utils.Constant;

/**
 * This class offers the methods useful for acquiring and replacing permit Cards
 * in a region's face up area
 */
public class FaceUpPermitCardArea {
	
	private PermitCard[] arrayPermitCard;

	private final City[] arrayBuildableCities;

	private final Configurations config;

	/**
	 * The constructor receives the arrayBuildableCities belonging to a region and
	 * initializes the arrayPermitCard with FACE_UP_PERMIT_CARD_PER_REGION_NUMBER
	 * number of permit cards
	 *
	 * @param arrayBuildableCities cities in which the region allows to build
	 * @param config needed for the creation of permit cards
     */
	public FaceUpPermitCardArea(City[] arrayBuildableCities, Configurations config){
		
		this.arrayBuildableCities = arrayBuildableCities;

		this.config=config;
		
		arrayPermitCard = new PermitCard[Constant.FACE_UP_PERMIT_CARD_PER_REGION_NUMBER];
		
		for(int i=0; i< arrayPermitCard.length; i++)
			arrayPermitCard[i]= new PermitCard(arrayBuildableCities, config);
		
	}

	/* This method is to take a permit card from the area.
	 * It takes as parameters the index of the permit card
	 * ("0" for arrayPermitCard[0] and so on)
	 * It returns the wanted permit card, creating a new one in the place of the
	 * taken one */

	/**
	 * This method removes the permit card from the face up area,
	 * returns it to the player and replaces it with a new permitCard
	 * @param index of the permitCard
	 * @return permit card
     */
	public PermitCard acquirePermitCard(int index){
		PermitCard chosenPermitCard = arrayPermitCard[index];
		replacePermitCard(index);
		return chosenPermitCard;
	}

	//TODO CHECK
	/**
	 * @param index of the permit card to be replaced
     */
	public void replacePermitCard(int index){
		arrayPermitCard[index]=new PermitCard(arrayBuildableCities, config);
	}

	public PermitCard[] getArrayPermitCard(){
		return arrayPermitCard;
	}
	
}
