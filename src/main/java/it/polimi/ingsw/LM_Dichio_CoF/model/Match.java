package it.polimi.ingsw.LM_Dichio_CoF.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.ControlMatch;
import it.polimi.ingsw.LM_Dichio_CoF.control.InfoMatch;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;


public class Match {

	private ArrayList<Player> arrayListPlayer;
	private Field field;
	private Market market;
	private boolean isGameOver=false;
	
	private ControlMatch controlMatch;
	private InfoMatch infoMatch;
	


	private Player playerTurn;
	
	public Match(ArrayList<Player> arrayListPlayer) {
		
		this.arrayListPlayer = arrayListPlayer;
		
		//Collections.shuffle(arrayListPlayer); //per adesso testiamo solo il primo giocatore

		Configurations config = readFileConfigurations();
		
		giveInitialPoliticCards(this.arrayListPlayer);
		
		giveInitialAssistants(this.arrayListPlayer);
		
		field = new Field(config, arrayListPlayer);
		
		market = new Market (arrayListPlayer);
		
		controlMatch = new ControlMatch(this);
		
		infoMatch = new InfoMatch(this);
		
		startGame();
		
	}

	// MatchMock
	public Match(){};

	public void startGame() {
		
		int turn=1;

		do{

			controlMatch.controlWithPlayer(arrayListPlayer.get(turn-1));
			
			//turn(arrayListPlayer.get(turn-1)); //array positioning

			//if (turn % arrayListPlayer.size() ==0)
				market.startMarket();

		}while(!isGameOver);
		

	}

