package it.polimi.ingsw.server.connection;

import it.polimi.ingsw.client.connection.RMIPlayerSideInterface;
import it.polimi.ingsw.server.control.GameSide;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *  This class implements "RMIGameSideInterface", object that the server provides to the client.
 *  It contains the implementation of the methods that can be called remotely to interact with the server.
 *
 */
public class RMIGameSide extends UnicastRemoteObject implements RMIGameSideInterface{
	
	private final GameSide gameSide;
	
	/**
	 * The constructor saves the "gameSide" in the corresponding variable
	 * 
	 * @param gameSide from which the server has been started
	 * @throws RemoteException if the server disconnects
	 */
	public RMIGameSide(GameSide gameSide) throws RemoteException{
		this.gameSide=gameSide;
	}
	
	/**
	 * The method starts the communication with the client calling "gameSide.startHandlePlayer"
	 * 
	 * @param rmiPlayerSide : the client "RMIPlayerSideInterface"
	 * @throws RemoteException if the server disconnects
	 */
	public void connectToServer(RMIPlayerSideInterface rmiPlayerSide) throws RemoteException{
		RMIConnectionWithPlayer r = new RMIConnectionWithPlayer(rmiPlayerSide);
		gameSide.startHandlePlayer(gameSide, r.getPlayer());
	}
	
	/**
	 * This method calls the corresponding "isNicknameInUse" of the "gameSide"
	 * 
	 * @param string : the string representing the nickname
	 * @throws RemoteException if the server disconnects
	 */
	public boolean isNicknameInUse(String nickname) throws RemoteException{
		return gameSide.isNicknameInUse(nickname);
	}
	
}
