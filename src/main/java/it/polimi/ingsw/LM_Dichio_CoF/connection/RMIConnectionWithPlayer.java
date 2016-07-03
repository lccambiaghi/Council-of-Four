package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIPlayerSideInterface;

public class RMIConnectionWithPlayer implements ConnectionWithPlayerInterface{

	private GameSide gameSide;
	private Player player;
	private RMIPlayerSideInterface rmiPlayerSide;
	
	private int intResult;
	
	public Object lock = new Object();
	
	public RMIConnectionWithPlayer(RMIPlayerSideInterface rmiPlayerSide, GameSide gameSide){
	
		this.gameSide=gameSide;
		this.rmiPlayerSide=rmiPlayerSide;
		
		System.out.println("A new player has connected");
		
		player = new Player('r');
		
		Broker b = new Broker(this, player);
		player.setBroker(b);
		
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void login(GameSide gameSide) throws DisconnectedException{
		String nickname= null;
		try {
			rmiPlayerSide.login();
			nickname = rmiPlayerSide.getNickname();
			player.setNickname(nickname);
		} catch (RemoteException e) {
			disconnectionHandler();
		}
	}
			
	public Configurations getConfigurations() throws DisconnectedException{
		Configurations config = null;
		try {
			config = (Configurations)rmiPlayerSide.getConfigurationsAsObject();
		} catch (RemoteException e) {
			disconnectionHandler();
		}
		return config;
	}
	
	public void askInputNumber(int lowerBound, int upperBound) throws DisconnectedException{
		int result = 0;
		try {
			result = rmiPlayerSide.inputNumber(lowerBound, upperBound);
		} catch (RemoteException e) {
			disconnectionHandler();
		}
		intResult=result;
		synchronized (lock) {
			lock.notify();
		}
	}
	
	public void print(String string) throws DisconnectedException{
		try {
			rmiPlayerSide.print(string);
		} catch (RemoteException e) {
			disconnectionHandler();
		}
	}
	
	public void println(String string) throws DisconnectedException{
		try {
			rmiPlayerSide.println(string);
		} catch (RemoteException e) {
			disconnectionHandler();
		}
	}
	
	public int getIntResult(){
		return intResult;
	}
	
	public Object getLock(){
		return lock;
	}
	
	private void disconnectionHandler() throws DisconnectedException{
		player.setConnected(false);
		throw new DisconnectedException();
	}
	
}


