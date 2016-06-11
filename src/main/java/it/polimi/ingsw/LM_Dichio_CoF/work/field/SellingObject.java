package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;
import it.polimi.ingsw.LM_Dichio_CoF.work.PoliticCard;

import static it.polimi.ingsw.LM_Dichio_CoF.work.Match.inputNumber;

public class SellingObject {
	private int price;
	private Object object;
	private Player owner;
	
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
		this.price=askPrice();
		this.owner=player;
	}

	
	private PermitCard askPermitCard (Player player){
		ArrayList <PermitCard> playerPermitCards = player.getArrayListPermitCard();
		System.out.println("Which Business Permit Tile would you like to sell?");
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
		System.out.println("Which Business Permit Tile would you like to sell?");

		//TODO implementa stampa
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

	
}
