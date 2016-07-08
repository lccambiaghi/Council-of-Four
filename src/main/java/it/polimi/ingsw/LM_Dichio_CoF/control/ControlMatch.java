package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import java.util.ArrayList;
import java.util.Collections;

public class ControlMatch {

	private final Market market;
	private Match match;
	private Player player;
	
	private final ArrayList<Player> allPlayers;
	private ArrayList<Player> playersConnected;

	private boolean goMarket;
	private Turn turn;
	
	private long startTime;
	private long endTime;

	private int lastTurnCounter;

	public ControlMatch(ArrayList<Player> arrayListPlayer){

		Collections.shuffle(arrayListPlayer);
		
		this.allPlayers =arrayListPlayer;
		this.match=Match.MatchFactory(arrayListPlayer);
		this.market=match.getMarket();
	
	}
	
	private ArrayList<Player> getPlayersConnected(){

		ArrayList<Player> ps = new ArrayList<Player>();

		for(Player p: allPlayers)
			if(p.isConnected())
				ps.add(p);

		return ps;

	}

	public void startMatch(){

		int playerNumber=0;

		do {
			
			playersConnected = getPlayersConnected();
			
			if(!atLeastTwoPlayersConnected()){
				
				match.setGameOver(true);

				lastTurnCounter=playersConnected.size()-1;

			}else{
				
				//CHECK IF LAST EMPORIUM BUILT
					
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
					
					if(!player.isConnected()){
						Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOut(player), playersConnected, player);
					}
				
				}else{
					
					goMarket=false;

					marketHandler();
					
				}

			}
			
		}while(!match.isGameOver() && lastTurnCounter<playersConnected.size()-1);

		//check on victory points



	}
	
	private void turnHandler(){
		
		try{
			Broadcast.printlnBroadcastOthers(Message.turnOf(player), playersConnected, player);
			player.getBroker().println(Message.yourTurn(Constant.TIMER_SECONDS_TO_PERFORM_ACTION));
		}catch (InterruptedException e) {}
		
		turn = new Turn(match, player, allPlayers);
		Thread turnThread = new Thread(turn);
		turnThread.start();
		
		/**
		 * This "while" permits to check every second if the timer
		 * to perform the action has expired
		 */
		startTime = System.currentTimeMillis();
		endTime = startTime + (Constant.TIMER_SECONDS_TO_PERFORM_ACTION)*1000;
		while (System.currentTimeMillis() < endTime) {
		    try {
		         Thread.sleep(1000);  // Sleep 1 second
		         
		         // If the player has finished his turn before the timer expires
		         if(!turnThread.isAlive())
		        	 break;
		         
		    } catch (InterruptedException e) {}	
		}
		
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
