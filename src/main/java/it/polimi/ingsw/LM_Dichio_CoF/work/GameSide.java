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
		
		System.out.println("I am managing a player through a thread");
		
		login(client);
		
		System.out.println("The client has successfully connected with nickname: "+client.getNickname());
		
		arrayListClient.add(client);
		
		
		if(arrayListClient.size()==1){
			client.sendString("config");
			setConfigurations(client);
		}else{
			client.sendString("wait");
		}
			
	}
	
	private void login(Client client){
		
		boolean logged = false;
		while(!logged){
			String message = client.receiveString();
			if(message.equals("login")){
				String nickname = client.receiveString();
				if(isNicknameInUse(nickname)){
					client.sendString("false");
				}else{
					client.setNickname(nickname);
					client.sendString("true");
					logged=true;
				}
			}
		}
		
	}
	
	private boolean isNicknameInUse(String nickname){
		for(Client client: arrayListClient){
			if(client.getNickname().equals(nickname))
				return true;
		}
		return false;
	}
	
	
	
	
	private synchronized/*??*/ void setConfigurations(Client client){
		
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
