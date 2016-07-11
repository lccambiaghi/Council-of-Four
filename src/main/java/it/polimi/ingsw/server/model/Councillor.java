package it.polimi.ingsw.server.model;

/**
 * This class offers all the parameters and method needed to handle a councillor
 */
public class Councillor {

	private final Color color;

	/**
	 * @param color of the newly created councillor
     */
	public Councillor(Color color){
		this.color=color;
	}

	public Color getColor() {
		return color;
	}	
	
}
