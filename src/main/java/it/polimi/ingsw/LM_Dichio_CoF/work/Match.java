package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.*;


public class Match {

	private Configurations config;
	private ArrayList<Player> arrayListPlayer;
	private int numberPlayers;
	private Field field;
	private Market market;
	
	public Match(ArrayList<Player> arrayListPlayer) {
		
		this.arrayListPlayer = arrayListPlayer;
		numberPlayers=arrayListPlayer.size();
		
		//Collections.shuffle(arrayListPlayer); //per adesso testiamo solo il primo giocatore
		
		readFileConfigurations();
		
		giveInitialPoliticCards();
		
		giveInitialAssistants();
		
		field = new Field(config, arrayListPlayer);
		
		startGame();
		
	}
	
	private void startGame() {	
		
		int turn=0;

		/* try{}
		   catch(LastEmporiumBuiltException e){}*/

		Player playerTurn = arrayListPlayer.get(turn);

		/* Draw a card */
		playerTurn.addPoliticCard(new PoliticCard());

		playerTurn.setMainActionsLeft(1);

		System.out.println("Would you like to perform Quick Action first?");

		if (askIfQuickAction(playerTurn)) {
			quickAction(playerTurn);
			mainAction(playerTurn);
		}
		else{
			mainAction(playerTurn);
			System.out.println("Would you like to perform Quick Action?");
			if (askIfQuickAction(playerTurn))
				quickAction(playerTurn);
		}
	}

