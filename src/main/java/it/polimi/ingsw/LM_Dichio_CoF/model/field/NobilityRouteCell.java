package it.polimi.ingsw.LM_Dichio_CoF.work.field;

public class NobilityRouteCell {
	
	int index;
	private Bonus [] arrayBonus;

	/* The constructor creates the NobilityRouteCell, assigning it index and
	   the array of bonuses both passed as parameters */
	public NobilityRouteCell(int index, Bonus[] arrayBonus){

		this.index=index;
		this.arrayBonus=arrayBonus;
		
	}

	/* This method checks if a cell has bonus*/
	public boolean hasBonus(){ return arrayBonus != null; }
	
	public Bonus [] getArrayBonus() {
		return arrayBonus;
	}
	
	public void setIndex(int index){
		this.index=index;
	}
	
}
