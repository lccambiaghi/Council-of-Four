package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIPlayerSideInterface;

public class RMIConnectionWithPlayer implements ConnectionWithPlayerInterface{

	GameSide gameSide;
	Player player;
	RMIPlayerSideInterface rmiPlayerSide;
	
	public RMIConnectionWithPlayer(RMIPlayerSideInterface rmiPlayerSide, GameSide gameSide){
	
		this.gameSide=gameSide;
		this.rmiPlayerSide=rmiPlayerSide;
		
		System.out.println("A new player has connected");
		
		player = new Player('r');
		
		player.setConnectionWithPlayer(this);
		
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void sendString(String string){
		try {
			rmiPlayerSide.sendString(string);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public String receiveString(){ 
		try {
			return rmiPlayerSide.receiveString();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void login(GameSide gameSide){
		String nickname= null;
		try {
			rmiPlayerSide.login();
			nickname = rmiPlayerSide.getNickname();
			player.setNickname(nickname);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
			
	public Configurations getConfigurations(){
		Configurations config = null;
		try {
			config = (Configurations)rmiPlayerSide.getConfigurationsAsObject();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	public synchronized int askInputNumber(int lowerBound, int upperBound){
		int result = 0;
		try {
			result = rmiPlayerSide.inputNumber(lowerBound, upperBound);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public synchronized void print(String string){
		try {
			rmiPlayerSide.print(string);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void println(String string){
		try {
			rmiPlayerSide.println(string);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}


