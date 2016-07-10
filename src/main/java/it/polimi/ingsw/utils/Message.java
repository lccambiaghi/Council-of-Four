package it.polimi.ingsw.utils;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class contains all the messages used to interact with the player
 */
public final class Message {

	/**
	 * @return message
     */
	public static String askToPlayAgain(){
		return "Do you want to play again?";
	}

	/**
	 * @return message
     */
	public static String sayByeBye(){
		return "It has been a pleasure having you as a player, BYE BYE!";
	}

	/**
	 * @return message
	 */
	public static String tooLateTurnOver(){
		return "Too late, your turn is over!";
	}
	
	/**
	 * @param players whose nickname has to be printed
	 * @return message
	 */
	public static String playingPlayers(List<Player> players){
		
		StringBuilder message = new StringBuilder();
		
		message.append("Playing players:");
		
		for(int i=0; i<players.size(); i++) 
			message.append("\n").append(i+1).append(". ").append(players.get(i).getNickname());
		
		message.append("\n");
		return message.toString();

	}
	
	/**
	 * @return message
	 */
	public static String waitForMatch(){
		return "You have been selected for a match, wait a moment...";
	}
	/**
	 * @return message
	 */
	public static String matchStarted(){
		return "\n --- THE MATCH HAS STARTED! ---\n ";
	}

	/**
	 *
	 * @return message
     */
	public static String chooseInfoOrAction_1_2(){
		return "What would you like to do?\n" +
				"1. Get info of the match\n" +
				"2. Perform an action";
	}

	/**
	 * @return message
     */
	public static String chooseInfo_0_6(){
		return "What would you like to know?\n"+
			"0. [Back to menu]\n" +
			"1. Your status\n" +
			"2. All cities' status\n" +
			"3. Other players' status\n" +
			"4. Balconies and councillors' status\n" +
			"5. All regions' status\n" +
			"6. Specific city's status";
	}

	/**
	 * @return message
     */
	public static String ifQuickAction(){
		return "Would you like to perform Quick Action?";
	}

	/**
	 * @return message
     */
	public static String chooseYesOrNo_1_2(){
		return "1. Yes\n" +
				"2. No";
	}

	/**
	 * @return message
     */
	public static String notEligibleForMove(){
		return "You are not eligible to perform this move. Please choose another one.";
	}

	/**
	 * @return message
     */
	public static String chooseQuickAction_1_4(){
		return "Which Quick Action would you like to do?\n" +
			"1. Engage an Assistant\n" +
			"2. Change Building Permit Tiles for a Region\n" +
			"3. Send an Assistant to Elect a Councillor\n" +
			"4. Perform an additional Main Action";
	}

	/**
	 * @return message
     */
	public static String chooseMainAction_1_4(){
		return "Which Main Action would you like to do?\n" +
			"1. Elect a Councillor\n" +
			"2. Acquire a Permit Tile\n" +
			"3. Build an Emporium using a Permit Tile\n" +
			"4. Build an Emporium with the Help of the King";
	}

	/**
	 * @return message
     */
	public static String chooseRegion_1_3(){
		return "Choose a region:\n" +
			"1. Sea\n" +
			"2. Hill\n" +
			"3. Mountain";
	}

	/**
	 * @param arrayBalcony to print
	 * @return message
     */
	public static String chooseBalcony(Balcony[] arrayBalcony){

		StringBuilder message = new StringBuilder();

		message.append("Choose a Balcony:");

		for(int i=0; i<arrayBalcony.length; i++)
			message.append("\n").append(i+1).append(" ").append(arrayBalcony[i].getNameBalcony());

		return message.toString();

	}

	/**
	 * @param playerPoliticCards to print
	 * @return message
     */
	public static String choosePoliticCard(List <PoliticCard> playerPoliticCards){

		StringBuilder message = new StringBuilder();

		message.append("Which Politics Cards would you like to use?");

		for (int i=0; i<playerPoliticCards.size(); i++){
			message.append("\n").append(i+1).append(". ")
					.append(playerPoliticCards.get(i).getCardColor().toString());
		}
		return message.toString();

	}
	
	/**
	 * @param playerPoliticCards to print
	 * @return message
     */
	public static String choosePoliticCardToSell(List <PoliticCard> playerPoliticCards){

		StringBuilder message = new StringBuilder();

		message.append("Which Politics Cards would you like to sell?");

		for (int i=0; i<playerPoliticCards.size(); i++){
			message.append("\n").append(i+1).append(". ")
					.append(playerPoliticCards.get(i).getCardColor().toString());
		}
		return message.toString();

	}
	

