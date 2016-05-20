package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Color;

public class AvailableCouncilor {

	/*
	 * Singleton DESIGN PATTERN
	 * The constructor is private, so that it is inaccessible from outside,
	 * Once the class is created for the first time, the method "createList()" is invoked
	 */
	
	ArrayList <Councilor> arrayListCouncilor;
	
	
	/// NON POSSO METTERLO SINGLETON :( o si? :D
	/*
	private static AvailableCouncilor instance = null; 
	private AvailableCouncilor() { } 
	public static AvailableCouncilor getInstance() {
		if (instance == null) {
			instance = new AvailableCouncilor();
			createList();
		}
		return instance;
	}
	*/
	
	public AvailableCouncilor(){
		arrayListCouncilor = new ArrayList<Councilor>();
		createArrayList();
	}
	
	
	/*
	 * This method creates the councilers, put them in order in the arrayList "listCouncilor" and then
	 * shuffle it to have them randomly
	 */
	private void createArrayList(){
		
		int councilorsPerColor = Constant.TOTAL_NUMBER_COUNCILORS/Constant.NUMBER_OF_COLORS;
		
		for(int i=0; i< Constant.TOTAL_NUMBER_COUNCILORS; i++){
			int indexOfColor =(int)(i/councilorsPerColor);
			Councilor councilor = new Councilor(Color.getColorFromIndex(indexOfColor));
			arrayListCouncilor.add(councilor);
		}
			
		Collections.shuffle(arrayListCouncilor);
		
	}
	
	public ArrayList<Councilor> getArrayListCouncilor() {
		return arrayListCouncilor;
	}
	
	public Councilor removeCouncilor(){
		Councilor councilor = arrayListCouncilor.remove(0);
		return councilor;
	}
	
	
}
