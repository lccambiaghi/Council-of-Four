package it.polimi.ingsw.LM_Dichio_CoF.control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.connection.CountDown;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class WaitingRoom extends Thread{

	private GameSide gameSide;
	private Player firstPlayer;
	
	private int playersMaxNumber = 0;
	private Configurations config = null;
	
	private int numPlayers;
	private boolean timeToPlay = false;
	private CountDown countDown;
	private boolean countDownStarted = false;
	
	private ArrayList<Player> arrayListPlayerMatch = new ArrayList<Player>();
	
	private static final Object lockNumPlayers = new Object();
	
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
		
		synchronized(gameSide.lockWaitingRoomAvailable){
			if(getNumPlayers()>playersMaxNumber){
				for(int index=playersMaxNumber; index<numPlayers; index++)
					gameSide.getArrayListPlayer().add(arrayListPlayerMatch.remove(index));
			}
		}
		
		while(!timeToPlay){
			if(getNumPlayers()>1&&!countDownStarted){
				countDownStarted=true;
				countDown = new CountDown(Constant.TIMER_SECONDS_NEW_MATCH);
				System.out.println("countdown started");
			}
			if(countDownStarted){
				if(countDown.isTimeFinished()){
					countDownStarted=false;
					timeToPlay=true;
				}
			}
			if(getNumPlayers()==playersMaxNumber){
				timeToPlay=true;
			}
		
		}
		
		gameSide.setWaitingRoomAvailable(false);
		
		Broker.printlnBroadcastAll(Message.matchStarted, arrayListPlayerMatch);
		
		controlMatch = new ControlMatch(arrayListPlayerMatch);
		controlMatch.startMatch();
		
	}
		
	private void askForConfigurations(){
		
		Broker.askToConfigure(firstPlayer);
		playersMaxNumber = Broker.getPlayersMaxNumber(firstPlayer);
		config = Broker.getConfigurations(firstPlayer);
		
		saveFileConfigurations(config);
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
	
	private void increaseNumPlayers(){
		synchronized (lockNumPlayers){
			numPlayers++;
		}
	}
	
	private int getNumPlayers(){
		synchronized (lockNumPlayers){
			return numPlayers;
		}
	}
	
	public void addPlayerToWaitingRoom(Player player){
		arrayListPlayerMatch.add(player);
		increaseNumPlayers();
		Message.waitForMatch(player);
	}
	
}
