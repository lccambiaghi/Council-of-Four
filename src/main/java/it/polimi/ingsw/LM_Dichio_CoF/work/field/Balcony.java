package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class Balcony {

	private String nameBalcony;

	private ArrayList<Councillor> arrayListCouncillor = new ArrayList<>();

	/* The constructor assigns the name and adds specified councillors to the balcony*/
	public Balcony(AvailableCouncillor availableCouncillor, String nameBalcony){
		
		this.nameBalcony=nameBalcony;
		
		/*
		 * This for cycle permits to remove a councilor from availableCouncillor
		 * and put it into arrayListCouncillor.
		 * It is random, because during the creation of AvailableCouncillor (which cointains
		 * all councillors initially - in the game 24) the arrayList is shuffled
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
