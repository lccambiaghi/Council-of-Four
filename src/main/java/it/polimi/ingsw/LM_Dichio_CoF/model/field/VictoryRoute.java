package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import java.util.ArrayList;
import java.util.Map;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;

import java.util.HashMap;

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
