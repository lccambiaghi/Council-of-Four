package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.PlayerSide;

/**
 * The purpose of this class is to listen through socket connection and interpreting
 * the messages sent by the server, calling the corresponding methods of the playerSide
 */
public class SocketListener {

	private boolean inputStop = false;
	
	private String message;
	private final PlayerSide playerSide;
	private final SocketConnection socketConnection;
	
	/**
	 * The constructor of the class
	 * 
	 * @param playerSide : the class calling the constructor 
	 * @param socketConnection : the object that offers the communication methods
	 */
	public SocketListener(PlayerSide playerSide, SocketConnection socketConnection){
		
		this.socketConnection = socketConnection;
		this.playerSide=playerSide;
		
	}
		
	
	/**
	 * This method permits to start listening through socket.
	 * 
	 * It reads the message sent by the server calling "socketConnection.receiveStringFS"
	 * and switches it in the correct case
	 */
	public void startListening(){
		
		while(true){
			message = socketConnection.receiveStringFS();
			switch(message){
			
			case	"SOCKETlogin":
				playerSide.login();
				break;
				
			case	"SOCKETisCustomConfig":
				socketConnection.sendStringTS(String.valueOf(playerSide.isCustomConfig()));
				break;
				
			case	"SOCKETgetConfigurationsAsObject":
				socketConnection.sendObjectTS(playerSide.getConfigurationsAsObject());
				break;
			
			case	"SOCKETinputNumber":
				inputNumberRequest();
				break;
			
			case	"SOCKETstopInputNumber":
				inputNumberStop();
				break;
			
			case	"SOCKETprint":
				System.out.print(socketConnection.receiveStringFS());
				break;
			
			case	"SOCKETprintln":
				System.out.println(socketConnection.receiveStringFS());
				break;
				
			default	:
				System.out.println("Error receiving String through socket");
				break;
			}
		}
	}
	
	private void inputNumberRequest(){
		int lowerBound = Integer.parseInt(socketConnection.receiveStringFS());
		int upperBound = Integer.parseInt(socketConnection.receiveStringFS());
		new HandleInputNumber(lowerBound, upperBound).start();
	}
	
	private void inputNumberStop(){
		inputStop=true;
		playerSide.getInputHandler().stopInputNumber();
	}
	
	
	/**
	 * This class extends Thread and permits to wait until the correct input is returned by
	 * the method "inputNumber". 
	 * 
	 * Then it checks if the boolean "inputStop" is true and, if so, sets it to false and returns;
	 * otherwise it sends to the server the result of "inputNumber"
	 */
	class HandleInputNumber extends Thread{
		
		final int lowerBound;
		final int upperBound;
		
		public HandleInputNumber(int lowerBound,int upperBound){
			this.lowerBound=lowerBound;
			this.upperBound=upperBound;
		}
		
		@Override
		public void run(){
			int result = playerSide.getInputHandler().inputNumber(lowerBound, upperBound);
			if(!inputStop)
				socketConnection.sendStringTS(String.valueOf(result));
			else
				inputStop=false;
		}
	}
	
}
