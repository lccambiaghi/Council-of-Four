package it.polimi.ingsw.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.utils.Constant;

/**
 * This class handles the arrayList of availableCouncillors.
 *
 * It offers methods to add and remove them from this list.
 */
public class AvailableCouncillors {
	
	private ArrayList <Councillor> arrayListCouncillor;

	/**
	 * The constructor creates the arrayList initially containing all availableCouncillors.
	 * Number of colours are set through a constant.
	 * Every colour has the same cardinality.
	 */
	public AvailableCouncillors(){
		arrayListCouncillor = new ArrayList<>();
		int councilorsPerColor = Constant.COUNCILLORS_NUMBER_TOTAL /Constant.COLORS_NUMBER;

		for(int i = 0; i< Constant.COUNCILLORS_NUMBER_TOTAL; i++){
			int indexOfColor = i/councilorsPerColor;
			Councillor councillor = new Councillor(Color.getColorFromIndex(indexOfColor));
			arrayListCouncillor.add(councillor);
		}

		Collections.shuffle(arrayListCouncillor);
	}

	/**
	 * @param councillor to be added to availableCouncillors
     */
	public void addAvailableCouncillor(Councillor councillor){
		arrayListCouncillor.add(councillor);
	}

	/**
	 * @return councillor removed
     */
	public Councillor removeAvailableCouncillor(){ return arrayListCouncillor.remove(0);}

	//Overloading

	/**
	 * @param color of councillor to be removed
	 * @return councillor removed
     */
	public Councillor removeAvailableCouncillor(Color color) {

		Councillor councillor;

		for (int i = 0; i < arrayListCouncillor.size(); i++) {
			councillor = arrayListCouncillor.get(i);
			if (councillor.getColor() == color)
				return arrayListCouncillor.remove(i);
		}

		return null; //should never be reached

	}

	public List<Councillor> getArrayListCouncillor() {
		return arrayListCouncillor;
	}

}
