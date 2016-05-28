package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

public class PermitCardBonus extends Bonus{

	/* The constructor assigns a random bonus to the created permit card*/
	public PermitCardBonus(){
		
		Random random = new Random();
		
		this.bonusName = BonusName.getRandomBonusName();
		
		int maxIncrement = bonusName.getMaxIncrement(bonusName);
		
		this.increment = random.nextInt(maxIncrement)+1;
				
	}
	
	/* This method is static.
	 * This method returns an array of different types of bonus, the size is the parameter */
	public static Bonus[] getArrayPermitCardBonus(int numberPermitCardBonus){
		
		Bonus[] arrayPermitCardBonus = new Bonus[numberPermitCardBonus];
		Bonus bonus;
		
		for(int i=0; i < numberPermitCardBonus; i++){
			do 
				bonus = new PermitCardBonus();
			while(bonus.bonusNameIsIn(arrayPermitCardBonus));
			arrayPermitCardBonus[i]=bonus;
		}
		
		return arrayPermitCardBonus;
	}
	
}
