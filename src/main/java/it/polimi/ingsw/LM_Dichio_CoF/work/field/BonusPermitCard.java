package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

public class BonusPermitCard extends Bonus{

	public BonusPermitCard(){
		
		Random random = new Random();
		
		this.bonusName = BonusName.getRandomBonusName();
		
		int maxIncrement = bonusName.getMaxIncrement(bonusName);
		
		this.increment = random.nextInt(maxIncrement)+1;
				
	}
	
	/*
	 * This method is static.
	 * It returns an array of "differentBonus" different types of bonus
	 */
	public static Bonus[] getArrayBonusPermitCard(int differentBonus){
		
		Bonus[] arrayBonus = new Bonus[differentBonus];
		Bonus bonus;
		
		for(int i=0; i < differentBonus; i++){
			do 
				bonus = new BonusPermitCard();
			while(bonus.bonusNameIsIn(arrayBonus));
			arrayBonus[i]=bonus;
		}
		
		return arrayBonus;
	}
	
}
