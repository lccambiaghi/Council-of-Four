package it.polimi.ingsw.LM_Dichio_CoF.work;

public class Player {

	String IP;
	String id;
	String nickname;
	
	char typeOfConnection;

	public Player(char typeOfConnection, String IP){
		
		this.typeOfConnection=typeOfConnection;
		this.IP=IP;
		
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public char getTypeOfConnection() {
		return typeOfConnection;
	}

	public void setTypeOfConnection(char typeOfConnection) {
		this.typeOfConnection = typeOfConnection;
	}
	
	
	
	
}
