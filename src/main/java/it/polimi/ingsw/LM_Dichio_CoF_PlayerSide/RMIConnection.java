package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIGameSideInterface;

public class RMIConnection {

	PlayerSide playerSide;
	RMIGameSideInterface gameSide;
	
	public RMIConnection(PlayerSide playerSide){
		this.playerSide=playerSide;
		lookUpForRegistry();
	}
	
	private void lookUpForRegistry(){
		try {
			System.out.println("I'm looking up for the registry...");
			Registry registry= LocateRegistry.getRegistry();
			gameSide = (RMIGameSideInterface)Naming.lookup("rmi://127.0.0.1:1099/CoF");
			System.out.println("Done!");
		} catch (RemoteException e) {
			System.out.println("Error in RMI connection " + e);
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public RMIGameSideInterface getGameSide() {
		return gameSide;
	}
	
}
