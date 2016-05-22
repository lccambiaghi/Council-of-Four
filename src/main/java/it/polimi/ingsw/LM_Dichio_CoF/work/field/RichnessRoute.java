package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RichnessRoute implements Route {

    private static Map<Player, Integer> richnessMap = new HashMap<>();
    //private HashMap<Player, Integer> richnessMap;

    public RichnessRoute (ArrayList<Player> arrayListPlayer) {
        for (int i=0; i < arrayListPlayer.size(); i++) {
            richnessMap.put(arrayListPlayer.get(i), Constant.INITIAL_RICHNESS_FIRST_PLAYER + i);
        }
    }

    public void movePlayer(int increment, Player player){
        int oldValue = richnessMap.get(player);
        if(oldValue+increment<Constant.MAX_RICHNESS)
            richnessMap.replace(player, oldValue+increment);
        else
            richnessMap.replace(player, Constant.MAX_RICHNESS);
    }

    public int getPosition(Player player){
        return richnessMap.get(player);
    }
}
