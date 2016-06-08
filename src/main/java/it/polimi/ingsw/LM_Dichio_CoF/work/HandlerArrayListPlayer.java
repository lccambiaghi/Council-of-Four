package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

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
		
		if(player.getTypeOfConnection()=='s'){
			player.sendString("SOCKETconfigure");
			player.sendString("SOCKETgetConfigurationsPlayersMaxNumber");
			playersMaxNumber = getPlayersMaxNumberFromPlayer(player);
			player.sendString("SOCKETgetConfigurationsAsObject");
			config = getConfigurationsFromPlayer(player);
		}else{
			try {
				player.getRmiPlayerSide().configure();
				playersMaxNumber = player.getRmiPlayerSide().getConfigurationsPlayersNumber();
				config = (Configurations)player.getRmiPlayerSide().getConfigurationsAsObject();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		saveFileConfigurations(config);
		
		new MatchStarter(gameSide, playersMaxNumber).start();
	}
	
	private int getPlayersMaxNumberFromPlayer(Player player){
		int playersMaxNumber;
		playersMaxNumber = Integer.parseInt(player.receiveString());
		return playersMaxNumber;
	}
	
	private Configurations getConfigurationsFromPlayer(Player player){
		return (Configurations)player.receiveObject();
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
