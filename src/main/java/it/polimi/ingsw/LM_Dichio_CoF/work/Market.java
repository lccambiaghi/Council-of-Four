package it.polimi.ingsw.LM_Dichio_CoF.work;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.Bonus;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Field;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.PermitCard;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Route;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.SellingObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class Market {

	private SellingObject sellingObject;
	private ArrayList <Player> arrayListPlayer;
	public ArrayList<SellingObject> arrayListSellingObjects = new ArrayList<>();
	private Field field;

	Market (ArrayList<Player> arrayListPlayer){
		this.arrayListPlayer = new ArrayList<Player>(arrayListPlayer);		
	}
	
	public void startMarket (){
		int turn = 0;
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
			System.out.println("Would you like to sell something? 1. Yes 2. No");
			if(inputNumber(1,2)==1){
				System.out.println("Which object would you like to sell?");
				for(int i=0; i<stock.length; i++){
					if(stock[i]>0)
						sellable[i]=true;
				}
				for(int i=0; i<sellable.length;i++){
					switch(i){
						case 0:
							if(sellable[i]){
								System.out.println(counter +". " + "Permit Card");
								itemsMenu.put(counter, "PermitCard");
								counter++;
							}
							break;
						case 1:
							if(sellable[i]){
								System.out.println(counter +". " + "Politic Card");
								itemsMenu.put(counter, "PoliticCard");
								counter++;
							}
							break;
						case 2:
							if(sellable[i]){
								System.out.println(counter +". " + "Assistants");
								itemsMenu.put(counter, "Assistants");
							}
							break;
					}
				}
				choice= inputNumber(1, counter);
				sellingObject = new SellingObject (playerTurn, itemsMenu.get(choice));
				arrayListSellingObjects.add(sellingObject);
			}
			else
				turn++;	
		}else{
			System.out.println("You can't sell nothing");
			turn++;
		}
		
		
		
		
			//prossimo giocatore
		
		//startBuying(arrayListSellingObjects);
		
	}
	private void startBuying(ArrayList <SellingObject> arrayListSelingObjects){
		Collections.shuffle(arrayListPlayer);
		int turn=0;
		
		Player playerTurn=arrayListPlayer.get(turn);
		
		for (SellingObject sellingObject : arrayListSelingObjects){
			System.out.println("Would you like to buy this object? 1. Yes 2. No");
			int accordingToBuy = inputNumber(1,2);
			
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
	
	private boolean canBuy(int price, Player playerTurn, Player sellingPlayer){
		Route richnessRoute = field.getRichnessRoute();
		if(checkIfEnoughRichness(playerTurn, price)){
			richnessRoute.movePlayer(price, sellingPlayer);
			richnessRoute.movePlayer(-price, playerTurn);
			arrayListSellingObjects.remove(sellingObject);
			return true;
		}
		System.out.println("You can't buy this object!");
		return false;
	}
	
	private boolean checkIfEnoughRichness(Player playerTurn, int payed) {
		Route richnessRoute = field.getRichnessRoute();

		if (richnessRoute.getPosition(playerTurn)<payed)
			return false;
		else
			return true;

	}

	public int inputNumber(int lowerBound, int upperBound){ //TODO throws RemoteException + spostare nella classe della CLI

		Scanner in = new Scanner(System.in);
		int inputNumber;
		boolean eligibleInput=false;

		do {
			while(!in.hasNextInt()){
				System.out.println("Insert an integer value!");
				in.nextLine();
			}
			inputNumber=in.nextInt();
			in.nextLine();

			if(inputNumber>=lowerBound && inputNumber<=upperBound)
				eligibleInput=true;
			else
				System.out.println("Insert a value between "+ lowerBound
						+ " and " + upperBound);
		} while(!eligibleInput);

		return inputNumber;

	}

}
