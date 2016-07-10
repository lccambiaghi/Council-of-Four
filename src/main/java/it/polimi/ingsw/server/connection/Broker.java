package it.polimi.ingsw.server.connection;

import it.polimi.ingsw.server.control.GameSide;
import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.DisconnectedException;

/**
 * This class is the pivot of the communication with a client.
 * 
 * It behaves as an intermediate between the class that calls a method to communicate
 * and the actual way the communication behaves. 
 * Every "Player" has a Broker object that links the server to the corresponding client.
 * 
 * The variable "connectionWithPlayerInterface" contains the real object that permits
 * the communication, object that can be "SocketConnectionWithPlayer" or 
 * "RMIconnectionWithPlayer"
 * 
 * In fact the broker doesn't have to worry about the type of communication chosen
 * by the client, but it simply calls the relative method of the "connectionWithPlayerInterface"
 * 
 * The Broker handles with Interrupts and Disconnections, behaving in different ways 
 * depending on the method called
 */
public class Broker {

	private ConnectionWithPlayerInterface connectionWithPlayer;
	private Player player;
	
	/**
	 * Constructor of the class
	 * 
	 * @param connectionWithPlayer : the real connection chosen by the client
	 * @param player : the player corresponding to the client
	 */
	public Broker(ConnectionWithPlayerInterface connectionWithPlayer, Player player){
		this.connectionWithPlayer=connectionWithPlayer;
		this.player=player;
	}
	
	/**
	 * The method simply calls "connectionWithPlayer.login"
	 * 
	 * @param gameSide : the class of the caller
	 * @throws DisconnectedException if the client disconnects
	 */
	public void login(GameSide gameSide) throws DisconnectedException{
		connectionWithPlayer.login(gameSide);
	}
	
	/**
	 * This method checks if the client has created his own configurations
	 * Checks if is interrupted through the method "interruptedExceptionLauncher"
	 * If the player disconnects it sets the boolean "connected" of the player to false
	 * 
	 * @return true if the player has set his own configurations, false otherwise
	 * @throws InterruptedException if the thread is interrupted
	 */
	public boolean isCustomConfig() throws InterruptedException{
		
		interruptedExceptionLauncher();
		
		boolean bool = false;
		try {
			bool = connectionWithPlayer.isCustomConfig();
		} catch (DisconnectedException e) {
			player.setConnected(false);
		}
		return bool;
	}
	
	/**
	 * This method try to get the configurations created by the player
	 * 
	 * @return the configurations
	 * @throws InterruptedException if the thread is interrupted
	 */
	public Configurations getConfigurations() throws InterruptedException{
		
		interruptedExceptionLauncher();
		
		Configurations config = null;
		try {
			config = connectionWithPlayer.getConfigurations();
		} catch (DisconnectedException e) {
			player.setConnected(false);
		}
		return config;
	}
	
	/**
	 * This method launches a thread that asks the player an integer between the bounds taken as parameter,
	 * calling the corresponding method of the "connectionWithPlayer".
	 * Then it calls the method "wait" on the lock offered by the "connectionWithPlayer".
	 * When the client returns the integer choice, in the "connectionWithPlayer" is called "notify" in
	 * the same lock.
	 * Then the method returns the integer value calling "connectionWithPlayer.getIntResult".
	 * 
	 * If the thread is interrupted the method throw the corresponding exception.
	 * 
	 * If the player disconnects it sets the boolean "connected" of the player to false
	 * 
	 * @param lowerBound
	 * @param upperBound
	 * @return the choice of the client
	 * @throws InterruptedException if the thread is interrupted
	 */
	public int askInputNumber(int lowerBound, int upperBound) throws InterruptedException{
		
		interruptedExceptionLauncher();
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					connectionWithPlayer.askInputNumber(lowerBound, upperBound);
				} catch (DisconnectedException e) {
					player.setConnected(false);
				}
			}
		});  
		Object lock = connectionWithPlayer.getLock();
		t.start();
		synchronized (lock) {
			try{
				lock.wait();
			} catch (InterruptedException e) {
				try {
					connectionWithPlayer.stopInputNumber();
				} catch (DisconnectedException e1) {
					player.setConnected(false);
				}
				throw new InterruptedException();
			}
		}
		return connectionWithPlayer.getIntResult();
	}
	
	/**
	 * Calls the method "connectionWithPlayer.print" to print the string on the client console
	 * If the player disconnects it sets the boolean "connected" of the player to false
	 * 
	 * @param string : the string to be printed
	 */
	public void print(String string){
		
		 try {
			connectionWithPlayer.print(string);
		} catch (DisconnectedException e) {
			player.setConnected(false);
		}
		
	}
	
	/**
	 * This method split the string on every escape character, then it calls "printlnReal"
	 * to have the correct printing of the total string
	 * 
	 * @param string : the string to be split and printed on the client console
	 */
	public void println(String string){
		CharSequence newLine = "\n";
		if(string.contains(newLine)){
			String[] arrayString = string.split("\n");
			for(String s: arrayString)
				printlnReal(s);
		}else{
			printlnReal(string);
		}
	}
	
	/**
	 * Calls the method "connectionWithPlayer.println" to print the string on the client console.
	 * If the player disconnects it sets the boolean "connected" of the player to false
	 * 
	 * @param string : the string to be printed
	 */
	private void printlnReal(String string){
		
		try {
			connectionWithPlayer.println(string);
		} catch (DisconnectedException e) {
			player.setConnected(false);
		}
		
	}
	 
	/**
	 * The method checks if the Thread has been interrupted and launches the correspondin Exception
	 * 
	 * @throws InterruptedException
	 */
	private void interruptedExceptionLauncher() throws InterruptedException{
		if (Thread.interrupted())
			throw new InterruptedException();
	}
	
}
