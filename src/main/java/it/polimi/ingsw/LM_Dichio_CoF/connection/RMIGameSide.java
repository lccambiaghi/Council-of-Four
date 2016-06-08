package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.rmi.*;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.LM_Dichio_CoF.work.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIPlayerSideInterface;

public class RMIGameSide extends UnicastRemoteObject implements RMIGameSideInterface{
	
	GameSide gameSide;
	
	public RMIGameSide(GameSide gameSide) throws RemoteException{
		this.gameSide=gameSide;
	}
	
	public void connectToServer(RMIPlayerSideInterface rmiPlayerSide) throws RemoteException{
		System.out.println("A new player has connected");
		new RMIConnectionWithPlayer(rmiPlayerSide, gameSide);
	}
	
	public boolean isNicknameInUse(String nickname) throws RemoteException{
		return gameSide.isNicknameInUse(nickname);
	}
	
}
