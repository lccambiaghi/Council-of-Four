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
				//acquirePermitCard(playerTurn);
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
}
