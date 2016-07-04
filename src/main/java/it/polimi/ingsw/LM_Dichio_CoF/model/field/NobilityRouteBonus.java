package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;

public class NobilityRouteBonus extends Bonus{
	
	NobilityRouteCell nobilityRouteCell;
	NobilityRoute nobilityMap;
	NobilityRouteCell [] arrayNobilityRouteCell;
	
	@Override
	public void applyBonus(Player playerTurn, Field field){
		arrayNobilityRouteCell=nobilityMap.getArrayNobilityRouteCell();
		nobilityRouteCell=arrayNobilityRouteCell[nobilityMap.getPosition(playerTurn)];
		
		Bonus [] arrayBonus=nobilityRouteCell.getArrayBonus();
		
		for (Bonus bonus : arrayBonus){
			setBonus(bonus, playerTurn, field);
		}
	}
	
	private void setBonus (Bonus bonus, Player player, Field field){
		BonusName bonusName = bonus.getBonusName();
		int increment = bonus.getIncrement();
		Route richnessRoute=field.getRichnessRoute();
		Route victoryRoute=field.getVictoryRoute();
		
		switch (bonusName){
		case Assistant:
			player.addAssistant(increment);
			break;
		case Richness:
			richnessRoute = field.getRichnessRoute();
			richnessRoute.movePlayer(increment,player);
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
