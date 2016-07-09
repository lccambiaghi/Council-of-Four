package it.polimi.ingsw.server.control.actions;

import java.util.ArrayList;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.SellingObject;
import it.polimi.ingsw.utils.Message;

public class MarketBuyAction extends Action {
	 
    public SellingObject possibleBuyedObject;
    public int choosenObject;
    public ArrayList <SellingObject> arrayListSellingObjects;
   
    public MarketBuyAction (Match match, Player player){
        this.match=match;
        this.player=player;
    }
   
    /**
     * This is the method used to buy an object. When the selling phase is finished
     * start this method and first of all it clone the arrayList of players and shuffles it,
     * because we need random turns. Then it ask to the first player if he wants to buy something
     * and if the answer is positive, it shows all the objects that are in the arraylist.
     * The player can choose one of them or quit the market. If it hasn't money and he cannot
     * buy nothing, he can presses 0 and quits the buying phase.
     * @param arrayListSelingObjects
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
                    buyingSuccessful=true;
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
     * The method verify if the buyer has the sufficient richness to buy the object, then
     * it provides to set the new values of the richness for the buyer and the seller.
     * @param price
     * @param playerTurn: the buyer
     * @param sellingPlayer
     * @return true if he has sufficient money, false otherwise
     * @throws InterruptedException
     */
    private boolean canBuy(int price, Player playerTurn) throws InterruptedException{
        if(checkIfEnoughRichness(playerTurn, price)){
            return true;
        }
        playerTurn.getBroker().println(Message.deniedBuying());
        return false;
    }
   
    private boolean checkIfEnoughRichness(Player playerTurn, int payed) {
   
        if (playerTurn.getRichness()<payed)
            return false;
        else
            return true;
 
}
   
    @Override
    public void execute(){ 
        Player sellingPlayer = possibleBuyedObject.getOwner();
        int price = possibleBuyedObject.getPrice();
       
        possibleBuyedObject.addToPlayer(player);
        arrayListSellingObjects.remove(choosenObject);
       
        sellingPlayer.addRichness(price);
        player.decrementRichness(price);
       
        resultMsg="Player " + player.getNickname() + " buyed an object from the market";
	       
	    }
	   
	  @Override	
	  public String getResultMsg(){return resultMsg;}
	
}

