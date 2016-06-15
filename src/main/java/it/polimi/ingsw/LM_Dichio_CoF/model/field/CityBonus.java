package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import java.util.Random;

public class CityBonus extends Bonus{

	/* The constructor assigns a random bonus name and assigns a random increment*/
	public CityBonus(){
		
		Random random = new Random();
		
		this.bonusName = BonusName.getRandomCityBonusName();
		
		int maxIncrement = bonusName.getMaxIncrement(bonusName);
		
		this.increment = random.nextInt(maxIncrement)+1;
				
	}
	
	/* This method is static.
	 * It returns an array of "differentBonus" different types of bonus */
	public static Bonus[] getArrayBonusCity(int differentBonus){
		
		Bonus[] arrayBonus = new Bonus[differentBonus];
		Bonus bonus;
		
		for(int i=0; i < differentBonus; i++){
			do 
				bonus = new CityBonus();
			while(bonus.bonusNameIsIn(arrayBonus));
			arrayBonus[i]=bonus;
		}
		
		return arrayBonus;
	}
	
}
