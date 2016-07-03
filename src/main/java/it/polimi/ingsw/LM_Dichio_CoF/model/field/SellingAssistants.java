package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;


public class SellingAssistants extends SellingObject{

	int sellingAssistants;
	
	public SellingAssistants(int numberAssistants, Player player, int price){
		this.sellingAssistants=numberAssistants;
		this.owner=player;
		this.price=price;		
	}
	
	@Override
	public String getObjectInfo(){
		return Message.getInfoAssistants(sellingAssistants, price);
	}
	
	@Override
	public void addToPlayer(Player buyer){
		buyer.addAssistant(sellingAssistants);	
	}
	
	
}
