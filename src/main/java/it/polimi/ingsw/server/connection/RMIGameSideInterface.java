package it.polimi.ingsw.server.connection;

import java.rmi.*;

import it.polimi.ingsw.client.connection.RMIPlayerSideInterface;

public interface RMIGameSideInterface extends Remote{

	public void connectToServer(RMIPlayerSideInterface playerSide) throws RemoteException;
	
	public boolean isNicknameInUse(String nickname) throws RemoteException;
	
}
