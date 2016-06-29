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
import it.polimi.ingsw.LM_Dichio_CoF.model.field.SellingObject;

public class Market {

	private SellingObject sellingObject;
	private ArrayList <Player> arrayListPlayer;
	public ArrayList<SellingObject> arrayListSellingObjects = new ArrayList<>();
	private Field field;

	public Market (ArrayList<Player> arrayListPlayer){
		this.arrayListPlayer = new ArrayList<Player>(arrayListPlayer);		
	}
	
	public void startMarket () throws InterruptedException{
		int turn = 0;
		while ((turn+1) % arrayListPlayer.size() != 0){
			startSelling(turn);
			turn++;
		}
		
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
			sellingObject = new SellingObject (playerTurn, chosenPermitCard, price);
			break;
		case "PoliticCard":
			chosenPoliticCard = askPoliticCard (playerTurn);
			price = askPrice(playerTurn);
			sellingObject=new SellingObject(playerTurn, chosenPoliticCard, price);
			break;
		case "Assistants":
			chosenAssistants = askAssistant (playerTurn);
			price = askPrice(playerTurn);
			sellingObject=new SellingObject(playerTurn, chosenAssistants, price);
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
		Collections.shuffle(arrayListPlayer);
		int turn=0;
		
		Player playerTurn=arrayListPlayer.get(turn);
		
		for (SellingObject sellingObject : arrayListSelingObjects){
			playerTurn.getBroker().println(Message.chooseToBuySomething_1_2());
			int accordingToBuy = playerTurn.getBroker().askInputNumber(1,2);
			
			if(accordingToBuy==1){	
				Object object = sellingObject.getObject();
				
				int price = sellingObject.getPrice();
				Player owner = sellingObject.getOwner();
				
				switch (object.getClass().getSimpleName()){
					case "PermitCard": 
						if(canBuy(price,playerTurn,owner)){
							playerTurn.getArrayListPermitCard().add((PermitCard)object);
						}
						break;
					case "PoliticCard": 
						if(canBuy(price,playerTurn,owner)){
							playerTurn.getArrayListPoliticCard().add((PoliticCard)object);
						}
						break;
					case "Integer":
						if(canBuy(price,playerTurn,owner)){
							playerTurn.addAssistant((int)object);
						}
						break;
				}
			}
		}
		
	}
	
	private boolean canBuy(int price, Player playerTurn, Player sellingPlayer) throws InterruptedException{
		Route richnessRoute = field.getRichnessRoute();
		if(checkIfEnoughRichness(playerTurn, price)){
			richnessRoute.movePlayer(price, sellingPlayer);
			richnessRoute.movePlayer(-price, playerTurn);
			arrayListSellingObjects.remove(sellingObject);
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