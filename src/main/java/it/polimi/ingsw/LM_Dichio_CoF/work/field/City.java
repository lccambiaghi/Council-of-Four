package it.polimi.ingsw.LM_Dichio_CoF.work.field;

public class City {

	NameCity name;
	Bonus bonus[];
	City nearbyCity[];
	
	
	/*
	 * As we can see the Constructor only receives the name of the city given by the Region
	 */
	City(NameCity name){
		this.name=name;
	}

	public NameCity getName() {
		return name;
	}

	public void setNameCity(NameCity name) {
		this.name = name;
	}
	
	
}
