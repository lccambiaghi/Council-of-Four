package it.polimi.ingsw.client.connection;

import java.rmi.Remote;
import java.rmi.RemoteException;



public interface RMIPlayerSideInterface extends Remote {
	
	
	/*
	 * Methods used to connect to the server and to get into a match
	 */
	void login() throws RemoteException;
	
	String getNickname() throws RemoteException;
	
	boolean isCustomConfig() throws RemoteException;
	
	Object getConfigurationsAsObject() throws RemoteException;
	
	int inputNumber(int lowerBound, int upperBound) throws RemoteException;
	
	void stopInputNumber() throws RemoteException;
	
	void print(String string) throws RemoteException;
	
	void println(String string) throws RemoteException;

}
