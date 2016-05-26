package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

public class BonusCity extends Bonus{

	Integer bonusIncrement;
	String bonusNameFromMap;
	
	public BonusCity(){
		
		Random random = new Random();
		
		this.bonusName = BonusName.getRandomBonusNameWithoutMainMove();
		
		int maxIncrement = bonusName.getMaxIncrement(bonusName);
		
		this.increment = random.nextInt(maxIncrement)+1;
				
	}
		
	public BonusCity(String bonusName, Integer bonusIncrement){
		
		if(BonusName.containString(bonusName)){
			 this.bonusName = BonusName.stringToBonusName(bonusName);
			 this.bonusIncrement=bonusIncrement;
		}
		
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