	private Configurations readFileConfigurations(){

		FileInputStream fileInputStream = null;
		
		try {
			
			//il salvataggio per ora è in locale, dovrà essere inviato al server quando ci sarà la connessione
			fileInputStream = new FileInputStream("./src/configurations/config");
			
			// create an ObjectInputStream for the file we created before
	         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	         return (Configurations) objectInputStream.readObject();
	         
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

		return null;
	}
		
	private void giveInitialPoliticCards(ArrayList<Player> arrayListPlayer){
		for (Player player : arrayListPlayer)
			for (int itCard = 0; itCard < Constant.POLITIC_CARDS_INITIAL_NUMBER; itCard++)
				player.addPoliticCard(new PoliticCard());
	}
	
	private void giveInitialAssistants(ArrayList<Player> arrayListPlayer){
		for(int itPlayer = 0, numberAssistants = Constant.ASSISTANT_INITIAL_FIRST_PLAYER;
			itPlayer<arrayListPlayer.size(); itPlayer++, numberAssistants++)
				arrayListPlayer.get(itPlayer).setAssistant(numberAssistants);
	}

	
	
	
	
	
	
	
	
	
	public void turn(Player playerTurn){

		this.playerTurn=playerTurn;
		
		/* Draw a card */
		playerTurn.addPoliticCard(new PoliticCard());

		playerTurn.setMainActionsLeft(1);

		Message.ifQuickAction(playerTurn);
		
		if (askYesOrNo()) {
			quickAction(playerTurn);
			mainAction(playerTurn);
		}
		else{
			mainAction(playerTurn);
			Message.ifQuickAction(playerTurn);
			if (askYesOrNo())
				quickAction(playerTurn);
		}

	}

	private boolean askYesOrNo(){ //TODO test e socket con playerTurn

		Message.chooseYesOrNo_1_2(playerTurn);

		switch (inputNumber(1,2)) {
			case 1:
				return true;
			default: //case 2:
				return false;
		}

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

	private void quickAction(Player playerTurn) { 
		Message.chooseQuickAction_1_4(playerTurn);

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
			Message.notEnoughRichness(playerTurn);
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
			Message.notEnoughAssistant(playerTurn);
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

		Message.chooseRegion_1_3(playerTurn);

		return field.getRegionFromIndex(inputNumber(1,3) - 1); // -1 for array positioning
	}

	private void electCouncillorWithAssistant(Player playerTurn){

		/* try{}
		   catch(NotEnoughAssistantsException e)*/

		if (playerTurn.getAssistant()<Constant.ELECTION_ASSISTANT_COST){
			Message.notEnoughAssistant(playerTurn);
			quickAction(playerTurn);
		}
		else {

			Balcony chosenBalcony = chooseBalcony();

			ArrayList<Color> choosableColors = getChoosableColors(field);
			Color chosenCouncillorColor=chooseCouncillorColor(choosableColors);

			electCouncillor(chosenBalcony, chosenCouncillorColor);

			playerTurn.decrementAssistant(Constant.ELECTION_ASSISTANT_COST);
		}
	}

	private Balcony chooseBalcony(){

		Message.chooseBalcony_1_4(playerTurn);

		return field.getBalconyFromIndex(inputNumber(1, 4)-1); //-1 for array positioning

	}

	private ArrayList<Color> getChoosableColors(Field field) {

		ArrayList<Councillor> availableCouncillors = field.getAvailableCouncillors().getArrayListCouncillor();
		boolean[] seen = new boolean[Constant.COLORS_NUMBER];

		ArrayList<Color> choosableColors = new ArrayList<>();
		for (Councillor councillor: availableCouncillors)
			if (!seen[Color.valueOf(councillor.getColor().toString()).ordinal()]) {
				choosableColors.add(councillor.getColor());
				seen[Color.valueOf(councillor.getColor().toString()).ordinal()]=true;
			}

		return choosableColors;
	}

	private Color chooseCouncillorColor(ArrayList<Color> choosableColors){

		Message.askNewCouncillor(playerTurn);
		
		for (int i=0; i<choosableColors.size(); i++)
			System.out.println(i+1 + ". " + choosableColors.get(i));

		return choosableColors.get(inputNumber(1, choosableColors.size()));

	}

	private void electCouncillor(Balcony chosenBalcony, Color chosenCouncillorColor) {
		AvailableCouncillors availableCouncillors = field.getAvailableCouncillors();
		Councillor chosenCouncillor = availableCouncillors.removeAvailableCouncillor(chosenCouncillorColor); //NullPointerException?
		chosenBalcony.electCouncillor(chosenCouncillor,availableCouncillors);
	}

	private void performAdditionalMainAction(Player playerTurn){
		if (playerTurn.getAssistant()<Constant.ADDITIONAL_MAIN_MOVE_ASSISTANT_COST){
			Message.notEnoughAssistant(playerTurn);
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

		Message.chooseMainAction_1_4(playerTurn);

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
				buildEmporiumWithKing(playerTurn);
				break;
		}

		playerTurn.setMainActionsLeft(playerTurn.getMainActionsLeft() - 1);

	}

	private void electCouncillor(Player playerTurn) {

		Balcony chosenBalcony = chooseBalcony();

		ArrayList<Color> choosableColors = getChoosableColors(field);

		Color chosenCouncillorColor=chooseCouncillorColor(choosableColors);

		electCouncillor(chosenBalcony,chosenCouncillorColor);

		Route richnessRoute = field.getRichnessRoute();
		richnessRoute.movePlayer(Constant.ELECTION_RICHNESS_INCREMENT, playerTurn);

	}

	private void buildEmporiumWithPermitCard(Player playerTurn) {

		ArrayList <PermitCard> usablePermitCards = getUsablePermitCards(playerTurn);

		if (usablePermitCards.size()<1){
			Message.notEnoughPoliticsCardsAndRichness(playerTurn);
			mainAction(playerTurn);
		}
		else {
			int indexChosenPermitCard = choosePermitCard(usablePermitCards);
			PermitCard chosenPermitCard = usablePermitCards.get(indexChosenPermitCard);

			int indexChosenCity = chooseBuildableCity(chosenPermitCard);
			City[] arrayCity = field.getArrayCity();
			arrayCity[indexChosenCity].buildEmporium(playerTurn);
			playerTurn.usePermitCard(playerTurn.getArrayListPermitCard().get(indexChosenPermitCard));

			// applying bonuses
			ArrayList<City> nearbyBuiltCities = getAdjacentBuiltCities(playerTurn, indexChosenCity);

			for (City city: nearbyBuiltCities)
				for (Bonus bonus : city.getArrayBonus())
					bonus.applyBonus(playerTurn, field);

		}

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

		Message.choosePermitCard(playerTurn);

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

	private int chooseBuildableCity(PermitCard chosenPermitCard) {

		Message.chooseCity(playerTurn);

		City[] actualBuildableCities = chosenPermitCard.getArrayBuildableCities();
		for (int i = 0; i < actualBuildableCities.length; i++) {
			City buildableCity = actualBuildableCities[i];
			System.out.println(i + 1 + ". " + buildableCity.getCityName());
		}

		return inputNumber(1, actualBuildableCities.length) - 1; // -1 for array positioning

	}

	/* Queue of adjacent built cities. It starts only with chosenCity.
		Elements of queue are removed and analysed one at a time.
		If a city is linked to the analysed element of the queue and emporium is present,
		the city is also added to the queue */
	private ArrayList<City> getAdjacentBuiltCities(Player playerTurn, int indexChosenCity) {

		List<Integer>[] arrayCityLinks = field.getArrayCityLinks();
		boolean[] visitedCities = new boolean[arrayCityLinks.length];
		Queue<Integer> toVisitQueue = new LinkedList<>();

		toVisitQueue.add(indexChosenCity);
		visitedCities[indexChosenCity] = true;

		ArrayList<City> nearbyBuiltCities = new ArrayList<>();
		City[] arrayCity=field.getArrayCity();
		while (!toVisitQueue.isEmpty()) {
			int visitingCity = toVisitQueue.remove();
			nearbyBuiltCities.add(arrayCity[visitingCity]);
			for (Integer adjCity : arrayCityLinks[visitingCity]) {
				if (arrayCity[adjCity].isEmporiumAlreadyBuilt(playerTurn) && !visitedCities[adjCity]) {
					toVisitQueue.add(adjCity);
					visitedCities[adjCity] = true;
				}
			}
		}

		return nearbyBuiltCities;

	}

	private void acquirePermitCard(Player playerTurn){
		
		Message.chooseRegion_1_3(playerTurn);
		
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
			Message.selectAnotherPoliticsCardsSet(playerTurn);
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
				Message.notEnoughRichness(playerTurn);
				mainAction(playerTurn);
			}
			else {
				Route richnessRoute = field.getRichnessRoute();
				richnessRoute.movePlayer(payed, playerTurn);
				
				Region regionOfBalcony = field.getRegionFromIndex(inputBalcony);
				FaceUpPermitCardArea faceUpPermitCardOfRegion = regionOfBalcony.getFaceUpPermitCardArea();

				Message.choosePermitCards_1_2(playerTurn);
				PermitCard chosenPermitCard = faceUpPermitCardOfRegion.acquirePermitCard(inputNumber(1, 2)-1);

				playerTurn.acquirePermitCard(chosenPermitCard);
			}
		}
		else{
				Message.notEnoughRichness(playerTurn);
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

	private void buildEmporiumWithKing(Player playerTurn) {

		ArrayList<PoliticCard> usablePoliticCards = getUsablePoliticCards(playerTurn, 3); // King's index
		Route richnessRoute = field.getRichnessRoute();
		int playerRichness = richnessRoute.getPosition(playerTurn);

		if (usablePoliticCards.size()<1){
			Message.notEnoughPoliticsCards(playerTurn);
			mainAction(playerTurn);
		}
		else {
			if (eligibleMove(usablePoliticCards, playerRichness)<1) {
				Message.notEnoughPoliticsCardsAndRichness(playerTurn);
				mainAction(playerTurn);
			}
			else {
				Message.choosePoliticsCards(playerTurn);

				boolean eligibleSet=false;
				int cost;
				ArrayList<PoliticCard> chosenPoliticCards;
				do {
					chosenPoliticCards = choosePoliticCards(usablePoliticCards);
					cost=eligibleMove(chosenPoliticCards, playerRichness);
					if (cost>0)
						eligibleSet=true;
					else
						Message.notEnoughRichness(playerTurn);
				}while(!eligibleSet);

				for (PoliticCard politicCard:chosenPoliticCards)
					playerTurn.discardPoliticCard(politicCard);

				richnessRoute.movePlayer(-cost, playerTurn);

				Map<City, Integer> movableCities = getMovableCities(field, playerTurn);
				Map.Entry<City, Integer> chosenCity = chooseKingCity(movableCities);

				field.getKing().setCurrentCity(chosenCity.getKey());
				chosenCity.getKey().buildEmporium(playerTurn);
				richnessRoute.movePlayer(-(chosenCity.getValue()), playerTurn);

			}
		}
	}

	/* First it adds Multicolor cards to usable cards, then for each councillor,
	   if it finds a matching color card, it adds it and goes to next councillor */
	private ArrayList<PoliticCard> getUsablePoliticCards(Player playerTurn, int indexChosenBalcony) {

		Balcony chosenBalcony = field.getBalconyFromIndex(indexChosenBalcony);
		ArrayList <PoliticCard> playerHand = playerTurn.getArrayListPoliticCard();
		ArrayList <PoliticCard> usableCards = new ArrayList<>();

		for (PoliticCard politicCard: playerHand)
			if (politicCard.getCardColor()==Color.Multicolor) {
				usableCards.add(politicCard);
				playerHand.remove(politicCard);
			}

		for (Councillor councillor : chosenBalcony.getArrayListCouncillor()) {
			boolean councillorSatisfied = false;
			while (!councillorSatisfied)
				for (PoliticCard politicCard : playerHand)
					if (councillor.getColor() == politicCard.getCardColor()) {
						usableCards.add(politicCard);
						playerHand.remove(politicCard);
						councillorSatisfied = true;
					}
		}

		return usableCards;

	}

	/* First call: if a set of cards that allows the player to perform the move exists,
		the method returns a positive number.
		Second call: if the specified set is eligible, the method returns what the player has to pay */
	private int eligibleMove(ArrayList<PoliticCard> usablePoliticCards, int playerRichness) {

		int numberMulticolor=0;
		for (PoliticCard politicCard:usablePoliticCards )
			if(politicCard.getCardColor()==Color.Multicolor) {
				numberMulticolor++;
				usablePoliticCards.remove(politicCard);
			}
		int numberSingleColor=usablePoliticCards.size();

		switch (numberSingleColor+numberMulticolor) {
			case 1:
				if (playerRichness > 10 + numberMulticolor)
					return (10 + numberMulticolor);
				break;
			case 2:
				if (playerRichness > 7 + numberMulticolor)
					return (7 + numberMulticolor);
				break;
			case 3:
				if (playerRichness > 4 + numberMulticolor)
					return (4 + numberMulticolor);
				break;
			default: // >3
				if (playerRichness > 4 - numberSingleColor)
					return (4 - numberSingleColor);
				break;
		}

		return -1;

	}

	private ArrayList<PoliticCard> choosePoliticCards(ArrayList<PoliticCard> usablePoliticCards) {

		ArrayList<PoliticCard> chosenPoliticCards = new ArrayList<>();

		System.out.println("Choose one card at a time to a maximum of four. Choose 0 when done.");
		for (int i = 0; i < usablePoliticCards.size(); i++) {
			System.out.println(i + 1 + ". " + usablePoliticCards.get(i).getCardColor());
		}

		int indexChosenPermitCard = inputNumber(1, usablePoliticCards.size()) - 1; // -1 for array positioning
		chosenPoliticCards.add(usablePoliticCards.remove(indexChosenPermitCard));

		do {
			System.out.println("0. Done.");
			for (int i = 0; i < usablePoliticCards.size(); i++) {
				System.out.println(i + 1 + ". " + usablePoliticCards.get(i).getCardColor());
			}
			indexChosenPermitCard = inputNumber(0, usablePoliticCards.size());

			if (indexChosenPermitCard > 0)
				chosenPoliticCards.add(usablePoliticCards.remove(indexChosenPermitCard - 1)); // -1 for array positioning

		} while (indexChosenPermitCard > 0 && chosenPoliticCards.size() < 4);

		return chosenPoliticCards;

	}

	private Map<City, Integer> getMovableCities(Field field, Player playerTurn) {

		City[] arrayCity=field.getArrayCity();
		int indexCurrentCity=Arrays.asList(arrayCity).indexOf(field.getKing().getCurrentCity());

		List<Integer>[] arrayCityLinks = field.getArrayCityLinks();
		boolean[] visitedCities = new boolean[arrayCityLinks.length];
		Queue<Integer> visitingLevelQueue = new LinkedList<>();
		Queue<Integer> nextLevelQueue = new LinkedList<>();

		visitingLevelQueue.add(indexCurrentCity);
		visitedCities[indexCurrentCity] = true;

		Map<City, Integer> movableCities= new LinkedHashMap<>();
		int levelCost=0;
		movableCities.put(arrayCity[indexCurrentCity], levelCost);

		while (playerTurn.getRichness()>levelCost) {
			while(!visitingLevelQueue.isEmpty()) {
				int visitingCity = visitingLevelQueue.remove();
				for (Integer adjCity : arrayCityLinks[visitingCity])
					if (!visitedCities[adjCity]) {
						nextLevelQueue.add(adjCity);
						visitedCities[adjCity] = true;
						if (!arrayCity[adjCity].isEmporiumAlreadyBuilt(playerTurn))
							movableCities.put(arrayCity[adjCity],levelCost);
					}
			}
			while(!nextLevelQueue.isEmpty())
				visitingLevelQueue.add(nextLevelQueue.remove());
			levelCost+=2;
		}

		return movableCities;

	}

	private Map.Entry<City, Integer> chooseKingCity(Map<City, Integer> movableCities) {

		System.out.println("Choose the destination city:");

		int i = 0;
		for (Map.Entry<City, Integer> city : movableCities.entrySet()){
			System.out.println(i + 1 + ". " + city.getKey() + " Cost: " + city.getValue());
			i++;
		}

		int index= inputNumber(1,movableCities.size());

		i=0;
		for (Map.Entry<City, Integer> city : movableCities.entrySet())
			if (i == index)
				return city;

		return null;
	}

	public Field getField(){
		return field;
	}

	public ArrayList<Player> getArrayListPlayer(){return arrayListPlayer;}

	public void setGameOver(){
		isGameOver=true;
	}
	
	public InfoMatch getInfoMatch() {return infoMatch;}

}
