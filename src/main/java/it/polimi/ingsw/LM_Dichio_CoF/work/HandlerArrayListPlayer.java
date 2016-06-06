package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class HandlerArrayListPlayer extends Thread {

	GameSide gameSide;
	HandlerArrayListPlayer instance;
	
	public HandlerArrayListPlayer(GameSide gameSide) {
		this.gameSide=gameSide;
	}
	
	public void run(){
		System.out.println("I am the handler of the arraylist of player, I start the match starter "
				+ "if there is at least an available player");
		Player player = gameSide.getFirstPlayer();
		player.sendString("config");
		int playersMaxNumber = getPlayersMaxNumberFromPlayer(player);
		setConfigurationsFromPlayer(player);
		new MatchStarter(gameSide, playersMaxNumber).start();
	}
	
	private int getPlayersMaxNumberFromPlayer(Player player){
		int playersMaxNumber;
		playersMaxNumber = Integer.parseInt(player.receiveString());
		return playersMaxNumber;
	}
	
	private void setConfigurationsFromPlayer(Player player){
		
		Object object = player.receiveObject();
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("./src/configurations/config");
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
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
