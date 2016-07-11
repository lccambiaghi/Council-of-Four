package it.polimi.ingsw.server.model;

/**
 * This Class extends Selling Object so it inherit all the methods and attributes. In the constructor it
 * assigns the PermitCard that a player wants to sell, the owner and the price. Then when an other
 * player buys it, the method addToPlayer assign the object to him.
 */

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.utils.Message;

public class SellingPermitCard extends SellingObject {

	private final PermitCard sellingPermitCard;
	
	public SellingPermitCard (PermitCard permitCard, Player player, int price){
		this.sellingPermitCard=permitCard;
		this.owner=player;
		this.price=price;
	}
	
	/**
	 * Info on the sold object
	 */
	
	@Override
	public String getObjectInfo(){
		return Message.getInfoPermitCard(sellingPermitCard, price);
	}
	
	@Override
	public void addToPlayer(Player buyer){
		buyer.getArrayListPermitCard().add(sellingPermitCard);
		
		
	}
	
	
	
	
}
