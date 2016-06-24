package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class Broker {

	public static void sendString(String string, Player player){
		player.getConnectionWithPlayer().sendString(string);
	}
	
	public static String receiveString(Player player){ 
		return player.getConnectionWithPlayer().receiveString();
	}
	
	public static void login(Player player, GameSide gameSide){
		player.getConnectionWithPlayer().login(gameSide);
	}
	
	public static void askToConfigure(Player player){
		player.getConnectionWithPlayer().askToConfigure();
	}
			
	public static int getPlayersMaxNumber(Player player){
		return player.getConnectionWithPlayer().getPlayersMaxNumber();
	}
			
	public static Configurations getConfigurations(Player player){
		return player.getConnectionWithPlayer().getConfigurations();
	}
	
	public static int askInputNumber(int lowerBound, int upperBound, Player player){
		return player.getConnectionWithPlayer().askInputNumber(lowerBound, upperBound);
	}
	
	public static void print(String string, Player player){
		player.getConnectionWithPlayer().print(string);
	}
	
	public static void println(String string, Player player){
		CharSequence newLine = "\n";
		if(string.contains(newLine)){
			String[] arrayString = string.split("\n");
			for(String s: arrayString)
				player.getConnectionWithPlayer().println(s);
		}else{
			player.getConnectionWithPlayer().println(string);
		}
	}
	
	public static void printlnBroadcastAll(String string, ArrayList<Player> players){
		for(Player p: players){
			println(string, p);
		}
	}
	
	public static void printlnBroadcastOthers(String string,  ArrayList<Player> players, Player playerNot){
		for(Player p: players){
			if(!p.equals(playerNot))
				println(string, p);
		}
	}
	
}
