package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Player{
	
	private String nickname;
	
	private char typeOfConnection;
	private Socket playerSocket;
	
	private char CLIorGUI;
	
	private Scanner input;
	private PrintWriter output;	
	
	
	/* The constructor assigns to the player the type of connection */
	public Player(char typeOfConnection){
		this.typeOfConnection=typeOfConnection; 	
	}

	public Socket getPlayerSocket() {return playerSocket;}
	public void setPlayerSocket(Socket playerSocket) { this.playerSocket = playerSocket; }
	
	public void openSocketStream(){
		try {
			output = new PrintWriter(playerSocket.getOutputStream());
			input = new Scanner(playerSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public char getCLIorGUI() {return CLIorGUI;}
	public void setCLIorGUI(char cLIorGUI) {CLIorGUI = cLIorGUI;}	

	public String getNickname() {return nickname;}
	public void setNickname(String nickname) {this.nickname = nickname;}
	
	public char getTypeOfConnection() {return typeOfConnection;}
	public void setTypeOfConnection(char typeOfConnection) {this.typeOfConnection = typeOfConnection;}
	
	public void sendString(String string){ output.println(string); output.flush();}
	public String receiveString(){ return input.nextLine();}
	
	public Object receiveObject(){ 
		Object object = null;
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(playerSocket.getInputStream());
			object = objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	
	
	
	// variables of the game
	private int richness;
	private int assistant;
	private ArrayList <PoliticCard> arrayListPoliticCard= new ArrayList<>();
	
	public int getRichness() {return richness;}
	public void setRichness(int richness) {this.richness = richness;}

	public int getAssistant() {	return assistant;}
	public void setAssistant(int assistant) {this.assistant = assistant;}
	public void addAssistant(int increment)	{this.assistant +=increment;}

	public ArrayList<PoliticCard> getArrayListPoliticCard() {return arrayListPoliticCard;}
	public void addPoliticCard(PoliticCard politicCard) {this.arrayListPoliticCard.add(politicCard);}
	
	
}
