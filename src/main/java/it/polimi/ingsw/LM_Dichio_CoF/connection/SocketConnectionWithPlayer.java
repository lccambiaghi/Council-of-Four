package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class SocketConnectionWithPlayer implements ConnectionWithPlayerInterface{
	
	GameSide gameSide;
	
	private Player player;
	private Socket playerSocket;
	private Scanner inputSocket;
	private PrintWriter outputSocket;
	
	private int intResult;
	
	private Object lock = new Object();

	public SocketConnectionWithPlayer(Socket clientSocket, GameSide gameSide){
		
		this.gameSide=gameSide;
		this.playerSocket=clientSocket;
		
		player = new Player('s');
		
		openSocketStream();
		
		Broker b = new Broker(this, player);
		player.setBroker(b); 
		
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
	
	private void sendString(String string) throws DisconnectedException{
		try{
			outputSocket.println(string);
			outputSocket.flush();
		}catch (NoSuchElementException e){
			disconnectionHandler();
		}
	}
	
	private String receiveString() throws DisconnectedException{ 
		String s = null;
		try{
			s = inputSocket.nextLine();
		}catch (NoSuchElementException e){
			disconnectionHandler();
		}
		return s;
	}
	
	public void login(GameSide gameSide) throws DisconnectedException{
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
			
	public int getPlayersMaxNumber() throws DisconnectedException{
		int playersMaxNumber=0;
		sendString("SOCKETgetConfigurationsPlayersMaxNumber");
		playersMaxNumber = Integer.parseInt(receiveString());
		return playersMaxNumber;
	}
			
	public Configurations getConfigurations() throws DisconnectedException{
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
	
	public void askInputNumber(int lowerBound, int upperBound) throws DisconnectedException{
		sendString("SOCKETinputNumber");
		sendString(String.valueOf(lowerBound));
		sendString(String.valueOf(upperBound));
		this.intResult = Integer.parseInt(receiveString());
		synchronized (lock) {
			lock.notify();
		}
	}
	
	public void stopInputNumber() throws DisconnectedException{
		sendString("SOCKETstopInputNumber");
	}
	
	public void print(String string) throws DisconnectedException{
		sendString("SOCKETprint");
		sendString(string);
	}
	
	public void println(String string) throws DisconnectedException{
		sendString("SOCKETprintln");
		sendString(string);
	}
	
	public int getIntResult() {
		return intResult;
	}
	
	public Object getLock(){
		return lock;
	}
	
	private void disconnectionHandler() throws DisconnectedException{
		closeSocketStream();
		player.setConnected(false);
		throw new DisconnectedException();
	}
	
	private void closeSocketStream(){
		inputSocket.close();
		outputSocket.close();
	}
	
	
	/*public boolean checkIfConnected(){
		print("");
		return player.isConnected();
	}*/
	
}