package it.polimi.ingsw.server.control;

import java.util.ArrayList;

/**
 * This class offers the methods to print a specific string in the console of more players (clients)
 * 
 * printlnBroadcastAll : println the specific string in all players console
 * 
 * printlnBroadcastOthers : println the specific string in all players console except the "playerNot"
 */
public class Broadcast {

	/**
	 * @param string to be printed 
	 * @param players where print the string
	 */
	public static void printlnBroadcastAll(String string, ArrayList<Player> players){
		for(Player p: players){
			p.getBroker().println(string);
		}
	}
	
	/**
	 * @param string to be printed 
	 * @param players where print the string
	 * @param playerNot that belongs to "players" where not print the string
	 */
	public static void printlnBroadcastOthers(String string,  ArrayList<Player> players, Player playerNot){
		for(Player p: players){
			if(!p.equals(playerNot))
				p.getBroker().println(string);
		}
	}
}
