package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	/* The constructor assigns to the player the type of connection */
	public Client(char typeOfConnection){
		this.typeOfConnection=typeOfConnection; 	
	}
	
	/* The construcor never used to be eredithed by the Player - Subclass */
	public Client(){}

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
	
	
	private String nickname;
	
	private char typeOfConnection;
	private Socket playerSocket;
	
	private char CLIorGUI;
	
	private Scanner input;
	private PrintWriter output;	
	
	
	
}
