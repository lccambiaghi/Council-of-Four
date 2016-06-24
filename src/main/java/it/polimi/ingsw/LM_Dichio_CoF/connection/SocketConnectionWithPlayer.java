package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class SocketConnectionWithPlayer extends Thread implements ConnectionWithPlayerInterface{
	
	GameSide gameSide;
	
	private Player player;
	private Socket playerSocket;
	private Scanner inputSocket;
	private PrintWriter outputSocket;
	
	public SocketConnectionWithPlayer(Socket clientSocket, GameSide gameSide){
		
		this.gameSide=gameSide;
		this.playerSocket=clientSocket;
		
		player = new Player('s');
		
		openSocketStream();
		
		player.setConnectionWithPlayer(this);
		
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void openSocketStream(){
		try {
			outputSocket = new PrintWriter(playerSocket.getOutputStream());
			inputSocket = new Scanner(playerSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendString(String string){
		outputSocket.println(string);
		outputSocket.flush();
	}
	
	public String receiveString(){ 
		return inputSocket.nextLine();
	}
	
	public void login(GameSide gameSide){
		String nickname= null;
		sendString("SOCKETlogin");
		boolean logged = false;
		while(!logged){
			nickname = receiveString();
			if(!gameSide.isNicknameInUse(nickname)){
				player.setNickname(nickname);
				sendString("true");
				logged=true;
			}else{
				sendString("false");
			}
		}
	}
	
	public void askToConfigure(){
		sendString("SOCKETconfigure");
	}
			
	public int getPlayersMaxNumber(){
		int playersMaxNumber=0;
		sendString("SOCKETgetConfigurationsPlayersMaxNumber");
		playersMaxNumber = Integer.parseInt(receiveString());
		return playersMaxNumber;
	}
			
	public Configurations getConfigurations(){
		Configurations config = null;
		sendString("SOCKETgetConfigurationsAsObject");
		config = (Configurations)receiveObject();
		return config;
	}
	
	private Object receiveObject(){ 
		Object object = null;
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(playerSocket.getInputStream());
			object = objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public synchronized int askInputNumber(int lowerBound, int upperBound){
		int result = 0;
		sendString("SOCKETinputNumber");
		sendString(String.valueOf(lowerBound));
		sendString(String.valueOf(upperBound));
		result = Integer.parseInt(receiveString());
		return result;
	}
	
	public synchronized void print(String string){
		sendString("SOCKETprint");
		sendString(string);
	}
	
	public synchronized void println(String string){
		sendString("SOCKETprintln");
		sendString(string);
	}
	
	
}
