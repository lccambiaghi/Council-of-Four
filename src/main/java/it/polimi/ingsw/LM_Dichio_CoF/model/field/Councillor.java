package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.model.Color;

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
