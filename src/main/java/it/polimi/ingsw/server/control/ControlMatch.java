package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.ControlTimer;
import it.polimi.ingsw.utils.Message;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class handles the control flow of the match.
 * It handles turns, checks conditions for the end of the match
 * and determines match's winner
 */
public class ControlMatch {

	private final ControlMarket market;

	private final Match match;

	private Player player;
	
	private final ArrayList<Player> allPlayers;

	private ArrayList<Player> playersConnected;

	private int playerNumber=0;

	private boolean gameOver;

	private boolean lastRound;

	private boolean goMarket;

	/*
	 * @param arrayListPlayer initial list of player attending the match
     */
	public ControlMatch(List<Player> arrayListPlayer){

		Collections.shuffle(arrayListPlayer);

		this.allPlayers = (ArrayList<Player>) arrayListPlayer;

		this.match = new Match((ArrayList<Player>) arrayListPlayer);

		this.market = match.getMarket();

	}

	/**
	 * This is the main method of the match.
	 * It gets connected players, determines which player has to play,
	 * determines if market has to be started.
	 *
	 * It hecks conditions for the match's end, handles last round
	 * and determines who is the winner
	 */
	public void startMatch(){

		playersConnected = getPlayersConnected();
		
		Broadcast.printlnBroadcastAll(Message.playingPlayers(playersConnected), playersConnected);
		
		do {

			playersConnected = getPlayersConnected();

			if(!atLeastTwoPlayersConnected()) {

				gameOver = true;

			} else {
				
				getNextConnectedPlayerAndCheckMarket();

				if (lastRound && player.isLastTurnDone()) {

					gameOver = true;

				} else {

					if (goMarket) {

						goMarket = false;

						marketHandler();

					} else {

						turnHandler();

						if (player.getArrayListEmporiumBuilt().size() == Constant.NUMBER_EMPORIUMS_TO_WIN && !lastRound) {

							player.addVictory(Constant.VICTORY_INCREMENT_LAST_EMPORIUM);

							lastRound = true;

							Broadcast.printlnBroadcastAll(Message.lastRoundHasStarted(player), playersConnected);

						}

						if (lastRound)
							player.setLastTurnDone(true);

						if (!player.isConnected())
							Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOut(player), playersConnected, player);

					}

				}

			}

		}while(!gameOver);

		assignVictoryPointsBecauseNobility();
		
		assignVictoryPointsBecausePermitCards();

		player = electWinner();

		Broadcast.printlnBroadcastAll("Player " + player.getNickname() + " has won the match!", playersConnected);

