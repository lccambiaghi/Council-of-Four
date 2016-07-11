package it.polimi.ingsw.server.connection;

import it.polimi.ingsw.client.connection.RMIPlayerSideInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This class implements "RMIGameSideInterface", object that the client provides to the client.
 * 
 * It accesses to the "RMIPlayerSideInterface" and his Proxy when the client calls the method "connectToServer"
 * starting the communication with it.
 *
 */
public interface RMIGameSideInterface extends Remote{

	/**
	 * The method is the first one called by the client to start communicating with the server.
	 * The client passes as parameter the object that he provides to the server itself.
	 * 
	 * @param playerSide : the RMIPlayerSideInterface provided by the client
	 * @throws RemoteException if the server disconnects
	 */
	void connectToServer(RMIPlayerSideInterface playerSide) throws RemoteException;
	
	/**
	 * This method checks if the string passed as parameter (representing the nickname chosen in the client)
	 * is already in use by some other client connected.
	 * 
	 * @param nickname : the nickname to be checked
	 * @return true if nickname already in use, false otherwise
	 * @throws RemoteException if the server disconnects
	 */
	boolean isNicknameInUse(String nickname) throws RemoteException;
	
}
