package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;


public class PermitCard {

	City[] arrayCityWhereBuild;
	Bonus[] arrayBonus;
	
	/*
	 * Constructor
	 * It takes as parameter the array of possible cities where build and the config
	 * Then, based on the dimension of it, it create the array of cities 
	 * where the card permit to build
	 */
	public PermitCard(City[] arrayCity, Configurations config) {
		
		City[] arrayPossibleCityWhereBuild = arrayCity.clone();
		
		/*
		 * "numberPossibleCities" contains the MAX number of cities that the permit card
		 * can permit to build. 
		 * It depends on the size of the array "this.possibleCityWhereBuild", because the more
		 * it is long, the more "numberPossibleCities" has to be long as well
		 */
		int numberPossibleCities = Constant.PERMIT_CARD_CITIES_NUMBER_MAX +
				arrayPossibleCityWhereBuild.length - Constant.CITIES_PER_REGION_MIN;
		
		
		/*
		 * Here there is a random in order to have the number of cities that ACTUALLY the permit card
		 * permits to build.
		 * The ranges of it are: "numberPossibleCities" (MAX) and Constant.PERMIT_CARD_CITIES_NUMBER_MIN (MIN)
		 * The result is "lengthArrayCityWhereBuild", used to define the length of "arrayCityWhereBuild",
		 * the array that will contain the real available cities of the permit card
		 */
		Random randomCities = new Random();
		int lengthArrayCityWhereBuild = 
				randomCities.nextInt(numberPossibleCities-Constant.PERMIT_CARD_CITIES_NUMBER_MIN +1)+
				Constant.PERMIT_CARD_CITIES_NUMBER_MIN;
		arrayCityWhereBuild = new City[lengthArrayCityWhereBuild];
		
		/*
		 * This method permit to shuffle the array "possibleCityWhereBuild"
		 */
		Collections.shuffle(Arrays.asList(arrayPossibleCityWhereBuild));
		
		
		/*
		 * Now we assign the first "lengthArrayCityWhereBuild" elements of the array "arrayPossibleCityWhereBuild"
		 * that has been  previously shuffled.
		 */
		for(int i=0; i< lengthArrayCityWhereBuild; i++){
			arrayCityWhereBuild[i]=arrayPossibleCityWhereBuild[i];
		}
		
		/*
		 * Now we assign the bonuses that the permit card will have.
		 * The ranges of it are: "MaxNumberBonusPerPermitCard" (MAX) and MinNumberBonusPerPermitCard (MIN)
		 * The result is "numberBonus", used to define the length of "arrayBonus",
		 * the array that will contain the bonuses of the permit card
		 */
		
		int numberBonus;
		Random randomBonus = new Random();
		numberBonus= randomBonus.nextInt(config.getPermitCardBonusNumberMax()-
				config.getPermitCardBonusNumberMin()+1) +
				config.getPermitCardBonusNumberMin();
		
		if(numberBonus!=0){
			arrayBonus = BonusPermitCard.getArrayBonusPermitCard(numberBonus);
		}
		
	}
	
	public City[] getArrayCityWhereBuild() {
		return arrayCityWhereBuild;
	}

	public Bonus[] getArrayBonus() {
		return arrayBonus;
	}		
	
	public boolean hasBonus(){
		if (arrayBonus== null)
			return false;
		return true;
	}
	
}
