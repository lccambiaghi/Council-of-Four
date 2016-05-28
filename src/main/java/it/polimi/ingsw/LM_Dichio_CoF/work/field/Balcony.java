package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class Balcony {

	String nameBalcony;
	AvailableCouncillor availableCouncillor;
	
	ArrayList<Councillor> arrayListCouncillor = new ArrayList<Councillor>();
	
	public Balcony(AvailableCouncillor availableCouncillor, String nameBalcony){
		
		this.nameBalcony=nameBalcony;
		this.availableCouncillor = availableCouncillor;
		
		
		/*
		 * This for cycle permit to remove a councilor from the AvailableCouncillor
		 * and put it into the arrayListCouncillor.
		 * It is random, because during the creation of AvailableCouncillor (that cointains
		 * all the councilor initially - in the game 24) the arrayList is shuffled
		 */
		
		
		
		for(int i = 0; i< Constant.COUNCILLORS_PER_BALCONY_NUMBER; i++){
			
			Councillor councillor = availableCouncillor.removeCouncilor();
			arrayListCouncillor.add(councillor);
		}
		
	}
	
	public ArrayList<Councillor> getArrayListCouncillor(){
		return arrayListCouncillor;
	}

	public String getNameBalcony() {
		return nameBalcony;
	}

	
}
