package it.polimi.ingsw.server.connection;

import java.rmi.*;

import it.polimi.ingsw.client.connection.RMIPlayerSideInterface;

public interface RMIGameSideInterface extends Remote{

	void connectToServer(RMIPlayerSideInterface playerSide) throws RemoteException;
	
	boolean isNicknameInUse(String nickname) throws RemoteException;
	
}
