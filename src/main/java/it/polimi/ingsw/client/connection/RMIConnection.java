package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.PlayerSide;
import it.polimi.ingsw.server.connection.RMIGameSideInterface;
import it.polimi.ingsw.utils.Constant;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * This class permits to connect to the server through RMI.
 * 
 * In fact the method "connectToServer" calls "lookUpForRegistry" that search the
 * registry created by the server and, when it has done, saves the interface contained
 * in the registry in the variable "rmiGameSide".
 * It is the Proxy that permits to call remote methods.
 * 
 * Then it connects to the server through "rmiGameSide.connectToServer", method offered
 * by the server to start the connection with it.
 * 
 * If there are connection problems it signals them catching the relative exceptions
 */
public class RMIConnection {

	private final PlayerSide playerSide;
	
	private RMIGameSideInterface rmiGameSide;
	private RMIPlayerSideInterface rmiPlayerSide;
	
	/**
	 * The constructor of the class
	 * 
	 * @param playerSide : the object PlayerSide that calls the constructor
	 */
	public RMIConnection(PlayerSide playerSide){
		this.playerSide=playerSide;
	}
	
	/**
	 * The method look up for the registry, assigns to "rmiPlayerSide" a new object,
	 * passes it to the server as a parameter in the remote method "rmiGameSide.connectToServer"
	 * 
	 * The variable "rmiPlayerSide" is the Proxy representing the playerSide and then used
	 * by the server to call remote methods that are in the client.
	 * 
	 * The method catches exceptions due to lack of connection and signals them to the user
	 */
	public void connectToServer(){
		try {
			lookUpForRegistry();
			rmiPlayerSide = new RMIPlayerSide(playerSide);
			rmiGameSide.connectToServer(rmiPlayerSide);
		} catch (RemoteException | NotBoundException | MalformedURLException e) {
			System.out.println("Error in RMI connection " + e);
		}
	}
	
	/**
	 * The method looks up for the registry and saves the interface found in the variable
	 * "rmiGameSide". If the connection fails it throws the exception.
	 * 
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws MalformedURLException
	 */
	private void lookUpForRegistry() throws RemoteException, NotBoundException, MalformedURLException{
		
		System.out.println("I'm looking up for the registry...");
		rmiGameSide = (RMIGameSideInterface)Naming.lookup(Constant.RMI_REGISTRY_LOOKUP_ADDRESS);
		System.out.println("Done!");
		
	}
	
	public RMIGameSideInterface getRmiGameSide() {
		return rmiGameSide;
	}
	
}
