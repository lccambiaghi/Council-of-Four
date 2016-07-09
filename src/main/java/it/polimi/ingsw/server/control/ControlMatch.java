package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.NobilityRoute;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.ControlTimer;
import it.polimi.ingsw.utils.Message;
import java.util.ArrayList;
import java.util.Collections;

public class ControlMatch {

	private final ControlMarket market;
	private Match match;
	private Player player;
	
	private final ArrayList<Player> allPlayers;
	private ArrayList<Player> playersConnected;

	private int playerNumber=0;

	private boolean gameOver;
	private boolean lastRound;

	private boolean goMarket;


	public ControlMatch(ArrayList<Player> arrayListPlayer){

		Collections.shuffle(arrayListPlayer);

		this.allPlayers = arrayListPlayer;
		this.match = new Match(arrayListPlayer);
		this.market = match.getMarket();

	}

	public void startMatch(){

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

							lastRound = true;

							Broadcast.printlnBroadcastOthers(Message.lastRoundHasStarted(player), playersConnected, player);

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

		Broadcast.printlnBroadcastAll("Player " + player.getNickname() + "has won the match!", playersConnected);

		//classifica punti vittoria?

	}

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
			playersConnected.get(0).getBroker().println(Message.youWon());
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

		Broadcast.printlnBroadcastAll("The market has started!", playersConnected);
		market.startMarket();

	}
	
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
	 * Finally it assigns victory points
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
		//else if(numberFirst > 1) no one gains victory points for the second place

	}

	/**
	 * First it finds maximum number of permit cards
	 * Then it adds victory points
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
	
}
