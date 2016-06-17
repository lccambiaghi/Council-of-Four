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
	
	private static void println(String string, Player player){
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broker.println(string, player);
	}
	
	public static void waitForServer(Player player){
		println("Just wait a moment...", player);
	}
	
	public static void waitForMatch(Player player){
		println("You have been selected for a match, wait a moment...", player);
	}
	
	
	public static void chooseInfoOrAction_1_2(Player player){
		println("What would you like to do?", player);
		println("1. Get info of the match", player);
		println("2. Perform an action", player);
	}
	
	public static void chooseInfo_0_6(Player player){
		println("Choose the info you want", player);
		println("0. [Back to menu]", player);
		println("1. Your goods", player);
		println("2. All cities", player);
		println("3. Other players status", player);
		println("4. Balconies and councillors", player);
		println("5. All regions", player);
		println("6. Specific city", player);
	}
	
	public static void chooseCityFromIndex(Player player){
		println("Choose a city inserting the corresponding index", player);
	}
	
	public static void ifQuickAction(Player player){
		println("Would you like to perform Quick Action?", player);
	}
	
	public static void chooseYesOrNo_1_2(Player player){
		println("1. Yes", player);
		println("2. No", player);
	}
	
	public static void chooseQuickAction_1_4(Player player){
		println("Which Quick Action would you like to do?", player);
		println("1. Engage an Assistant", player);
		println("2. Change Building Permit Tiles for a Region", player);
		println("3. Send an Assistant to Elect a Councillor", player);
		println("4. Perform an additional Main Action", player);
	}
	
	public static void chooseMainAction_1_4(Player player){
		println("Which Main Action would you like to do?", player);
		println("1. Elect a Councillor", player);
		println("2. Acquire a Permit Tile", player);
		println("3. Build an Emporium using a Permit Tile", player);
		println("4. Build an Emporium with the Help of the King", player);
	}
	
	public static void chooseRegion_1_3(Player player){
		println("Choose a region", player);
		println("1. Sea", player);
		println("2. Hill", player);
		println("3. Mountain", player);
	}
	
	public static void chooseBalcony(Player player, Balcony[] arrayBalcony){

		println("Choose a Balcony:", player);

		for(int i=0; i<arrayBalcony.length; i++)
			println(i+1 + " " + arrayBalcony[i].getNameBalcony(), player);

	}
	
	public static void choosePermitCard(Player player, ArrayList<PermitCard> arrayListPermitCard){

		println("Choose a Building Permit Tile:", player);

		for(int i=0; i<arrayListPermitCard.size(); i++) {

			PermitCard permitCard = arrayListPermitCard.get(i);

			println(i + 1 + ". \nBuildable Cities:\n", player);
			for (City buildableCity : permitCard.getArrayBuildableCities())
				Broker.print(buildableCity.getCityName() + " ", player);

			println("\nBonus:\n", player);
			for (Bonus bonus : permitCard.getArrayBonus())
				Broker.print(bonus.getBonusName() + " " + bonus.getIncrement() + "   ", player);

		}

	}
	
	public static void chooseCity(Player player, City[] arrayCity){

		println("Choose a city:", player);

		for(int i=0; i<arrayCity.length; i++)
			println(i+1 + " " + arrayCity[i], player);

	}

	// Polymorphism
	public static void chooseCity(Player player, Map<City, Integer> movableCities){

		println("Choose a destination city for the king:", player);
		int i=0;
		for (Map.Entry<City, Integer> city : movableCities.entrySet()){
			println(i + 1 + ". " + city.getKey() + " Cost: " + city.getValue(), player);
			i++;
		}
	}

	
	public static void choosePoliticsCards(Player player, ArrayList<PoliticCard> arrayListPoliticCard){

		println("Choose Politic Cards:", player);

		for(int i=0; i<arrayListPoliticCard.size(); i++)
			println(i+1 + " " + arrayListPoliticCard.get(i).getCardColor(), player);

	}
	
	public static void choosePermitCards_1_2(Player player){
		println("Which Permit Tile do you want take, 1 or 2?", player);
	}
	
	public static void youCantBuild(Player player){
		println("You either have no Business Permit Tiles" +
				" or you have already built in every city they avail you to", player);
	}
	
	public static void askCouncillorColor(Player player, ArrayList<Color> choosableColors){

		println("What color would you like the new councillor to be?", player);

		for(int i=0; i<choosableColors.size(); i++)
			println(i+1 + " " + choosableColors.get(i), player);

	}
	
	public static void askHowManyPoliticsCards(Player player){
		println("How many Politic Cards do you want to use?", player);
	}
	
	public static void selectAnotherPoliticsCardsSet(Player player){
		println("You don't have these cards in your hand. Select an other set", player);
	}
	
	public static void notEnoughRichness(Player player){
		println("You don't have enough richness", player);
	}
	
	public static void notEnoughAssistant(Player player){
		println("You don't have enough assistants", player);
	}
	
	public static void notEnoughPoliticsCards(Player player){
		println("You don't have enough Politics Card", player);
	}
	public static void notEnoughPoliticsCardsAndRichness(Player player){
		println("You don't have enough Politics Cards and Richness at the same time", player);
	}
	
}
