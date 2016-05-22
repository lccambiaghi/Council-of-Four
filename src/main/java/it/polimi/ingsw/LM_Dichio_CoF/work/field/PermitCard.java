package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;


public class PermitCard {

	City[] arrayCityWhereBuild;
	
	/*
	 * Constructor
	 * It takes as parameter the array of possible cities where build
	 * Then, based on the dimension of it, it create the array of cities 
	 * where the card permit to build
	 */
	public PermitCard(City[] possibleCityWhereBuild) {
		
		Random random = new Random();
		
		int numberPossibleCities=Constant.MAX_CITIES_PER_PERMIT_CARD +
				possibleCityWhereBuild.length - Constant.MIN_CITIES_PER_REGION;
		
		int lengthCityWhereBuild = random.nextInt(numberPossibleCities)+
				Constant.MIN_CITIES_PER_PERMIT_CARD;
		
		Collections.shuffle(Arrays.asList(possibleCityWhereBuild));
		
		arrayCityWhereBuild = new City[lengthCityWhereBuild];
		
		for(int i=0; i< lengthCityWhereBuild; i++){
			arrayCityWhereBuild[i]=possibleCityWhereBuild[i];
		}
		
	}

	public City[] getArrayCityWhereBuild() {
		return arrayCityWhereBuild;
	}	
	
	
}
