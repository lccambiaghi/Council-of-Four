package it.polimi.ingsw.server.control;

import java.util.ArrayList;

public class Broadcast {

	public static void printlnBroadcastAll(String string, ArrayList<Player> players){
		for(Player p: players){
			p.getBroker().println(string);
		}
	}
	
	public static void printlnBroadcastOthers(String string,  ArrayList<Player> players, Player playerNot){
		for(Player p: players){
			if(!p.equals(playerNot))
				p.getBroker().println(string);
		}
	}
}
