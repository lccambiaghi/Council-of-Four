package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.utils.Message;

/**
 * This Class extends Selling Object so it inherit all the methods and attributes. In the constructor it
 * assigns the PoliticCard that a player wants to sell, the owner and the price. Then when an other
 * player buys it, the method addToPlayer assign the object to him.
 */


public class SellingPoliticCard extends SellingObject{

	PoliticCard sellingPoliticCard;
	
	public SellingPoliticCard (PoliticCard politicCard, Player player, int price){
		this.sellingPoliticCard=politicCard;
		this.owner=player;
		this.price=price;
	}
	
	/**
	 * Info on the sold object 
	 */
	
	@Override
	public String getObjectInfo(){		
		return Message.getInfoPoliticCard(sellingPoliticCard, price);
	}
	
	@Override
	public void addToPlayer(Player buyer){
		buyer.getArrayListPoliticCard().add(sellingPoliticCard);	
	}
	
	
	
	
}