	/**
	 * @param playerPoliticCards potential cards to print
	 * @param numberSelectedCards actual number of cards to print
     * @return message
     */
	public static String choosePoliticCardWithDone(List <PoliticCard> playerPoliticCards, int numberSelectedCards){

		StringBuilder message = new StringBuilder();

		message.append("Which Politics Cards would you like to use?");

		if(numberSelectedCards>=1)
			message.append("\n0. [Done] ");

		for (int i=0; i<playerPoliticCards.size(); i++)
			message.append("\n").append(i+1).append(". ")
					.append(playerPoliticCards.get(i).getCardColor().toString());

		return message.toString();

	}

	/**
	 * @param arrayListPermitCard to print
	 * @return message
     */
	public static String choosePermitCard(List<PermitCard> arrayListPermitCard){

		StringBuilder message= new StringBuilder();

		message.append("Choose a Building Permit Tile:");

		for(int i=0; i<arrayListPermitCard.size(); i++) {

			PermitCard permitCard = arrayListPermitCard.get(i);

			message.append("\n").append(i+1).append(". \nBuildable Cities:\n");

			for (City buildableCity : permitCard.getArrayBuildableCities())
				message.append(buildableCity.getCityName().toString()).append(" ");

			message.append("\nBonus:\n");

			for (Bonus bonus : permitCard.getArrayBonus())
				message.append(bonus.getBonusName()).append(" ").append(bonus.getIncrement()).append("   ");

		}

		return message.toString();

	}

	/**
	 * @param arrayListPermitCard potential cards to print
	 * @param index actual number of cards to print
     * @return message
     */
	public static String choosePermitCardNoBonus(List<PermitCard> arrayListPermitCard, int index){

		StringBuilder message= new StringBuilder();

		message.append("Choose a Building Permit Tile:");

		for(int i=0; i<index; i++) {

			PermitCard permitCard = arrayListPermitCard.get(i);

			message.append("\n").append(i+1).append(". Buildable Cities:");

			for (City buildableCity : permitCard.getArrayBuildableCities())
				message.append(" ").append(buildableCity.getCityName().toString());

		}

		return message.toString();

	}

	/**
	 * @param playerPermitCards to print
	 * @return message
     */
	public static String choosePermitCardNoBonus(List <PermitCard> playerPermitCards){
		StringBuilder message = new StringBuilder ();

		message.append("Choose a Permit Card:\n Your Cards: ");

		for (int i=0; i<playerPermitCards.size(); i++){
			PermitCard permitCard = playerPermitCards.get(i);

			message.append("\n").append(i+1).append(". \nBuildable Cities:\n");
			for (City buildableCity : permitCard.getArrayBuildableCities()){
				message.append(buildableCity.getCityName().toString()).append(" ");
			}
		}

		return message.toString();

	}

	/**
	 * @param arrayCity cities to print
	 * @return message
     */
	public static String chooseDestinationCity(City[] arrayCity){

		StringBuilder message = new StringBuilder();
		
		message.append("Choose a city:");

		for(int i=0; i<arrayCity.length; i++)
			message.append("\n").append(i+1).append(" ").append(arrayCity[i].getCityName().toString());

		return message.toString();

	}

	/**
	 * @param arrayBuildableCity potential cities to print
	 * @param numberBuildableCities actual number of cities to print
     * @return message
     */
	public static String chooseCityToBuild(City[] arrayBuildableCity, int numberBuildableCities){

		StringBuilder message= new StringBuilder();

		message.append("Where would you like to build the emporium?");

		for(int i=0; i<numberBuildableCities; i++)
			message.append("\n").append(i+1).append(" ").append(arrayBuildableCity[i].getCityName().toString());

		return message.toString();

	}

	/**
	 * @param movableCities cities the king can move to
	 * @return message
     */
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

	/**
	 * @return message
     */
	public static String youCantBuild(){
		return "You either have no Business Permit Tiles" +
				" or you have already built in every city they avail you to. " +
				"Please choose another main move.";
	}

	/**
	 * @return message
     */
	public static String youCantBuildMaxEmporium() {

		return "You have already built the needed number of emporiums to win. " +
				"Choose another move.";

	}

	/**
	 * @param choosableColors colors from which to choose
	 * @return message
     */
	public static String askCouncillorColor(List<Color> choosableColors){

		StringBuilder message = new StringBuilder();

		message.append("What color would you like the new councillor to be?");

		for(int i=0; i<choosableColors.size(); i++)
			message.append("\n").append(i+1).append(".").append(" ").append(choosableColors.get(i));

		return message.toString();

	}

	/**
	 * @return message
     */
	public static String noEligibleSet() {

		return "You do not own the needed politics cards to perform this action";

	}

	/**
	 * @return message
     */
	public static String youCantReachAnyCity() {

		return "Your richness and permit cards don't allow you to reach any city with the king. " +
				"Please choose another move.";

	}

