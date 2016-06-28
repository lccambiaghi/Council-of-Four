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
		return ("What would you like to do?\n" +
				"1. Get info of the match\n" +
				"2. Perform an action");
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
		return("1. Yes\n" +
			"2. No");
	}
	
	public static String chooseQuickAction_1_4(){
		return("Which Quick Action would you like to do?\n" +
			"1. Engage an Assistant\n" +
			"2. Change Building Permit Tiles for a Region\n" +
			"3. Send an Assistant to Elect a Councillor\n" +
			"4. Perform an additional Main Action");
	}
	
	public static String chooseMainAction_1_4(){
		return("Which Main Action would you like to do?\n" +
			"1. Elect a Councillor\n" +
			"2. Acquire a Permit Tile\n" +
			"3. Build an Emporium using a Permit Tile\n" +
			"4. Build an Emporium with the Help of the King");
	}
	
	public static String chooseRegion_1_3(){
		return ("Choose a region\n" +
			"1. Sea\n" +
			"2. Hill\n" +
			"3. Mountain");
	}
	
	public static String chooseBalcony(Balcony[] arrayBalcony){

		String message="Choose a Balcony:";

		for(int i=0; i<arrayBalcony.length; i++)
			message = message + (i+1) + " " + arrayBalcony[i].getNameBalcony();

		return message;

	}
	
	public static String choosePermitCard(ArrayList<PermitCard> arrayListPermitCard){

		String message="Choose a Building Permit Tile:\n";

		for(int i=0; i<arrayListPermitCard.size(); i++) {

			PermitCard permitCard = arrayListPermitCard.get(i);

			message = message + (i + 1) + ". \nBuildable Cities:\n";
			for (City buildableCity : permitCard.getArrayBuildableCities())
				message = message + buildableCity.getCityName() + " ";

			message = message + "\nBonus:\n";
			for (Bonus bonus : permitCard.getArrayBonus())
				message = message + bonus.getBonusName() + " " + bonus.getIncrement() + "   ";
		}

		return message;

	}
	
	public static String chooseCity(City[] arrayCity){

		String message= "Choose a city:\n";

		for(int i=0; i<arrayCity.length; i++)
			message = message + (i+1) + " " + arrayCity[i];

		return message;

	}

	// Overloading
	public static String chooseCity(Map<City, Integer> movableCities){

		String message = "Choose a destination city for the king:";

		int i=0;
		for (Map.Entry<City, Integer> city : movableCities.entrySet()){
			message = message + (i + 1) + ". " + city.getKey() + " Cost: " + city.getValue();
			i++;
		}

		return message;

	}

	
	public static String choosePoliticsCards(ArrayList<PoliticCard> arrayListPoliticCard){

		String message = "Choose Politic Cards:";

		for(int i=0; i<arrayListPoliticCard.size(); i++)
			message = message + (i+1) + " " + arrayListPoliticCard.get(i).getCardColor();

		return message;

	}
	
	public static String choosePermitCards_1_2(){
		return ("Which Permit Tile do you want take, 1 or 2?");
	}
	
	public static String youCantBuild(){
		return ("You either have no Business Permit Tiles" +
				" or you have already built in every city they avail you to");
	}
	
	public static String askCouncillorColor(ArrayList<Color> choosableColors){

		String message = ("What color would you like the new councillor to be?");

		for(int i=0; i<choosableColors.size(); i++)
			message = message + (i+1) + " " + choosableColors.get(i);

		return message;

	}
	
	public static String askHowManyPoliticsCards(){
		return ("How many Politic Cards do you want to use?");
	}
	
	public static String selectAnotherPoliticsCardsSet(){
		return("You don't have these cards in your hand. Select an other set");
	}
	
	public static String notEnoughRichness(){
		return ("You don't have enough richness");
	}
	
	public static String notEnoughAssistant(){
		return ("You don't have enough assistants");
	}
	
	public static String notEnoughPoliticsCards(){
		return ("You don't have enough Politics Card");
	}

	public static String notEnoughPoliticsCardsAndRichness(){
		return ("You don't have enough Politics Cards and Richness at the same time");
	}
	
	public static String turnOf(Player player){
		return ("Turn of: " +player.getNickname());
	}
	
	public static String yourTurn(int  seconds){
		return ("It's your turn! You have "+ seconds +" seconds!");
	}
	
	public static String playerHasBeenKickedOff(Player player){
		return ("Player " + player.getNickname() + " has been kicked off!");
	}
	
	public static String chooseToSellSomething_1_2(){
		return ("Would you like to sell something?\n" +
				"1. Yes\n" +
				"2. No");
	}
	public static String chooseToBuySomething_1_2(){
		return ("Would you like to buy this object?\n" +
				"1. Yes\n" +
				"2. No");
	}
	
	public static String askForObject(){
		return ("Which object would you like to sell?");
	}
	public static String permitCard(int counter){
		return (counter+". Permit Card");
	}
	public static String politicCard(int counter){
		return (counter+". Politic Card");
	}
	public static String assistants(int counter){
		return (counter+". Assistants");
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
	public static String choosePoliticCard(){
		return ("Which Politics Tile would you like to sell?");
	}
	
	
	
	
	
}
