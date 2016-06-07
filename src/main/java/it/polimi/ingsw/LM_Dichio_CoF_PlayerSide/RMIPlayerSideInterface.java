package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIPlayerSideInterface extends Remote {

	public void sendString( String string) throws RemoteException;
	
	public String receiveString () throws RemoteException;
	
}
