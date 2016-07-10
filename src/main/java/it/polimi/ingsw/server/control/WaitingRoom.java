package it.polimi.ingsw.server.control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.ControlTimer;
import it.polimi.ingsw.utils.Message;

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
			
		askForNumberPlayersAndConfig();
			
		if(!firstPlayer.isConnected()){
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
		
		removeInactivePlayersAndHandleActiveOnes();
		
	}
	
	private void removeInactivePlayersAndHandleActiveOnes(){
		Player p;
		for(int i=0; i<arrayListPlayerMatch.size(); i++){
			p=arrayListPlayerMatch.get(i);
			if(!p.isConnected())
				gameSide.removePlayerFromArrayList(p);
			else
				gameSide.startHandlePlayer(gameSide, p);
		}
	}
	
	
	/**
	 * 
	 */
	public void askForNumberPlayersAndConfig(){
		
		Thread t = new Thread(new Runnable(){
			public void run(){
				
				try {
					firstPlayer.getBroker().println("You are the first player!\n"
							+ "You have "+Constant.TIMER_SECONDS_WAITING_CONFIGURATIONS+" seconds to answer:");
					askForNumberPlayers();
					if(firstPlayer.getBroker().isCustomConfig())
						askForConfigurations();
					
				} catch (InterruptedException e) {
					firstPlayer.getBroker().println("You'll play using the standard max players number and configurations");
				}finally{
					firstPlayer.getBroker().println("You are waiting for other players to join you!");
				}
				
			}			
			
		});
		t.start();
		
		
		new ControlTimer().waitForThreadUntilTimerExpires(t, Constant.TIMER_SECONDS_WAITING_CONFIGURATIONS);
		
		/**
		 * If the timer has expired and the player hasn't set the number of players
		 */
		if(t.isAlive()){
			t.interrupt();
			playersMaxNumber=Constant.PLAYERS_MAX_NUMBER;
		}
		
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	private void askForNumberPlayers() throws InterruptedException{
		firstPlayer.getBroker().println("Insert the max number of players you want to play with.\n"
				+ "Min: " +Constant.PLAYERS_MIN_NUMBER+ ", Max: "+ Constant.PLAYERS_MAX_NUMBER);
		playersMaxNumber = firstPlayer.getBroker().askInputNumber(2, 8);
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	private void askForConfigurations() throws InterruptedException{
		
		firstPlayer.getBroker().println("Do you want to play with the configurations you have created?\n");
		firstPlayer.getBroker().println(Message.chooseYesOrNo_1_2());
		int choice = firstPlayer.getBroker().askInputNumber(1, 2);
		if(choice==1){
			config = firstPlayer.getBroker().getConfigurations();
			saveFileConfigurations(config);
		}
	
	}
	
	/**
	 * 
	 * @param player
	 */
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
		
		player.getBroker().println(Message.waitForMatch());
		
	}
	
	/**
	 * 
	 */
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
			fileOutputStream = new FileOutputStream(Constant.PATH_FILE_CONFIGURATIONS);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(config);
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();	
		}


	}
	
	public Object getLockWaitingRoom(){ return lockWaitingRoom; }
	
}
