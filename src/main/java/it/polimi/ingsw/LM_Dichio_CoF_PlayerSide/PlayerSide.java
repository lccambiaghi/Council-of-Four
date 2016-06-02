package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;

import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;

public class PlayerSide {

	
	public static void main (String[] args){
		new PlayerSide();
	}
	
	
	
	private  Socket mySocket;
	private final static String ADDRESS = "localhost";
	private final static int SOCKET_PORT = 3000;
	
	private Scanner inCLI;
	
	private Scanner inSocket;
	private PrintWriter outSocket;
	
	String message;
	
	private int playersNumber;
	private Configurations config;
	
	public PlayerSide() {
		
		System.out.println("I am alive");	
		
		inCLI = new Scanner(System.in);
		
		connectToServer();
		
		communicateWithServer();
		
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
	
	private void communicateWithServer(){
		
		login();
		
		message = receiveStringFS();
		if(message.equals("config")){
			
			boolean standardConfig = false;
			/* 
			 * It starts a thread to manage the creation of configurations
			 * This way server says that the time is over the thread will stop
			 * The timer is not fixed to 20 seconds, this is a server's problem
			 * I have to implement another timer:
			 * when the second player arrives a new timer starts (for example 1 minute)
			 * so that the other players don't have to wait to much for a new match, whose config
			 * are created by the first player.
			 * If the player can't set them on time then he will send a standard configuration
			 */
			CreateConfigurations createConfigurations = new CreateConfigurations(this);
			Thread threadCreateConfigurations = new Thread(createConfigurations);
			threadCreateConfigurations.start();
			
			//Case: client can't make the config on time
			///// Here I need a method to stop the thread
			System.out.println("Too late, standard configurations will be used");
			standardConfig = true;
			
			if(standardConfig){
				setStandardConfigurations();
			}
			sendPlayerMaxNumberConfigurations();
			
		}else
			System.out.println(message);
		
		
		
	}
	
	// TS= To Server, FS= From Server
	private void sendStringTS(String string){ outSocket.println(string); outSocket.flush();}
	private String receiveStringFS(){ String string = inSocket.nextLine(); return string; }
	
	private void login(){
		boolean logged = false;
		while(!logged){
			System.out.println("Enter a valid nickname");
			String nickname = inCLI.nextLine();
			sendStringTS("login");
			sendStringTS(nickname);
			logged = Boolean.valueOf(receiveStringFS());
			if(!logged){
				System.out.println("Nickname already in use");
			}
		}	
	}
	
	public void setPlayersNumber(int playersNumber){ this.playersNumber= playersNumber; }
	public void setConfigurations(Configurations config){ this.config = config; }
		
		
		
		
	private void setStandardConfigurations(){
		
		FileInputStream fileInputStream = null;
		try {

			fileInputStream = new FileInputStream("./src/playerConfigurations/config");
	        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	        this.config = (Configurations) objectInputStream.readObject();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// close the stream
			try {
				fileInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	
	private void sendPlayerMaxNumberConfigurations(){
		
		ObjectOutputStream objectOutputStream = null;
		try {
			//sendStringTS("4");
			objectOutputStream = new ObjectOutputStream(mySocket.getOutputStream());
			objectOutputStream.writeObject(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
