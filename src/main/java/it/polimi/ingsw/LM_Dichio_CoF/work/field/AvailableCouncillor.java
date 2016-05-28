package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class AvailableCouncillor {

	/*
	 * Singleton DESIGN PATTERN
	 * The constructor is private, so that it is inaccessible from outside,
	 * Once the class is created for the first time, the method "createList()" is invoked
	 */
	
	ArrayList <Councillor> arrayListCouncillor;
	
	
	/// NON POSSO METTERLO SINGLETON :( o si? :D
	/*
	private static AvailableCouncillor instance = null;
	private AvailableCouncillor() { }
	public static AvailableCouncillor getInstance() {
		if (instance == null) {
			instance = new AvailableCouncillor();
			createList();
		}
		return instance;
	}
	*/
	
	public AvailableCouncillor(){
		arrayListCouncillor = new ArrayList<Councillor>();
		createArrayList();
	}
	
	
	/*
	 * This method creates the councilers, put them in order in the arrayList "listCouncilor" and then
	 * shuffle it to have them randomly
	 */
	private void createArrayList(){
		
		int councilorsPerColor = Constant.COUNCILORS_NUMBER_TOTAL /Constant.COLORS_NUMBER;
		
		for(int i = 0; i< Constant.COUNCILORS_NUMBER_TOTAL; i++){
			int indexOfColor =(int)(i/councilorsPerColor);
			Councillor councillor = new Councillor(Color.getColorFromIndex(indexOfColor));
			arrayListCouncillor.add(councillor);
		}
			
		Collections.shuffle(arrayListCouncillor);
		
	}
	
	public ArrayList<Councillor> getArrayListCouncillor() {
		return arrayListCouncillor;
	}
	
	public Councillor removeCouncilor(){
		Councillor councillor = arrayListCouncillor.remove(0);
		return councillor;
	}
	
	
}
