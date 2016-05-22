package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

public class BonusPermitCard extends Bonus{

	public BonusPermitCard(){
		
		Random random = new Random();
		
		this.bonusName = BonusName.getRandomBonusName();
		
		int maxIncrement = bonusName.getMaxIncrement(bonusName);
		
		this.increment = random.nextInt(maxIncrement)+1;
				
	}
	
}
