package it.polimi.ingsw.client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

import it.polimi.ingsw.client.connection.RMIConnection;
import it.polimi.ingsw.client.connection.SocketConnection;
import it.polimi.ingsw.client.connection.SocketListener;
import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.MessageClient;

public class PlayerSide {
	
	private Object lock = new Object();
	
	private String nickname;
	
	private char typeOfConnection;
	
	private SocketConnection socketConnection;
	private RMIConnection rmiConnection;
	
	private Scanner in;
	private InputHandler inputHandler;
	
	private boolean freeScanner;
	private Thread threadScanner;

	private Configurations config;
	private boolean customConfig;
	
	public PlayerSide() {
		
		initializeScanner();
		
		chooseToCreateConfigurations();
		
		chooseConnection();
	
	}
	
	private void initializeScanner(){
		
		freeScanner=true;
		in = new Scanner(System.in);
		inputHandler = new InputHandler(this, in);
		threadScanner = new Thread(new ScannerHandler());
		threadScanner.start();
		
	}
	
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
	
	private void handleSocketConnection(){
		socketConnection = new SocketConnection(this);
		new SocketListener(this, socketConnection);
	}
	
	private void handleRMIConnection(){
		rmiConnection = new RMIConnection(this);
	}

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