package it.polimi.ingsw.client;

import java.rmi.RemoteException;
import java.util.Scanner;

import it.polimi.ingsw.client.connection.RMIConnection;
import it.polimi.ingsw.client.connection.SocketConnection;
import it.polimi.ingsw.client.connection.SocketListener;
import it.polimi.ingsw.server.connection.RMIGameSideInterface;
import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.MessageClient;

public class PlayerSide {

	
	public static void main (String[] args){
		new PlayerSide();
	}
	
	private String nickname;
	
	private char typeOfConnection;
	
	private SocketConnection socketConnection;
	
	private RMIConnection rmiConnection;
	
	private InputHandler inputHandler;

	private Configurations config;
	private boolean customConfig;
	
	public PlayerSide() {
		
		inputHandler = new InputHandler();
		
		chooseToCreateConfigurations();
		
		chooseConnection();
	
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
		boolean logged = false;
		String nickname = null;
		Scanner input = new Scanner(System.in);
		while(!logged){
			System.out.println(MessageClient.enterYourNickname());
			nickname = input.nextLine();
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
		this.nickname=nickname;
		System.out.println(MessageClient.loginSuccesfully());
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