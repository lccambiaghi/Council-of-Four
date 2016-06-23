package it.polimi.ingsw.LM_Dichio_CoF.connection;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public interface ConnectionWithPlayerInterface {

	public Player getPlayer();
	
	public void sendString(String string);
	
	public String receiveString();
	
	public void login(GameSide gameSide);
	
	public void askToConfigure();
			
	public int getPlayersMaxNumber();
			
	public Configurations getConfigurations();
	
	public int askInputNumber(int lowerBound, int upperBound);
	
	public void print(String string);
	
	public void println(String string);
	
}
