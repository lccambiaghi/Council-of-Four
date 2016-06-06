package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIGameSideInterface extends Remote{

	public boolean connectToServer() throws RemoteException;
	
	public void sendStringTS(String string) throws RemoteException;
	
	//public String receiveStringFS() throws RemoteException;
	
	public void sendObjectTS(Object object) throws RemoteException;
	
}
