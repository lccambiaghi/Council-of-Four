package it.polimi.ingsw.LM_Dichio_CoF.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Bonus;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Field;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.PermitCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Route;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.SellingAssistants;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.SellingObject;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.SellingPermitCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.SellingPoliticCard;

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
	private PoliticCard askPoliticCard(Player playerTurn) throws InterruptedException{
		ArrayList <PoliticCard> playerPoliticCards = playerTurn.getArrayListPoliticCard();
		
		playerTurn.getBroker().println(Message.choosePoliticCard(playerPoliticCards));
		
		int chosenPoliticCard= playerTurn.getBroker().askInputNumber (1, playerPoliticCards.size())-1;
		
		PoliticCard sellingPoliticCard = playerTurn.getArrayListPoliticCard().get(chosenPoliticCard);
		playerTurn.getArrayListPoliticCard().remove(chosenPoliticCard);
		return sellingPoliticCard;
	}
	
	private PermitCard askPermitCard(Player playerTurn) throws InterruptedException{
		ArrayList <PermitCard> playerPermitCards = playerTurn.getArrayListPermitCard();
		
		playerTurn.getBroker().println(Message.choosePermitCard_noBonus(playerPermitCards));

		int chosenPermitCard = playerTurn.getBroker().askInputNumber(1, playerPermitCards.size())-1;
		
		PermitCard sellingPermitCard = playerTurn.getArrayListPermitCard().get(chosenPermitCard);
		playerTurn.getArrayListPermitCard().remove(chosenPermitCard-1);
		
		return sellingPermitCard;
	}
	
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
				for (int i=0; i<arrayListSelingObjects.size();i++){
					playerTurn.getBroker().println((i+1)+ ". " + arrayListSellingObjects.get(i).getObjectInfo());
				}
				do{
					playerTurn.getBroker().println(Message.askObjectToBuy());
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
	
	private boolean canBuy(int price, Player playerTurn, Player sellingPlayer) throws InterruptedException{
		Route richnessRoute = field.getRichnessRoute();
		if(checkIfEnoughRichness(playerTurn, price)){
			richnessRoute.movePlayer(price, sellingPlayer);
			richnessRoute.movePlayer(-price, playerTurn);
			return true;
		}
		playerTurn.getBroker().println(Message.deniedBuying());
		return false;
	}
	
	private boolean checkIfEnoughRichness(Player playerTurn, int payed) {
		Route richnessRoute = field.getRichnessRoute();

		if (richnessRoute.getPosition(playerTurn)<payed)
			return false;
		else
			return true;

	}
}