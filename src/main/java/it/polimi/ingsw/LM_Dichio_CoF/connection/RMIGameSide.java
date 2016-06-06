package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public class RMIGameSide extends UnicastRemoteObject implements RMIGameSideInterface{
	
	public RMIGameSide() throws RemoteException{}
	
	public boolean connectToServer() throws RemoteException{
		Player player = new Player('r');
		try {
			player.setHost(getClientHost());
			player.setRMIStream();
			
			
			return true;
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void sendStringTS(String string) throws RemoteException{
		
	}
	
	/*public String receiveStringFS() throws RemoteException{
		Mi sa che questo metodo vada nello skeleton del client
	}*/
	
	public void sendObjectTS(Object object) throws RemoteException{
		
	}
	
}
