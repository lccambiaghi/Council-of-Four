package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketConnection {

	PlayerSide playerSide;
	
	private final static String ADDRESS = "localhost";
	private final static int SOCKET_PORT = 3000;
	
	private Socket mySocket;
	private Scanner inSocket;
	private PrintWriter outSocket;
	
	public SocketConnection(PlayerSide playerSide){
		this.playerSide=playerSide;
		connectToServer();
	}
	
	private void connectToServer(){
		
		try {

			mySocket = new Socket(ADDRESS, SOCKET_PORT);
			
			try {
				inSocket = new Scanner(mySocket.getInputStream());
				outSocket = new PrintWriter(mySocket.getOutputStream());
			} catch (IOException e) {
				System.out.println("Cannot open channels of communication");
				e.printStackTrace();
			}
			System.out.println("I am connected to the Server");
			
		} catch (IOException e) {
			System.out.println("Cannot connect to the Server");
			e.printStackTrace();
		}

	}	
	
	
	public void sendStringTS(String string){
		outSocket.println(string);
		outSocket.flush();
	}
	
	public String receiveStringFS(){
		String string = inSocket.nextLine(); 
		return string; 
	}
	
	public void sendObjectTS(Object object){
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(mySocket.getOutputStream());
			objectOutputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
