package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIPlayerSide extends UnicastRemoteObject implements RMIPlayerSideInterface {

	public RMIPlayerSide() throws RemoteException {}

	public void sendString(String string){
		System.out.println(string);
	}
	
	public String receiveString (){
		
		Scanner inCLI = new Scanner(System.in);
		//Mi sa che qui ci vuole un controllo......
		String string = inCLI.nextLine();
		return string;
	}
	
}
