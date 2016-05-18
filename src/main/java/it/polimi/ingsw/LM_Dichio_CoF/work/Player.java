package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.net.Socket;

public class Player {

	String id;
	String nickname;
	
	char typeOfConnection;
	Socket playerSocket;
	
	char CLIorGUI;

	public Player(String typeOfConnection){
		
		this.typeOfConnection=typeOfConnection.charAt(0);
		
	}

	public Socket getPlayerSocket() {
		return playerSocket;
	}

	public void setPlayerSocket(Socket playerSocket) {
		this.playerSocket = playerSocket;
		
		
		
	}
	
	
	public char getCLIorGUI() {
		return CLIorGUI;
	}
	public void setCLIorGUI(char cLIorGUI) {
		CLIorGUI = cLIorGUI;
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
