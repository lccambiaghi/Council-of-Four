package it.polimi.ingsw.server.connection;

public class DisconnectedException extends Exception {

	public DisconnectedException(){
		super();
	}
	
	public DisconnectedException(String s){
		super(s);
	}
	
}
