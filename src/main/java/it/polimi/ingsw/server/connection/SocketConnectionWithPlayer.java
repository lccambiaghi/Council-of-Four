package it.polimi.ingsw.server.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import it.polimi.ingsw.server.control.GameSide;
import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.DisconnectedException;

/**
 * This class implements ConnectionWithPlayerInterface and implements its methods.
 * 
 * It bases the communication with the client on Socket:
 * every method is called by the broker and then adapted to the socket connection sending
 * a specific flow of strings.
 */
public class SocketConnectionWithPlayer implements ConnectionWithPlayerInterface{
	
	GameSide gameSide;
	
	private Player player;
	private Socket playerSocket;
	private Scanner inputSocket;
	private PrintWriter outputSocket;
	
	private int intResult;
	
	private Object lock = new Object();

	/**
	 * Constructor of the class
	 * 
	 * It creates the Player, create a Broker and sets it as parameter of the Player.
	 * 
	 * It also calls the method "openSocketStream" to open the channels of communication
	 * 
	 * @param rmiPlayerSide : the remote object the class will communicate to
	 * @param gameSide
	 */
	public SocketConnectionWithPlayer(Socket clientSocket, GameSide gameSide){
		
		this.gameSide=gameSide;
		this.playerSocket=clientSocket;
		
		player = new Player();
		
		openSocketStream();
		
		Broker b = new Broker(this, player);
		player.setBroker(b); 
		
	}
	
	public Player getPlayer(){
		return player;
	}
	
	private void openSocketStream(){
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
	
	public boolean isCustomConfig() throws DisconnectedException{
		sendString("SOCKETisCustomConfig");
		return Boolean.valueOf(receiveString());
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
	
	/**
	 * It closes the socket stream with the client and launches a DisconnectedException
	 * 
	 * @throws DisconnectedException
	 */
	private void disconnectionHandler() throws DisconnectedException{
		closeSocketStream();
		throw new DisconnectedException();
	}
	
	private void closeSocketStream(){
		inputSocket.close();
		outputSocket.close();
	}
	
}