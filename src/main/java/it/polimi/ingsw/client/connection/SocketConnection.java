package it.polimi.ingsw.client.connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import it.polimi.ingsw.utils.Constant;

/**
 * This class permits to connect to the server through socket.
 * In fact the method "connectToServer" opens the channel of communication with it.
 * 
 * It offers methods to send strings/objects and receive strings.
 */
public class SocketConnection {

	private Socket mySocket;
	private Scanner inSocket;
	private PrintWriter outSocket;
	
	/**
	 * Constructor of the class.
	 *
	 */
	public SocketConnection(){
	}
	
	/**
	 * The method instantiates the variable "mySocket" that is used to link to the server.
	 * Then it opens the channel of communication, creating a Scanner for the out-coming strings
	 * and a printWriter for the in-coming ones.
	 * 
	 * It catches Input/Output exceptions due to lack of connection
	 */
	public void connectToServer(){
		
		try {
			mySocket = new Socket(Constant.SOCKET_ADDRESS, Constant.SOCKET_PORT);
		} catch (IOException e) {
			System.out.println("Cannot connect to the Server");
			e.printStackTrace();
		}
		
		try {
			inSocket = new Scanner(mySocket.getInputStream());
			outSocket = new PrintWriter(mySocket.getOutputStream());
			System.out.println("I am connected to the Server");
		} catch (IOException e) {
			System.out.println("Cannot open channels of communication");
			e.printStackTrace();
		}
	
	}	
	
	/**
	 * Sends a String to the server
	 * @param string to be sent
	 */
	public void sendStringTS(String string){
		outSocket.println(string);
		outSocket.flush();
	}
	
	/**
	 * Receives a String from the Server
	 * @return the string received
	 */
	public String receiveStringFS(){
		return inSocket.nextLine(); 
	}
	
	/**
	 * Send an object to the server
	 * @param object to be sent
	 */
	public void sendObjectTS(Object object){
		ObjectOutputStream objectOutputStream;
		try {
			objectOutputStream = new ObjectOutputStream(mySocket.getOutputStream());
			objectOutputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
