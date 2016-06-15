package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class VictoryRoute implements Route {

	private Map <Player, Integer> victoryMap = new HashMap <>();

	/* The constructor creates richnessMap assigning initial richness to every player */
	public VictoryRoute (ArrayList <Player> arrayListPlayer){
		for (Player anArrayListPlayer : arrayListPlayer) victoryMap.put(anArrayListPlayer, 0); }

	/* This method increases/decreases the specified player's victoryMap of the increment specified */
	public void movePlayer (int increment, Player player){
		int oldValue = victoryMap.get(player);
		if ((oldValue+increment)<Constant.VICTORY_MAX)
			victoryMap.replace(player, oldValue+increment);
		else 
			victoryMap.replace(player, Constant.VICTORY_MAX);
	}
	
	public int getPosition (Player player){
		return victoryMap.get(player);
	}
	
}
