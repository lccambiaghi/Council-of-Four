package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;


public class PermitCard {

	City[] possibleCityWhereBuild;
	City[] arrayCityWhereBuild;
	Bonus[] arrayBonus;
	
	/*
	 * Constructor
	 * It takes as parameter the array of possible cities where build
	 * Then, based on the dimension of it, it create the array of cities 
	 * where the card permit to build
	 */
	public PermitCard(City[] possibleCityWhereBuild) {
		
		this.possibleCityWhereBuild = new City[possibleCityWhereBuild.length];
		
		Random random = new Random();
		
		int numberPossibleCities=Constant.MAX_CITIES_PER_PERMIT_CARD +
				this.possibleCityWhereBuild.length - Constant.MIN_CITIES_PER_REGION;
		
		int lengthCityWhereBuild = 
				random.nextInt(numberPossibleCities-Constant.MIN_CITIES_PER_PERMIT_CARD+1)+
				Constant.MIN_CITIES_PER_PERMIT_CARD;
		
		Collections.shuffle(Arrays.asList(this.possibleCityWhereBuild));
		
		arrayCityWhereBuild = new City[lengthCityWhereBuild];
		
		for(int i=0; i< lengthCityWhereBuild; i++){
			arrayCityWhereBuild[i]=this.possibleCityWhereBuild[i];
		}
		
		arrayBonus = new Bonus[Constant.NUMBER_BONUS_PER_PERMIT_CARD];
		for(int i=0; i < arrayBonus.length; i++){
			arrayBonus[i] = new BonusPermitCard();
		}
		
	}
	
	public City[] getArrayCityWhereBuild() {
		return arrayCityWhereBuild;
	}

	public Bonus[] getArrayBonus() {
		return arrayBonus;
	}		
	
}
