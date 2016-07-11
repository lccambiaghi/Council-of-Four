package it.polimi.ingsw.server.connection;

import it.polimi.ingsw.server.control.GameSide;
import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.DisconnectedException;

/**
 * This interface offers the methods that the Broker of every Player can call to communicate with the real client
 */
public interface ConnectionWithPlayerInterface {

	/**
	 * This method permit to perform the login with the player
	 * 
	 * @param gameSide : the GameSide that calls the method
	 * @throws DisconnectedException if the client disconnects
	 */
	void login(GameSide gameSide) throws DisconnectedException;
	
	/**
	 * @return true if the client has created his own configurations, false otherwise
	 * @throws DisconnectedException if the client disconnects
	 */
	boolean isCustomConfig() throws DisconnectedException;
	
	/**
	 * @return the configurations created by the player
	 * @throws DisconnectedException if the client disconnects
	 */
	Configurations getConfigurations() throws DisconnectedException;
	
	/**
	 * This method permits to asks the client an integer value between the bounds passed as parameter
	 * 
	 * @param lowerBound 
	 * @param upperBound
	 * @throws DisconnectedException if the client disconnects
	 */
	void askInputNumber(int lowerBound, int upperBound) throws DisconnectedException;
	
	/**
	 * This method stops the request of the method "inputNumber"
	 * 
	 * @throws DisconnectedException if the client disconnects
	 */
	void stopInputNumber() throws DisconnectedException;
	
	/**
	 * @return the client's choice returned by "inputNumber"
	 */
	int getIntResult();
	
	/**
	 * Print a string in the client's console
	 * 
	 * @param string to be printed
	 * @throws DisconnectedException if the client disconnects
	 */
	void print(String string) throws DisconnectedException;
	
	/**
	 * Print a string with final escape in the client's console
	 * 
	 * @param string to be printed
	 * @throws DisconnectedException if the client disconnects
	 */
	void println(String string) throws DisconnectedException;
	
	/**
	 * @return the lock used by the class
	 */
	Object getLock();
	
}
