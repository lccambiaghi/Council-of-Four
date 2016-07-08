package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIGameSideInterface;
import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;

public class RMIConnection {

	PlayerSide playerSide;
	
	RMIGameSideInterface rmiGameSide;
	RMIPlayerSideInterface rmiPlayerSide;
	
	public RMIConnection(PlayerSide playerSide){
		this.playerSide=playerSide;
		lookUpForRegistry();
		connectToServer();
	}
	
	private void lookUpForRegistry(){
		try {
			System.out.println("I'm looking up for the registry...");
			rmiGameSide = (RMIGameSideInterface)Naming.lookup(Constant.RMI_REGISTRY_LOOKUP_ADDRESS);
			System.out.println("Done!");
		} catch (RemoteException e) {
			System.out.println("Error in RMI connection " + e);
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void connectToServer(){
		try {
			rmiPlayerSide = new RMIPlayerSide(playerSide);
			rmiGameSide.connectToServer(rmiPlayerSide);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public RMIGameSideInterface getRmiGameSide() {
		return rmiGameSide;
	}
	
	public RMIPlayerSideInterface getRMIPlayerSide(){
		return rmiPlayerSide;
	}
	
}
