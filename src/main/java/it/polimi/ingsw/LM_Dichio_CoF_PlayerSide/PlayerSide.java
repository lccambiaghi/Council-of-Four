package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
		
	}
	
	// TS= To Server, FS= From Server
	private void sendStringTS(String string){ outSocket.println(string); outSocket.flush();}
	private String receiveStringFS(){ String string = inSocket.nextLine(); return string; }
	
	
	private void login(){
		boolean logged = false;
		while(!logged){
			System.out.println("Enter a valid nickname");
			String nickname = inCLI.nextLine();
			System.out.println("Enter a password");
			String password = inCLI.nextLine();
			sendStringTS("login");
			sendStringTS(nickname);
			sendStringTS(password);
			logged = Boolean.valueOf(receiveStringFS());
			if(!logged){
				System.out.println("Combination nickname/password not valid or nickname already in use");
			}
		}	
	}
	
		
		
		
		
		
		
		
	
		/* TO SEND THE CONFIGURATIONS 
		if(message.equals("configRequest")){
			
			//per ora così
			Configurations config = createConfigurations();
			
			ObjectOutputStream objectOutputStream = null;
			try {
				objectOutputStream = new ObjectOutputStream(mySocket.getOutputStream());
				objectOutputStream.writeObject(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}else{
			System.out.println(message);
		}
		*/
	
	
	private Configurations createConfigurations(){
		
		Configurations config = new Configurations();
		
		/*
		 * Do not change this parameter and the difficulty one until we haven't create 
		 * new maps for those combination missing
		 */
		config.setCitiesNumber(15);
		
		config.setPermitCardBonusNumberMin(2);
		config.setPermitCardBonusNumberMax(3);
		
		config.setNobilityBonusRandom(false);
		if(config.isNobilityBonusRandom()==false){
			config.setNobilityBonusNumber(7);
		}
		
		config.setCityLinksPreconfigured(false);
		if(config.isCityLinksPreconfigured()==false){
			int[][]cityLinksMatrix =  new int[][]{
				{0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	1,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0}
			};
			/*
    		 * This fir cycle is for making the matrix specular,
    		 * because in the txt file it is only upper triangular set
    		 */
			for(int i=0; i<config.getCitiesNumber(); i++){
				for(int j=i; j<config.getCitiesNumber();j++){
					cityLinksMatrix[j][i]=cityLinksMatrix[i][j];
				}
			}
			config.setCityLinksMatrix(cityLinksMatrix);
			
		}else{
			config.setDifficulty("n".charAt(0));
		}
		
		config.setCityBonusRandom(true);
		if(config.isCityBonusRandom()==false){
			//da implementare, ma essendo un esempio non ha senso farlo ora (l'array di mappe)
		}else{
			config.setCityBonusNumberMin(2);
			config.setCityBonusNumberMax(3);
		}
		
		return config;
	}
	
}
