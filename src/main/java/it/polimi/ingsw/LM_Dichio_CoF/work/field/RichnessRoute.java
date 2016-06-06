package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RichnessRoute implements Route {

    private Map<Player, Integer> richnessMap = new HashMap<>();
    //private HashMap<Player, Integer> richnessMap;

    /* The constructor creates richnessMap assigning initial richness to every player */
    public RichnessRoute (ArrayList<Player> arrayListPlayer) {
        for (int i=0; i < arrayListPlayer.size(); i++) {
            richnessMap.put(arrayListPlayer.get(i), Constant.RICHNESS_INITIAL_FIRST_PLAYER + i);
        }
        for (Player player:arrayListPlayer) {
            player.setRichness(getPosition(player));
        }
    }

    /* This method increases/decreases the specified player's richnessMap of the increment specified */
    public void movePlayer(int increment, Player player){
        int oldValue = richnessMap.get(player);
        if(oldValue+increment<Constant.RICHNESS_MAX) {
            richnessMap.replace(player, oldValue + increment);
            player.setRichness(getPosition(player));
        }
        else{
            richnessMap.replace(player, Constant.RICHNESS_MAX);
            player.setRichness(getPosition(player));
        }
    }

    public int getPosition(Player player){
        return richnessMap.get(player);
    }
}
