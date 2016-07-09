package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.Random;

/**
 * This class offers the method to set an array of random bonuses to a city
 */
public class CityBonus extends Bonus implements Serializable{	
	
	public CityBonus(BonusName name, int increment){

		this.bonusName=name;

		this.increment=increment;

	}

	/**
	 * This method creates an array of bonuses, having random bonusName
	 * and random increment, upper bounded by max increment
	 *
	 * @param size of the array to create
	 * @return array of random bonuses
     */
	public static Bonus[] createArrayCityBonus(int size){
		
		Bonus[] arrayBonus = new Bonus[size];
		Bonus bonus;
		
		for(int i=0; i < size; i++){
			do {
				Random random = new Random();
				BonusName bonusName = BonusName.getRandomCityBonusName();
				int maxIncrement = BonusName.getMaxIncrement(bonusName);
				int increment = random.nextInt(maxIncrement)+1;				
				bonus = new CityBonus(bonusName, increment);
			}
			while(bonus.bonusNameIsIn(arrayBonus));
			arrayBonus[i]=bonus;
		}
		
		return arrayBonus;
	}
	
}
