package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;

public class Bonus {

	BonusName bonusName;
	int increment;
		
	public BonusName getBonusName() {
		return bonusName;
	}

	public int getIncrement() {
		return increment;
	}
	
	public Bonus(){}
	
	/* The constructor (1) assigns the specified bonusName and increment to the newly created bonus*/
	public Bonus(BonusName bonusName, int increment){
			this.bonusName=bonusName;
			this.increment=increment;
	}

	/* The constructor (2)
	  If String in input corresponds to a bonusName, then it creates the corresponding bonus
	  with specified increment */
	public Bonus(String bonusName, int increment){
		
		if(BonusName.containString(bonusName)){
			 this.bonusName = BonusName.stringToBonusName(bonusName);
			 this.increment=increment;
		}
		
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

	public void setBonusName(BonusName bonusName) {
		this.bonusName = bonusName;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public void applyBonus(Player player, Field field) {

		Route richnessRoute;
		Route nobilityRoute;
		Route victoryRoute;

		switch (bonusName){
			case Assistant:
				player.addAssistant(increment);
				break;
			case Richness:
				richnessRoute = field.getRichnessRoute();
				richnessRoute.movePlayer(increment,player);
				break;
			case Nobility:
				nobilityRoute = field.getNobilityRoute();
				nobilityRoute.movePlayer(increment, player);
				break;
			case Victory:
				victoryRoute = field.getVictoryRoute();
				victoryRoute.movePlayer(increment, player);
				break;
			case Cards:
				for (int i=0; i<increment; i++)
					player.addPoliticCard(new PoliticCard());
				break;
		}

	}
}
