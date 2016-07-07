package it.polimi.ingsw.LM_Dichio_CoF.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Field;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.PermitCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.SellingAssistants;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.SellingObject;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.SellingPermitCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.SellingPoliticCard;

/**
 * This class control the market operations. It interacts with the player asking them what they
 * want to sell and then what they want to buy. 
 * When a player sells an object it's added to an arraylist of SellingObject, an useful
 * abstract class that is in the model package.
 */

public class Market {

	private SellingObject sellingObject;
	private ArrayList <Player> arrayListPlayer;
	public ArrayList<SellingObject> arrayListSellingObjects = new ArrayList<>();
	private Field field;

	public Market (ArrayList<Player> arrayListPlayer, Field field){
		this.arrayListPlayer = new ArrayList<Player>(arrayListPlayer);
		this.field=field;
	}
	
	public void startMarket () throws InterruptedException{
		int turn = 0;
		do {
			startSelling(turn);
			turn++;
			}
		while ((turn % arrayListPlayer.size()) != 0);
				
		startBuying(arrayListSellingObjects);
	}

	/**
	 * This is the method that ask to the player which object he would like to sell. 
	 * First of all it verify that the user, identified by the turm parameter, has something
	 * to sell. Then there is an HashMap used to create an interactive list of items,
	 * for example if the player doesn't have Assistants he won't see "Assistants" on his
	 * monitor, so he can't select it. 
	 * @param turn
	 * @throws InterruptedException
	 */
	
	private void startSelling(int turn) throws InterruptedException{
		int choice;

		boolean [] sellable = new boolean [3] ;
		Player playerTurn=arrayListPlayer.get(turn);
		int numberPermitCard=playerTurn.getArrayListPermitCard().size();
		int numberPoliticCard=playerTurn.getArrayListPoliticCard().size();
		int numberAssistants=playerTurn.getAssistant();
		int [] stock = new int [3];
		stock[0]=numberPermitCard;
		stock[1]=numberPoliticCard;
		stock[2]=numberAssistants;
		Map <Integer, String> itemsMenu = new HashMap<>();

		int counter=1;
		if (numberPermitCard>0 || numberPoliticCard>0 || numberAssistants>0){
			
			playerTurn.getBroker().println(Message.chooseToSellSomething_1_2());
			if(playerTurn.getBroker().askInputNumber(1,2)==1){
				playerTurn.getBroker().println(Message.askForObject());
				
				for(int i=0; i<stock.length; i++){
					if(stock[i]>0)
						sellable[i]=true;
				}
				for(int i=0; i<sellable.length;i++){
					switch(i){
						case 0:
							if(sellable[i]){
								playerTurn.getBroker().println(Message.permitCard(counter));
								itemsMenu.put(counter, "PermitCard");
								counter++;
							}
							break;
						case 1:
							if(sellable[i]){
								playerTurn.getBroker().println(Message.politicCard(counter));
								itemsMenu.put(counter, "PoliticCard");
								counter++;
							}
							break;
						case 2:
							if(sellable[i]){
								playerTurn.getBroker().println(Message.assistants(counter));
								itemsMenu.put(counter, "Assistants");
							}
							break;
					}
				}
				choice= playerTurn.getBroker().askInputNumber(1, counter);
				selectObject(playerTurn, itemsMenu.get(choice));
			}
		}else{
			playerTurn.getBroker().println(Message.deniedSelling());
		}
	}
	/**
	 * This method read the value inserted by the player and call the right method to
	 * ask to the player the physical object. When it has finished the object is added
	 * to the arraylist of selling object
	 * @param playerTurn
	 * @param type: it could be "PoliticCard", "PermitCard" or "Assistants"
	 * @throws InterruptedException
	 */
	private void selectObject(Player playerTurn, String type) throws InterruptedException{	
		
		PermitCard chosenPermitCard;
		PoliticCard chosenPoliticCard;
		int chosenAssistants;
		int price;
		
		switch(type){
		case "PermitCard":
			chosenPermitCard=askPermitCard(playerTurn);
			price = askPrice(playerTurn);
			sellingObject = new SellingPermitCard (chosenPermitCard, playerTurn, price);
			break;
		case "PoliticCard":
			chosenPoliticCard = askPoliticCard (playerTurn);
			price = askPrice(playerTurn);
			sellingObject=new SellingPoliticCard(chosenPoliticCard, playerTurn, price);
			break;
		case "Assistants":
			chosenAssistants = askAssistant (playerTurn);
			price = askPrice(playerTurn);
			sellingObject=new SellingAssistants(chosenAssistants, playerTurn, price);
			break;
		}
		
		arrayListSellingObjects.add(sellingObject);
		
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
		playerTurn.getBroker().println(Message.chooseAssistants(playerTurn));
	
		int chosenAssistants= playerTurn.getBroker().askInputNumber(1, playerTurn.getAssistant());
		
		playerTurn.decrementAssistant(chosenAssistants);
		return chosenAssistants;
	}
	
	private int askPrice(Player playerTurn) throws InterruptedException{
		playerTurn.getBroker().println(Message.askPrice());
		return playerTurn.getBroker().askInputNumber(1, 20);
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
	
	private void startBuying(ArrayList <SellingObject> arrayListSelingObjects) throws InterruptedException{
		ArrayList <Player> arrayListPlayerMarket = arrayListPlayer;
		Collections.shuffle(arrayListPlayerMarket);
		int turn=0;
		boolean buyingSuccessful;
		
		do{
			Player playerTurn=arrayListPlayerMarket.get(turn);
			playerTurn.getBroker().println(Message.chooseToBuySomething_1_2());
			if(playerTurn.getBroker().askInputNumber(1,2)==1){
				playerTurn.getBroker().println(Message.skipBuying());
				playerTurn.getBroker().println(Message.askObjectToBuy());
				for (int i=0; i<arrayListSelingObjects.size();i++){
					playerTurn.getBroker().println((i+1)+ ". " + arrayListSellingObjects.get(i).getObjectInfo());
				}
				do{
					int choosenObject = playerTurn.getBroker().askInputNumber(0, arrayListSelingObjects.size())-1;
					if(choosenObject==-1){
						buyingSuccessful=true;
						turn++;
					}				
					else{
						SellingObject possibleBuyedObject=arrayListSelingObjects.get(choosenObject);
						
						if (canBuy(possibleBuyedObject.getPrice(), playerTurn, possibleBuyedObject.getOwner())){
							possibleBuyedObject.addToPlayer(playerTurn);
							arrayListSelingObjects.remove(choosenObject);
							buyingSuccessful=true;
						}
						else {
							
							buyingSuccessful=false;
						}
					}
				}
				while(!buyingSuccessful);
			}
			turn++;
		}
		while ((turn % arrayListPlayerMarket.size()) != 0);
		
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
	private boolean canBuy(int price, Player playerTurn, Player sellingPlayer) throws InterruptedException{
		if(checkIfEnoughRichness(playerTurn, price)){
			sellingPlayer.addRichness(price);
			playerTurn.decrementRichness(price);
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
}