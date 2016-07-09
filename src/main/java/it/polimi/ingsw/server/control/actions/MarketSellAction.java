package it.polimi.ingsw.server.control.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.server.control.ControlMarket;
import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.PermitCard;
import it.polimi.ingsw.server.model.PoliticCard;
import it.polimi.ingsw.server.model.SellingAssistants;
import it.polimi.ingsw.server.model.SellingObject;
import it.polimi.ingsw.server.model.SellingPermitCard;
import it.polimi.ingsw.server.model.SellingPoliticCard;
import it.polimi.ingsw.utils.Message;

public class MarketSellAction extends Action{
	 
    private SellingObject sellingObject;

    /**
     * @param match : the match in which the move is being executed
     * @param player : the player who executes the move
     */
    public MarketSellAction (Match match, Player player){
            this.match=match;
            this.player=player;
        }
   
    @Override
    public boolean preliminarySteps() throws InterruptedException{
        int choice;
 
        boolean [] sellable = new boolean [3] ;
        int numberPermitCard=player.getArrayListPermitCard().size();
        int numberPoliticCard=player.getArrayListPoliticCard().size();
        int numberAssistants=player.getAssistant();
        int [] stock = new int [3];
        stock[0]=numberPermitCard;
        stock[1]=numberPoliticCard;
        stock[2]=numberAssistants;
        Map <Integer, String> itemsMenu = new HashMap<>();
 
        int counter=1;
        if (numberPermitCard>0 || numberPoliticCard>0 || numberAssistants>0){
           
            player.getBroker().println(Message.chooseToSellSomething_1_2());
            if(player.getBroker().askInputNumber(1,2)==1){
                player.getBroker().println(Message.askForObject());
               
                for(int i=0; i<stock.length; i++){
                    if(stock[i]>0)
                        sellable[i]=true;
                }
                for(int i=0; i<sellable.length;i++){
                    switch(i){
                        case 0:
                            if(sellable[i]){
                                player.getBroker().println(Message.permitCard(counter));
                                itemsMenu.put(counter, "PermitCard");
                                counter++;
                            }
                            break;
                        case 1:
                            if(sellable[i]){
                                player.getBroker().println(Message.politicCard(counter));
                                itemsMenu.put(counter, "PoliticCard");
                                counter++;
                            }
                            break;
                        case 2:
                            if(sellable[i]){
                                player.getBroker().println(Message.assistants(counter));
                                itemsMenu.put(counter, "Assistants");
                            }
                            break;
                    }
                }
                choice= player.getBroker().askInputNumber(1, counter);
                selectObject(player, itemsMenu.get(choice));
                return true;
            }
        }else{
	        player.getBroker().println(Message.deniedSelling());
	        return false;
        }
        return false;
    }
   
    /**
     * This method read the value inserted by the player and call the right method to
     * ask to the player the physical object. When it has finished the object is added
     * to the arraylist of selling object
     * @param playerTurn
     * @param type: it could be "PoliticCard", "PermitCard" or "Assistants"
     * @throws InterruptedException
     */
   
    private void selectObject(Player player, String type) throws InterruptedException{ 
       
        PermitCard chosenPermitCard;
        PoliticCard chosenPoliticCard;
        int chosenAssistants;
        int price;
       
        switch(type){
        case "PermitCard":
            chosenPermitCard=askPermitCard(player);
            price = askPrice(player);
            sellingObject = new SellingPermitCard (chosenPermitCard, player, price);
            break;
        case "PoliticCard":
            chosenPoliticCard = askPoliticCard (player);
            price = askPrice(player);
            sellingObject=new SellingPoliticCard(chosenPoliticCard, player, price);
            break;
        case "Assistants":
            chosenAssistants = askAssistant (player);
            price = askPrice(player);
            sellingObject=new SellingAssistants(chosenAssistants, player, price);
            break;
        }
       
    }
    /**
     * The method askPoliticCard iterates on the Politic Cards of the user and he has to select
     * a value from an lowerbound to an upperbound. After that he has to choose the price of the
     * object.  
     * @param playerTurn
     * @return
     * @throws InterruptedException
     */
   
    private PoliticCard askPoliticCard(Player playerTurn) throws InterruptedException{
        ArrayList <PoliticCard> playerPoliticCards = playerTurn.getArrayListPoliticCard();
       
        playerTurn.getBroker().println(Message.choosePoliticCard(playerPoliticCards));
       
        int chosenPoliticCard= playerTurn.getBroker().askInputNumber (1, playerPoliticCards.size())-1;
       
        PoliticCard sellingPoliticCard = playerTurn.getArrayListPoliticCard().get(chosenPoliticCard);
        playerTurn.getArrayListPoliticCard().remove(chosenPoliticCard);
        return sellingPoliticCard;
    }
    /**
     *
     * The method askPermitCard iterates on the Politic Cards of the user and he has to select
     * a value from an lowerbound to an upperbound. After that he has to choose the price of the
     * object.  
     * @param playerTurn
     * @return
     * @throws InterruptedException
     */
    private PermitCard askPermitCard(Player playerTurn) throws InterruptedException{
        ArrayList <PermitCard> playerPermitCards = playerTurn.getArrayListPermitCard();
       
        playerTurn.getBroker().println(Message.choosePermitCardNoBonus(playerPermitCards));
 
        int chosenPermitCard = playerTurn.getBroker().askInputNumber(1, playerPermitCards.size())-1;
       
        PermitCard sellingPermitCard = playerTurn.getArrayListPermitCard().get(chosenPermitCard);
        playerTurn.getArrayListPermitCard().remove(chosenPermitCard);
       
        return sellingPermitCard;
    }
    /**
     * The method askAssistants shows to the player how many assistants he has; then the player
     * has to insert a number of the assistants that he would like to sell.
     * After that he has to choose the price of them.
     *  
     * @param playerTurn
     * @return
     * @throws InterruptedException
     */
    private int askAssistant(Player playerTurn) throws InterruptedException{
        playerTurn.getBroker().println(Message.chooseNumberAssistantsToSell(playerTurn));
   
        int chosenAssistants= playerTurn.getBroker().askInputNumber(1, playerTurn.getAssistant());
       
        playerTurn.decrementAssistant(chosenAssistants);
        return chosenAssistants;
    }
   
    private int askPrice(Player playerTurn) throws InterruptedException{
        playerTurn.getBroker().println(Message.askPrice());
        return playerTurn.getBroker().askInputNumber(1, 20);
    }
   
    @Override
    public void execute(){
        ControlMarket market = match.getMarket();
       
        market.addSellingObjectToArrayList(sellingObject);
       
        resultMsg="Player " + player.getNickname() + " added a new object in the market";
       
    }
   
    @Override
    public String getResultMsg(){return resultMsg;}
   
}