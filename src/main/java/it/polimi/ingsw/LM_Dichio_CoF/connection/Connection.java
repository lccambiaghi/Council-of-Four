package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class Connection{
	
	private ServerSocket serverSocket;
	
	public Connection() throws IOException{
		
		serverSocket = new ServerSocket(Constant.SOCKET_PORT);
		while(true){
			Socket clientSocket = serverSocket.accept();
			new SocketConnectionWithClient(clientSocket);
			
		}
		
	
	}
	
	
}
