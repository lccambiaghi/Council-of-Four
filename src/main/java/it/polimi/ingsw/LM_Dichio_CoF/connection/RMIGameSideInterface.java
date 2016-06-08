package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.rmi.*;

import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIPlayerSideInterface;

public interface RMIGameSideInterface extends Remote{

	public void connectToServer(RMIPlayerSideInterface playerSide) throws RemoteException;
	
}
