package it.polimi.ingsw.LM_Dichio_CoF.control;

import java.util.ArrayList;

public class Broadcast {

	public static void printlnBroadcastAll(String string, ArrayList<Player> players){
		for(Player p: players){
			try {
				p.getBroker().println(string);
			} catch (InterruptedException e) {}
		}
	}
	
	public static void printlnBroadcastOthers(String string,  ArrayList<Player> players, Player playerNot){
		for(Player p: players){
			if(!p.equals(playerNot))
				try {
					p.getBroker().println(string);
				} catch (InterruptedException e) {}
		}
	}
}
