package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.net.Socket;
import java.util.ArrayList;

public class Player {

	private String nickname;
	
	private char typeOfConnection;
	private Socket playerSocket;
	
	private char CLIorGUI;

	
	
	// variables of the game
	private int richness;
	private int assistant;
	private ArrayList <PoliticCard> arrayListPoliticCard= new ArrayList<>();
	
	
	
	/* The constructor assigns to the player the type of connection */
	public Player(String typeOfConnection){ this.typeOfConnection=typeOfConnection.charAt(0); }

	public Socket getPlayerSocket() {return playerSocket;}
	public void setPlayerSocket(Socket playerSocket) { this.playerSocket = playerSocket; }
	
	public char getCLIorGUI() {return CLIorGUI;}
	public void setCLIorGUI(char cLIorGUI) {CLIorGUI = cLIorGUI;}	

	public String getNickname() {return nickname;}
	public void setNickname(String nickname) {this.nickname = nickname;}

	public char getTypeOfConnection() {return typeOfConnection;}
	public void setTypeOfConnection(char typeOfConnection) {this.typeOfConnection = typeOfConnection;}

	
	
	public int getRichness() {return richness;}
	public void setRichness(int richness) {this.richness = richness;}

	public int getAssistant() {	return assistant;}
	public void setAssistant(int assistant) {this.assistant = assistant;}
	public void addAssistant(int increment)	{this.assistant +=increment;}

	public ArrayList<PoliticCard> getArrayListPoliticCard() {return arrayListPoliticCard;}
	public void addPoliticCard(PoliticCard politicCard) {this.arrayListPoliticCard.add(politicCard);}
	
	
	
	
	
}
