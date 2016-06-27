package it.polimi.ingsw.LM_Dichio_CoF.connection;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public interface ConnectionWithPlayerInterface {

	public abstract Player getPlayer();
	
	public void sendString(String string);
	
	public String receiveString();
	
	public void login(GameSide gameSide);
	
	public Configurations getConfigurations();
	
	public void askInputNumber(int lowerBound, int upperBound);
	
	public void print(String string);
	
	public void println(String string);
	
	public int getIntResult();
	
	public Object getLock();
	
}
