package it.polimi.ingsw.server.model;

/**
 * Each cell has an index and the linked arrayBonus. The method hasBonus return true if the array bonus
 * of the cell is not null.
 */

public class NobilityRouteCell {

	private final Bonus [] arrayBonus;

	/**
	 * The constructor creates the NobilityRouteCell, assigning it index and
	 * the array of bonuses both passed as parameters 
	 * @param arrayBonus: the arrayBonus linked to the cell
	 */
	public NobilityRouteCell(Bonus[] arrayBonus){

		this.arrayBonus=arrayBonus;
		
	}

	/* This method checks if a cell has bonus*/
	public boolean hasBonus(){ return arrayBonus != null; }
	
	public Bonus [] getArrayBonus() {
		return arrayBonus;
	}

}
