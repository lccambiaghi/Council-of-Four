package it.polimi.ingsw.server.connection;

import it.polimi.ingsw.client.connection.RMIPlayerSideInterface;
import it.polimi.ingsw.server.control.GameSide;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIGameSide extends UnicastRemoteObject implements RMIGameSideInterface{
	
	private final GameSide gameSide;
	
	public RMIGameSide(GameSide gameSide) throws RemoteException{
		this.gameSide=gameSide;
	}
	
	public void connectToServer(RMIPlayerSideInterface rmiPlayerSide) throws RemoteException{
		RMIConnectionWithPlayer r = new RMIConnectionWithPlayer(rmiPlayerSide);
		gameSide.startHandlePlayer(gameSide, r.getPlayer());
	}
	
	public boolean isNicknameInUse(String nickname) throws RemoteException{
		return gameSide.isNicknameInUse(nickname);
	}
	
}
