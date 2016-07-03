package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;

public class SellingPermitCard extends SellingObject {

	PermitCard sellingPermitCard;
	
	public SellingPermitCard (PermitCard permitCard, Player player, int price){
		this.sellingPermitCard=permitCard;
		this.owner=player;
		this.price=price;
	}
	
	@Override
	public String getObjectInfo(){
		return Message.getInfoPermitCard(sellingPermitCard, price);
	}
	
	@Override
	public void addToPlayer(Player buyer){
		buyer.getArrayListPermitCard().add(sellingPermitCard);
		
		
	}
	
	
	
	
}
