package it.polimi.ingsw.server.control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import it.polimi.ingsw.server.connection.DisconnectedException;
import it.polimi.ingsw.server.model.Configurations;

public class WaitingRoom extends Thread{

	private GameSide gameSide;
	private Player firstPlayer;
	
	private int playersMaxNumber;
	private Configurations config = null;
	
	private int numPlayers;
	private ControlTimer controlTimer;
	
	private boolean timeToPlay = false;
	
	private ArrayList<Player> arrayListPlayerMatch = new ArrayList<Player>();
	
	private final Object lockWaitingRoom = new Object();
	private Object lockWaitingRoomFromGameSide;
	
	private ControlMatch controlMatch;
	
	public WaitingRoom(GameSide gameSide, Player player) 
	{
		this.gameSide=gameSide;
		this.firstPlayer=player;
		this.arrayListPlayerMatch.add(firstPlayer);
		this.numPlayers=1;
	}
	
	public void run(){
		
		lockWaitingRoomFromGameSide = gameSide.getLockWaitingRoomFromGameSide();
		
		boolean destroy = false;
		
		try {
			
			askForNumberPlayersAndConfig();
			
		} catch (DisconnectedException e1) {
			gameSide.removePlayerFromArrayList(firstPlayer);
			gameSide.setWaitingRoomAvailable(false);
			destroy=true;
		}
		
		synchronized (lockWaitingRoom) {
			lockWaitingRoom.notify();
		}
		
		if(destroy)
			return;
		
		while(!timeToPlay){
			
			if(isCountDownFinished())
				break;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
		}

		Broadcast.printlnBroadcastAll(Message.matchStarted(), arrayListPlayerMatch);
		
		controlMatch = new ControlMatch(arrayListPlayerMatch);
	
		controlMatch.startMatch();
		
	}
	
	public void askForNumberPlayersAndConfig() throws DisconnectedException{
		
		Thread t = new Thread(new Runnable(){
			public void run(){
				try {
					firstPlayer.getBroker().println("You are the first player!\n"
							+ "You have "+Constant.TIMER_SECONDS_WAITING_CONFIGURATIONS+" seconds to answer:");
					askForNumberPlayers();
					if(firstPlayer.getBroker().isCustomConfig())
						askForConfigurations();
				} catch (InterruptedException e) {
					try {
						firstPlayer.getBroker().println("You'll play using the standard max players number and configurations");
					} catch (InterruptedException e1) {}
				} catch (DisconnectedException e) {}
				
			}			
			
		});
		t.start();
		
		
		new ControlTimer().waitForThreadUntilTimerExpires(t, Constant.TIMER_SECONDS_WAITING_CONFIGURATIONS);
		
		if(!firstPlayer.isConnected())
			throw new DisconnectedException();
		
		/**
		 * If the timer has expired and the player hasn't set the number of players
		 */
		if(t.isAlive()){
			t.interrupt();
			playersMaxNumber=Constant.PLAYERS_MAX_NUMBER;
		}
		
	}
	
	private void askForNumberPlayers() throws InterruptedException{
		firstPlayer.getBroker().println("Insert the max number of players you want to play with.\n"
				+ "Min: " +Constant.PLAYERS_MIN_NUMBER+ ", Max: "+ Constant.PLAYERS_MAX_NUMBER);
		playersMaxNumber = firstPlayer.getBroker().askInputNumber(2, 8);
	}
	
	
	private void askForConfigurations() throws DisconnectedException, InterruptedException{
		
		firstPlayer.getBroker().println("Do you want to play with last configurations used?\n");
		firstPlayer.getBroker().println(Message.chooseYesOrNo_1_2());
		int choice = firstPlayer.getBroker().askInputNumber(1, 2);
		if(choice==2){
			config = firstPlayer.getBroker().getConfigurations();
			saveFileConfigurations(config);
		}
	
	}
	
	public void addPlayerToWaitingRoom(Player player){
			
		arrayListPlayerMatch.add(player);
		numPlayers++;
		
		if(numPlayers==playersMaxNumber){
			
			gameSide.setWaitingRoomAvailable(false);
			timeToPlay=true;
			
		}else{
			
			if(numPlayers==2){
				startCountDown();
			}
			
		}	
		
		try {
			
			player.getBroker().println(Message.waitForMatch());
			
		} catch (InterruptedException e) {}
		
	}
	
	private void startCountDown(){
		controlTimer = new ControlTimer();
		controlTimer.startCountDown(Constant.TIMER_SECONDS_NEW_MATCH);
	}
	
	
	/**
	 * 
	 * @return
	 */
	private boolean isCountDownFinished(){
		synchronized (lockWaitingRoomFromGameSide) {
			if(controlTimer!=null){
				if(controlTimer.isCountDownFinished()){
					gameSide.setWaitingRoomAvailable(false);
					return true;
				}
			}
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param config
	 */
	private void saveFileConfigurations(Configurations config){
		
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("./src/configurations/config");
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(config);
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();	
		}


	}
	
	public Object getLockWaitingRoom(){ return lockWaitingRoom; }
	
}
