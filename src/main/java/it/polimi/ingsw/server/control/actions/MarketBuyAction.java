package it.polimi.ingsw.server.control.actions;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.SellingObject;
import it.polimi.ingsw.utils.Message;

import java.util.ArrayList;

/**
 * Preliminary Steps: the method verifies that the arrayList of Selling Objects is not empty
 * then interacts with the player and, if he chooses to buy something, verifies if he can effectively
 * buy the object.
 * 
 * Execute: the method adds the bought object to the buyer and decrements his richness, in addition it
 * increases the richness of the seller, and removes the object from the arrayList of Selling Objects.
 */

public class MarketBuyAction extends Action {

    private SellingObject possibleBuyedObject;
    private int choosenObject;
    private ArrayList <SellingObject> arrayListSellingObjects;

    /**
     * @param match : the match in which the move is being executed
     * @param player : the player who executes the move
     */
    public MarketBuyAction (Match match, Player player){
        this.match=match;
        this.player=player;
    }
   
    /**
     * This is the method used to buy an object. When the selling phase is finished
     * this method is called and first of all it clones the arrayList of players and shuffles it,
     * because we need random turns. Then it ask to the first player if he wants to buy something
     * and if the answer is positive, it shows all the objects that are in the arraylist.
     * The player can choose one of them or quit the market. If it hasn't money and he cannot
     * buy nothing, he can presses 0 and quits the buying phase.
     *
     * @throws InterruptedException
     */
     
    @Override
    public boolean preliminarySteps() throws InterruptedException{    	
        boolean buyingSuccessful;
        arrayListSellingObjects = match.getMarket().getArrayListSellingObjects();
 
        if(arrayListSellingObjects.size()==0){
        	player.getBroker().println(Message.noMarketElements());
        	return false;
        }
        player.getBroker().println(Message.chooseToBuySomething_1_2());
        if(player.getBroker().askInputNumber(1,2)==1){
            player.getBroker().println(Message.askObjectToBuy());
            player.getBroker().println(Message.skipBuying());
            for (int i=0; i<arrayListSellingObjects.size();i++){
                player.getBroker().println((i+1)+ ". " + arrayListSellingObjects.get(i).getObjectInfo());
            }
            do{
                choosenObject = player.getBroker().askInputNumber(0, arrayListSellingObjects.size())-1;
                if(choosenObject==-1){
                    return false;
                }              
                else{
                    possibleBuyedObject=arrayListSellingObjects.get(choosenObject);
                   
                    if (canBuy(possibleBuyedObject.getPrice(), player)){
                        buyingSuccessful=true;
                    }
                    else {
                        buyingSuccessful=false;
                        player.getBroker().println(Message.askObjectToBuy());
                        player.getBroker().println(Message.skipBuying());
                    }
                }
            }
            while(!buyingSuccessful);
            return true;
        }
        return false;
    }
   
    /**
     * The method verify if the buyer has the sufficient richness to buy the object
     * @param price
     * @param playerTurn: the buyer
     * @return true if he has sufficient money, false otherwise
     */
    private boolean canBuy(int price, Player playerTurn) {
        if(checkIfEnoughRichness(playerTurn, price)){
            return true;
        }
        playerTurn.getBroker().println(Message.deniedBuying());
        return false;
    }
   
    private boolean checkIfEnoughRichness(Player playerTurn, int payed) {

        return playerTurn.getRichness() >= payed;
 
}
    
    @Override
    public void execute(){ 
        Player sellingPlayer = possibleBuyedObject.getOwner();
        int price = possibleBuyedObject.getPrice();
       
        possibleBuyedObject.addToPlayer(player);
        arrayListSellingObjects.remove(choosenObject);
       
        sellingPlayer.addRichness(price);
        player.decrementRichness(price);
       
        resultMsg="Player " + player.getNickname() + " bought an object from the market";
	       
	    }
	   
	  @Override	
	  public String getResultMsg(){return resultMsg;}
	
}

