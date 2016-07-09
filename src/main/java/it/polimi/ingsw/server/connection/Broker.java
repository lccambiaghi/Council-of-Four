package it.polimi.ingsw.server.connection;

import it.polimi.ingsw.server.control.GameSide;
import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.DisconnectedException;

public class Broker {

	private ConnectionWithPlayerInterface connectionWithPlayer;
	private Player player;
	
	public Broker(ConnectionWithPlayerInterface connectionWithPlayer, Player player){
		this.connectionWithPlayer=connectionWithPlayer;
		this.player=player;
	}
	
	public void login(GameSide gameSide) throws DisconnectedException{
		connectionWithPlayer.login(gameSide);
	}
	
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
	
	public int askInputNumber(int lowerBound, int upperBound) throws InterruptedException{
		
		interruptedExceptionLauncher();
		
		Thread t = new Thread(new Runnable() {
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
	
	public void print(String string){
		
		 try {
			connectionWithPlayer.print(string);
		} catch (DisconnectedException e) {
			player.setConnected(false);
		}
		
	}
	
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
	
	private void printlnReal(String string){
		
		try {
			connectionWithPlayer.println(string);
		} catch (DisconnectedException e) {
			player.setConnected(false);
		}
		
	}
	 
	private void interruptedExceptionLauncher() throws InterruptedException{
		if (Thread.interrupted())
			throw new InterruptedException();
	}
	
}
