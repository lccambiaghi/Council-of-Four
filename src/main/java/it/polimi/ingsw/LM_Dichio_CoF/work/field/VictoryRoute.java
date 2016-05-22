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
		if ((oldValue+increment)<Constant.MAX_VICTORY)
			victoryMap.replace(player, oldValue+increment);
		else 
			victoryMap.replace(player, Constant.MAX_VICTORY);
	}
	
	public int getPosition (Player player){
		return victoryMap.get(player);
	}
	
}
