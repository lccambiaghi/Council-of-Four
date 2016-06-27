package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class Broker {

	ConnectionWithPlayerInterface connectionWithPlayer;
	
	public Broker(ConnectionWithPlayerInterface connectionWithPlayer){
		this.connectionWithPlayer=connectionWithPlayer;
	}
	
	public void login(GameSide gameSide){
		connectionWithPlayer.login(gameSide);
	}
			
	public Configurations getConfigurations(){
		return connectionWithPlayer.getConfigurations();
	}
	
	public int askInputNumber(int lowerBound, int upperBound) throws InterruptedException{
		handleInterrupt();
		Thread t = new Thread(new Runnable() {
		     public void run() {
		    	 connectionWithPlayer.askInputNumber(lowerBound, upperBound);
		     }
		});  
		Object lock = connectionWithPlayer.getLock();
		t.start();
		synchronized (lock) {
			lock.wait();
		}
		
		return connectionWithPlayer.getIntResult();
	}
	
	public void print(String string) throws InterruptedException{
		handleInterrupt();
		connectionWithPlayer.print(string);
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
		handleInterrupt();
		connectionWithPlayer.println(string);
	}
	
	private void handleInterrupt() throws InterruptedException{
		if (Thread.interrupted())
			throw new InterruptedException();
	}
	
}
