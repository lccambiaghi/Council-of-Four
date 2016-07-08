package it.polimi.ingsw.server.connection;

import it.polimi.ingsw.server.control.GameSide;
import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Configurations;

public interface ConnectionWithPlayerInterface {

	public abstract Player getPlayer();
	
	public void login(GameSide gameSide) throws DisconnectedException;
	
	public boolean isCustomConfig() throws DisconnectedException;
	
	public Configurations getConfigurations() throws DisconnectedException;
	
	public void askInputNumber(int lowerBound, int upperBound) throws DisconnectedException;
	
	public void print(String string) throws DisconnectedException;
	
	public void println(String string) throws DisconnectedException;
	
	public int getIntResult();
	
	public Object getLock();
	
	public void stopInputNumber() throws DisconnectedException;
	
}
