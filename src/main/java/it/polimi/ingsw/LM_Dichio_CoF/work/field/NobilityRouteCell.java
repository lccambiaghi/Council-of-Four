package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class NobilityRouteCell {
	
	private NobilityCellBonus [] arrayBonus;
	int i;

	/* The constructor creates the NobilityRouteCell assigning it bonus
	   according to its index, specified as parameter*/
	public NobilityRouteCell(int index){
				
		if(((index%2==0)&&(index!=0)) || (index==19)){
			for(int j=0; j< Constant.NOBILITY_CELL_BONUSES.length; j++)
			arrayBonus = Constant.NOBILITY_CELL_BONUSES [j];
		}
		else 
			arrayBonus=null;
		
	}

	/* This method checks if a cell has bonus*/
	public boolean hasBonus(){ return arrayBonus != null; }
	
	public NobilityCellBonus [] getArrayBonus() {
		return arrayBonus;
	}
	
}
