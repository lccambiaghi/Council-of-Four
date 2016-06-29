package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
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
		
		for(Player p: players){
			if(p.getBroker().isConnected())
				p.setPlaying(true);
		}

	}

	public void startMatch() throws InterruptedException{

		int playerNumber=1;

		do {
			
			int playingPlayers=0;
			for(Player p: players){
				if(p.isPlaying())
					playingPlayers++;
			}
			
			if(playingPlayers==1){
				for(Player p: players){
					if(p.isPlaying())
						p.getBroker().println(Message.youWon());
				}
				gameOver=true;
				
			}else{
				
				player=players.get(playerNumber-1);
				
				if(player.isPlaying()){
					if(player.getBroker().isConnected()){
						turn = new Turn(match, player, players);
						turn.startTurn();
					}
					if(!player.getBroker().isConnected()){
						Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOff(player), players, player);
						player.setPlaying(false);
					}
				}
				
				//CHECK IF LAST EMPORIUM BUILT
	
				if (playerNumber % players.size() == 0){
					broadcastAll("The market has started!", players);
					market.startMarket();
					playerNumber=1;
				}
				else
					playerNumber++;
			}
			
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
