package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

public class SocketListener {

	String message;
	PlayerSide playerSide;
	SocketConnection socketConnection;
	
	public SocketListener(PlayerSide playerSide, SocketConnection socketConnection){
		
		this.socketConnection = socketConnection;
		this.playerSide=playerSide;
		
		while(true){
			message = socketConnection.receiveStringFS();
			switch(message){
			
			case	"SOCKETlogin":
				playerSide.login();
				break;
				
			case	"SOCKETgetConfigurationsAsObject":
				socketConnection.sendObjectTS(playerSide.getConfigurationsAsObject());
				break;
			
			case	"SOCKETinputNumber":{
				int lowerBound = Integer.parseInt(socketConnection.receiveStringFS());
				int upperBound = Integer.parseInt(socketConnection.receiveStringFS());
				int result = InputHandler.inputNumber(lowerBound, upperBound);
				socketConnection.sendStringTS(String.valueOf(result));
				break;
			}
			
			//case	"SOCKETstopInputNumber":
			//	break;
			
			case	"SOCKETprint":
				System.out.print(socketConnection.receiveStringFS());
				break;
			
			case	"SOCKETprintln":
				System.out.println(socketConnection.receiveStringFS());
				break;
				
			default	:
				System.out.println("error");
				break;
			}
		}
	}
	
}
