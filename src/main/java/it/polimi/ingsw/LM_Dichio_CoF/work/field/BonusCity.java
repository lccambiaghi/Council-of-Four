package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

public class BonusCity extends Bonus{

	public BonusCity(){
		
		Random random = new Random();
		
		this.bonusName = BonusName.getRandomBonusNameWithoutMainMove();
		
		int maxIncrement = bonusName.getMaxIncrement(bonusName);
		
		this.increment = random.nextInt(maxIncrement)+1;
				
	}
	
}
