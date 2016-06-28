package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.control.Player;


public class SellingObject {
	private int price;
	private Object object;
	private Player owner;
	
	public SellingObject (Player player, Object object, int price){
		
		this.object=object;
		this.price=price;
		this.owner=player;
	}
	
	public int getPrice() {
		return price;
	}

	public Object getObject() {
		return object;
	}

	public Player getOwner() {
		return owner;
	}

}
