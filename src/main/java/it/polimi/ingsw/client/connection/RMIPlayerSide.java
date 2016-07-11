package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.PlayerSide;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class implements "RMIPlayerSideInterface", object that the client provides to the server.
 * It contains the implementation of the methods that can be called remotely to interact with the client.
 */
public class RMIPlayerSide extends UnicastRemoteObject implements RMIPlayerSideInterface {

	private final PlayerSide playerSide;
	
	/**
	 * The constructor saves the calling "playerSide" in the corresponding variable.
	 * 
	 * @param playerSide : the caller
	 * @throws RemoteException
	 */
	public RMIPlayerSide(PlayerSide playerSide) throws RemoteException {
		this.playerSide=playerSide;
	}

	/**
	 * This method calls the corresponding "login" in "playerSide"
	 */
	public void login(){
		playerSide.login();
	}
	
	/**
	 * This method calls the corresponding "getNickname" in "playerSide"
	 * 
	 * @return the nickname
	 */
	public String getNickname(){
		return playerSide.getNickname();
	}
	
	/**
	 * This method calls the corresponding "isConfig" in "playerSide"
	 * 
	 * @return true if the client created his configurations, false otherwise
	 */
	public boolean isCustomConfig(){
		return playerSide.isCustomConfig();
	}
	
	/**
	 * This method calls the corresponding "getConfigurationsAsObject" in "playerSide"
	 * 
	 * @return the configurations
	 */
	public Object getConfigurationsAsObject(){
		return playerSide.getConfigurationsAsObject();
	}
	
	/**
	 * This method calls the method "System.out.print" 
	 * 
	 * @param string : the string to be printed
	 */
	public void print(String string){
		System.out.print(string);
	}
	
	/**
	 * This method calls the method "System.out.print" 
	 * 
	 * @param string : the string to be printed
	 */
	public void println(String string){
		System.out.println(string);
	}
	
	/**
	 * This method calls the corresponding "inputNumber" in "inputHandler"
	 * 
	 * @param lowerBound
	 * @param upperBound
	 * @return the choice of the user
	 */
	public int inputNumber(int lowerBound, int upperBound){
		return playerSide.getInputHandler().inputNumber(lowerBound, upperBound);
	}
	
	/**
	 * This method calls the corresponding "stopInputNumber" in "inputHandler"
	 */
	public void stopInputNumber(){
		playerSide.getInputHandler().stopInputNumber();
	}
	
	
}