	/**
	 * @return message
     */
	public static String notEnoughRichnessForThisSet(){
		return "You don't have enough richness to use this set. Please select another one.";
	}

	/**
	 * @return message
     */
	public static String notEnoughRichness() {
		return "You don't have enough richness to perform this move. Please choose another one.";
	}

	/**
	 * @return message
     */
	public static String notEnoughAssistant(){
		return "You don't have enough assistants to perform this move. Please select another one.";
	}

	/**
	 * @param player whose turn to announce
	 * @return message
     */
	public static String turnOf(Player player){

		return "Turn of: " + player.getNickname();
	}

	/**
	 * @param seconds duration of the turn
	 * @return
     */
	public static String yourTurn(int  seconds){
		return "It's your turn! You have " + seconds + " seconds!";
	}

	/**
	 * @param player who has been kicked out
	 * @return message
     */
	public static String playerHasBeenKickedOut(Player player){
		return "Player " + player.getNickname() + " has been kicked out!";
	}

	/**
	 * @param index to print
	 * @return message
     */
	public static String permitCard(int index){ return String.valueOf(index) + ". Permit Card"; }

	/**
	 * @param index to print
	 * @return message
     */
	public static String politicCard(int index){

		return String.valueOf(index) + ". Politic Card";

	}

	/**
	 * @param index to print
	 * @return message
     */
	public static String assistants(int index){

		return String.valueOf(index) + ". Assistants";

	}

	/**
	 * @param sellingPermitCard present on the market
	 * @param price to pay to redeem the object
     * @return message
     */
	public static String getInfoPermitCard (PermitCard sellingPermitCard, int price){

		return "PermitCard - Buildable Cities: " + sellingPermitCard.getArrayBuildableCities().toString() +
				"\nThe price is: " + price + " coins";

	}

	/**
	 * @param sellingPoliticCard present on the market
	 * @param price to pay to redeem the object
     * @return message
     */
	public static String getInfoPoliticCard (PoliticCard sellingPoliticCard, int price){

		return "Politic Card Color:" + " " + sellingPoliticCard.getCardColor() + ". " +
				"\nThe price is: " + price + " coins";
	}

	/**
	 * @param sellingAssistants present on the market
	 * @param price to pay to redeem the object
     * @return message
     */
	public static String getInfoAssistants (int sellingAssistants, int price){

		return "Number of Assistants: " + sellingAssistants + ". " +
				"The price is: " + price + " coins";

	}

	/**
	 * @return message
     */
	public static String youWon(){
		return "You won the match!";
	}

	/**
	 * @param player who built last emporium
	 * @return message
     */
	public static String lastRoundHasStarted(Player player) {

		return player.getNickname() + " has built the last emporium. Last round has started.";

	}

	//Market messages

	/**
	 * @return message
     */
	public static String marketHasStarted(){
		return "\n --- THE MARKET HAS STARTED --- \n ";
	}

	/**
	 * @return message
     */
	public static String markedHasFinished(){
		return "\n --- THE MARKET HAS ENDED --- \n ";
	}

	/**
	 * @return message
     */
	public static String sellingPhase(){
		return "SELLING PHASE\n ";
	}

	/**
	 * @return message
     */
	public static String buyingPhase(){
		return "\nBUYING PHASE\n ";
	}

	/**
	 * @return message
     */
	public static String chooseToSellSomething_1_2(){
		return "Would you like to sell anything?\n" +
				"1. Yes\n" +
				"2. No";
	}

	/**
	 * @return message
     */
	public static String chooseToBuySomething_1_2(){
		return "Would you like to buy anything?\n" +
				"1. Yes\n" +
				"2. No";
	}

	/**
	 * @return message
     */
	public static String askForObject(){
		return "Which object would you like to sell?";
	}

	/**
	 * @return message
     */
	public static String askObjectToBuy(){
		return "Which object would you like to buy?";
	}

	/**
	 * @return message
     */
	public static String deniedSelling (){
		return "You can't sell anything";
	}

	/**
	 * @return message
     */
	public static String deniedBuying (){
		return "You can't buy this object";
	}

	/**
	 * @param player to query for assistants' number
	 * @return message
     */
	public static String chooseNumberAssistantsToSell (Player player){
		return "How many assistants would you like to sell? You have: " +
				player.getAssistant() + " assistants";
	}

	/**
	 * @return message
     */
	public static String askPrice (){
		return "Which price would you like to sell it for?";
	}

	/**
	 * @return message
     */
	public static String skipBuying (){
		return "0. Exit from buying phase";
	}


	/**
	 * @return message
     */
	public static String noMarketElements(){
		return "There are no objects on sale in the market";
	}

}
