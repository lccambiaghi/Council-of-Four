package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.PoliticCard;

public class SellingObject {
	int price;
	Object object;
	Player owner;
	
	public SellingObject (Player player, String string){
		
		switch(string){
			case "PermitCard": {
				this.object=askPermitCard(player);
			}
			case "PoliticCard": {
				this.object=askPoliticCard(player);
			}
			case "Assints": {
				this.object=askAssistant(player);			
			}
		}
		this.price=askPrice(player);
		this.owner=player;
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
	
	public int inputNumber(int lowerBound, int upperBound){ //TODO throws RemoteException + spostare nella classe della CLI
		 
        Scanner in = new Scanner(System.in);
        int inputNumber;
        boolean eligibleInput=false;
 
        do {
            while(!in.hasNextInt()){
                System.out.println("Insert an integer value!");
                in.nextInt();
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

	
	public int getPrice() {
		return price;
	}

	public Object getObject() {
		return object;
	}

	public Player getOwner() {
		return owner;
	}

	
}
