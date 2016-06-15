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
		if(player.getTypeOfConnection()=='s'){
			player.getOutputSocket().println(string);
			player.getOutputSocket().flush();
		}else{
			try {
				player.getRmiPlayerSide().sendString(string);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String receiveString(Player player){ 
		if(player.getTypeOfConnection()=='s'){
			return player.getInputSocket().nextLine();
		}else{
			try {
				return player.getRmiPlayerSide().receiveString();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}return "ERROR"; // mi serve davvero?
	}
	
	public static Object receiveObject(Player player){ 
		Object object = null;
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(player.getPlayerSocket().getInputStream());
			object = objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static void login(Player player, GameSide gameSide){
		String nickname= null;
		if(player.getTypeOfConnection()=='s'){
			sendString("SOCKETlogin",player);
			boolean logged = false;
			while(!logged){
				nickname = receiveString(player);
				if(!gameSide.isNicknameInUse(nickname)){
					player.setNickname(nickname);
					sendString("true",player);
					logged=true;
				}else{
					sendString("false",player);
				}
			}
		}else{
			try {
				player.getRmiPlayerSide().login();
				nickname = player.getRmiPlayerSide().getNickname();
				player.setNickname(nickname);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void waitForServer(Player player){
		if(player.getTypeOfConnection()=='s'){
			sendString("SOCKETwaitForServer",player);
		}else{
			try {
				player.getRmiPlayerSide().waitForServer();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void askToConfigure(Player player){
		if(player.getTypeOfConnection()=='s'){
			sendString("SOCKETconfigure",player);
		}else{
			try {
				player.getRmiPlayerSide().configure();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
			
	public static int getPlayersMaxNumber(Player player){
		int playersMaxNumber=0;
		if(player.getTypeOfConnection()=='s'){
			sendString("SOCKETgetConfigurationsPlayersMaxNumber", player);
			playersMaxNumber = Integer.parseInt(receiveString(player));
		}else{
			try {
				playersMaxNumber = player.getRmiPlayerSide().getConfigurationsPlayersNumber();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return playersMaxNumber;
	}
			
	public static Configurations getConfigurations(Player player){
		Configurations config = null;
		if(player.getTypeOfConnection()=='s'){
			sendString("SOCKETgetConfigurationsAsObject",player);
			config = (Configurations)receiveObject(player);
		}else{
			try {
				config = (Configurations)player.getRmiPlayerSide().getConfigurationsAsObject();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return config;
	}
	
	public static synchronized void sayMatchIsStarting(Player player){
		if(player.getTypeOfConnection()=='s'){
			sendString("SOCKETstartingMatch",player);
		}else{
			try {
				player.getRmiPlayerSide().startingMatch();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static synchronized void sayMatchHasStarted(Player player){
		if(player.getTypeOfConnection()=='s'){
			sendString("SOCKETmatchStarted",player);
		}else{
			try {
				player.getRmiPlayerSide().matchStarted();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static synchronized int askInputNumber(int lowerBound, int upperBound, Player player){
		int result = 0;
		if(player.getTypeOfConnection()=='s'){
			sendString("SOCKETinputNumber",player);
			sendString(String.valueOf(lowerBound), player);
			sendString(String.valueOf(upperBound),player);
			result = Integer.parseInt(receiveString(player));
		}else{
			try {
				result = player.getRmiPlayerSide().inputNumber(lowerBound, upperBound);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static synchronized void print(String string, Player player){
		if(player.getTypeOfConnection()=='s'){
			sendString("SOCKETprint",player);
			sendString(string, player);
		}else{
			try {
				player.getRmiPlayerSide().print(string);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static synchronized void println(String string, Player player){
		if(player.getTypeOfConnection()=='s'){
			sendString("SOCKETprintln",player);
			sendString(string, player);
		}else{
			try {
				player.getRmiPlayerSide().println(string);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static synchronized void printlnBroadcastAll(String string, ArrayList<Player> players){
		for(Player p: players){
			println(string, p);
		}
	}
	
	public static synchronized void printlnBroadcastOthers(String string,  ArrayList<Player> players, Player playerNot){
		for(Player p: players){
			if(!p.equals(playerNot))
				println(string, p);
		}
	}
	
}
