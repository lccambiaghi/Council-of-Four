package it.polimi.ingsw.server.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import it.polimi.ingsw.utils.Constant;

/**
 * This class creates the Permit Cards. It receives the city of the region and then with a random
 * choose the number of cities that will be appear on the Permit Card. Then it assigns the array Bonus to the
 * Permit Card in a random way too.
 */

public class PermitCard {

	private City[] arrayBuildableCities;

	private Bonus[] arrayBonus;
	
	/**
	 * The constructor takes as parameter the array of all possible buildable cities and the config
	 * Then it create the array of ACTUALLY buildable cities
	 * @param arrayCity: the cities of the Region that invokes the creation of FaceUpPermitCardArea
	 * @param config: configurations of the match
	 */
	public PermitCard(City[] arrayCity, Configurations config) {
		
		City[] arrayTotalBuildableCities = arrayCity.clone();

		int numberTotalBuildableCities = Constant.PERMIT_CARD_CITIES_NUMBER_MAX +
				arrayTotalBuildableCities.length - Constant.CITIES_PER_REGION_MIN;
		
		/* Here there is a random in order to have the number of cities that ACTUALLY the permit card
		 * permits to build.
		 * The ranges of it are: "numberTotalBuildableCities" (MAX) and Constant.PERMIT_CARD_CITIES_NUMBER_MIN (MIN)
		 * The result is "numberActuallyBuildableCities", used to define the length of "arrayBuildableCities",
		 * the array that will contain the real available cities of the permit card*/
		Random randomCities = new Random();
		int numberActuallyBuildableCities =
				randomCities.nextInt(numberTotalBuildableCities-Constant.PERMIT_CARD_CITIES_NUMBER_MIN +1)+
				Constant.PERMIT_CARD_CITIES_NUMBER_MIN;
		arrayBuildableCities = new City[numberActuallyBuildableCities];

		Collections.shuffle(Arrays.asList(arrayTotalBuildableCities));

		/* Now we assign the first "numberActuallyBuildableCities" elements of the array "arrayTotalBuildableCities"
		  that has been  previously shuffled. */
		System.arraycopy(arrayTotalBuildableCities, 0, arrayBuildableCities, 0, numberActuallyBuildableCities);
		
		/* Now we assign the bonuses that the permit card will have.
		 * The ranges of it are: "MaxNumberBonusPerPermitCard" (MAX) and MinNumberBonusPerPermitCard (MIN)
		 * The result is "numberBonus", used to define the length of "arrayBonus",
		 * the array that will contain the bonuses of the permit card */
		int numberBonus;
		Random randomBonus = new Random();
		numberBonus= randomBonus.nextInt(config.getPermitCardBonusNumberMax()-
				config.getPermitCardBonusNumberMin()+1) +
				config.getPermitCardBonusNumberMin();
		
		if(numberBonus!=0){
			arrayBonus = PermitCardBonus.getArrayPermitCardBonus(numberBonus);
		}
		
	}

	public boolean hasBonus(){ return arrayBonus != null; }

	public City[] getArrayBuildableCities() {
		return arrayBuildableCities;
	}

	public Bonus[] getArrayBonus() {
		return arrayBonus;
	}

}
