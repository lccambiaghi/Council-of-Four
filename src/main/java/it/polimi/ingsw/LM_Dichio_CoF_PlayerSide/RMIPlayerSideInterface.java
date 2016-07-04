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
	
	public Object getConfigurationsAsObject() throws RemoteException;
	
	public int inputNumber(int lowerBound, int upperBound) throws RemoteException;
	
	public void stopInputNumber() throws RemoteException;
	
	public void print(String string) throws RemoteException;
	
	public void println(String string) throws RemoteException;

}
