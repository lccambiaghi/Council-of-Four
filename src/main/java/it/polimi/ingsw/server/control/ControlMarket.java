package it.polimi.ingsw.server.control;

import java.util.ArrayList;
import java.util.Collections;
import it.polimi.ingsw.server.control.actions.Action;
import it.polimi.ingsw.server.control.actions.MarketBuyAction;
import it.polimi.ingsw.server.control.actions.MarketSellAction;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.SellingObject;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.ControlTimer;
import it.polimi.ingsw.utils.Message;

/**
 * This class control the market operations. It interacts with the player asking them what they
 * want to sell and then what they want to buy.
 * When a player sells an object it's added to an arraylist of SellingObject, an useful
 * abstract class that is in the model package.
 */
 
public class ControlMarket {
 
    private ArrayList <Player> players;
    
    private ArrayList<SellingObject> arrayListSellingObjects = new ArrayList<>();
    private Match match;
    private Action action;
    
    private int turn;
    private Player player;
   
    public ControlMarket (ArrayList<Player> arrayListPlayer, Match match){
        this.players = new ArrayList<Player>(arrayListPlayer);
        this.match=match;
    }
   
    /**
     * There is a control on the number of the players, if there are more than two players, 
     * the market starts and notify all the players.
     */
    
    public void startMarket (){
        
    	if(atLeastTwoPlayersConnected()){
    		Broadcast.printlnBroadcastAll(Message.marketHasStarted(), players);
    		startSelling();
    		
    		if(atLeastTwoPlayersConnected())
    			startBuying();
    		
    		Broadcast.printlnBroadcastAll(Message.markedHasFinished(), players);
    	}
        
    } 
    
    /**
     * This method starts the selling phase and manages the turns of the players. For each player
     * it creates a new Action. Then it launches the threadMarketHandler that manages the timer and the
     * eventual disconnection by a player.
     */
    
    private void startSelling(){
    	
    	turn = 0;
    	Broadcast.printlnBroadcastAll(Message.sellingPhase(), players);
    	do {
    		
    		player=players.get(turn);
    		
    		if(player.isConnected()){
    			Broadcast.printlnBroadcastOthers(Message.turnOf(player), players, player);
    			action = new MarketSellAction(match, player);
            	turnMarketHandler();
            	
    		}else if(!player.isMessageDisconnectedSent()){
        		Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOut(player), players, player);
        		player.setMessageDisconnectedSent(true);
        	}
    		
            turn++;
        }
        while ((turn % players.size()) != 0);
    	
    }
   
    private void startBuying (){
    	
        turn = 0;
        ArrayList <Player> playersBuyPhase = new ArrayList <> (players);
        Collections.shuffle(playersBuyPhase);
        Broadcast.printlnBroadcastAll(Message.buyingPhase(), players);
       
        do {
        	
        	player = playersBuyPhase.get(turn);
        	
        	if(player.isConnected()){
        		Broadcast.printlnBroadcastOthers(Message.turnOf(player), players, player);
	            action = new MarketBuyAction(match, player);
	            turnMarketHandler();
	            
        	}else if(!player.isMessageDisconnectedSent()){
        		Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOut(player), playersBuyPhase, player);
        		player.setMessageDisconnectedSent(true);
        	}
        	
            turn++;
        }
        while ((turn % playersBuyPhase.size()) != 0);      
    }
    
    private void turnMarketHandler(){
    	
    	Thread turnMarketThread = new Thread(new Runnable(){
    		public void run() {
	    		try {
	    			player.getBroker().println(Message.yourTurn(Constant.TIMER_SECONDS_MARKET_ACTION));
					if (action.preliminarySteps()){
					    action.execute();
			    		Broadcast.printlnBroadcastOthers(action.getResultMsg(), players, player);
					}
				} catch (InterruptedException e) {}
    		}
		});
    	turnMarketThread.start();
    	
    	new ControlTimer().waitForThreadUntilTimerExpires(turnMarketThread, Constant.TIMER_SECONDS_MARKET_ACTION);
    	
    	if(turnMarketThread.isAlive()){
    		turnMarketThread.interrupt();
    	}
    	
    	if(!player.isConnected()){
    		Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOut(player), players, player);
    		player.setMessageDisconnectedSent(true);
    	}
    	
    }
    
    private ArrayList<Player> getPlayersConnected(){
		ArrayList<Player> ps = new ArrayList<>();
		for(Player p: players){
			if(p.isConnected())
				ps.add(p);
		}
		return ps;
	}
    
    private boolean atLeastTwoPlayersConnected(){
    	ArrayList<Player> playersConnected = getPlayersConnected();
		if((playersConnected==null)||(playersConnected.size()==1)){
			return false;
		}else{
			return true;
		}
	}
    
    public ArrayList<SellingObject> getArrayListSellingObjects() {
        return arrayListSellingObjects;
    }
   
    public void addSellingObjectToArrayList(SellingObject sellingObject){
        arrayListSellingObjects.add(sellingObject);
    }
   
}