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
			
			case	"SOCKETwaitForServer":
				playerSide.waitForServer();
				break;
				
			case 	"SOCKETconfigure":
				playerSide.configure();
				break;
				
			case	"SOCKETgetConfigurationsPlayersMaxNumber":
				socketConnection.sendStringTS(String.valueOf(playerSide.getConfigurationsPlayersMaxNumber()));
				break;
				
			case	"SOCKETgetConfigurationsAsObject":
				socketConnection.sendObjectTS(playerSide.getConfigurationsAsObject());
				break;
			
			case 	"SOCKETstartingMatch":
				playerSide.startingMatch();
				break;
				
			case	"SOCKETmatchStarted":
				playerSide.matchStarted();
				break;
			
			case	"SOCKETinputNumber":{
				int lowerBound = Integer.parseInt(socketConnection.receiveStringFS());
				int upperBound = Integer.parseInt(socketConnection.receiveStringFS());
				InputHandler.inputNumber(lowerBound, upperBound);
			}
			
			case	"SOCKETprint":
				System.out.print(socketConnection.receiveStringFS());
				break;
			
			case	"SOCKETprintln":
				System.out.println(socketConnection.receiveStringFS());
				break;
			
			case	"SOCKETwaitTurn":
				playerSide.waitTurn();
				break;
			
			case	"SOCKETplay":
				playerSide.play();
				break;
			
			default	:
				System.out.println("error");
				break;
			}
		}
	}
	
}
