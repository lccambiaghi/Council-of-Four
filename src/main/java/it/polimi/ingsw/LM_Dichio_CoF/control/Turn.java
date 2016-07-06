package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.control.actions.*;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;

import java.util.ArrayList;

import static it.polimi.ingsw.LM_Dichio_CoF.control.Message.chooseInfoOrAction_1_2;

public class Turn implements Runnable{

	private Player player;
	private Match match;
	private ArrayList<Player> players = new ArrayList<Player>();

	private int choice;
	
	public Turn(Match match, Player player, ArrayList<Player> players){
		this.match=match;
		this.player=player;
		this.players=players;
	}
	
	public void run(){
		
		// Draw a card
		player.addPoliticCard(new PoliticCard(Color.getRandomColor()));
					
		//Set actions 
		player.setMainActionsLeft(1);
		player.setQuickActionDone(false);
		
		try {
			infoOrAction();
		} catch (InterruptedException e) {
			System.out.println("The thread managing " + player.getNickname() + " has been successfully interrupted");
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
				player.getBroker().println(Message.chooseDestinationCity(arrayCity));
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
