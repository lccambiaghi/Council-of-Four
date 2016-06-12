package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

import it.polimi.ingsw.LM_Dichio_CoF.connection.BrokerGameSide;

public class HandlerArrayListPlayer extends Thread {

	GameSide gameSide;
	HandlerArrayListPlayer instance;
	
	public HandlerArrayListPlayer(GameSide gameSide) {
		this.gameSide=gameSide;
	}
	
	public void run(){
		
		int playersMaxNumber = 0;
		Configurations config = null;
		
		System.out.println("I am the handler of the arraylist of player, I start the match starter "
				+ "if there is at least an available player");
		
		Player player = gameSide.getFirstPlayer();
		
		BrokerGameSide.askToConfigure(player);
		playersMaxNumber = BrokerGameSide.getPlayersMaxNumber(player);
		config = BrokerGameSide.getConfigurations(player);
		
		saveFileConfigurations(config);
		
		new MatchStarter(gameSide, playersMaxNumber).start();
	}
		
	private void saveFileConfigurations(Configurations config){
		
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("./src/configurations/config");
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(config);
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
