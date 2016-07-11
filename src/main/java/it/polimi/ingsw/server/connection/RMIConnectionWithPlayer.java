package it.polimi.ingsw.server.connection;

import java.rmi.RemoteException;

import it.polimi.ingsw.client.connection.RMIPlayerSideInterface;
import it.polimi.ingsw.server.control.GameSide;
import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.DisconnectedException;

/**
 * This class implements ConnectionWithPlayerInterface and implements its methods.
 * 
 * It bases the communication with the client on RMI
 */
public class RMIConnectionWithPlayer implements ConnectionWithPlayerInterface{

	private final GameSide gameSide;
	private final Player player;
	private final RMIPlayerSideInterface rmiPlayerSide;
	
	private int intResult;
	
	private final Object lock = new Object();
	
	/**
	 * Constructor of the class
	 * 
	 * It creates the Player, create a Broker and sets it as parameter of the Player
	 * 
	 * @param rmiPlayerSide : the remote object the class will communicate to
	 * @param gameSide
	 */
	public RMIConnectionWithPlayer(RMIPlayerSideInterface rmiPlayerSide, GameSide gameSide){
	
		this.gameSide=gameSide;
		this.rmiPlayerSide=rmiPlayerSide;
		
		player = new Player();
		
		Broker b = new Broker(this, player);
		player.setBroker(b);
		
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void login(GameSide gameSide) throws DisconnectedException{
		String nickname;
		try {
			rmiPlayerSide.login();
			nickname = rmiPlayerSide.getNickname();
			player.setNickname(nickname);
		} catch (RemoteException e) {
			disconnectionHandler();
		}
	}
	
	public boolean isCustomConfig() throws DisconnectedException{
		boolean bool = false;
		try {
			bool = rmiPlayerSide.isCustomConfig();
		} catch (RemoteException e) {
			disconnectionHandler();
		}
		return bool;
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
	
	public void stopInputNumber() throws DisconnectedException{
		try {
			rmiPlayerSide.stopInputNumber();
		} catch (RemoteException e) {
			disconnectionHandler();
		}
	}
	
	/**
	 * It launches a DisconnectedException
	 * 
	 * @throws DisconnectedException
	 */
	private void disconnectionHandler() throws DisconnectedException{
		throw new DisconnectedException();
	}
	
}


