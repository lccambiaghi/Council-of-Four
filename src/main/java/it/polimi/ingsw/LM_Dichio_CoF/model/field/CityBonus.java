package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import java.io.Serializable;
import java.util.Random;

public class CityBonus extends Bonus implements Serializable{
	
	/* The constructor assigns a random bonus name and assigns a random increment*/
	public CityBonus(){
		
		Random random = new Random();
		
		this.bonusName = BonusName.getRandomCityBonusName();
		
		int maxIncrement = bonusName.getMaxIncrement(bonusName);
		
		this.increment = random.nextInt(maxIncrement)+1;
				
	}
	
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
			do 
				bonus = new CityBonus();
			while(bonus.bonusNameIsIn(arrayBonus));
			arrayBonus[i]=bonus;
		}
		
		return arrayBonus;
	}
	
}
