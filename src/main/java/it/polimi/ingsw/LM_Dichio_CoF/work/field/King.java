package it.polimi.ingsw.LM_Dichio_CoF.work.field;

public class King {

	private City actualCity;

	/* The constructor assigns the king to its initial city */
	public King(City actualCity){
		this.actualCity=actualCity;
	}

	public City getActualCity() {
		return actualCity;
	}
	
}
