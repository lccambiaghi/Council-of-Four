package it.polimi.ingsw.server.connection;

import it.polimi.ingsw.server.control.GameSide;
import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Configurations;

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
	
	public boolean isCustomConfig() throws DisconnectedException{
		return connectionWithPlayer.isCustomConfig();
	}
			
	public Configurations getConfigurations() throws DisconnectedException{
		return connectionWithPlayer.getConfigurations();
	}
	
	public int askInputNumber(int lowerBound, int upperBound) throws InterruptedException{
		
		interruptedExceptionLauncher();
		
		Thread t = new Thread(new Runnable() {
		     public void run() {
		    	 try {
					connectionWithPlayer.askInputNumber(lowerBound, upperBound);
				} catch (DisconnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				throw new InterruptedException();
			}
		}
		return connectionWithPlayer.getIntResult();
	}
	
	public void print(String string) throws InterruptedException{
		
		interruptedExceptionLauncher();
		 try {
			connectionWithPlayer.print(string);
		} catch (DisconnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void println(String string) throws InterruptedException{
		CharSequence newLine = "\n";
		if(string.contains(newLine)){
			String[] arrayString = string.split("\n");
			for(String s: arrayString)
				printlnReal(s);
		}else{
			printlnReal(string);
		}
	}
	
	private void printlnReal(String string) throws InterruptedException{
		
		interruptedExceptionLauncher();
		
		try {
			connectionWithPlayer.println(string);
		} catch (DisconnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	private void interruptedExceptionLauncher() throws InterruptedException{
		if (Thread.interrupted())
			throw new InterruptedException();
	}
	
}