	private void readFileConfigurations(){

		FileInputStream fileInputStream = null;
		
		try {
			
			//il salvataggio per ora è in locale, dovrà essere inviato al server quando ci sarà la connessione
			fileInputStream = new FileInputStream("./src/configurations/config");
			
			// create an ObjectInputStream for the file we created before
	         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	         this.config = (Configurations) objectInputStream.readObject();
	         
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			// close the stream
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
		
	private void giveInitialPoliticCards(){
		for (Player anArrayListPlayer : arrayListPlayer) {
			for (int itCard = 0; itCard < Constant.POLITIC_CARDS_INITIAL_NUMBER; itCard++) {
				anArrayListPlayer.addPoliticCard(new PoliticCard());
			}
		}
	}
	
	private void giveInitialAssistants(){
		for(int itPlayer=0, numberAssistants=1; itPlayer<arrayListPlayer.size(); itPlayer++, numberAssistants++){
			arrayListPlayer.get(itPlayer).setAssistant(numberAssistants);
		}
	}

	private boolean askIfQuickAction(Player playerTurn){ //TODO test e socket con playerTurn

		System.out.println("1. Yes");
		System.out.println("2. No");

		switch (inputNumber(1,2)) {
			case 1:
				return true;
			default: //case 2:
				return false;
		}

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

	private void quickAction(Player playerTurn) { 
		System.out.println("Which Quick Action would you like to do?");
		System.out.println("1. Engage an Assistant");
		System.out.println("2. Change Building Permit Tiles for a Region");
		System.out.println("3. Send an Assistant to Elect a Councillor");
		System.out.println("4. Perform an additional Main Action");

		switch (inputNumber(1,4)) {
			case 1:
				engageAssistant(playerTurn);
				break;
			case 2:
				replacePermitCards(playerTurn);
				break;
			case 3:
				electCouncillorWithAssistant(playerTurn);
				break;
			case 4:
				performAdditionalMainAction(playerTurn);
				break;
		}
	}

	private void engageAssistant(Player playerTurn){

		/* try{}
		   catch(NotEnoughRichnessException e)*/

		Route richnessRoute = field.getRichnessRoute();

		if(richnessRoute.getPosition(playerTurn)<Constant.ASSISTANT_ENGAGEMENT_RICHNESS_COST){
			System.out.println("You don't have enough richness to perform this action." +
					" Choose another Quick Action.");
			quickAction(playerTurn);
		}
		else {
			richnessRoute.movePlayer(-(Constant.ASSISTANT_ENGAGEMENT_RICHNESS_COST),playerTurn); //eccezione lanciata qui
			playerTurn.addAssistant(1);
		}
	}

	private void replacePermitCards(Player playerTurn){

		/* try{}
		   catch(NotEnoughAssistantsException e)*/

		if (playerTurn.getAssistant()<Constant.PERMIT_CARD_CHANGE_ASSISTANT_COST){
			System.out.println("You don't have enough assistants to perform this Action. " +
					"Choose another one.");
			quickAction(playerTurn);
		}
		else{
			Region chosenRegion = chooseRegion();
			FaceUpPermitCardArea chosenArea=chosenRegion.getFaceUpPermitCardArea();
			PermitCard[] arrayPermitCard = chosenArea.getArrayPermitCard();

			for (int i = 0; i < arrayPermitCard.length; i++) {
				chosenArea.changePermitCard(i);
			}

			playerTurn.decrementAssistant(Constant.PERMIT_CARD_CHANGE_ASSISTANT_COST);

		}
	}

	private Region chooseRegion() {

		System.out.println("In which region would you like to change Building Permit Tiles?");
		System.out.println("1. Sea");
		System.out.println("2. Hill");
		System.out.println("3. Mountain");

		return field.getRegionFromIndex(inputNumber(1,3) - 1); // -1 for array positioning
	}

	private void electCouncillorWithAssistant(Player playerTurn){

		/* try{}
		   catch(NotEnoughAssistantsException e)*/

		if (playerTurn.getAssistant()<Constant.ELECTION_ASSISTANT_COST){
			System.out.println("You don't have enough assistants to perform this Action. " +
					"Choose another one.");
			quickAction(playerTurn);
		}
		else {

			Balcony chosenBalcony = chooseBalcony();
			Color chosenCouncillorColor=chooseCouncillorColor();
			electCouncillor(chosenBalcony, chosenCouncillorColor);

			playerTurn.decrementAssistant(Constant.ELECTION_ASSISTANT_COST);
		}
	}

	private Balcony chooseBalcony(){

		System.out.println("In which balcony would you like to elect a new councillor?");
		System.out.println("1. Sea Balcony");
		System.out.println("2. Hill Balcony");
		System.out.println("3. Mountain Balcony");
		System.out.println("4. King Balcony");

		return field.getBalconyFromIndex(inputNumber(1, 4)-1); //-1 for array positioning

	}

	private Color chooseCouncillorColor(){

		System.out.println("What color would you like the new councillor to be?");
		System.out.println("1. White");
		System.out.println("2. Black");
		System.out.println("3. Cyan");
		System.out.println("4. Orange");
		System.out.println("5. Pink");
		System.out.println("6. Purple");

		// Se implementiamo questo, dobbiamo cambiare l'implementazione di removeAvailableCouncillor
		// inserendo checkIfColorAvailable
/*		Color chosenCouncillorColor;
		Councillor chosenCouncillor;
		AvailableCouncillors availableCouncillors = field.getAvailableCouncillors();
		boolean electionSuccessful=false;
		do {
			try {
				chosenCouncillorColor = Color.getColorFromIndex(inputNumber(1, 4));
				chosenCouncillor = availableCouncillors.removeAvailableCouncillor(chosenCouncillorColor); // qui l'eccezione
				chosenBalcony.electCouncillor(chosenCouncillor,availableCouncillors);
				electionSuccessful=true;
			} catch (NullPointerException e) {
				System.out.println("Color not available! Choose another one.");
			}
		} while (!electionSuccessful);*/

		Color chosenCouncillorColor= Color.getColorFromIndex(inputNumber(1, 6));
		AvailableCouncillors availableCouncillors = field.getAvailableCouncillors();
		boolean colorAvailable = availableCouncillors.checkIfColorAvailable(chosenCouncillorColor);

		while(!colorAvailable) {
			System.out.println("Color not available! Choose another one."); //TODO e se il giocatore volesse annullare la mossa?
			chosenCouncillorColor = Color.getColorFromIndex(inputNumber(1,6));
			colorAvailable = availableCouncillors.checkIfColorAvailable(chosenCouncillorColor);
		}

		return chosenCouncillorColor;

	}

	private void electCouncillor(Balcony chosenBalcony, Color chosenCouncillorColor) {
		AvailableCouncillors availableCouncillors = field.getAvailableCouncillors();
		Councillor chosenCouncillor = availableCouncillors.removeAvailableCouncillor(chosenCouncillorColor); //NullPointerException?
		chosenBalcony.electCouncillor(chosenCouncillor,availableCouncillors);
	}

	private void performAdditionalMainAction(Player playerTurn){
		if (playerTurn.getAssistant()<Constant.ADDITIONAL_MAIN_MOVE_ASSISTANT_COST){
			System.out.println("You don't have enough assistants to perform this Action. " +
					"Choose another one.");
			quickAction(playerTurn);
		}
		else{
			playerTurn.setMainActionsLeft(playerTurn.getMainActionsLeft() + 1);
			playerTurn.decrementAssistant(Constant.ADDITIONAL_MAIN_MOVE_ASSISTANT_COST);
		}
	}
	
	/* This method asks the user which main move he wants to perform,
	 	calls the according method and decreases his mainActionsLeft by one */
	private void mainAction(Player playerTurn) {

		System.out.println("Which Main Action would you like to do?");
		System.out.println("1. Elect a Councillor");
		System.out.println("2. Acquire a Business Permit Tile");
		System.out.println("3. Build an Emporium using a Permit Tile");
		System.out.println("4. Build an Emporium with the Help of the King");

		switch (inputNumber(1,4)) {
			case 1:
				electCouncillor(playerTurn);
				break;
			case 2:
				acquirePermitCard(playerTurn);
				break;
			case 3:
				buildEmporiumWithPermitCard(playerTurn);
				break;
			case 4:
				//buildEmporiumWithKing(playerTurn);
				break;
		}

		playerTurn.setMainActionsLeft(playerTurn.getMainActionsLeft() - 1);

	}

	private void electCouncillor(Player playerTurn) {

		Balcony chosenBalcony = chooseBalcony();

		Color chosenCouncillorColor=chooseCouncillorColor();

		electCouncillor(chosenBalcony,chosenCouncillorColor);

		Route richnessRoute = field.getRichnessRoute();
		richnessRoute.movePlayer(Constant.ELECTION_RICHNESS_INCREMENT, playerTurn);

	}

	private void buildEmporiumWithPermitCard(Player playerTurn) {

/*		try{		}
		catch(ArrayIndexOutOfBoundException noUsablePermitCards){		}
		catch(NullPointerException emporiumAlreadyPresent){		}
		catch(NotEnoughAssistantException notEnoughAssistant){		}*/

		ArrayList <PermitCard> usablePermitCards = getUsablePermitCards(playerTurn);

		if (usablePermitCards.size()<1){
			System.out.println("You either have no Business Permit Tiles" +
					" or you have already built in every city they avail you to" +
					". Choose another Main Move.");
			mainAction(playerTurn);
		}
		else {
			int indexChosenPermitCard = choosePermitCard(usablePermitCards);

			PermitCard chosenPermitCard = usablePermitCards.get(indexChosenPermitCard);
			int indexChosenCity = chooseBuildableCity(chosenPermitCard);

			City[] arrayCity = field.getArrayCity();
			arrayCity[indexChosenCity].buildEmporium(playerTurn);
			playerTurn.usePermitCard(playerTurn.getArrayListPermitCard().get(indexChosenPermitCard));

			//implementazione bonus a città vicine
		}

	}

	private int chooseBuildableCity(PermitCard chosenPermitCard) {

		System.out.println("Which city would you like to build your emporium in?");

		City[] actualBuildableCities = chosenPermitCard.getArrayBuildableCities();
		for (int i = 0; i < actualBuildableCities.length; i++) {
			City buildableCity = actualBuildableCities[i];
			System.out.println(i + 1 + ". " + buildableCity.getCityName());
		}

		return inputNumber(1, actualBuildableCities.length) - 1; // -1 for array positioning

	}

	/*  This method creates an arrayList of usablePermitCards setting actualBuildableCities.
		   It moves these permitCards in the front of the player's hand to make them
		   easily removable once the player selects one of them  */
	private ArrayList<PermitCard> getUsablePermitCards(Player playerTurn) {

		ArrayList <PermitCard> usablePermitCards= new ArrayList<>();

		ArrayList<PermitCard> playerPermitCards = playerTurn.getArrayListPermitCard();
		for (int i = 0; i < playerPermitCards.size(); i++) {

			PermitCard permitCard = playerPermitCards.get(i);

			ArrayList <City> actualBuildableCities = new ArrayList<>();
			for (City buildableCity : permitCard.getArrayBuildableCities()) {
				if (!buildableCity.isEmporiumAlreadyBuilt(playerTurn))
					actualBuildableCities.add(buildableCity);
			}

			if (actualBuildableCities.size() > 0) {
				Collections.swap(playerPermitCards,i,usablePermitCards.size());
				usablePermitCards.add(permitCard);
				permitCard.setArrayBuildableCities(actualBuildableCities);
			}
		}

		return usablePermitCards;

	}

	private int choosePermitCard(ArrayList<PermitCard> usablePermitCards) {

		System.out.println("Which of your Business Permit Tiles would you like to use?");

		for(int i=0; i<usablePermitCards.size();i++) {

			PermitCard usablePermitCard = usablePermitCards.get(i); // ArrayIndexOutOfBoundException?

			System.out.println(i + 1 + ". ");
			System.out.println("Buildable Cities:");

			City[] arrayBuildableCities = usablePermitCard.getArrayBuildableCities();
			for (City buildableCity : arrayBuildableCities)
				System.out.print(buildableCity.getCityName() + " ");

			System.out.println("Bonus:");
			Bonus[] arrayBonus = usablePermitCard.getArrayBonus();
			for (Bonus bonus : arrayBonus)
				System.out.print(bonus.getBonusName() + " ");
		}

		return inputNumber(1, usablePermitCards.size())-1; // -1 for array positioning
	}
	
	private void acquirePermitCard(Player playerTurn){
		System.out.println("Which balcony would you like to satisfy?");
		System.out.println("1. Sea Balcony");
		System.out.println("2. Hill Balcony");
		System.out.println("3. Mountain Balcony");
		
		int inputBalcony = inputNumber(1, 3) -1;
		Balcony chosenBalcony = field.getBalconyFromIndex(inputBalcony);
		
		ArrayList <Color> colorOfCouncillors = getColorOfCouncillor (chosenBalcony);
		
		System.out.println("Insert one or more cards that you want use");
		System.out.println("Your Cards:");
		ArrayList<PoliticCard> playerHand = playerTurn.getArrayListPoliticCard();
		ArrayList<Color> colorOfCards = getColorOfCards(playerTurn.getArrayListPoliticCard());

				/*
		 * leggo da cli le carte che il giocatore vuole usare per soddisfare il consiglio
		 */
		
		System.out.println("How many cards do you want to use?");
		int numberOfCardsUsed = inputNumber (1,4);
		
		ArrayList <PoliticCard> usableCards= inputStringToPermitCard(playerHand, numberOfCardsUsed);
		
		Color cardColor;
		ArrayList <Color> colorOfUsableCards = new ArrayList <> ();
		boolean usedMulticolor = false;
		int numberOfMulticolor = 0;
		/*
		 * Controllo che effettivamente l'input corrisponda alle carte che il giocatore
		 * possiede, altrimenti richiedo di inserirle
		 */
		if(colorOfCards.containsAll(usableCards)){	
			for (PoliticCard card : usableCards){
				cardColor=card.getCardColor();
				if (cardColor==Color.getColorFromIndex(6)){ //multicolor
					usedMulticolor = true;
					numberOfMulticolor++;
				}
				colorOfUsableCards.add(cardColor);
			}// creo l'arraylist di colori delle carte scelte da usare, includo il multicolor
		}
		else  {
			System.out.println("You don't have these cards in your hand. Select an other set");
			acquirePermitCard(playerTurn);
		}	
		/*
		 * la condizione dell'if è che l'arrayList di carte usate sia contenuto interamente 
		 * in quello del consiglio da voler soddisfare. 
		 */
		int payed=Constant.MAXIMUM_COST_TO_BUY_PERMIT_TILES;
		if(colorOfCouncillors.containsAll(colorOfUsableCards)){
			 numberOfCardsUsed=colorOfUsableCards.size();
			 if(numberOfCardsUsed==1)
				 payed=Constant.MAXIMUM_COST_TO_BUY_PERMIT_TILES;
			 else{ //calcola costo per soddisfare il consiglio
				 while (numberOfCardsUsed>1){
					 payed = payed - numberOfCardsUsed;
					 numberOfCardsUsed--;
				}
			}
			if(usedMulticolor)
				payed=payed + numberOfMulticolor;
			
			if(!checkIfEnoughRichness(playerTurn, payed)){
				System.out.println("You don't have enough money. Which Main Action would you like to do?");
				mainAction(playerTurn);
			}
			else {
				Route richnessRoute = field.getRichnessRoute();
				richnessRoute.movePlayer(payed, playerTurn);
				
				Region regionOfBalcony = field.getRegionFromIndex(inputBalcony);
				FaceUpPermitCardArea faceUpPermitCardOfRegion = regionOfBalcony.getFaceUpPermitCardArea();

				System.out.println("Which Permit Card do you want take, 1 or 2?");
				PermitCard chosenPermitCard = faceUpPermitCardOfRegion.acquirePermitCard(inputNumber(1, 2)-1);

				playerTurn.acquirePermitCard(chosenPermitCard);
			}
		}
		else{
				System.out.println("You don't have enough money. Which Main Action would you like to do?");
				mainAction(playerTurn);
		}

	}
	
	private ArrayList <Color> getColorOfCards (ArrayList <PoliticCard> playerHand){
		PoliticCard politicCard;
		ArrayList <Color> colorOfCards = new ArrayList <> ();
		
		for (int i=0; i<playerHand.size(); i++){
			politicCard=playerHand.get(i);
			System.out.println(i+1 + ". " + politicCard.getCardColor());
			colorOfCards.add(politicCard.getCardColor());
		}//aggiungo i colori delle carte del giocatore in un arraylist predefinito
		return colorOfCards;
	}

	private ArrayList <Color> getColorOfCouncillor (Balcony chosenBalcony){
		ArrayList<Councillor> councillorOfBalcony = chosenBalcony.getArrayListCouncillor();
		ArrayList<Color> colorOfCouncillors = new ArrayList <> ();
		for (Councillor councillor : councillorOfBalcony){
			System.out.print(councillor.getColor() + " ");
			colorOfCouncillors.add(councillor.getColor());
		}//aggiungo i colori dei consiglieri della balconata in un arraylist predefinito
		return colorOfCouncillors;
	}
	
	private boolean checkIfEnoughRichness(Player playerTurn, int payed) {
		Route richnessRoute = field.getRichnessRoute();

		if (richnessRoute.getPosition(playerTurn)<payed)
			return false;
		else
			return true;

	}
	
	/*
	 * This method works on an arraylist of politicCard. It read from command line a string 
	 * with the colors of the cards that a player want to use, then it convert the string
	 * into a PoliticCard type and add it into a new arraylist.	 * 
	 */
		
	private ArrayList <PoliticCard> inputStringToPermitCard(ArrayList<PoliticCard> playerHand, int numberOfCards){
		Scanner in = new Scanner(System.in);
		String colorIn;
		int counter=0;
		ArrayList <PoliticCard> usableCards = new ArrayList <> ();

		do {
			colorIn=in.nextLine();
			
			for(PoliticCard politicCard: playerHand){
				if (politicCard.getCardColor().toString().equals(colorIn)){
					usableCards.add(politicCard);
				}
			}
			counter++;
		} while(counter<numberOfCards);
		//in.close();
		return usableCards;
	}
	
}
