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
		ArrayList<Player> ps = new ArrayList<Player>();
		for(Player p: allPlayers){
			p.isConnected();
				ps.add(p);
		}
		return ps;
	}

	public void startMatch() throws InterruptedException{

		int playerNumber=0;

		do {
			
			playersConnected = getPlayersConnected();
			
			if(!atLeastTwoPlayersConnected()){
				
				gameOver=true;

			}else{
				
				//CHECK IF LAST EMPORIUM BUILT
					
				player=allPlayers.get(playerNumber);
			
				while(!player.isConnected()){
				
					//If it's the last player of the group
					if(playerNumber==allPlayers.size()-1){
						
						playerNumber=0;
						goMarket=true;
						break;
						
					}else{
						playerNumber++;
						player=allPlayers.get(playerNumber-1);
					}
				}
				
				if(!goMarket){
					
					turnHandler();
					
					if(!player.isConnected()){
						Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOff(player), playersConnected, player);
					}
					
					playerNumber++;
				
				}else{
					
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
