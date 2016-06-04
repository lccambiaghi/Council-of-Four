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
		this.numberPlayers=arrayListPlayer.size();
		
		Collections.shuffle(arrayListPlayer);
		
		readFileConfigurations();
		
		giveInitialPoliticCards();
		
		giveInitialAssistants();
		
		this.field = new Field(config, arrayListPlayer);
		
		startGame();
		
	}
	
	private void startGame() {	
		
		int turn=0;

		// gestisci con try catch: quando ci sono le condizioni viene sollevata l'eccezione partita finita

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

		int choice=inputNumber(1,2);

		switch (choice) {
			case 1:
				return true;
			case 2:
				return false;
		}

		return false; // non so perchè me lo fa inserire, dovrebbe essere superfluo
	}

	private int inputNumber (int lowerBound, int upperBound){ //TODO throws RemoteException

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

	private void quickAction(Player playerTurn) { //TODO

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
				//buildEmporiumWithPermitCard(playerTurn);
				break;
			case 4:
				//buildEmporiumWithKing(playerTurn);
				break;
		}

		playerTurn.setMainActionsLeft(playerTurn.getMainActionsLeft() - 1);

	}

	private void electCouncillor(Player playerTurn) {

		System.out.println("Which balcony would you like to operate on?");
		System.out.println("1. Sea Balcony");
		System.out.println("2. Hill Balcony");
		System.out.println("3. Mountain Balcony");
		System.out.println("4. King Balcony");

		Balcony chosenBalcony = field.getBalconyFromIndex(inputNumber(1, 4)-1);

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

		Color chosenCouncillorColor = Color.getColorFromIndex(inputNumber(1, 4));
		AvailableCouncillors availableCouncillors = field.getAvailableCouncillors();
		boolean colorAvailable = availableCouncillors.checkIfColorAvailable(chosenCouncillorColor);

		while(!colorAvailable) {
			System.out.println("Color not available! Choose another one.");
			chosenCouncillorColor = Color.getColorFromIndex(inputNumber(1, 4));
			colorAvailable = availableCouncillors.checkIfColorAvailable(chosenCouncillorColor);
		}

		Councillor chosenCouncillor = availableCouncillors.removeAvailableCouncillor(chosenCouncillorColor); //TODO NullPointerException?
		chosenBalcony.electCouncillor(chosenCouncillor,availableCouncillors);

		Route richnessRoute = field.getRichnessRoute();

		richnessRoute.movePlayer(Constant.ELECTION_RICHNESS_INCREMENT, playerTurn);

	}
	private void acquirePermitCard(Player playerTurn){
		System.out.println("Which balcony would you like to satisfy?");
		System.out.println("1. Sea Balcony");
		System.out.println("2. Hill Balcony");
		System.out.println("3. Mountain Balcony");
		
		int inputBalcony = inputNumber(1, 3) -1;
		Balcony chosenBalcony = field.getBalconyFromIndex(inputBalcony);
		
		ArrayList<Councillor> councillorOfBalcony = chosenBalcony.getArrayListCouncillor();
		ArrayList<Color> colorOfCouncillors = new ArrayList <> ();
		for (Councillor councillor : councillorOfBalcony){
			System.out.print(councillor.getColor() + " ");
			colorOfCouncillors.add(councillor.getColor());
		}//aggiungo i colori dei consiglieri della balconata in un arraylist predefinito
		
		ArrayList<PoliticCard> playerHand = playerTurn.getArrayListPoliticCard();
		ArrayList<Color> colorOfCards = new ArrayList <> ();

		System.out.println("Insert one or more cards that you want use");
		System.out.println("Your Cards:");
		
		PoliticCard politicCard;
		for (int i=0; i<playerHand.size(); i++){
			politicCard=playerHand.get(i);
			System.out.println(i+1 + ". " + politicCard.getCardColor());
			colorOfCards.add(politicCard.getCardColor());
		}//aggiungo i colori delle carte del giocatore in un arraylist predefinito
		
		
		/*
		 * leggo da cli le carte che il giocatore vuole usare per soddisfare il consiglio
		 */
		
		ArrayList <PoliticCard> usableCards = new ArrayList <>();
		usableCards= inputStringToPermitCard(playerHand);
		
		Color cardColor;
		ArrayList <Color> colorOfUsableCards = new ArrayList <> ();
		boolean usedMulticolor = false;
		
		/*
		 * Controllo che effettivamente l'input corrisponda alle carte che il giocatore
		 * possiede, altrimenti richiedo di inserirle
		 */
		if(playerHand.containsAll(usableCards)){	
			for (PoliticCard card : usableCards){
				cardColor=card.getCardColor();
				if (cardColor==Color.getColorFromIndex(6)) //multicolor
					usedMulticolor = true;
				colorOfUsableCards.add(cardColor);
			}// creo l'arraylist di colori delle carte scelte da usare, includo il multicolor
		}
		else  {
			System.out.println("You don't have these cards in your hand. Select an other set");
			acquirePermitCard(playerTurn);
		}
			
		
		int totalUsedCards;
		int payed=10;
		
		/*
		 * la condizione dell'if è che l'arrayList di carte usate sia contenuto interamente 
		 * in quello del consiglio da voler soddisfare. 
		 */
		
		if(colorOfCouncillors.containsAll(colorOfUsableCards)){
			 totalUsedCards=colorOfUsableCards.size();
			 if(totalUsedCards==1)
				 payed=10;
			 else{ //calcola costo per soddisfare il consiglio
				 while (totalUsedCards>0){
					 payed = payed - totalUsedCards;
					 totalUsedCards--;
				}
			}
			if(usedMulticolor)
				payed++;
			
			Route richnessRoute = field.getRichnessRoute();
			int playerPosition = richnessRoute.getPosition(playerTurn);
			if(playerPosition>payed)
				richnessRoute.movePlayer(-payed, playerTurn);
			else{
				System.out.println("You don't have enough money. Which Main Action would you like to do?");
				mainAction(playerTurn);
			}
		}
		else{
				System.out.println("You don't have enough money. Which Main Action would you like to do?");
				mainAction(playerTurn);
		}
		Region regionOfBalcony = field.getRegionFromIndex(inputBalcony);
		FaceUpPermitCardArea faceUpPermitCardOfRegion = regionOfBalcony.getFaceUpPermitCardArea();
		
		System.out.println("Which Permit Card do you want take, 1 or 2?");
		PermitCard chosenPermitCard = faceUpPermitCardOfRegion.takePermitCard(inputNumber(1, 2)-1);
		
		//playerTurn.arrayListPermitCard.add(chosenPermitCard);
	}
	
	/*
	 * This method works on an arraylist of politicCard. It read from command line a string 
	 * with the colors of the cards that a player want to use, then it convert the string
	 * into a PoliticCard type and add it into a new arraylist.	 * 
	 */
		
	public ArrayList <PoliticCard> inputStringToPermitCard(ArrayList <PoliticCard> playerHand){
		Scanner in = new Scanner(System.in);
		String colorIn;
		ArrayList <PoliticCard> usableCards = new ArrayList <> ();

		do {
			colorIn=in.next();
			
			for(PoliticCard politicCard: playerHand){
				if (politicCard.getCardColor().toString().equals(colorIn)){
					usableCards.add(politicCard);
				}
			}
		} while(in.hasNextLine());
		in.close();
		return usableCards;
	}
	
	
	
}
