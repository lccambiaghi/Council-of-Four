package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.Random;

public class CityBonus extends Bonus implements Serializable{	
	
	public CityBonus(BonusName name, int increment){
		this.bonusName=name;
		this.increment=increment;
	}
	
	/* This method is static.
	 * It returns an array of different types of bonus of the specified size */
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
