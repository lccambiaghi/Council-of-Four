package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIPlayerSideInterface extends Remote {
	
	
	/*
	 * Methods used to connect to the server and to get into a match
	 */
	public void login() throws RemoteException;
	
	public String getNickname() throws RemoteException;

	public void sendString( String string) throws RemoteException;
	
	public String receiveString () throws RemoteException;
	
	public void waitForServer() throws RemoteException;
	
	public void configure() throws RemoteException;
	
	public int getConfigurationsPlayersNumber() throws RemoteException;
	
	public Object getConfigurationsAsObject() throws RemoteException;
	
	public void startingMatch() throws RemoteException;
	
	public void playMatch() throws RemoteException;
	
	
	/*
	 * Methods of the match
	 */
	public void waitTurn() throws RemoteException;
	
}
