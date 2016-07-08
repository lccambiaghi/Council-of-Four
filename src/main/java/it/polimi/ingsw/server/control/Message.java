package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.model.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Message {

	public static String waitForMatch(){
		return ("You have been selected for a match, wait a moment...");
	}

	public static String matchStarted(){
		return ("\n --- THE MATCH HAS STARTED! ---\n ");
	}

	public static String chooseInfoOrAction_1_2(){
		return "What would you like to do?\n" +
				"1. Get info of the match\n" +
				"2. Perform an action";
	}

	public static String chooseInfo_0_6(){
		return "Choose the info you want\n"+
			"0. [Back to menu]\n" +
			"1. Your goods\n" +
			"2. All cities\n" +
			"3. Other players status\n" +
			"4. Balconies and councillors\n" +
			"5. All regions\n" +
			"6. Specific city";
	}

	public static String ifQuickAction(){
		return("Would you like to perform Quick Action?");
	}

	public static String chooseYesOrNo_1_2(){
		return "1. Yes\n2. No";
	}

	public static String notEligibleForMove(){
		return "You are not eligible to perform this move. Please choose another one.";
	}

	public static String chooseQuickAction_1_4(){
		return "Which Quick Action would you like to do?\n" +
			"1. Engage an Assistant\n" +
			"2. Change Building Permit Tiles for a Region\n" +
			"3. Send an Assistant to Elect a Councillor\n" +
			"4. Perform an additional Main Action";
	}

	public static String chooseMainAction_1_4(){
		return "Which Main Action would you like to do?\n" +
			"1. Elect a Councillor\n" +
			"2. Acquire a Permit Tile\n" +
			"3. Build an Emporium using a Permit Tile\n" +
			"4. Build an Emporium with the Help of the King";
	}

	public static String chooseRegion_1_3(){
		return "Choose a region\n" +
			"1. Sea\n" +
			"2. Hill\n" +
			"3. Mountain";
	}

	public static String chooseBalcony(Balcony[] arrayBalcony){

		StringBuilder message = new StringBuilder();

		message.append("Choose a Balcony:");

		for(int i=0; i<arrayBalcony.length; i++)
			message.append("\n").append(i+1).append(" ").append(arrayBalcony[i].getNameBalcony());

		return message.toString();

	}

	public static String choosePoliticCard(List <PoliticCard> playerPoliticCards){

		StringBuilder message = new StringBuilder();

		message.append("Which Politics Cards would you like to use?");

		for (int i=0; i<playerPoliticCards.size(); i++){
			message.append("\n").append(i+1).append(". ")
					.append(playerPoliticCards.get(i).getCardColor().toString());
		}
		return message.toString();

	}

	public static String choosePoliticCardWithDone(List <PoliticCard> playerPoliticCards, int numberSelectedCards){

		StringBuilder message = new StringBuilder();

		message.append("Which Politics Cards would you like to use?");

		if(numberSelectedCards>=1)
			message.append("0. [Done] ");

		for (int i=0; i<playerPoliticCards.size(); i++)
			message.append("\n").append(i+1).append(". ")
					.append(playerPoliticCards.get(i).getCardColor().toString());

		return message.toString();

	}

	public static String choosePermitCard(ArrayList<PermitCard> arrayListPermitCard){

		StringBuilder message= new StringBuilder();

		message.append("Choose a Building Permit Tile:");

		for(int i=0; i<arrayListPermitCard.size(); i++) {

			PermitCard permitCard = arrayListPermitCard.get(i);

			message.append("\n").append(i+1).append(". \nBuildable Cities:\n");

			for (City buildableCity : permitCard.getArrayBuildableCities())
				message.append(buildableCity.getCityName()).append(" ");

			message.append("\nBonus:\n");

			for (Bonus bonus : permitCard.getArrayBonus())
				message.append(bonus.getBonusName()).append(" ").append(bonus.getIncrement()).append("   ");

		}

		return message.toString();

	}

	public static String choosePermitCardNoBonus(List<PermitCard> arrayListPermitCard, int index){

		StringBuilder message= new StringBuilder();

		message.append("Choose a Building Permit Tile:");

		for(int i=0; i<index; i++) {

			PermitCard permitCard = arrayListPermitCard.get(i);

			message.append("\n").append(i+1).append(". Buildable Cities:");

			for (City buildableCity : permitCard.getArrayBuildableCities())
				message.append(" ").append(buildableCity.getCityName());

		}

		return message.toString();

	}

	public static String choosePermitCardNoBonus(List <PermitCard> playerPermitCards){
		StringBuilder message = new StringBuilder ();

		message.append("Choose a Permit Card:\n Your Cards: ");

		for (int i=0; i<playerPermitCards.size(); i++){
			PermitCard permitCard = playerPermitCards.get(i);

			message.append("\n").append(i+1).append(". \nBuildable Cities:\n");
			for (City buildableCity : permitCard.getArrayBuildableCities()){
				message.append(buildableCity.getCityName()).append(" ");
			}
		}

		return message.toString();

	}

	public static String chooseDestinationCity(City[] arrayCity){

		StringBuilder message = new StringBuilder();
		
		message.append("Choose a city:");

		for(int i=0; i<arrayCity.length; i++)
			message.append("\n").append(i+1).append(" ").append(arrayCity[i].getCityName().toString());

		return message.toString();

	}

	public static String chooseCityToBuild(City[] arrayBuildableCity, int numberBuildableCities){

		StringBuilder message= new StringBuilder();

		message.append("Where would you like to build the emporium?");

		for(int i=0; i<numberBuildableCities; i++)
			message.append("\n").append(i+1).append(" ").append(arrayBuildableCity[i].getCityName());

		return message.toString();

	}

	public static String chooseDestinationCity(Map<City, Integer> movableCities){

		StringBuilder message= new StringBuilder();

		message.append("Choose a destination city for the king:\n");

		Iterator itCities = movableCities.entrySet().iterator();

		int i=1;
		while (itCities.hasNext()) {

			Map.Entry entryCity = (Map.Entry) itCities.next();

			message.append(i).append(". ").append(((City) entryCity.getKey()).getCityName().toString());

			message.append(" Cost: ").append(entryCity.getValue()).append("\n");

			i++;

		}

		return message.toString();

	}

	public static String youCantBuild(){
		return "You either have no Business Permit Tiles" +
				" or you have already built in every city they avail you to. " +
				"Please choose another main move.";
	}

	public static String youCantBuildMaxEmporium() {

		return "You have already built the needed number of emporiums to win. " +
				"Choose another move.";

	}

	public static String askCouncillorColor(ArrayList<Color> choosableColors){

		StringBuilder message = new StringBuilder();

		message.append("What color would you like the new councillor to be?");

		for(int i=0; i<choosableColors.size(); i++)
			message.append("\n").append(i+1).append(".").append(" ").append(choosableColors.get(i));

		return message.toString();

	}

	public static String notEnoughRichnessForThisSet(){
		return "You don't have enough richness to use this set. Please select another one.";
	}

	public static String notEnoughAssistant(){
		return "You don't have enough assistants to perform this move. Please select another one.";
	}

	public static String turnOf(Player player){

		return "Turn of: " + player.getNickname();
	}

	public static String yourTurn(int  seconds){
		return "It's your turn! You have " + seconds + " seconds!";
	}

	public static String playerHasBeenKickedOut(Player player){
		return "Player " + player.getNickname() + " has been kicked out!";
	}

	public static String chooseToSellSomething_1_2(){		
		return "Would you like to sell something?\n" +
				"1. Yes\n" +
				"2. No";
	}
	public static String chooseToBuySomething_1_2(){
		return "Would you like to buy something?\n" +
				"1. Yes\n" +
				"2. No";
	}

	public static String askForObject(){
		return "Which object would you like to sell?";
	}

	public static String askObjectToBuy(){
		return "Which object would you like to buy?";
	}

	public static String permitCard(int counter){ return String.valueOf(counter) + ". Permit Card"; }

	public static String politicCard(int counter){

		return String.valueOf(counter) + ". Politic Card";

	}
	public static String assistants(int counter){

		return String.valueOf(counter) + ". Assistants";
	}
	public static String deniedSelling (){
		return "You can't sell nothing";
	}
	public static String deniedBuying (){
		return "You can't buy this object";
	}
	public static String youWon(){
		return "You won the match!";
	}

	public static String chooseSellingObject(Player player, SellingObject sellingObject){

		return "";

	}

	public static String chooseAssistants (Player player){

		return "How many assistants would you like to sell? You have: " +
				player.getAssistant() + " assistants";

	}

	public static String askPrice (){
		return "Which price would you like to sell it for?";
	}
		
	public static String getInfoPermitCard (PermitCard sellingPermitCard, int price){

		StringBuilder message= new StringBuilder();
		
		message.append("PermitCard - Buildable Cities: ").append(sellingPermitCard.getArrayBuildableCities().toString())
		.append("\nThe price is: ").append(price).append(" coins");
		
		return message.toString();
	}
	
	public static String getInfoPoliticCard (PoliticCard sellingPoliticCard, int price){

		return "Politic Card Color:" + " " + sellingPoliticCard.getCardColor() + ". " +
				"The price is: " + price + " coins";
	}
	
	public static String getInfoAssistants (int sellingAssistants, int price){

		return "Number of Assistants: " + sellingAssistants + ". " +
				"The price is: " + price + " coins";

	}

	public static String skipBuying (){
		return "0. Exit from the buying step";
	}

	public static String noMarketElements(){
		return "There aren't object on sale in the market";
	}

}
