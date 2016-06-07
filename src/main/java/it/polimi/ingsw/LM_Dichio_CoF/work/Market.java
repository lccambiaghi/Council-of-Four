package it.polimi.ingsw.LM_Dichio_CoF.work;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.SellingObject;
import java.util.ArrayList;

public class Market {

	SellingObject sellingObject[];
	ArrayList <Player> arrayListPlayer= new ArrayList<>();
		
	Market (ArrayList <Player> arrayListPlayer){
		this.arrayListPlayer=arrayListPlayer;
	}
	
	private void startMarket (){
		int turn = 0;
		
		Player playerTurn=arrayListPlayer.get(turn);
		
		SellingObject object = new SellingObject(playerTurn);
		
	}
	
}
