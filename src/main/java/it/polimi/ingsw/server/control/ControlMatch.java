package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.model.Match;
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

	private boolean gameOver;
	private boolean lastRound;

	private boolean goMarket;


	public ControlMatch(ArrayList<Player> arrayListPlayer){

		Collections.shuffle(arrayListPlayer);

		this.allPlayers =arrayListPlayer;
		this.match=Match.MatchFactory(arrayListPlayer);
		this.market=match.getMarket();

	}


	public void startMatch(){

		int playerNumber=0;

		do {

			playersConnected = getPlayersConnected();

			if(!atLeastTwoPlayersConnected()) {

				gameOver = true;

				break;

			}

			do{
				//If it's the last player of the group
				if(playerNumber==allPlayers.size()){

					playerNumber=0;

					goMarket=true;

					break;

				}else{

					player=allPlayers.get(playerNumber);

					playerNumber++;

				}

			}while(!player.isConnected());

			if(!goMarket){

				turnHandler();

				if(player.getArrayListEmporiumBuilt().size() == Constant.NUMBER_EMPORIUMS_TO_WIN) {

					lastRound = true;

					player.setLastTurnDone(true);

					Broadcast.printlnBroadcastOthers(Message.lastRoundHasStarted(player), playersConnected, player);

				}

				if(!player.isConnected()){

					Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOut(player), playersConnected, player);

				}

			}else if(!lastRound) {

				goMarket = false;

				marketHandler();

			}else //if(lastRound)
				player.setLastTurnDone(true);

		}while(!gameOver || !player.isLastTurnDone());

		//final steps

	}

	private ArrayList<Player> getPlayersConnected(){

		ArrayList<Player> ps = new ArrayList<>();

		for(Player p: allPlayers)
			if(p.isConnected())
				ps.add(p);

		return ps;

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
	
	private void marketHandler(){

		Broadcast.printlnBroadcastAll("The market has started!", playersConnected);
		market.startMarket();
		
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
	
}
