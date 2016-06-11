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

	
	public static void main (String[] args){
		new PlayerSide();
	}
	
	public void printString(String string) { System.out.println(string); }
	
	private String nickname;
	
	private RMIGameSideInterface rmiGameSide;
	
	char typeOfConnection;
	
	private SocketConnection socketConnection;
	
	private RMIConnection rmiConnection;
	
	private Scanner inCLI;
	
	PlayMatch playMatch;
	
	private String message;
	
	private int playersMaxNumber;
	private Configurations config;
	
	public PlayerSide() {
		
		printString("I am alive");	
		
		inCLI = new Scanner(System.in);
		
		/*
		 * Method of the client
		 * It already controls the input, that can only be "s" or "r"
		 */
		chooseConnection();
		
		if(typeOfConnection=='s'){
			socketConnection = new SocketConnection(this);
		}else{
			rmiConnection = new RMIConnection(this);
		}
		
		communicateWithServer();
		
	}
	
	
	
	private void communicateWithServer(){
		
		if(typeOfConnection=='s'){
			/*
			 * If SOCKET is chosen
			 */
			while(true){
				message = receiveStringFS();
				switch(message){
				
				case	"SOCKETlogin":
					login();
					break;
				
				case	"SOCKETwaitForServer":
					waitForServer();
					break;
					
				case 	"SOCKETconfigure":
					configure();
					break;
					
				case	"SOCKETgetConfigurationsPlayersMaxNumber":
					sendStringTS(String.valueOf(getConfigurationsPlayersMaxNumber()));
					break;
					
				case	"SOCKETgetConfigurationsAsObject":
					sendObjectTS(getConfigurationsAsObject());
					break;
				
				case 	"SOCKETstartingMatch":
					startingMatch();
					break;
					
				case	"SOCKETplayMatch":
					playMatch();
					break;
					
				default	:
					System.out.println("error");
					break;
				}
			}
			
		}else{
			/*
			 * If RMI is chosen
			 */
			//rmiGameSide=rmiConnection.getRmiGameSide();
		
		}
	
	}
		
	
	private void chooseConnection(){
		boolean chosen = false;
		while(!chosen){
			printString("Choose connection: 's' (Socket) or 'r' (RMI)");
			String in = inCLI.nextLine();
			if(in.equals("s")||in.equals("r")){
				this.typeOfConnection=in.charAt(0);
				chosen=true;
			}
		}
	}
	
	/*
	 * Methods only used by the connection SOCKET
	 */
	// TS= To Server, FS= From Server
	protected void sendStringTS(String string){
		socketConnection.sendStringTS(string);
	}
		
	protected String receiveStringFS(){
		return socketConnection.receiveStringFS();
	}
	
	protected void sendObjectTS(Object object){
		socketConnection.sendObjectTS(object);
	}
	
	
	protected char getTypeOfConnection() {
		return typeOfConnection;
	}
	
	
	
	
	public void login(){
		boolean logged = false;
		String nickname = null;
		while(!logged){
			printString("Enter your nickname");
			nickname = inCLI.nextLine();
			if(typeOfConnection=='s'){
				sendStringTS(nickname);
				String received = receiveStringFS();
				logged = Boolean.valueOf(received);
			}else{
				try {
					boolean usedNickname = rmiGameSide.isNicknameInUse(nickname);
					logged = !usedNickname;
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			if(!logged){
				printString("Nickname already in use, enter another one");
			}
		}
		this.nickname=nickname;
		printString("Logged!");
	}
	
	public String getNickname(){
		return nickname;
	}
	
	
	public void waitForServer(){
		printString("Just wait a moment...");
	}
	
	public void configure(){
		printString("You are the first player, standard configurations will be used (FOR THE MOMENT)");
	}	
	
	public int getConfigurationsPlayersMaxNumber(){
		printString("(FOR THE MOMENT) the standard players number will be used");
		setStandardPlayersMaxNumber();
		return playersMaxNumber;
	}
	
	public Object getConfigurationsAsObject(){
		printString("(FOR THE MOMENT) the standard configurations will be used");
		setStandardConfigurations();
		return config;
	}
	
	public void startingMatch(){
		printString("You have been selected for a match, wait a moment...");
	}
	
	public void playMatch(){
		printString("Match started!");
		playMatch = new PlayMatch(this,rmiGameSide);
		playMatch.startPlayMatch();
	}
	
	public PlayMatch getPlayMatch() {
		return playMatch;
	}
	
	
	
	//Those two methods are not used for the moment
	public void setPlayersMaxNumber(int playersMaxNumber){ this.playersMaxNumber= playersMaxNumber; }
	public void setConfigurations(Configurations config){ this.config = config; }
		
	public void setStandardPlayersMaxNumber(){ this.playersMaxNumber= 4;}
		
	private void setStandardConfigurations(){
		
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {

			//This part permit to read the file of the standard configurations
			fileInputStream = new FileInputStream("./src/playerConfigurations/standardConfig");
	        objectInputStream = new ObjectInputStream(fileInputStream);
	        this.config = (Configurations) objectInputStream.readObject();
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	/*	
	standardConfig = true;
	if(standardConfig){
		setStandardPlayersNumber();
		setStandardConfigurations();
	}
	sendStringTS(""+playersMaxNumber);
	sendObjectTS(config);
	}*/
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
	//printString(/*"Too late,"*/"standard configurations will be used");
	/*standardConfig = true;
	if(standardConfig){
		setStandardPlayersNumber();
		setStandardConfigurations();
	}
	sendStringTS(""+playersMaxNumber);
	sendObjectTS(config);
	*/
	
}
