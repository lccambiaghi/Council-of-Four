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
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Timer;

import it.polimi.ingsw.LM_Dichio_CoF.connection.RMIGameSideInterface;
import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF.work.GameSide;

public class PlayerSide {

	RMIGameSideInterface gameSide;
	
	public static void main (String[] args){
		new PlayerSide();
	}
	
	char typeOfConnection;
	
	SocketConnection socketConnection;
	
	RMIConnection rmiConnection;
	RMIPlayerSideInterface rmiPlayerSide;
	
	private Scanner inCLI;
	
	String message;
	
	private int playersMaxNumber;
	private Configurations config;
	
	public PlayerSide() {
		
		System.out.println("I am alive");	
		
		inCLI = new Scanner(System.in);
		
		/*
		 * Method of the client
		 * It already controls the input, that can only be "s" or "r"
		 */
		chooseConnection();
		
		if(typeOfConnection=='s'){
			//socketConnection = new SocketConnection(this);
		}else{
			rmiConnection = new RMIConnection(this);
			gameSide=rmiConnection.getGameSide();
			System.out.println("I got the RMI");
			
			try {
				rmiPlayerSide = new RMIPlayerSide();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		communicateWithServer();
		
	}
	
	
	
	private void communicateWithServer(){
		
		try {
			gameSide.connectToServer(rmiPlayerSide);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		login();
		
		while(true){
			message = receiveStringFS();
			switch(message){
				case 	"config":
					
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
					//CreateConfigurations createConfigurations = new CreateConfigurations(this);
					//Thread threadCreateConfigurations = new Thread(createConfigurations);
					//threadCreateConfigurations.start();
					
					//Case: client can't make the config on time
					///// Here I need a method to stop the thread
					System.out.println(/*"Too late,"*/" standard configurations will be used");
					standardConfig = true;
					if(standardConfig){
						setStandardPlayersNumber();
						setStandardConfigurations();
					}
					sendStringTS(""+playersMaxNumber);
					sendObjectTS(config);
					break;
				
				case 	"wait":
					System.out.println("Just wait a moment");
					break;
				
				case 	"startingMatch":
					System.out.println("You are waiting for a match to start");
					break;
					
				default	:
					System.out.println("error");
					break;
					
			}
		}
	}
	
	public void chooseConnection(){
		boolean chosen = false;
		while(!chosen){
			System.out.println("Choose connection: 's' (Socket) or 'r' (RMI)");
			String in = inCLI.nextLine();
			if(in.equals("s")||in.equals("r")){
				this.typeOfConnection=in.charAt(0);
				chosen=true;
			}
		}
	}
	
	
	// TS= To Server, FS= From Server
	private void sendStringTS(String string){
		if(typeOfConnection=='s'){
			socketConnection.sendStringTS(string);
		}
	}
	private String receiveStringFS(){
		if(typeOfConnection=='s'){
			return socketConnection.receiveStringFS();
		}
		else
			return "error";
	}
	
	private void sendObjectTS(Object object){
		if(typeOfConnection=='s'){
			socketConnection.sendObjectTS(object);;
		}
	}
	
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
	
	public void setPlayersNumber(int playersMaxNumber){ this.playersMaxNumber= playersMaxNumber; }
	public void setConfigurations(Configurations config){ this.config = config; }
		
	public void setStandardPlayersNumber(){ this.playersMaxNumber= 4;}
		
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
	
}
