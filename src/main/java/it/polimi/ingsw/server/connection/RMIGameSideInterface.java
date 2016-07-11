package it.polimi.ingsw.server.connection;

import it.polimi.ingsw.client.connection.RMIPlayerSideInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIGameSideInterface extends Remote{

	void connectToServer(RMIPlayerSideInterface playerSide) throws RemoteException;
	
	boolean isNicknameInUse(String nickname) throws RemoteException;
	
}
