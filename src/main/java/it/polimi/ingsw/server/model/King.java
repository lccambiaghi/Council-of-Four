package it.polimi.ingsw.server.model;

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
