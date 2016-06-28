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

		
		/**
		 * This "while" permits to check every second if the timer
		 * to perform the action has expired
		 */
		while (System.currentTimeMillis() < endTime) {
		    try {
		         Thread.sleep(1000);  // Sleep 1 second
		         
		         // If the player has finished his turn before the timer expires
		         if(!turnHandler.isAlive())
		        	 break;
		    } catch (InterruptedException e) {}	
		}
		
		/**
		 * If the timer has expired and the player hasn't completed an action
		 */
		if(turnHandler.isAlive()){
			turnHandler.interrupt();
			player.setPlaying(false);
			//broadcastOthers(Message.playerHasBeenKickedOff(player), players, player);
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

		player.getBroker().println(chooseInfoOrAction_1_2());

		choice = player.getBroker().askInputNumber(1, 2);
		
		if(choice==1)
			infoMatch();
		else
			actionsLeftHandler();

	}
	
	private void infoMatch() throws InterruptedException{

		match.getInfoMatch().setPlayer(player);

		do{
			player.getBroker().println(Message.chooseInfo_0_6());

			choice = player.getBroker().askInputNumber(0, 6);
					
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
				player.getBroker().println(Message.chooseCity(arrayCity));
				match.getInfoMatch().printInfoCity(player, player.getBroker().askInputNumber(1, arrayCity.length)-1); //array positioning
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

		player.getBroker().println(Message.ifQuickAction());
		player.getBroker().println(Message.chooseYesOrNo_1_2());

		choice = player.getBroker().askInputNumber(1, 2);

		if(choice==1)
			quickAction();
		else
			if(player.getMainActionsLeft()!=0)
				mainAction();

	}
	
	
	private void quickAction() throws InterruptedException {

		player.getBroker().println(Message.chooseQuickAction_1_4());

		choice = player.getBroker().askInputNumber(1, 4);

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

		player.getBroker().println(Message.chooseMainAction_1_4());

		choice = player.getBroker().askInputNumber(1, 4);

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
			player.getBroker().println(string);
	}

	private void broadcastAll(String string, ArrayList<Player> players) throws InterruptedException{
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broadcast.printlnBroadcastAll(string, players);
	}

	private void broadcastOthers(String string, ArrayList<Player> players, Player playerNot) throws InterruptedException{
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			Broadcast.printlnBroadcastOthers(string, players, playerNot);
	}
	
}
