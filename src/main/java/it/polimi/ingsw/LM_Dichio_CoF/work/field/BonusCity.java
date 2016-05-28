package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

public class BonusCity extends Bonus{

	public BonusCity(){
		
		Random random = new Random();
		
		this.bonusName = BonusName.getRandomBonusNameWithoutMainMove();
		
		int maxIncrement = bonusName.getMaxIncrement(bonusName);
		
		this.increment = random.nextInt(maxIncrement)+1;
				
	}
	
	/*
	 * This method is static.
	 * It returns an array of "differentBonus" different types of bonus
	 */
	public static Bonus[] getArrayBonusCity(int differentBonus){
		
		Bonus[] arrayBonus = new Bonus[differentBonus];
		Bonus bonus;
		
		for(int i=0; i < differentBonus; i++){
			do 
				bonus = new BonusCity();
			while(bonus.bonusNameIsIn(arrayBonus));
			arrayBonus[i]=bonus;
		}
		
		return arrayBonus;
	}
	
}
