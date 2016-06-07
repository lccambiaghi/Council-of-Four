package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.PoliticCard;

public class SellingObject {
	int price;
	Object object;
	
	public SellingObject (Player player){
		
		switch(askForType()){
			case 1: {
				this.object=askPermitCard(player);
			}
			case 2: {
				this.object=askPoliticCard(player);
			}
			case 3: {
				this.object=askAssistant(player);			
			}
		}
		this.price=askPrice(player);
		
	}
	
	private int askForType (){
		int choice;
		System.out.println("Which object do you would to sell?");
		System.out.println("1. Business Permit Tile");
		System.out.println("2. Politic Tile");
		System.out.println("3. Assistant");
		choice=inputNumber(1,3);
		return choice;
		
	}
	
	private PermitCard askPermitCard (Player player){
		ArrayList <PermitCard> playerPermitCards = player.getArrayListPermitCard();
		System.out.println("Which card do you would to sell?");
		System.out.println("Your Cards");
		for (int i=0; i<playerPermitCards.size(); i++){
			System.out.println(i+1);
		}
		int choicePermitCard = inputNumber (1, playerPermitCards.size());
		PermitCard sellingPermitCard = player.getArrayListPermitCard().get(choicePermitCard-1);
		player.getArrayListPermitCard().remove(choicePermitCard-1);
		return sellingPermitCard;
	}
	
	private PoliticCard askPoliticCard (Player player) {
		ArrayList <PoliticCard> playerPoliticCards = player.getArrayListPoliticCard();
		System.out.println("Which card do you would to sell?");
		System.out.println("Your Cards");
		for (int i=0; i<playerPoliticCards.size(); i++){
			System.out.println(i+1 + playerPoliticCards.get(i).getCardColor().toString());
		}
		int choicePoliticCard = inputNumber (1, playerPoliticCards.size());
		PoliticCard sellingPoliticCard = player.getArrayListPoliticCard().get(choicePoliticCard-1);
		player.getArrayListPermitCard().remove(choicePoliticCard-1);
		return sellingPoliticCard;
	}
	
	private int askAssistant (Player player){
		System.out.println("How many assistants do you would to sell? You have:" + player.getAssistant() + "assistants");
		int numberAssistants = inputNumber(1, player.getAssistant());
		player.decrementAssistant(numberAssistants);
		return numberAssistants;
	}
	
	private int askPrice(Player player){
		System.out.println("Which price do you would to set for the object?");
		int price = inputNumber(1, 20);
		return price;
	}
	
	private int inputNumber (int lowerBound, int upperBound){ //TODO throws RemoteException + spostare nella classe della CLI

		Scanner in = new Scanner(System.in);
		int number;

		do {
			while(!in.hasNextInt()){
				System.out.println("Insert a valid input!");
				in.nextInt();
			}
			number=in.nextInt();
			in.nextLine();
		} while(number<lowerBound && number>upperBound);
		//in.close();

		return number;

	}
}
