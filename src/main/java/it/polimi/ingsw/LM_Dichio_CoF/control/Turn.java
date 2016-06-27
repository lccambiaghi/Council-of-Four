package it.polimi.ingsw.LM_Dichio_CoF.control;

import static it.polimi.ingsw.LM_Dichio_CoF.control.Message.chooseInfoOrAction_1_2;

import java.util.ArrayList;
import java.util.Timer;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.CountDown.TimeIsFinished;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.AcquirePermitCardMainAction;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.Action;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.BuildEmporiumPermitCardMainAction;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.BuildEmporiumWithKingMainAction;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.ChangePermitCardsQuickAction;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.ElectCouncillorMainAction;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.ElectCouncillorWithAssistantQuickAction;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.EngageAssistantQuickAction;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.PerformAdditionalMAQuickAction;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;

public class Turn {

	private Timer timer;
	private Player player;
	private Match match;
	private ArrayList<Player> players = new ArrayList<Player>();
	
	private TurnHandler turnHandler;
	
	private long startTime;
	private long endTime;
	
	private int choice;
	
	public Turn(Match match, Player player, ArrayList<Player> players){
		this.match=match;
		this.player=player;
		this.players=players;
		
		turnHandler = new TurnHandler();
	}
	
	public void startTurn() throws InterruptedException{
		
		/*
		CountDown countDown = new CountDown(Constant.TIMER_SECONDS_TO_PERFORM_ACTION*1000);
		println("It's your turn! You have "+Constant.TIMER_SECONDS_TO_PERFORM_ACTION+" seconds!", player);
		println("Your turn has ended!", player);
		*/
		
		broadcastOthers(Message.turnOf(player), players, player);
		println(Message.yourTurn(Constant.TIMER_SECONDS_TO_PERFORM_ACTION), player);
		
		// Draw a card
		player.addPoliticCard(new PoliticCard());
					
		//Set actions 
		player.setMainActionsLeft(1);
		player.setQuickActionDone(false);
		
		
		startTime = System.currentTimeMillis();
		endTime = startTime + (Constant.TIMER_SECONDS_TO_PERFORM_ACTION+20)*1000;
		
		turnHandler.start(); 

		while (System.currentTimeMillis() < endTime) {
		    try {
		         Thread.sleep(1000);  // Sleep 1 second
		         if(!turnHandler.isAlive())
		        	 break;
		    } catch (InterruptedException e) {}	
		}
		
		if(turnHandler.isAlive()){
			turnHandler.interrupt();
			player.setPlaying(false);
			broadcastOthers(Message.playerHasBeenKickedOff(player), players, player);
		}
		
	}
	
	class TurnHandler extends Thread{
	
		public void run(){
			try{
				infoOrAction();
			} catch (InterruptedException e) {
				System.out.println("The thread was successfully interrupted");
			}
		}
	}
	
		
	public void infoOrAction() throws InterruptedException{

		Broker.println(chooseInfoOrAction_1_2(),player);

		choice = Broker.askInputNumber(1, 2, player);
		
		if(choice==1)
			infoMatch();
		else
			actionsLeftHandler();

	}
	
	private void infoMatch() throws InterruptedException{

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

	private void actionsLeftHandler() throws InterruptedException{

		if(player.getMainActionsLeft()>0)
			if(!player.isQuickActionDone())
				ifQuickAction();
			else
				mainAction();
		else
			if(!player.isQuickActionDone())
				ifQuickAction();

	}
	
	private void ifQuickAction() throws InterruptedException{

		Broker.println(Message.ifQuickAction(),player);
		Broker.println(Message.chooseYesOrNo_1_2(), player);

		choice = Broker.askInputNumber(1, 2, player);

		if(choice==1)
			quickAction();
		else
			if(player.getMainActionsLeft()!=0)
				mainAction();

	}
	
	
	private void quickAction() throws InterruptedException {

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
	
	private void mainAction() throws InterruptedException {

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
	
	//ONLY FOR TESTS
	private void println(String string, Player player) throws InterruptedException{
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broker.println(string, player);
	}

	private void broadcastAll(String string, ArrayList<Player> players) throws InterruptedException{
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broker.printlnBroadcastAll(string, players);
	}

	private void broadcastOthers(String string, ArrayList<Player> players, Player playerNot) throws InterruptedException{
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broker.printlnBroadcastOthers(string, players, playerNot);
	}
	
}
