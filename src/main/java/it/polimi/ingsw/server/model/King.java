package it.polimi.ingsw.server.model;

/**
 * This class is referred to the king and his position on the map. When the field is created, the constructor
 * set the initial city of the King (City J). Then the current position is changeable with set method.
 */

public class King {

	private City currentCity;

	/* The constructor assigns the king to its initial city */
	public King(City currentCity){
		this.currentCity = currentCity;
	}

	public City getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(City newCity) {
		currentCity =newCity;
	}

}
