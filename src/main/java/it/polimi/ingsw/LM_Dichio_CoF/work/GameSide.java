package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameSide {

	ArrayList <Client> arrayListClient;
	ServerSocket serverSocket;
	
	public GameSide() {
		
		arrayListClient = new ArrayList <Client>();
		
		try {
			serverSocket = new ServerSocket(Constant.SOCKET_PORT);
			while(true){
				Socket socket = serverSocket.accept();
				new SocketConnectionWithClient(socket, this);		
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void handleClient(Client client){
		
		arrayListClient.add(client);
	
		System.out.println("I am managing a player through a thread");
		
		
		if(arrayListClient.size()==1){
			askForConfigurations(client);
		}else{
			client.sendString("Sei arrivato tardi");
		}
			
	}
	
	private synchronized/*??*/ void askForConfigurations(Client client){
		
		client.sendString("configRequest");
		
		Object object = client.receiveObject();
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("./src/configurations/config");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();	
		}finally{
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
