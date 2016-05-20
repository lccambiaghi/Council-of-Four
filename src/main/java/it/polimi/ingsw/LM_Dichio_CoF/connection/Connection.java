package it.polimi.ingsw.LM_Dichio_CoF.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class Connection{
	
	private ServerSocket serverSocket;
	
	public Connection() throws IOException{
		
		serverSocket = new ServerSocket(Constant.PORT_SOCKET); 
		while(true){
			Socket clientSocket = serverSocket.accept();
			new SocketConnectionWithClient(clientSocket);
			
		}
		
	
	}
	
	
}
