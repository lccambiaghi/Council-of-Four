package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

public class SocketListener {

	String message;
	PlayerSide playerSide;
	SocketConnection socketConnection;
	boolean inputStop;
	
	public SocketListener(PlayerSide playerSide, SocketConnection socketConnection){
		
		this.socketConnection = socketConnection;
		this.playerSide=playerSide;
		this.inputStop=false;
		
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
				int result = playerSide.getInputHandler().inputNumber(lowerBound, upperBound);
				if(!inputStop)
					socketConnection.sendStringTS(String.valueOf(result));
				else
					inputStop=false;
				break;
			}
			
			case	"SOCKETstopInputNumber":
				System.out.println("Someone told me to stop");
				inputStop=true;
				playerSide.getInputHandler().stopInputNumber();
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
	
}
