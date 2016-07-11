package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.ControlTimer;
import it.polimi.ingsw.utils.Message;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This class permits to launch a match when enough players are connected to the match.
 * 
 * The first player is added through the constructor, the other players through the public method
 * "addPlayerToWaitingRoom".
 * 
 * The class extends Thread and uses locks with "wait" and "notify" to synchronize with the GameSide.
 * 
 * The first player has only a few seconds (constant in the Constant class) to set the players
 * max number and, if created, to choose whether send his configurations or not.
 * If the first player disconnects before these time, WaitingRoom returns, 
 * setting the boolean "waitingRoomAvailable" of the GameSide to false.
 * 
 * The public method "addPlayerToWaitingRoom" is used to add a new player into the WaitingRoom
 * and checks by itself the number of players already inside.
 * It also starts the CountDown of 20 seconds (constant in the Constant class).
 * 
 * The "run" method of the class checks periodically if it's time to play (enough players or countDown finished).
 * 
 * If so it launches a new ControlMatch passing it the arrayList of Players in the waiting room.
 */
public class WaitingRoom extends Thread{

	private final ArrayList<Player> arrayListPlayerMatch = new ArrayList<>();
	
	private final GameSide gameSide;
	private final Player firstPlayer;
	
	private int playersMaxNumber;

	private int numPlayers;
	private ControlTimer controlTimer;
	
	private boolean timeToPlay = false;
	
	private final Object lockWaitingRoom = new Object();
	private Object lockWaitingRoomFromGameSide;

	/**
	 * Constructor of the class
	 * 
	 * @param gameSide : the caller of the constructor
	 * @param player : the first player of it that can choose to create the configurations
	 */
	public WaitingRoom(GameSide gameSide, Player player) 
	{
		this.gameSide=gameSide;
		this.firstPlayer=player;
		this.arrayListPlayerMatch.add(firstPlayer);
		this.numPlayers=1;
	}
	
	@Override
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
		
		/**
		 * "destroy" is true if the first player has disconnected
		 */
		if(destroy)
			return;
		
		while(!timeToPlay){
			
			if(isCountDownFinished())
				break;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
		}

		
		/**
		 * At this point the match can start
		 */
		Broadcast.printlnBroadcastAll(Message.matchStarted(), arrayListPlayerMatch);

		ControlMatch controlMatch = new ControlMatch(arrayListPlayerMatch);
		controlMatch.startMatch();
		
		/**
		 * The match has ended
		 */
		removeInactivePlayersAndHandleActiveOnes();
		
	}
	
	/**
	 * The method checks the connection of the players at the end of a match.
	 * 
	 * It removes the disconnected ones from the "arrayListAllPlayer" of the GameSide through
	 * the method "removePlayerFromArrayList".
	 * It calls the method "startHandlePlayer" in the GameSide if the player is still active
	 */
	private void removeInactivePlayersAndHandleActiveOnes(){
		Player p;
		for (Player anArrayListPlayerMatch : arrayListPlayerMatch) {
			p = anArrayListPlayerMatch;
			if (!p.isConnected())
				gameSide.removePlayerFromArrayList(p);
			else
				gameSide.startHandlePlayer(gameSide, p);
		}
	}
	
	/**
	 * This method launches a new Thread that asks the player the max number of players he wants in the match
	 * and if he has created his own configurations if he wants to use them.
	 * 
	 * The Thread is interrupted if the timer expires and the last configurations will be used
	 */
	private void askForNumberPlayersAndConfig(){
		
		Thread t = new Thread(new Runnable(){
			@Override
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
	 * The method asks the player the max number of player he wants in the match.
	 * 
	 * @throws InterruptedException if the timer expires before the player answers
	 */
	private void askForNumberPlayers() throws InterruptedException{
		firstPlayer.getBroker().println("Insert the max number of players you want to play with.\n"
				+ "Min: " +Constant.PLAYERS_MIN_NUMBER+ ", Max: "+ Constant.PLAYERS_MAX_NUMBER);
		playersMaxNumber = firstPlayer.getBroker().askInputNumber(2, 8);
	}
	
	/**
	 * The method asks the player the if he wants to play with the configurations he has created.
	 * If yes it gets them through the method "getConfigurations".
	 * If not it returns.
	 * 
	 * @throws InterruptedException if the timer expires before the player answers
	 */
	private void askForConfigurations() throws InterruptedException{
		
		firstPlayer.getBroker().println("Do you want to play with the configurations you have created?\n");
		firstPlayer.getBroker().println(Message.chooseYesOrNo_1_2());
		int choice = firstPlayer.getBroker().askInputNumber(1, 2);
		if(choice==1){
			Configurations config = firstPlayer.getBroker().getConfigurations();
			saveFileConfigurations(config);
		}
	
	}
	
	/**
	 * This method permits to add a player into the waitingRoom.
	 * It checks if the max number of player is reached and if so it sets  the boolean "timeToPlaY" to true
	 * If not it checks if it's the second player added and calls the method "startCountDown"
	 * Finally it sends a string to the player to tell him to wait for a match
	 * 
	 * @param player : the player to be added
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
	
	private void startCountDown(){
		controlTimer = new ControlTimer();
		controlTimer.startCountDown(Constant.TIMER_SECONDS_NEW_MATCH);
	}
	
	
	/**
	 * The method checks if the countDown has finishes and if so it sets the boolean "waitingRoomAvailable" 
	 * of the GameSide to false.
	 * 
	 * @return true if the countDown has finished, false otherwise
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
	 * The method saves the configurations into a file that then will be used by the match
	 * 
	 * @param config : the configurations to be saved
	 */
	private void saveFileConfigurations(Configurations config){
		
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;
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
