package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.control.Player;


public abstract class SellingObject {
	public int price;
	public Player owner;
	
	public int getPrice() {
		return price;
	}

	public Player getOwner() {
		return owner;
	}
	
	public abstract String getObjectInfo();
	
	public abstract void addToPlayer(Player buyer);
}
