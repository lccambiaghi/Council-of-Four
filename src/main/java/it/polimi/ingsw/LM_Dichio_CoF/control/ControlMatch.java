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
			if(p.getBroker().isConnected())
				ps.add(p);
		}
		return ps;
	}

	public void startMatch() throws InterruptedException{

		int playerNumber=0;

		do {
			
			playersConnected = getPlayersConnected();
			
			if(playersConnected==null){
				
				gameOver=true;
				
			}else if(playersConnected.size()==1){
				
				playersConnected.get(0).getBroker().println(Message.youWon());
				gameOver=true;
				
			}else{
				
				//CHECK IF LAST EMPORIUM BUILT
				
				if(playerNumber==allPlayers.size()){
					goMarket=true;
				}
				
				if(!goMarket){
					player=allPlayers.get(playerNumber);
				
					while(!player.getBroker().isConnected()){
					
						playerNumber++;
						if(playerNumber==allPlayers.size()){
							goMarket=true;
							break;
						}else{
							player=allPlayers.get(playerNumber-1);
						}
					}
				}
				
				if(!goMarket){
					
					turnHandler();
					
					if(!player.getBroker().isConnected()){
						Broadcast.printlnBroadcastOthers(Message.playerHasBeenKickedOff(player), playersConnected, player);
					}
					
					playerNumber++;
				
				}else{
					
					playerNumber=0;
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
			
		startTime = System.currentTimeMillis();
		endTime = startTime + (Constant.TIMER_SECONDS_TO_PERFORM_ACTION+20)*1000;
		
		
		turn = new Turn(match, player, allPlayers);
		Thread turnThread = new Thread(turn);
		turnThread.start();
		
		/**
		 * This "while" permits to check every second if the timer
		 * to perform the action has expired
		 */
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

	public void setGameOver(){ gameOver=true;	}

}
