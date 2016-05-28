package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.io.IOException;
import java.net.Socket;

public class PlayerSide {

	private  Socket mySocket;
	private final static String address="localhost";
	
	public PlayerSide() {
		
		System.out.println("I am alive");	
		
		connectToServer();
	
	}

	private void connectToServer(){
		
		try {

			mySocket = new Socket(address,3000);
			
			System.out.println("I am connected to the Server");
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	
	public static void main (String[] args){
		
		new PlayerSide();
		
	}
	
}
