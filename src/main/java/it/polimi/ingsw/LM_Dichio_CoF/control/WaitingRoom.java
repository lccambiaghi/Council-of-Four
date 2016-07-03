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
	
	private int playersMaxNumber;
	private Configurations config = null;
	
	private int numPlayers;
	private CountDown countDown;
	
	private boolean timeToPlay = false;
	private boolean canGoWithCountDown = false;
	
	private ArrayList<Player> arrayListPlayerMatch = new ArrayList<Player>();
	
	private final Object lockWaitingRoom = new Object();
	
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
		
		boolean onTime = askForNumberPlayers();
		if(onTime){
			askForConfigurations();
		}else{
			playersMaxNumber=8;
			canGoWithCountDown=true;
		}
		
		while(!timeToPlay){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			if(canGoWithCountDown){
				if(countDown.isTimeFinished()){
					timeToPlay=true;
				}
			}
		}
		
		try {
			Broadcast.printlnBroadcastAll(Message.matchStarted(), arrayListPlayerMatch);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		controlMatch = new ControlMatch(arrayListPlayerMatch);
		try {
			controlMatch.startMatch();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean askForNumberPlayers(){
		
		Thread t = new Thread(new Runnable(){
			public void run(){
				try {
					firstPlayer.getBroker().println("You are the first player!\n"
							+ "Insert the number of players you want to play with.\n"
							+ "Min: 2, Max: 8\n"
							+ "(You have 10 seconds)");
					playersMaxNumber = firstPlayer.getBroker().askInputNumber(2, 8);	
				} catch (InterruptedException e) {
					try {
						firstPlayer.getBroker().println("Too late!\n"
								+ "You'll play using the standard players number and configurations");
					} catch (InterruptedException e1) {}
				}
				
			}			
			
		});
		t.start();
		

		interruptThreadIfTimerExpires(t, 10);
		
		synchronized (lockWaitingRoom) {
			lockWaitingRoom.notify();
		}
		
		/**
		 * If the timer has expired and the player hasn't set the number of players
		 */
		if(t.isAlive()){
			t.interrupt();
			return false;
		}else{
			return true;
		}
		
	}
	
	private void askForConfigurations(){

		Thread t = new Thread(new Runnable(){
			public void run(){
				try {
					firstPlayer.getBroker().println("Do you want to play with last configurations used?\n"
							+ "(You have 120 seconds)");
					firstPlayer.getBroker().println(Message.chooseYesOrNo_1_2());
					int choice = firstPlayer.getBroker().askInputNumber(1, 2);
					if(choice==2){
						config = firstPlayer.getBroker().getConfigurations();
						saveFileConfigurations(config);
					}
				} catch (InterruptedException e) {
					try {
						firstPlayer.getBroker().println("Too late! You'll play with last configurations used");
					} catch (InterruptedException e1) {}
				}
				
			}			
			
		});
		t.start();
		
		interruptThreadIfTimerExpires(t, 120);
		
		/**
		 * If the timer has expired and the player hasn't set the configurations
		 */
		if(t.isAlive()){	
			t.interrupt();
		}
		
		if(numPlayers==playersMaxNumber)
			timeToPlay=true;
		else if(numPlayers>1)
			countDown = new CountDown(Constant.TIMER_SECONDS_NEW_MATCH);
	
	}
	
	public void addPlayerToWaitingRoom(Player player){
		
		synchronized (lockNumPlayers){
			arrayListPlayerMatch.add(player);
			numPlayers++;
			if(numPlayers==playersMaxNumber){
				gameSide.setWaitingRoomAvailable(false);
				timeToPlay=true;
			}else{
				if(numPlayers==2){
					if(canGoWithCountDown){
						synchronized (lockCountDown) {
							countDown = new CountDown(Constant.TIMER_SECONDS_NEW_MATCH);
						}
					}
				}
			}
			synchronized (lockWaitingRoom) {
				lockWaitingRoom.notify();
			}
		}
		
		try {
			player.getBroker().println(Message.waitForMatch());
		} catch (InterruptedException e) {}
		
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
	
	private void interruptThreadIfTimerExpires(Thread t, int seconds){
		/**
		 * This "while" permits to check every second if the timer
		 * to set configurations has expired
		 */
		long startTime = System.currentTimeMillis();
		long endTime = startTime + (seconds)*1000;
		while (System.currentTimeMillis() < endTime) {
		    try {
		         Thread.sleep(1000);  // Sleep 1 second
		         
		         // If the player has set players before the timer expires
		         if(!t.isAlive())
		        	 break;
		         
		    } catch (InterruptedException e) {}	
		}
	}
	
	public Object getLockWaitingRoom(){ return lockWaitingRoom; }
	
}
