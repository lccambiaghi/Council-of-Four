package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public interface Route {

	void movePlayer(int increment, Player player);
	int getPosition(Player player);
	
}
