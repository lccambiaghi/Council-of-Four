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
import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

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
	
	public PlayerSide() {
		
		System.out.println("I am alive");	
		
		inputHandler = new InputHandler();
		
		/*
		 * Method of the client
		 * It already controls the input, that can only be "s" or "r"
		 */
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

	protected char getTypeOfConnection() {
		return typeOfConnection;
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
		
		CreateConfigurations cc = new CreateConfigurations(this, inputHandler);
		
		config = cc.getCustomConfig();
		
		//System.out.println("(FOR THE MOMENT) the standard configurations will be used");
		//setStandardConfigurations();
		return config;
	}
	
	public InputHandler getInputHandler(){
		return inputHandler;
	}
	
	//DEPRECATED
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
	
}