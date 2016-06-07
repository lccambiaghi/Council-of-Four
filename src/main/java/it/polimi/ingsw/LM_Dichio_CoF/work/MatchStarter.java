package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.ArrayList;

public class MatchStarter extends Thread{

	GameSide gameSide;
	int playersMaxNumber;
	
	int indexNextPlayer=0;
	
	boolean timeToPlay = false;
	
	private final Object lockIndexNextPlayer = new Object();
	
	ArrayList<Player> arrayListPlayerMatch = new ArrayList<>();
	ArrayList<Player> arrayListPlayerGameSide = new ArrayList<>();
	
	public MatchStarter(GameSide gameSide, int playersMaxNumber){
		this.gameSide=gameSide;
		this.playersMaxNumber=playersMaxNumber;
	}

	public void run(){
		
		System.out.println("I am the match starter, i wait for players (if needed) and start the match");

		arrayListPlayerGameSide = gameSide.getArrayListPlayer();
		
		for(; indexNextPlayer<arrayListPlayerGameSide.size() && indexNextPlayer<playersMaxNumber; indexNextPlayer++){
			addPlayerToMatch(arrayListPlayerGameSide.get(indexNextPlayer));
		}
		
		AddOnePlayerIfPresent threadWaitingForAPlayer;
		
		if(indexNextPlayer<Constant.PLAYERS_MIN_NUMBER){
			threadWaitingForAPlayer = new AddOnePlayerIfPresent();
			while(threadWaitingForAPlayer.isAlive()){
				;
			}
			System.out.println("Ci sono " + indexNextPlayer + " giocatori");
		}
		
		if(indexNextPlayer==playersMaxNumber){
			timeToPlay=true;
		}
		
		if(!timeToPlay){
			CountDown countDown = new CountDown(Constant.TIMER_SECONDS_NEW_MATCH-18);
			System.out.println("Countdown iniziato");
			threadWaitingForAPlayer = new AddOnePlayerIfPresent();
			while(!timeToPlay){
				if(!threadWaitingForAPlayer.isAlive()){
					System.out.println("Ci sono " + indexNextPlayer + " giocatori");
					threadWaitingForAPlayer = new AddOnePlayerIfPresent();
				}
				synchronized (lockIndexNextPlayer) {
					if(countDown.isTimeFinished()||indexNextPlayer==playersMaxNumber){
						timeToPlay=true;
					}
				}
			}		
		}
		
		gameSide.removeArrayListPlayer(arrayListPlayerMatch.size());
		
		synchronized (GameSide.lockArrayListPlayer) {
			if (gameSide.getArrayListPlayer().size()==0){
				gameSide.setFirstAvailablePlayer(true);
			}else{
				new HandlerArrayListPlayer(gameSide).start();
			}
		}
		
		System.out.println("FINE");
		
		System.out.println("Si gioca in " + arrayListPlayerMatch.size() +" giocatori");
		for(Player player: arrayListPlayerMatch){
			System.out.println(player.getNickname());
		}
		
		new Match(arrayListPlayerMatch);
		
		
	}
	
	private void addPlayerToMatch(Player player){
		arrayListPlayerMatch.add(player);
		player.sendString("startingMatch");
	}
	
	class AddOnePlayerIfPresent extends Thread{
		
		boolean added = false;
		
		private AddOnePlayerIfPresent(){
			start();
		}
		
		public void run(){
			
			System.out.println("Ciao, sguardo se si aggiunge un giocatore");
			while(!added&&!timeToPlay){
				arrayListPlayerGameSide=gameSide.getArrayListPlayer();
				if(arrayListPlayerGameSide.size()>indexNextPlayer){
					synchronized (lockIndexNextPlayer) {
						Player player = arrayListPlayerGameSide.get(indexNextPlayer);
						addPlayerToMatch(player);
						indexNextPlayer++;
						System.out.println("Si è aggiunto uno!");
						added=true;
					}
				}
			}
		}
		
	}
	
		
}
