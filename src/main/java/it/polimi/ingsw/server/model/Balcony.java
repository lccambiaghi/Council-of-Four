package it.polimi.ingsw.server.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.utils.Constant;

/**
 * The Balcony class offers the methods to operate on the councillors of his balcony.
 * It has the attribute nameBalcony in order to get to the belonging Region.
 * It has been preferred to a Region attribute because of the king's balcony.
 */
public class Balcony {

	private String nameBalcony;

	private ArrayList<Councillor> arrayListCouncillor = new ArrayList<>();

	/**
	 * The constructor assigns the region name and adds councillors from available councillors to the balcony
	 *
	 * @param availableCouncillors from which the Balcony removes the needed councillors
	 * @param nameBalcony name to be assigned to the region
     */
	public Balcony(AvailableCouncillors availableCouncillors, String nameBalcony){
		
		this.nameBalcony=nameBalcony;
		
		/* This for cycle permits to remove a councilor from availableCouncillors
		 * and put it into arrayListCouncillor.
		 * It is random, because during the creation of AvailableCouncillors (which cointains
		 * all councillors initially - in the game 24) the arrayList is shuffled
		 */
		
		for(int i = 0; i< Constant.COUNCILLORS_PER_BALCONY_NUMBER; i++){
			
			Councillor councillor = availableCouncillors.removeAvailableCouncillor();
			arrayListCouncillor.add(councillor);
		}
		
	}

	/**
	 * This method puts the councillor passed as parameter on the leftmost
	 * position, slides the other councillors on the balcony,
	 * adds the fallen councillor to available councillors
	 *
	 * @param councillor to be elected
	 * @param availableCouncillors to which the method adds the "fallen" councillor
     */
	public void electCouncillor(Councillor councillor, AvailableCouncillors availableCouncillors) {
		arrayListCouncillor.add(0,councillor);
		availableCouncillors.addAvailableCouncillor(arrayListCouncillor.remove(arrayListCouncillor.size()-1));
	}

	public List<Councillor> getArrayListCouncillor(){
		return arrayListCouncillor;
	}

	public String getNameBalcony() {
		return nameBalcony;
	}

}
