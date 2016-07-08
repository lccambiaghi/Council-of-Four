package it.polimi.ingsw.server.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.server.model.PoliticCard;
import it.polimi.ingsw.server.control.actions.Action;
import it.polimi.ingsw.server.control.actions.MarketBuyAction;
import it.polimi.ingsw.server.control.actions.MarketSellAction;
import it.polimi.ingsw.server.model.Field;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.PermitCard;
import it.polimi.ingsw.server.model.SellingAssistants;
import it.polimi.ingsw.server.model.SellingObject;
import it.polimi.ingsw.server.model.SellingPermitCard;
import it.polimi.ingsw.server.model.SellingPoliticCard;

/**
 * This class control the market operations. It interacts with the player asking them what they
 * want to sell and then what they want to buy.
 * When a player sells an object it's added to an arraylist of SellingObject, an useful
 * abstract class that is in the model package.
 */
 
public class MarketHandler {
 
    private ArrayList <Player> arrayListPlayers;
    public ArrayList<SellingObject> arrayListSellingObjects = new ArrayList<>();
    private Match match;
   
    public MarketHandler (ArrayList<Player> arrayListPlayer, Match match){
        this.arrayListPlayers = new ArrayList<Player>(arrayListPlayer);
        this.match=match;
    }
   
    public void startMarket () throws InterruptedException{
        int turn = 0;
        Action action;
       
        do {
            action = new MarketSellAction(match, arrayListPlayers.get(turn));
            if (action.preliminarySteps()) {
                action.execute();
                broadcastOthers(action.getResultMsg(), arrayListPlayers, arrayListPlayers.get(turn));
            }
            turn++;
        }
        while ((turn % arrayListPlayers.size()) != 0);
       
        startBuying();
    }  
   
    public void startBuying () throws InterruptedException{
        int turn = 0;
        Action action;
        ArrayList <Player> arrayListPlayerBuyPhase = new ArrayList <> (arrayListPlayers);
        Collections.shuffle(arrayListPlayerBuyPhase);
       
        do {
            action = new MarketBuyAction(match, arrayListPlayerBuyPhase.get(turn));
            if (action.preliminarySteps()) {
                action.execute();
                broadcastOthers(action.getResultMsg(), arrayListPlayerBuyPhase, arrayListPlayerBuyPhase.get(turn));
            }
            turn++;
        }
        while ((turn % arrayListPlayerBuyPhase.size()) != 0);      
    }
 
    public ArrayList<SellingObject> getArrayListSellingObjects() {
        return arrayListSellingObjects;
    }
   
    public void addArrayListSellingObject(SellingObject sellingObject){
        arrayListSellingObjects.add(sellingObject);
    }
    private void broadcastOthers(String string, ArrayList<Player> players, Player playerNot) throws InterruptedException{
        //FOR_TEST
        if(Constant.test)
            System.out.println(string);
        else
            Broadcast.printlnBroadcastOthers(string, players, playerNot);
    }
   
}