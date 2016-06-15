package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.model.Market;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;

import java.util.ArrayList;

public class ControlMatch {

	private final Market market;
	private Match match;
	private Player player;
	private final ArrayList<Player> players;
	private int choice;
	private static boolean gameOver;

	public ControlMatch(ArrayList<Player> arrayListPlayers){
		this.players =arrayListPlayers;
		match=Match.MatchFactory(arrayListPlayers);
		market=match.getMarket();
	}
	
	public void startMatch(){

		int turn=1;

		do {
			playerTurn(players.get(turn-1));

			if (turn % players.size() == 0)
				market.startMarket();

			turn++;
		}while(!gameOver);

	}

	public void playerTurn(Player player){
		
		this.player=player;
		
		Message.chooseInfoOrAction_1_2(player);
		choice = Broker.askInputNumber(1, 2, player);
		
		if(choice==1){
			infoMatch();
		}else if(choice==2){
			ifQuickAction();
		}
	}
	
	private void infoMatch(){
		match.getInfoMatch().setPlayer(player);
		do{
			Message.chooseInfo_0_6(player);
			choice = Broker.askInputNumber(0, 6, player);
					// PER I TEST InputHandler.inputNumber(0, 6);
					
			switch (choice) {
			case 1:
				match.getInfoMatch().printInfoPlayer(player);
				break;
			case 2:
				match.getInfoMatch().printInfoAllCities(player);
				break;
			case 3:
				match.getInfoMatch().printInfoAllPlayers(player);
				break;
			case 4:
				match.getInfoMatch().printInfoBalconies(player);
				break;
			case 5:
				match.getInfoMatch().printInfoRegions(player);
				break;
			case 6:
				cityFromIndex();
				break;
			}
		}
		while(choice!=0);
	}
	
	private void cityFromIndex(){
		int numCities = match.getField().getArrayCity().length;
		Message.chooseCityFromIndex(player);
		match.getInfoMatch().printCityAndIndex(player);
		choice = Broker.askInputNumber(1, numCities, player);
		match.getInfoMatch().printInfoCity(player, choice-1);
	}
	
	
	private void ifQuickAction(){
		Message.ifQuickAction(player);
		Message.chooseYesOrNo_1_2(player);
		choice = Broker.askInputNumber(1, 2, player);
		if(choice==1){
			quickAction();
		}else if(choice==2){
			mainAction();
		}
	}
	
	
	private void quickAction() { 
		Message.chooseQuickAction_1_4(player);
		choice = Broker.askInputNumber(1, 4, player);
		switch (choice) {
			case 1:
				//engageAssistant(player);
				break;
			case 2:
				//replacePermitCards(player);
				break;
			case 3:
				//electCouncillorWithAssistant(player);
				break;
			case 4:
				//performAdditionalMainAction(player);
				break;
		}
	}
	
	private void mainAction() {
		Message.chooseMainAction_1_4(player);
		choice = Broker.askInputNumber(1, 4, player);
		switch (choice) {
			case 1:
				//electCouncillor(playerTurn);
				break;
			case 2:
				//acquirePermitCard(playerTurn);
				break;
			case 3:
				//buildEmporiumWithPermitCard(playerTurn);
				break;
			case 4:
				//buildEmporiumWithKing(playerTurn);
				break;
		}
		player.setMainActionsLeft(player.getMainActionsLeft() - 1);
	}

	public static void setGameOver(){ gameOver=true;	}

}
