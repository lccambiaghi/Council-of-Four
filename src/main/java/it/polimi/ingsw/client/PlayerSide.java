package it.polimi.ingsw.client;

import java.rmi.RemoteException;
import java.util.Scanner;

import it.polimi.ingsw.server.connection.RMIGameSideInterface;
import it.polimi.ingsw.server.model.Configurations;

public class PlayerSide {

	
	public static void main (String[] args){
		new PlayerSide();
	}
	
	private String nickname;
	
	private RMIGameSideInterface rmiGameSide;
	
	private char typeOfConnection;
	
	private SocketConnection socketConnection;
	
	private RMIConnection rmiConnection;
	
	private InputHandler inputHandler;

	private SocketListener socketListener;

	private Configurations config;
	private boolean customConfig;
	
	public PlayerSide() {
		
		System.out.println("I am alive");	
		
		inputHandler = new InputHandler();
		
		/*
		 * Method of the client
		 * It already controls the input, that can only be "s" or "r"
		 */
		
		chooseToCreateConfigurations();
		
		chooseConnection();
		
		if(typeOfConnection=='s'){
			socketConnection = new SocketConnection(this);
			socketListener = new SocketListener(this, socketConnection);
		}else{
			rmiConnection = new RMIConnection(this);
		}
	
	}
		
	
	private void chooseConnection(){
		System.out.println("Choose connection:\n"
				+ "1. Socket\n"
				+ "2. RMI");
		int choice = inputHandler.inputNumber(1, 2);
		if(choice==1)
			this.typeOfConnection='s';
		else
			this.typeOfConnection='r';
	}

	private void chooseToCreateConfigurations(){
		
		System.out.println("Do you want to create your own configurations?\n"
				+ "1. Yes\n"
				+ "2. No");
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
		boolean logged = false;
		String nickname = null;
		Scanner inCLI = new Scanner(System.in);
		while(!logged){
			System.out.println("Enter your nickname");
			nickname = inCLI.nextLine();
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
				System.out.println("Nickname already in use, enter another one");
			}
		}
		this.nickname=nickname;
		System.out.println("Logged!");
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
	
}