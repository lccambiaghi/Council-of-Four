package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.ArrayList;

public class MatchStarter {

	GameSide gameSide;
	ArrayList<Player> arrayListPlayerMatch = new ArrayList<>();
	ArrayList<Player> arrayListPlayer = new ArrayList<>();
	
	boolean timeToPlay = true;
	
	public MatchStarter(GameSide gameSide, int playersMaxNumber){
		
		this.gameSide=gameSide;

		arrayListPlayer = gameSide.getArrayListPlayer();
		int availablePlayers = arrayListPlayer.size();
		for(int i=0; i<availablePlayers && i<playersMaxNumber; i++){
			arrayListPlayerMatch.add(arrayListPlayer.remove(0));
			(arrayListPlayerMatch.get(i)).sendString("startingMatch");
		}
		
		while(arrayListPlayerMatch.size()<Constant.PLAYERS_MIN_NUMBER){
			;
		}
		
		if(arrayListPlayerMatch.size()<playersMaxNumber){
			timeToPlay=false;
		}
		
		if(!timeToPlay){
			CountDown countDown = new CountDown(Constant.TIMER_SECONDS_NEW_MATCH);
			while(!timeToPlay){
				if(countDown.isTimeFinished()||arrayListPlayerMatch.size()==playersMaxNumber){
					timeToPlay=true;
				}else{
					new AddPlayerIfPresent();
				}
					
			}
		}
		
		gameSide.canCreateNewMatch();
		new Match(arrayListPlayerMatch);
		
		
	}
	
	class AddPlayerIfPresent extends Thread{
		
		public AddPlayerIfPresent(){
			start();
		}
		
		public void run(){
			boolean added= false;
			while(!added&&!timeToPlay){
				arrayListPlayer=gameSide.getArrayListPlayer();
				if(arrayListPlayer!=null){
					arrayListPlayerMatch.add((Player) arrayListPlayer.remove(0));
					added=true;
				}
			}
		}
	}
	
		
}
