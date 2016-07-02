package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Balcony;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Bonus;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.PermitCard;

import java.util.ArrayList;
import java.util.Map;

public final class Message {
	
	public static String waitForMatch(){
		return ("You have been selected for a match, wait a moment...");
	}
	
	public static String matchStarted(){
		return ("\n --- THE MATCH HAS STARTED! ---\n ");
	}
	
	public static String waitTurn(){
		return("It's not your turn yet, wait...");
	}
	
	public static String play(Player player){
		return ("It's your turn!");
	}
	
	public static String chooseInfoOrAction_1_2(){
		
		StringBuilder message = new StringBuilder();
		
		message.append("What would you like to do?\n").append("1. Get info of the match\n").append("2. Perform an action");
		return message.toString();
	}
	
	public static String chooseInfo_0_6(){
		return("Choose the info you want\n"+
			"0. [Back to menu]\n" +
			"1. Your goods\n" +
			"2. All cities\n" +
			"3. Other players status\n" +
			"4. Balconies and councillors\n" +
			"5. All regions\n" +
			"6. Specific city");
	}
	
	public static String chooseCityFromIndex(){
		return ("Choose a city inserting the corresponding index");
	}
	
	public static String ifQuickAction(){
		return("Would you like to perform Quick Action?");
	}
	
	public static String chooseYesOrNo_1_2(){
		StringBuilder message = new StringBuilder();
		
		message.append("1. Yes\n").append("2. No");
		return message.toString();
	}
	
	public static String chooseQuickAction_1_4(){
		StringBuilder message = new StringBuilder();
		
		message.append("Which Quick Action would you like to do?\n").append("1. Engage an Assistant\n")
		.append("2. Change Building Permit Tiles for a Region\n").append("3. Send an Assistant to Elect a Councillor\n")
		.append("4. Perform an additional Main Action");
		
		return message.toString();
	}
	
	public static String chooseMainAction_1_4(){
		StringBuilder message = new StringBuilder();
		
		message.append("Which Main Action would you like to do?\n").append("1. Elect a Councillor\n")
		.append("2. Acquire a Permit Tile\n").append("3. Build an Emporium using a Permit Tile\n")
		.append("4. Build an Emporium with the Help of the King");
		
		return message.toString();
	}
	
	public static String chooseRegion_1_3(){
		StringBuilder message = new StringBuilder();
		
		message.append("Choose a region\n").append("1. Sea\n").append("2. Hill\n").append("3. Mountain");
		
		return message.toString();
	}
	
	public static String chooseBalcony(Balcony[] arrayBalcony){

		StringBuilder message= new StringBuilder();
		message.append("Choose a Balcony:\n");

		for(int i=0; i<arrayBalcony.length; i++)
			message.append(i+1).append(" ").append(arrayBalcony[i].getNameBalcony()).append("\n");

		return message.toString();

	}
	
	public static String choosePermitCard(ArrayList<PermitCard> arrayListPermitCard){

		StringBuilder message= new StringBuilder();

		message.append("Choose a Building Permit Tile:\n");

		for(int i=0; i<arrayListPermitCard.size(); i++) {

			PermitCard permitCard = arrayListPermitCard.get(i);

			message.append(i+1).append(". \nBuildable Cities:\n");

			for (City buildableCity : permitCard.getArrayBuildableCities())
				message.append(buildableCity.getCityName()).append(" ");

			message.append("\nBonus:\n");

			for (Bonus bonus : permitCard.getArrayBonus())
				message.append(bonus.getBonusName()).append(" ").append(bonus.getIncrement()).append("   ");

		}

		return message.toString();

	}
	
	public static String chooseCity(City[] arrayCity){

		StringBuilder message = new StringBuilder();
		
		message.append("Choose a city:\n");

		for(int i=0; i<arrayCity.length; i++)
			message.append(i+1).append(" ").append(arrayCity[i]);

		return message.toString();

	}

	// Overloading
	public static String chooseCity(Map<City, Integer> movableCities){
		StringBuilder message = new StringBuilder();

		message.append("Choose a destination city for the king:\n");

		int i=0;
		for (Map.Entry<City, Integer> city : movableCities.entrySet()){
			message.append(i+1).append(". ").append(city.getKey()).append(" Cost: ").append(city.getValue());
			i++;
		}

		return message.toString();

	}

	
	public static String choosePoliticsCards(ArrayList<PoliticCard> arrayListPoliticCard){

		StringBuilder message= new StringBuilder();

		for(int i=0; i<arrayListPoliticCard.size(); i++)
			message.append(i+1).append(" ").append(arrayListPoliticCard.get(i).getCardColor()).append("\n");

		return message.toString();

	}
	
