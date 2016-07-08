package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.control.Message;
import it.polimi.ingsw.server.control.Player;

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
