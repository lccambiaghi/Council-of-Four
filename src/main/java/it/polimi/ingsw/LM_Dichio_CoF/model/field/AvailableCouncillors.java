package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;

public class AvailableCouncillors {

	/* Singleton DESIGN PATTERN
	   The constructor is private, so that it is inaccessible from outside,
	   Once the class is created for the first time, the method "createList()" is invoked */
	
	private ArrayList <Councillor> arrayListCouncillor;

	/* The constructor creates councillors in order in the arrayList and then
	  shuffles it */
	public AvailableCouncillors(){
		arrayListCouncillor = new ArrayList<Councillor>();
		int councilorsPerColor = Constant.COUNCILLORS_NUMBER_TOTAL /Constant.COLORS_NUMBER;

		for(int i = 0; i< Constant.COUNCILLORS_NUMBER_TOTAL; i++){
			int indexOfColor =(i/councilorsPerColor);
			Councillor councillor = new Councillor(Color.getColorFromIndex(indexOfColor));
			arrayListCouncillor.add(councillor);
		}

		Collections.shuffle(arrayListCouncillor);
	}

	public void addAvailableCouncillor(Councillor councillor){
		arrayListCouncillor.add(councillor);
	}

	/* This method returns the first Councillor of the ArrayList, removing it */
	public Councillor removeAvailableCouncillor(){ return arrayListCouncillor.remove(0);}

	//Overloading
	/* This method returns the Councillor of the specified color if present, else null */
	public Councillor removeAvailableCouncillor(Color color) {

		Councillor councillor;
		// If checkIfColorAvailable(Color color)
		for (int i = 0; i < arrayListCouncillor.size(); i++) {
			councillor = arrayListCouncillor.get(i);
			if (councillor.getColor() == color)
				return arrayListCouncillor.remove(i);
		}

		return null;

	}

	public boolean checkIfColorAvailable(Color color){

		Councillor councillor;

		for (Councillor anArrayListCouncillor : arrayListCouncillor) {
			if (anArrayListCouncillor.getColor() == color)
				return true;
		}

		return false;

	}

	public ArrayList<Councillor> getArrayListCouncillor() {
		return arrayListCouncillor;
	}





}
