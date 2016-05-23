package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class Balcony {

	String nameBalcony;
	AvailableCouncilor availableCouncilor;
	
	ArrayList<Councilor> arrayListCouncilor = new ArrayList<Councilor>();
	
	public Balcony(AvailableCouncilor availableCouncilor, String nameBalcony){
		
		this.nameBalcony=nameBalcony;
		this.availableCouncilor = availableCouncilor;
		
		
		/*
		 * This for cycle permit to remove a councilor from the AvailableCouncilor
		 * and put it into the arrayListCouncilor.
		 * It is random, because during the creation of AvailableCouncilor (that cointains
		 * all the councilor initially - in the game 24) the arrayList is shuffled
		 */
		
		
		
		for(int i=0;i< Constant.NUMBER_COUNCILERS_PER_BALCONY; i++){
			
			Councilor councilor = availableCouncilor.removeCouncilor();
			arrayListCouncilor.add(councilor);
		}
		
	}
	
	public ArrayList<Councilor> getArrayListCouncilor(){
		return arrayListCouncilor;
	}

	public String getNameBalcony() {
		return nameBalcony;
	}

	
}
