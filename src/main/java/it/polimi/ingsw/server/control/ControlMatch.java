package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.model.Match;
import java.util.ArrayList;
import java.util.Collections;

public class ControlMatch {

	private final ControlMarket market;
	private Match match;
	private Player player;
	
	private final ArrayList<Player> allPlayers;
	private ArrayList<Player> playersConnected;

	private boolean gameOver;

	private boolean goMarket;
	private Turn turn;

	private long startTime;
	private long endTime;

	public ControlMatch(ArrayList<Player> arrayListPlayer){

		Collections.shuffle(arrayListPlayer);

		this.allPlayers =arrayListPlayer;
		this.match=Match.MatchFactory(arrayListPlayer);
		this.market=match.getMarket();

	}

	private ArrayList<Player> getPlayersConnected(){
		ArrayList<Player> ps = new ArrayList<>();
		for(Player p: allPlayers){
			p.isConnected();
				ps.add(p);
		}
		return ps;
	}

	public void startMatch(){

		int playerNumber=0;
		boolean lastRound = false;

		do {

			playersConnected = getPlayersConnected();

			if(!atLeastTwoPlayersConnected()){

				gameOver=true;

			}else{

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

					// TODO controllo sul giocatore per il numero di empori
					if(true) {

						lastRound = true;

						// per ogni giocatore tranne il corrente lastTurnDone=false
						// il corrente ha lTD=true

						Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOut(player), playersConnected, player);

					}
					if(!player.isConnected()){
						Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOut(player), playersConnected, player);
					}

				}else if(goMarket  && !lastRound){

					goMarket=false;

					marketHandler();

				}


			}

		}while(!gameOver);

	}
	
	private void turnHandler(){
		
		try{
			Broadcast.printlnBroadcastOthers(Message.turnOf(player), playersConnected, player);
			player.getBroker().println(Message.yourTurn(Constant.TIMER_SECONDS_TO_PERFORM_ACTION));
		}catch (InterruptedException e) {}
		
		turn = new Turn(match, player, allPlayers);
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
		
		try {
			
			Broadcast.printlnBroadcastAll("The market has started!", playersConnected);
			market.startMarket();
			
		} catch (InterruptedException e) {}
		
	}

	private boolean atLeastTwoPlayersConnected(){
		if(playersConnected==null){
			return false;
		}else if(playersConnected.size()==1){
			try {
				playersConnected.get(0).getBroker().println(Message.youWon());
			} catch (InterruptedException e) {}
			return false;
		}else{
			return true;
		}
	}
	
}
