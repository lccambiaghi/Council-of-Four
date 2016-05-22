package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

public class Bonus {

	BonusName bonusName;
	int increment;
	
	public Bonus(){
		
		Random random = new Random();
		
		this.bonusName = BonusName.getRandomBonusName();
		
		int maxIncrement = bonusName.getMaxIncrement(bonusName);
		
		this.increment = random.nextInt(maxIncrement);
				
	}

	public BonusName getBonusName() {
		return bonusName;
	}

	public int getIncrement() {
		return increment;
	}
	
}
