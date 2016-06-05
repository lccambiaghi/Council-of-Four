package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.ArrayList;

public class MatchStarter extends Thread{

	GameSide gameSide;
	int playersMaxNumber;
	
	int indexNextPlayer=0;
	
	boolean timeToPlay = false;
	
	ArrayList<Player> arrayListPlayerMatch = new ArrayList<>();
	ArrayList<Player> arrayListPlayerGameSide = new ArrayList<>();
	
	public MatchStarter(GameSide gameSide, int playersMaxNumber){
		this.gameSide=gameSide;
		this.playersMaxNumber=playersMaxNumber;
	}

	public void run(){

		arrayListPlayerGameSide = gameSide.getArrayListPlayer();
		
		System.out.println("giocatori disponibili: " +arrayListPlayerGameSide.size());
		
		for(; indexNextPlayer<arrayListPlayerGameSide.size() && indexNextPlayer<playersMaxNumber; indexNextPlayer++){
			addPlayerToMatch(arrayListPlayerGameSide.get(indexNextPlayer));
		}
		
		AddOnePlayerIfPresent threadWaitingForAPlayer;
		
		if(indexNextPlayer<Constant.PLAYERS_MIN_NUMBER){
			threadWaitingForAPlayer = new AddOnePlayerIfPresent();
			while(threadWaitingForAPlayer.isAlive()){
				;
			}
			indexNextPlayer++;
			System.out.println("Ci sono " + indexNextPlayer + " giocatori");
		}
		
		if(indexNextPlayer==playersMaxNumber){
			timeToPlay=true;
		}
		
		if(!timeToPlay){
			CountDown countDown = new CountDown(Constant.TIMER_SECONDS_NEW_MATCH);
			System.out.println("Countdown iniziato");
			threadWaitingForAPlayer = new AddOnePlayerIfPresent();
			while(!timeToPlay){
				if(!threadWaitingForAPlayer.isAlive()){
					System.out.println("Ci sono " + indexNextPlayer + " giocatori");
					indexNextPlayer++;
					threadWaitingForAPlayer = new AddOnePlayerIfPresent();
				}
				if(countDown.isTimeFinished()||indexNextPlayer==playersMaxNumber){
					timeToPlay=true;
				}
			}		
		}
		
		gameSide.removeArrayListPlayer(arrayListPlayerMatch.size());
		
		System.out.println("FINE");
		
		System.out.println("Si gioca in " + arrayListPlayerMatch.size() +" giocatori");
		for(Player player: arrayListPlayerMatch){
			System.out.println(player.getNickname());
		}
		//new Match(arrayListPlayerMatch);
		
		
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
					Player player = arrayListPlayerGameSide.get(indexNextPlayer);
					addPlayerToMatch(player);
					System.out.println("Si è aggiunto uno!");
					added=true;
				}
			}
		}
		
	}
	
		
}
