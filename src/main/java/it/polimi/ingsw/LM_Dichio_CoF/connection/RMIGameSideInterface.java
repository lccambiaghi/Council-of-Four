package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.rmi.*;

import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIPlayerSideInterface;

public interface RMIGameSideInterface extends Remote{

	public void connectToServer(RMIPlayerSideInterface playerSide) throws RemoteException;
	
	//public void sendStringTS(String string) throws RemoteException;
	
	//public String receiveStringFS() throws RemoteException;
	
	//public void sendObjectTS(Object object) throws RemoteException;
	
}
