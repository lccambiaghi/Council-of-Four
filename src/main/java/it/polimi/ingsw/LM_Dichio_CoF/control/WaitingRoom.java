package it.polimi.ingsw.LM_Dichio_CoF.control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class WaitingRoom extends Thread{

	private GameSide gameSide;
	private Player firstPlayer;
	
	private int playersMaxNumber = 8; //DEFAULT MAX NUMBER
	private Configurations config = null;
	
	private int numPlayers;
	private CountDown countDown;
	
	private boolean timeToPlay = false;
	private boolean standardConfig = false;
	
	private ArrayList<Player> arrayListPlayerMatch = new ArrayList<Player>();
	
	private final Object lockNumPlayers = new Object();
	private final Object lockCountDown = new Object();
	private final Object lockTimeToPlay = new Object();
	
	
	private ControlMatch controlMatch;
	
	public WaitingRoom(GameSide gameSide, Player player) 
	{
		this.gameSide=gameSide;
		this.firstPlayer=player;
		this.arrayListPlayerMatch.add(firstPlayer);
		this.numPlayers=1;
	}
	
	public void run(){
		
		askForConfigurations();
		
		while(!isTimeToPlay()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			synchronized (lockCountDown) {
				if(countDown!=null){
					if(countDown.isTimeFinished())
						setTimeToPlay(true);
				}
			}	
		}
		
		
		
		try {
			Broadcast.printlnBroadcastAll(Message.matchStarted(), arrayListPlayerMatch);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		controlMatch = new ControlMatch(arrayListPlayerMatch);
		try {
			controlMatch.startMatch();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	private void askForConfigurations(){

		int playersMaxNumberChoice=0;
		
		try {
			firstPlayer.getBroker().println("You are the first player, insert the number of players you want to play with.\n"
					+ "Min: 2, Max: 8");
			playersMaxNumberChoice = firstPlayer.getBroker().askInputNumber(2, 8);
		} catch (InterruptedException e) {}
		
		synchronized (lockNumPlayers) {
			if(playersMaxNumberChoice>numPlayers){
				playersMaxNumber=playersMaxNumberChoice;
			}else{
				try {
					firstPlayer.getBroker().println("Too late! There are already " + numPlayers +" players waiting for the match,\n"
							+ "you'll play with them using last configurations created");
				} catch (InterruptedException e) {}
				standardConfig=true;
			}
		}
		
		if(!standardConfig){
			config = firstPlayer.getBroker().getConfigurations();
			saveFileConfigurations(config);
		}

	}
	
	public void addPlayerToWaitingRoom(Player player){
		synchronized (lockNumPlayers){
			arrayListPlayerMatch.add(player);
			numPlayers++;
			if(numPlayers==playersMaxNumber){
				setTimeToPlay(true);;
			}else{
				if(numPlayers==2){
					synchronized (lockCountDown) {
						countDown = new CountDown(Constant.TIMER_SECONDS_NEW_MATCH);
					}
				}
			}
		}
		try {
			player.getBroker().println(Message.waitForMatch());
		} catch (InterruptedException e) {}
	}
	
	private void setTimeToPlay(boolean b){
		synchronized (lockTimeToPlay) {timeToPlay=b;}
		gameSide.setWaitingRoomAvailable(false);
	}
	
	private boolean isTimeToPlay(){
		synchronized (lockTimeToPlay){ return timeToPlay; }
	}
	
	
	private void saveFileConfigurations(Configurations config){
		
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("./src/configurations/config");
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(config);
		} catch (IOException e) {
			e.printStackTrace();	
		}finally{
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
