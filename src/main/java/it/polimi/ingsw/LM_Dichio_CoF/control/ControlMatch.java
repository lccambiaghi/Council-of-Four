package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.*;
import it.polimi.ingsw.LM_Dichio_CoF.model.Market;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;

import java.util.ArrayList;
import java.util.Collections;

import static it.polimi.ingsw.LM_Dichio_CoF.control.Message.chooseInfoOrAction_1_2;

public class ControlMatch {

	private final Market market;
	private Match match;
	private Player player;
	private final ArrayList<Player> players;
	private int choice;
	private boolean gameOver;

	public ControlMatch(ArrayList<Player> arrayListPlayer){

		Collections.shuffle(arrayListPlayer);
		
		this.players =arrayListPlayer;
		this.match=Match.MatchFactory(arrayListPlayer);
		this.market=match.getMarket();

	}

	public void startMatch(){

		for(Player p: players)
			p.setPlaying(true);
		
		int turn=1;

		do {
			
			player=players.get(turn-1);
			
			CountDown countDown = new CountDown(Constant.TIMER_SECONDS_TO_PERFORM_ACTION*1000);
			
			broadcastOthers("Turn of: " +player.getNickname(), players, player);
			println("It's your turn! You have "+Constant.TIMER_SECONDS_TO_PERFORM_ACTION+" seconds!", player);

			// Draw a card
			player.addPoliticCard(new PoliticCard());
			
			//Set actions 
			player.setMainActionsLeft(1);
			player.setQuickActionDone(false);
			
			infoOrAction();
			
			println("Your turn has ended!", player);
			
			//CHECK IF LAST EMPORIUM BUILT

			if (turn % players.size() == 0){
				broadcastAll("The market has started!", players);
				market.startMarket();
				turn=1;
			}
			else
				turn++;
			
		}while(!gameOver);

	}

	public void infoOrAction(){

		Broker.println(chooseInfoOrAction_1_2(),player);

		choice = Broker.askInputNumber(1, 2, player);
		
		if(choice==1)
			infoMatch();
		else
			actionsLeftHandler();

	}
	
	private void infoMatch(){

		match.getInfoMatch().setPlayer(player);

		do{
			Broker.println(Message.chooseInfo_0_6(), player);

			choice = Broker.askInputNumber(0, 6, player);
					
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
				City[] arrayCity = match.getField().getArrayCity();
				Broker.println(Message.chooseCity(arrayCity), player);
				match.getInfoMatch().printInfoCity(player, Broker.askInputNumber(1, arrayCity.length, player)-1); //array positioning
				break;
			}
		}
		while(choice!=0);

		infoOrAction();

	}

	private void actionsLeftHandler(){

		if(player.getMainActionsLeft()>0)
			if(!player.isQuickActionDone())
				ifQuickAction();
			else
				mainAction();
		else
			if(!player.isQuickActionDone())
				ifQuickAction();

	}
	
	private void ifQuickAction(){

		Broker.println(Message.ifQuickAction(),player);
		Broker.println(Message.chooseYesOrNo_1_2(), player);

		choice = Broker.askInputNumber(1, 2, player);

		if(choice==1)
			quickAction();
		else
			if(player.getMainActionsLeft()!=0)
				mainAction();

	}
	
	
	private void quickAction() {

		Broker.println(Message.chooseQuickAction_1_4(), player);

		choice = Broker.askInputNumber(1, 4, player);

		Action action;

		switch (choice) {
			case 1:
				action= new EngageAssistantQuickAction(match, player);
				break;
			case 2:
				action= new ChangePermitCardsQuickAction(match, player);
				break;
			case 3:
				action= new ElectCouncillorWithAssistantQuickAction(match,player);
				break;
			default:
				action= new PerformAdditionalMAQuickAction(match,player);
				break;
		}

		if (action.preliminarySteps()) {
			action.execute();
			player.setQuickActionDone(true);
			broadcastOthers(action.getResultMsg(), players, player);
		}
		
		actionsLeftHandler();
			
	}
	
	private void mainAction() {

		Broker.println(Message.chooseMainAction_1_4(), player);

		choice = Broker.askInputNumber(1, 4, player);

		Action action;

		switch (choice) {
			case 1:
				action = new ElectCouncillorMainAction(match, player);
				break;
			case 2:
				action = new AcquirePermitCardMainAction(match, player);
				break;
			case 3:
				action = new BuildEmporiumPermitCardMainAction(match, player);
				break;
			default:
				action = new BuildEmporiumWithKingMainAction(match, player);
				break;
		}
		
		if (action.preliminarySteps()) {
			action.execute();
			player.setMainActionsLeft(player.getMainActionsLeft() - 1);
			broadcastOthers(action.getResultMsg(), players, player);
		}

		actionsLeftHandler();
		
	}
	
	public void setGameOver(){ gameOver=true;	}

	//ONLY FOR TESTS
	private void println(String string, Player player){
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broker.println(string, player);
	}

	private void broadcastAll(String string, ArrayList<Player> players){
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broker.printlnBroadcastAll(string, players);
	}

	private void broadcastOthers(String string, ArrayList<Player> players, Player playerNot){
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broker.printlnBroadcastOthers(string, players, playerNot);
	}

}
