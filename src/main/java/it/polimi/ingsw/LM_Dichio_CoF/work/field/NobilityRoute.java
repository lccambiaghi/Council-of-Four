package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public class NobilityRoute implements Route{

	NobilityRouteCell nobilityRouteCell;
	Map <Player,Integer> nobilityMap = new HashMap<>();
	
	public NobilityRoute(ArrayList<Player> arrayListPlayer){
		
		for(int i=0; i<arrayListPlayer.size(); i++){
			nobilityMap.put(arrayListPlayer.get(i), 0);	
		}
		
	}
	
	public void movePlayer(int increment, Player player){
		int oldValue = nobilityMap.get(player);
		if (oldValue+increment < Constant.MAX_NOBILITY)
			nobilityMap.replace(player, oldValue+increment);
		else
			nobilityMap.replace(player, Constant.MAX_NOBILITY);
	}
	
	public int getPosition(Player player){
		return nobilityMap.get(player);
	}
	
	
}
