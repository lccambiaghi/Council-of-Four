package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class Broker {

	private ConnectionWithPlayerInterface connectionWithPlayer;
	private Player player;
	
	public Broker(ConnectionWithPlayerInterface connectionWithPlayer, Player player){
		this.connectionWithPlayer=connectionWithPlayer;
		this.player=player;
	}
	
	public void login(GameSide gameSide){
		connectionWithPlayer.login(gameSide);
	}
			
	public Configurations getConfigurations(){
		return connectionWithPlayer.getConfigurations();
	}
	
	public int askInputNumber(int lowerBound, int upperBound) throws InterruptedException{
		exceptionLauncher();
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
		exceptionLauncher();
		if(isConnected())
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
		exceptionLauncher();
		if(isConnected())
			connectionWithPlayer.println(string);
	}
	
	private void exceptionLauncher() throws InterruptedException{
		if (Thread.interrupted())
			throw new InterruptedException();
	}
	
	public boolean isConnected(){
		if(connectionWithPlayer.isConnected())
			return true;
		else
			return false;
	}
	
}
