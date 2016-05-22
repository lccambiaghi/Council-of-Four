package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public interface Route {

	public abstract void movePlayer(int increment, Player player);
	public abstract int getPosition(Player player);
	
}
