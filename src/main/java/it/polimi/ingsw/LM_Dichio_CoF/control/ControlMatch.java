package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.model.Market;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import java.util.ArrayList;
import java.util.Collections;

public class ControlMatch {

	private final Market market;
	private Match match;
	private Player player;
	private final ArrayList<Player> players;
	private boolean gameOver;
	private Turn turn;

	public ControlMatch(ArrayList<Player> arrayListPlayer){

		Collections.shuffle(arrayListPlayer);
		
		this.players =arrayListPlayer;
		this.match=Match.MatchFactory(arrayListPlayer);
		this.market=match.getMarket();

	}

	public void startMatch() throws InterruptedException{

		for(Player p: players)
			p.setPlaying(true);
		
		int playerNumber=1;

		do {
			
			player=players.get(playerNumber-1);
			
			turn = new Turn(match, player, players);
			turn.startTurn();
			
			//CHECK IF LAST EMPORIUM BUILT

			if (playerNumber % players.size() == 0){
				broadcastAll("The market has started!", players);
				market.startMarket();
				playerNumber=1;
			}
			else
				playerNumber++;
			
		}while(!gameOver);

	}

	public void setGameOver(){ gameOver=true;	}

	//ONLY FOR TESTS
	private void println(String string, Player player) throws InterruptedException{
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			player.getBroker().println(string);
	}

	private void broadcastAll(String string, ArrayList<Player> players) throws InterruptedException{
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broadcast.printlnBroadcastAll(string, players);
	}

	private void broadcastOthers(String string, ArrayList<Player> players, Player playerNot) throws InterruptedException{
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broadcast.printlnBroadcastOthers(string, players, playerNot);
	}

}
