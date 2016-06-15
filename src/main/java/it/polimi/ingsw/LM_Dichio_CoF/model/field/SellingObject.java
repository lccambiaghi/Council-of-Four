package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;

public class SellingObject {
	private int price;
	private Object object;
	private Player owner;
	
	public SellingObject (Player player, String string){
		
		switch(string){
			case "PermitCard":
				this.object=askPermitCard(player);
				break;
			case "PoliticCard":
				this.object=askPoliticCard(player);
				break;
			case "Assistants":
				this.object=askAssistant(player);
				break;
		}
		this.price=askPrice();
		this.owner=player;
	}

	
	private PermitCard askPermitCard (Player player){
		ArrayList <PermitCard> playerPermitCards = player.getArrayListPermitCard();
		System.out.println("Which Business Permit Tile would you like to sell?");
		System.out.println("Your Cards");
	
		for (int i=0; i<playerPermitCards.size(); i++){
			System.out.print(i+1+". Buildable Cities: ");
			for(City city: playerPermitCards.get(i).getArrayBuildableCities()){
				System.out.print(city.getCityName().toString()+" ");
			};
			System.out.println();
		}
		int choicePermitCard = inputNumber (1, playerPermitCards.size());
		PermitCard sellingPermitCard = player.getArrayListPermitCard().get(choicePermitCard-1);
		player.getArrayListPermitCard().remove(choicePermitCard-1);
		return sellingPermitCard;
	}
	
	private PoliticCard askPoliticCard (Player player) {
		ArrayList <PoliticCard> playerPoliticCards = player.getArrayListPoliticCard();
		System.out.println("Which Politics Tile would you like to sell?");

		for (int i=0; i<playerPoliticCards.size(); i++){
			System.out.println(i+1+". "+ playerPoliticCards.get(i).getCardColor().toString());
		}
		int choicePoliticCard = inputNumber (1, playerPoliticCards.size())-1;
		PoliticCard sellingPoliticCard = player.getArrayListPoliticCard().get(choicePoliticCard);
		player.getArrayListPoliticCard().remove(choicePoliticCard);
		return sellingPoliticCard;
	}
	
	private int askAssistant (Player player){
		System.out.println("How many assistants do you would to sell? You have:" + player.getAssistant() + "assistants");
		int numberAssistants = inputNumber(1, player.getAssistant());
		player.decrementAssistant(numberAssistants);
		return numberAssistants;
	}
	
	private int askPrice(){
		System.out.println("Which price would you like to sell it for?");
		return inputNumber(1, 20);
	}
	
	public int getPrice() {
		return price;
	}

	public Object getObject() {
		return object;
	}

	public Player getOwner() {
		return owner;
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