package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class VictoryRoute implements Route {

	Map <Player, Integer> victoryMap = new HashMap <>();
	
	public VictoryRoute (ArrayList <Player> arrayListPlayer){
		for(int i=0; i<arrayListPlayer.size(); i++)
			victoryMap.put(arrayListPlayer.get(i), 0);
	}
		
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
