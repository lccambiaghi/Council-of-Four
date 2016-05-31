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
	
	public static void main (String[] args){
		
		new PlayerSide();
		
	}	
	
	private void communicateWithServer(){
		
		while(true){
			
			String message=inSocket.nextLine();
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
			
		}
		
	}
	
	
	
	
	
	
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
	
	
	/*
	
	private void createFileConfigurations(){
		
		FileOutputStream fileOutputStream = null;
		
		try {
			
			//il salvataggio per ora è in locale, dovrà essere inviato al server quando ci sarà la connessione
			fileOutputStream = new FileOutputStream("./src/configurations/config");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			// write something in the file
			objectOutputStream.writeObject(config);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		// close the stream
			try {
				fileOutputStream.close();
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	}
	*/
}
