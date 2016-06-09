package it.polimi.ingsw.LM_Dichio_CoF.work;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.Bonus;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Field;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.PermitCard;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Route;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.SellingObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class Market {

	SellingObject sellingObject;
	ArrayList <Player> arrayListPlayer= new ArrayList<>();
	Map <SellingObject, Player> mapSellingObjects = new HashMap<>()	;
	Field field;
	
	Market (ArrayList <Player> arrayListPlayer){
		this.arrayListPlayer=arrayListPlayer;
		
	}
	
	public void startMarket (){
		int turn = 0;
		
		Player playerTurn=arrayListPlayer.get(turn);
		
		SellingObject sellingObject = new SellingObject(playerTurn);
		mapSellingObjects.put(sellingObject, playerTurn);
		
		startBuying(mapSellingObjects);
		
	}
	private void startBuying(Map <SellingObject, Player> mapSellingObjects){
		Collections.shuffle(arrayListPlayer);
		int turn=0;
		
		Player playerTurn=arrayListPlayer.get(turn);
		
		for (Entry<SellingObject, Player> sellingOBjectPlayerEntry : mapSellingObjects.entrySet()) {
			Entry entry = (Entry) sellingOBjectPlayerEntry;
			SellingObject sellingObject = (SellingObject) entry.getKey();
			Player player = (Player) entry.getValue();
			
			int accordingToBuy = wantBuy(1,2);
			String name;
			
			if(accordingToBuy==1){	
				Object object = sellingObject.getObject();
				name=sellingObject.getObject().getClass().toString();
				switch (name){
					case "PermitCard": 
						playerTurn.getArrayListPermitCard().add((PermitCard)object);
						break;
					case "PoliticCard": 
						playerTurn.getArrayListPoliticCard().add((PoliticCard)object);
						break;
					case "Assistants":
						playerTurn.addAssistant((int)object);
						break;
				}
				int price = sellingObject.getPrice();
				Route richnessRoute = field.getRichnessRoute();
				if(checkIfEnoughRichness(playerTurn, price)){
					richnessRoute.movePlayer(price, player);
					richnessRoute.movePlayer(-price, playerTurn);
					mapSellingObjects.remove(sellingObject);
				}
			}
		}
		
	}
	
	private boolean checkIfEnoughRichness(Player playerTurn, int payed) {
		Route richnessRoute = field.getRichnessRoute();

		if (richnessRoute.getPosition(playerTurn)<payed)
			return false;
		else
			return true;

	}
	
	private int wantBuy (int lowerBound, int upperBound){ //TODO throws RemoteException + spostare nella classe della CLI

			Scanner in = new Scanner(System.in);
			int number;

			do {
				while(!in.hasNextInt()){
					System.out.println("Insert a valid input!");
					in.nextInt();
				}
				number=in.nextInt();
				in.nextLine();
			} while(number<lowerBound && number>upperBound);
			//in.close();

			return number;

		}
	
	

}
