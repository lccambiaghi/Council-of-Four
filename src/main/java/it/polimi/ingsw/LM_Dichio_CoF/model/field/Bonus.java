package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;

public class Bonus {

	BonusName bonusName;
	int increment;
	
	public Bonus(){}
	
	/* The constructor (1) assigns the specified bonusName and increment to the newly created bonus*/
	public Bonus(BonusName bonusName, int increment){
			this.bonusName=bonusName;
			this.increment=increment;
	}

	public BonusName getBonusName() {
		return bonusName;
	}

	public int getIncrement() {
		return increment;
	}

	/* This method checks if BonusName of calling bonus is present in arrayBonus (parameter) */
	public boolean bonusNameIsIn(Bonus[] arrayBonus){
		
		for(int i=0; i<arrayBonus.length; i++){
			if(arrayBonus[i]!=null)
				if(bonusName==arrayBonus[i].getBonusName())
					return true;
		}
		return false;
	}

	public void applyBonus(Player player, Field field) {

		NobilityRoute nobilityRoute;
		
		switch (bonusName){
			case Assistant:
				player.addAssistant(increment);
				break;
			case Richness:
				player.addRichness(increment);
				break;
			case Nobility:
				nobilityRoute = field.getNobilityRoute();
				nobilityRoute.movePlayer(increment, player);
				break;
			case Victory:
				player.addVictory(increment);
				break;
			case Cards:
				for (int i=0; i<increment; i++)
					player.addPoliticCard(new PoliticCard(Color.getRandomColor()));
				break;
			case MainMove:
				player.addMainActionLeft(increment);
		}

	}
}
