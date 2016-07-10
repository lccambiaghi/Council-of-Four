package it.polimi.ingsw.client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

import it.polimi.ingsw.client.connection.RMIConnection;
import it.polimi.ingsw.client.connection.SocketConnection;
import it.polimi.ingsw.client.connection.SocketListener;
import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.MessageClient;

/**
 * This class contains the main that launches the client.
 *	
 * initializeScanner: open the scanner and starts a thread that discard every input not asked
 * 
 * chooseToCreateConfigurations: the method asks the user whether he wants to create the configurations
 * or not; if yes it saves the created ones in the variable "config"
 *
 * chooseConnection: the method permits the user to choose the type of connection through which he wants
 * to connect to the server: Socket or RMI
 */
public class PlayerSide {
	
	private Object lock = new Object();
	
	private String nickname;
	
	private char typeOfConnection;
	
	private SocketConnection socketConnection;
	private SocketListener socketListener;
	private RMIConnection rmiConnection;
	
	private Scanner in;
	private InputHandler inputHandler;
	
	private boolean freeScanner;
	private Thread threadScanner;

	private Configurations config;
	private boolean customConfig;
	
	/**
	 * Constructor: it starts to communicate with the user and then with the server
	 */
	public PlayerSide() {
		
		initializeScanner();
		
		chooseToCreateConfigurations();
		
		chooseConnection();
	
	}
	
	/**
	 * Initialize the scanner that starts reading from "System.in".
	 * It also creates the object InputHandler and the thread that discards every input not asked
	 */
	private void initializeScanner(){
		
		freeScanner=true;
		in = new Scanner(System.in);
		inputHandler = new InputHandler(this, in);
		threadScanner = new Thread(new ScannerHandler());
		threadScanner.start();
		
	}
	
	/**
	 * The method asks through "inputNumber" the type of connection wanted by the user and calls
	 * the corresponding method that handle the type of connection
	 */
	private void chooseConnection(){
		
		System.out.println(MessageClient.chooseConnection_1_2());
		int choice = inputHandler.inputNumber(1, 2);
		
		if(choice==1){
			this.typeOfConnection='s';
			handleSocketConnection();
		}else{
			this.typeOfConnection='r';
			handleRMIConnection();
		}
	}
	
	/**
	 * The method assign to "socketConnection" a new SocketConnection and starts listening through
	 * socket with SocketListener
	 */
	private void handleSocketConnection(){
		socketConnection = new SocketConnection(this);
		socketListener = new SocketListener(this, socketConnection);
		socketListener.startListening();
	}
	
	private void handleRMIConnection(){
		rmiConnection = new RMIConnection(this);
	}

	
	/**
	 * The method asks the users if he wants to create configurations.
	 * 
	 * If so it creates an object "CreateConfigurations", it starts it, it waits until the configurations
	 * are completed, it saves them in the variable "config" and sets the boolean "customConfig" to true
	 * If not it simply sets the "customConfig" to false
	 */
	private void chooseToCreateConfigurations(){
		
		System.out.println(MessageClient.chooseToCreateConfigurations_1_2());
		int choice = inputHandler.inputNumber(1, 2);
		
		if(choice==1){
			CreateConfigurations cc = new CreateConfigurations(this, inputHandler);
			cc.startCreating();
			config = cc.getConfigurations();
			customConfig = true;
		}else{
			customConfig = false;
		}
		
	}
	
	/**
	 * The method is called remotely and permits the user to set his nickname and login to the server.
	 * 
	 * First it sets the boolean "freeScanner" to false, then, depending on the type of connection,
	 * it starts interacting with the server until the users insert a valid nickname.
	 * When it's done the method releases the scanner and returns.
	 */
	public void login(){
		
		setFreeScanner(false);
		
		boolean logged = false;
		while(!logged){
			System.out.println(MessageClient.enterYourNickname());
			nickname = in.nextLine();
			if(typeOfConnection=='s'){
				socketConnection.sendStringTS(nickname);
				String received = socketConnection.receiveStringFS();
				logged = Boolean.valueOf(received);
			}else{
				try {
					boolean usedNickname = rmiConnection.getRmiGameSide().isNicknameInUse(nickname);
					logged = !usedNickname;
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			if(!logged){
				System.out.println(MessageClient.nicknameAlreadyInUse());
			}
		}
		System.out.println(MessageClient.loginSuccesfully());
		
		setFreeScanner(true);
		
	}
	
	public String getNickname(){
		return nickname;
	}
	
	public Object getConfigurationsAsObject(){
		return config;
	}
	
	public InputHandler getInputHandler(){
		return inputHandler;
	}
	
	public boolean isCustomConfig(){
		return customConfig;
	}
	
	/**
	 * This class permits to lock the scanner and discard every input NOT requested of the user.
	 * It implements "Runnable" and overrides the method "run".
	 *
	 * If the scanner is free the method check if it there is input in the stream "System.in"
	 * through the method "System.in.available".
	 * It uses a "lock" that permits to control the stream in a Thread-safe way.
	 */
	class ScannerHandler implements Runnable{
		
		@Override
		public void run() {
			boolean go = true;
			while(go){
				while(isFreeScanner()){
					synchronized (lock) {
						try {
							if(System.in.available()>0)
								in.nextLine();
						} catch (IOException e) {
							go=false;
						}
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setFreeScanner(boolean freeScanner){
		synchronized (lock) {
			this.freeScanner=freeScanner;
		}
	}
	
	public boolean isFreeScanner(){
		synchronized (lock) {
			return freeScanner;
		}
	}
	
	/**
	 * The main launches the playerSide
	 * @param args
	 */
	public static void main (String[] args){
		new PlayerSide();
	}
	
}