package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;

public class SellingPoliticCard extends SellingObject{

	PoliticCard sellingPoliticCard;
	
	public SellingPoliticCard (PoliticCard politicCard, Player player, int price){
		this.sellingPoliticCard=politicCard;
		this.owner=player;
		this.price=price;
	}
	
	@Override
	public String getObjectInfo(){		
		return Message.getInfoPoliticCard(sellingPoliticCard, price);
	}
	
	@Override
	public void addToPlayer(Player buyer){
		buyer.getArrayListPoliticCard().add(sellingPoliticCard);	
	}
	
	
	
	
}
