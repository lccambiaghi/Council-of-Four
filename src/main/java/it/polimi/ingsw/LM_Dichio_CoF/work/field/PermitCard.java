package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;


public class PermitCard {

	City[] arrayCityWhereBuild;
	Bonus[] arrayBonus;
	
	/*
	 * Constructor
	 * It takes as parameter the array of possible cities where build
	 * Then, based on the dimension of it, it create the array of cities 
	 * where the card permit to build
	 */
	public PermitCard(City[] arrayCity) {
		
		City[] arrayPossibleCityWhereBuild = arrayCity.clone();
		
		/*
		 * "numberPossibleCities" contains the MAX number of cities that the permit card
		 * can permit to build. 
		 * It depends on the size of the array "this.possibleCityWhereBuild", because the more
		 * it is long, the more "numberPossibleCities" has to be long as well
		 */
		int numberPossibleCities = Constant.MAX_CITIES_PER_PERMIT_CARD +
				arrayPossibleCityWhereBuild.length - Constant.MIN_CITIES_PER_REGION;
		
		
		/*
		 * Here there is a random in order to have the number of cities that ACTUALLY the permit card
		 * permits to build.
		 * The ranges of it are: "numberPossibleCities" (MAX) and Constant.MIN_CITIES_PER_PERMIT_CARD (MIN)
		 * The result is "lengthArrayCityWhereBuild", used to define the length of "arrayCityWhereBuild",
		 * the array that will contain the real available cities of the permit card
		 */
		Random random = new Random();
		int lengthArrayCityWhereBuild = 
				random.nextInt(numberPossibleCities-Constant.MIN_CITIES_PER_PERMIT_CARD+1)+
				Constant.MIN_CITIES_PER_PERMIT_CARD;
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
		 * They are in a fixed number: Constant.NUMBER_BONUS_PER_PERMIT_CARD
		 */
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
