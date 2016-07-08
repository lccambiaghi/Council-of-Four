package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Color;

public class Councillor {

	private Color color;

	/* The constructor assigns the color passed */
	public Councillor(Color color){
		this.color=color;
	}

	public Color getColor() {
		return color;
	}	
	
}
