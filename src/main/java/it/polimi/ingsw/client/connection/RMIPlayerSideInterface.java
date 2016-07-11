package it.polimi.ingsw.client.connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface represents the remote object that the client provides to the server,
 * it contains the methods that can be called remotely to interact with the client
 */
public interface RMIPlayerSideInterface extends Remote {
	
	/**
	 * This method is called by the server to permit the client to login
	 * @throws RemoteException if the client disconnects
	 */
	void login() throws RemoteException;
	
	/**
	 * This method is called by the server to get the nickname chosen by the client.
	 * It inter-operates with the method "login"
	 * 
	 * @return the nickname
	 * @throws RemoteException if the client disconnects
	 */
	String getNickname() throws RemoteException;
	
	/**
	 * This method permits the server to check if the client as set his own configurations
	 * 
	 * @return true if the client created his configurations, false otherwise
	 * @throws RemoteException if the client disconnects
	 */
	boolean isCustomConfig() throws RemoteException;
	
	/**
	 * This method permits to get the configurations created by the client as an object,
	 * than they have to be casted to "Configurations"
	 * 
	 * @return an Object containing the configurations
	 * @throws RemoteException if the client disconnects
	 */
	Object getConfigurationsAsObject() throws RemoteException;
	
	/**
	 * The method allows the server to ask for an input by the client.
	 * The input has to be an integer value between the bounds passed as parameters
	 * 
	 * @param lowerBound
	 * @param upperBound
	 * @return the choice of the user
	 * @throws RemoteException if the client disconnects
	 */
	int inputNumber(int lowerBound, int upperBound) throws RemoteException;
	
	/**
	 * The method permits to stop a previous request of "inputNumber"
	 * 
	 * @throws RemoteException if the client disconnects
	 */
	void stopInputNumber() throws RemoteException;
	
	/**
	 * This method simply print a string in the console of the client
	 * 
	 * @param string to be printed
	 * @throws RemoteException if the client disconnects
	 */
	void print(String string) throws RemoteException;
	
	/**
	 * This method permits to print a string with the final escape character in the console of the client
	 * 
	 * @param string to be printed
	 * @throws RemoteException if the client disconnects
	 */
	void println(String string) throws RemoteException;

}
