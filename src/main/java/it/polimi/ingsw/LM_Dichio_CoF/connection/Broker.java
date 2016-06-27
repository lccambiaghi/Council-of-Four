package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class Broker {

	public static void login(Player player, GameSide gameSide){
		player.getConnectionWithPlayer().login(gameSide);
	}
			
	public static Configurations getConfigurations(Player player){
		return player.getConnectionWithPlayer().getConfigurations();
	}
	
	public static int askInputNumber(int lowerBound, int upperBound, Player player) throws InterruptedException{
		handleInterrupt();
		return player.getConnectionWithPlayer().askInputNumber(lowerBound, upperBound);
	}
	
	public static void print(String string, Player player) throws InterruptedException{
		handleInterrupt();
		player.getConnectionWithPlayer().print(string);
	}
	
	public static void println(String string, Player player) throws InterruptedException{
		CharSequence newLine = "\n";
		if(string.contains(newLine)){
			String[] arrayString = string.split("\n");
			for(String s: arrayString)
				printlnReal(s, player);
		}else{
			printlnReal(string, player);
		}
	}
	
	private static void printlnReal(String string, Player player) throws InterruptedException{
		handleInterrupt();
		player.getConnectionWithPlayer().println(string);
	}
	
	public static void printlnBroadcastAll(String string, ArrayList<Player> players) throws InterruptedException{
		for(Player p: players){
			println(string, p);
		}
	}
	
	public static void printlnBroadcastOthers(String string,  ArrayList<Player> players, Player playerNot) throws InterruptedException{
		for(Player p: players){
			if(!p.equals(playerNot))
				println(string, p);
		}
	}
	
	private static void handleInterrupt() throws InterruptedException{
		if (Thread.interrupted())
			throw new InterruptedException();
	}
	
}