	public static String choosePermitCards_1_2(){
		return ("Which Permit Tile do you want take, 1 or 2?");
	}
	
	public static String youCantBuild(){
		StringBuilder message = new StringBuilder();
		message.append("You either have no Business Permit Tiles")
		.append(" or you have already built in every city they avail you to");
		
		return message.toString();
	}
	
	public static String askCouncillorColor(ArrayList<Color> choosableColors){

		StringBuilder message = new StringBuilder();	
		message.append("What color would you like the new councillor to be?");

		for(int i=0; i<choosableColors.size(); i++)
			message.append(i+1).append(" ").append(choosableColors.get(i)).append(" ");

		return message.toString();

	}
	
	public static String askHowManyPoliticsCards(){
		return ("How many Politic Cards do you want to use?");
	}
	
	public static String selectAnotherPoliticsCardsSet(){
		return("You don't have these cards in your hand. Select an other set");
	}
	
	public static String notEnoughRichness(){
		return ("You don't have enough richness.");
	}
	
	public static String notEnoughAssistant(){
		return ("You don't have enough assistants");
	}
	
	public static String notEnoughPoliticsCards(){
		StringBuilder message = new StringBuilder();
		
		message.append("You don't have enough Politics Card\n").append("Please choose another main move.");
		
		return message.toString();
	}

	public static String notEnoughPoliticsCardsAndRichness(){
		return ("You don't have enough Politics Cards and Richness at the same time");
	}
	
	public static String turnOf(Player player){
		StringBuilder message = new StringBuilder();
		message.append("Turn of: ").append(player.getNickname());
		
		return message.toString();
	}
	
	public static String yourTurn(int  seconds){
		StringBuilder message = new StringBuilder();
		message.append("It's your turn! You have ").append(seconds).append(" seconds!");
		
		return message.toString();
	}
	
	public static String playerHasBeenKickedOff(Player player){
		StringBuilder message = new StringBuilder();
		
		message.append("Player ").append(player.getNickname()).append(" has been kicked off!");
		
		return message.toString();
	}
	
	public static String chooseToSellSomething_1_2(){
		StringBuilder message = new StringBuilder();
		
		message.append("Would you like to sell something?\n").append("1. Yes\n").append("2. No");
		
		return message.toString();
	}
	public static String chooseToBuySomething_1_2(){
		StringBuilder message = new StringBuilder();
		
		message.append("Would you like to buy this object?\n").append("1. Yes\n").append("2. No");

		return message.toString();
	}
	
	public static String askForObject(){
		return ("Which object would you like to sell?");
	}
	public static String permitCard(int counter){
		StringBuilder message = new StringBuilder();
		message.append(counter).append(". Permit Card");
		return message.toString();
	}
	public static String politicCard(int counter){
		StringBuilder message = new StringBuilder();
		message.append(counter).append(". Politic Card");
		return message.toString();
	}
	public static String assistants(int counter){
		StringBuilder message = new StringBuilder();
		message.append(counter).append(". Assistants");
		return message.toString();
	}
	public static String deniedSelling (){
		return ("You can't sell nothing");
	}
	public static String deniedBuying (){
		return ("You can't buy this object");
	}
	public static String youWon(){
		return ("You won the match!");
	}
	public static String choosePoliticCard(ArrayList <PoliticCard> playerPoliticCards){
		StringBuilder message = new StringBuilder();
		
		message.append("Which Politics Tile would you like to sell?");
		
		for (int i=0; i<playerPoliticCards.size(); i++){
			message.append("\n").append(i+1).append(". ")
			.append(playerPoliticCards.get(i).getCardColor().toString());
		}
		return message.toString();
		
	}
	
	public static String choosePermitCard_noBonus(ArrayList <PermitCard> playerPermitCards){
		StringBuilder message = new StringBuilder();
		
		message.append("Which Business Permit Tile would you like to sell?\n")
		.append("Your Cards:");
	
		for (int i=0; i<playerPermitCards.size(); i++){
			PermitCard permitCard = playerPermitCards.get(i);

			message.append(i+1).append(". \nBuildable Cities:\n");
			for (City buildableCity : permitCard.getArrayBuildableCities()){
				message.append(buildableCity.getCityName()).append(" ");
			}
			message.append("\n");
		}
				
		return message.toString();
		
	}
	
	public static String chooseAssistants (Player player){
		StringBuilder message = new StringBuilder();
		
		message.append("How many assistants do you would to sell? You have: ")
		.append(player.getAssistant()).append(" assistants");
		
		return message.toString();

	}

	public static String askPrice (){
		return ("Which price would you like to sell it for?");

	}
	
}