		resetPlayersStatus();

	}

	/**
	 * This method checks which player gets to win
	 * according to victory points.
	 *
	 * It gives victory bonus according to nobility route,
	 * permitcards and - if necessary - assistants and politcs cards
	 * @return winner player
     */
	private Player electWinner() {

		int max=0;

		for (Player aPlayer: playersConnected)
			if(aPlayer.getVictory()> max)
				max = aPlayer.getVictory();


		ArrayList<Player> maxVictoryPlayers = new ArrayList<>();

		for (Player aPlayer: playersConnected)
			if(aPlayer.getVictory() == max)
				maxVictoryPlayers.add(aPlayer);

		int maxAssistansAndPoliticsCard = 0;

		if(maxVictoryPlayers.size()==1)
			return maxVictoryPlayers.get(0);

		//else
		for(Player aPlayer: maxVictoryPlayers)
			if(aPlayer.getAssistant() + aPlayer.getArrayListPoliticCard().size() > maxAssistansAndPoliticsCard)
				maxAssistansAndPoliticsCard = aPlayer.getAssistant() + aPlayer.getArrayListPoliticCard().size();

		for(Player aPlayer: maxVictoryPlayers)
			if(aPlayer.getAssistant() + aPlayer.getArrayListPoliticCard().size() == maxAssistansAndPoliticsCard)
				return aPlayer;

		return null; //should never be reached

	}

	private ArrayList<Player> getPlayersConnected(){

		ArrayList<Player> ps = new ArrayList<>();

		for(Player p: allPlayers){
			if(p.isConnected()){
				ps.add(p);
			}else if(!p.isMessageDisconnectedSent()){
				Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOut(p), allPlayers, p);
			}
		}
		return ps;

	}

	private boolean atLeastTwoPlayersConnected(){
		if(playersConnected==null){
			return false;
		}else if(playersConnected.size()==1){
			return false;
		}else{
			return true;
		}
	}

	private void getNextConnectedPlayerAndCheckMarket() {

		do {

			//If it's the last player of the group
			if (playerNumber == allPlayers.size()) {

				playerNumber = 0;

				if (!lastRound) {

					goMarket = true;

					break;

				} else {

					player = allPlayers.get(playerNumber);

					playerNumber++;

				}

			} else {

				player = allPlayers.get(playerNumber);

				playerNumber++;

			}

		} while (!player.isConnected());

	}

	private void marketHandler(){

		market.startMarket();
		
		playersConnected = getPlayersConnected();
		Broadcast.printlnBroadcastAll(Message.playingPlayers(playersConnected), playersConnected);

	}

	/**
	 * This method handles a single turn of the match.
	 *
	 * It checks if turn is completed in time, otherwise it
	 * interrupts the player, interrupting his ability to participate in the match.
	 */
	private void turnHandler(){
		
		Broadcast.printlnBroadcastOthers(Message.turnOf(player), playersConnected, player);
		player.getBroker().println(Message.yourTurn(Constant.TIMER_SECONDS_TO_PERFORM_ACTION));

		Turn turn = new Turn(match, player, allPlayers);
		Thread turnThread = new Thread(turn);
		turnThread.start();
		
		new ControlTimer().waitForThreadUntilTimerExpires(turnThread, Constant.TIMER_SECONDS_TO_PERFORM_ACTION);
		
		/**
		 * If the timer has expired and the player hasn't completed an action
		 */
		if(turnThread.isAlive()){	
			turnThread.interrupt();
		}
		
	}


	/**
	 * First it finds max victory points and second max
	 * Then it counts how many players are first
	 * Finally it assigns victory points according to these numbers
	 */
	private void assignVictoryPointsBecauseNobility() {

		NobilityRoute nobilityRoute = match.getField().getNobilityRoute();

		int first=0;

		int second=0;

		int playerNobility;

		for (Player aPlayer: playersConnected){

			playerNobility = nobilityRoute.getPosition(aPlayer);

			if (playerNobility>second){

				second=playerNobility;

				if (playerNobility>first){

					first=playerNobility;

				}

			}

		}

		ArrayList<Player> firstNobilityPlayers = new ArrayList<>();

		ArrayList<Player> secondNobilityPlayers = new ArrayList<>();

		for (Player aPlayer: playersConnected) {

			if (nobilityRoute.getPosition(aPlayer) == first) {

				firstNobilityPlayers.add(aPlayer);

			}

			if(nobilityRoute.getPosition(aPlayer) == second){

				secondNobilityPlayers.add(aPlayer);

			}

		}

		for (Player aPlayer: firstNobilityPlayers)
			aPlayer.addVictory(Constant.FIRST_NOBILITY_VICTORY_INCREMENT);

		if(firstNobilityPlayers.size() == 1)
			for (Player aPlayer : secondNobilityPlayers)
				aPlayer.addVictory(Constant.SECOND_NOBILITY_VICTORY_INCREMENT);
		//else if numberFirst > 1 no one gains victory points for the second place

	}

	/**
	 * First it finds maximum number of owned permit cards
	 * Then it adds victory points to those player/players
	 */
	private void assignVictoryPointsBecausePermitCards() {

		int max=0;

		int numberPermitCard;

		for (Player aPlayer: playersConnected) {

			numberPermitCard=0;

			for(int i=0; i<aPlayer.getArrayListPermitCard().size(); i++)
				numberPermitCard++;

			for(int i=0; i<aPlayer.getArrayListUsedPermitCard().size(); i++)
				numberPermitCard++;

			if(numberPermitCard>max)
				max=numberPermitCard;

		}

		for (Player aPlayer: playersConnected) {

			numberPermitCard = 0;

			for(int i=0; i<aPlayer.getArrayListPermitCard().size(); i++)
				numberPermitCard++;

			for(int i=0; i<aPlayer.getArrayListUsedPermitCard().size(); i++)
				numberPermitCard++;

			if (numberPermitCard == max)
				aPlayer.addVictory(Constant.MAX_PERMIT_CARD_VICTORY_INCREMENT);

		}

	}
	
	private void resetPlayersStatus(){
		for(Player p: playersConnected)
			p.resetMatchStatus();
	}
	
}
