package it.polimi.ingsw.server.model;

import java.util.Random;

/**
 * This class extends the super class Bonus so inherits all the methods and variables. The class creates
 * an array of random bonuses that later will appear on the Permit Cards. 
 */

public class PermitCardBonus extends Bonus{
	
	/* The constructor assigns a random bonus to the created permit card*/
	private PermitCardBonus(BonusName bonusName, int increment){
		
		this.bonusName=bonusName;
		this.increment=increment;
	}
	
	/**
	 * This method is static.
	 * This method returns an array of different types of bonus, the size is the parameter 
	 * 
	 * @param numberPermitCardBonus: the random number of bonuses that was created in the PermitCard
	 * Class Constructor
	 * 
	 * @return array of bonuses 
	 */
	public static Bonus[] getArrayPermitCardBonus(int numberPermitCardBonus){
		
		Bonus[] arrayPermitCardBonus = new Bonus[numberPermitCardBonus];
		Bonus bonus;
		
		for(int i=0; i < numberPermitCardBonus; i++){
			do {
				Random random = new Random();				
				BonusName bonusName = BonusName.getRandomPermitCardBonusName();
				int maxIncrement = BonusName.getMaxIncrement(bonusName);
				int increment = random.nextInt(maxIncrement)+1;
				bonus = new PermitCardBonus(bonusName, increment);
			}
			while(bonus.bonusNameIsIn(arrayPermitCardBonus));
			arrayPermitCardBonus[i]=bonus;
		}
		
		return arrayPermitCardBonus;
	}
	
}
