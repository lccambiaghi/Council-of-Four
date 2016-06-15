package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.control.Player;

public interface Route {

	void movePlayer(int increment, Player player);
	int getPosition(Player player);
	
}
