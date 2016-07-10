package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.control.Player;

/**
 * 
 * The abstract class that all the Selling Object Classes need to extends
 *
 */
public abstract class SellingObject {
	public int price;
	public Player owner;
	
	public int getPrice() {
		return price;
	}
	
	/**
	 * The info of a sold object, dynamically loaded when we works on Array of Selling Object type.
	 * @return
	 */
	public Player getOwner() {
		return owner;
	}
	
	public abstract String getObjectInfo();
	
	public abstract void addToPlayer(Player buyer);
}
