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
	
	public void connectToServer(RMIPlayerSideInterface playerSide) throws RemoteException{
		System.out.println("A new player has connected");
		new RMIConnectionWithPlayer(playerSide, gameSide);
	}
	
	/*public void sendStringTS(String string) throws RemoteException{
		
	}
	
	/*public String receiveStringFS() throws RemoteException{
		Mi sa che questo metodo vada nello skeleton del client
	}*/
	
	/*public void sendObjectTS(Object object) throws RemoteException{
		
	}*/
	
}
