package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.control.actions.*;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.PoliticCard;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.server.model.City;

import java.util.ArrayList;

/**
 * This class is designed to handle the turn of the player.
 *
 * It implements Runnable because the whole turn of the player has is handled by a Thread.
 * The Player has a specific amount of time to complete his turn: if this timer is over before that
 * the player complete his turn the Thread is interrupted by outside and the Turn finishes.
 *
 * run : this method add a politic card to the player, set the parameters of the turn and starts
 * asking the player the actions he wants to do
 *
 * infoOrAction : asks the player if he wants to have the info of the match or perform an action
 *
 * infoMatch : asks the player which info of the match he wants to have
 *
 * actionsLeftHandler : handles the actions the player can do, checking both main action and quick action
 * he can still do
 *
 * ifQuickAction : asks the player if he wants to perform a quick action
 *
 * quickAction : asks the player which quick action he wants to do
 *
 * mainAction : asks the player which main action he wants to do
 *
 */
public class Turn implements Runnable{

	private Player player;
	private Match match;
	private ArrayList<Player> players = new ArrayList<>();

	private int choice;
	
	/**
	 * Constructor that creates the Turn
	 *
	 * @param match : the match where the player is playing
	 * @param player : the player of the turn
	 * @param players : the other players of the match
	 */
	public Turn(Match match, Player player, ArrayList<Player> players){
		this.match=match;
		this.player=player;
		this.players=players;
	}
	
	@Override
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
		
	/**
	 * This method asks the player if he wants to have the info of the match or perform a match
	 * Depending on the choice it calls "infoMatch" or "actionsLeftHandler"
	 *
	 * @throws InterruptedException if the timer is over
	 */
	private void infoOrAction() throws InterruptedException{

		player.getBroker().println(Message.chooseInfoOrAction_1_2());

		choice = player.getBroker().askInputNumber(1, 2);
		
		if(choice==1)
			infoMatch();
		else
			actionsLeftHandler();

	}
	
	/**
	 * This method shows the player the info of the match that he can have.
	 * Depending on the choice it calls different methods of InfoMatch.
	 *
	 * If the choice is "0" it returns to the caller.
	 *
	 * @throws InterruptedException if the timer is over
	 */
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
				match.getInfoMatch().printInfoAllCities();
				break;
			case 3:
				match.getInfoMatch().printInfoAllPlayers();
				break;
			case 4:
				match.getInfoMatch().printInfoBalconies();
				break;
			case 5:
				match.getInfoMatch().printInfoRegions();
				break;
			default: // case 6
				City[] arrayCity = match.getField().getArrayCity();
				player.getBroker().println(Message.chooseDestinationCity(arrayCity));
				match.getInfoMatch().printInfoCity(player.getBroker().askInputNumber(1, arrayCity.length)-1); //array positioning
				break;
			}
		}
		while(choice!=0);

		infoOrAction();

	}

	/**
	 * This method handles the actions left to do by the player calling the corresponding method
	 * "ifQuickAction" or "mainAction".
	 *
	 * If the player has completed all the actions it simply returns to the caller.
	 *
	 * @throws InterruptedException if the timer is over
	 */
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
	
	/**
	 * This methods asks the player if he wants to perform a quick action (because it is not compulsory).
	 * If yes it calls quickAction, else it checks the main actions left of the player and then it calls
	 * "mainAction" or simply return.
	 *
	 * @throws InterruptedException if the timer is over
	 */
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
	
	/**
	 * This method asks the player which quick action he wants to do and create the object of the corresponding Action
	 *
	 * Then it calls "action.preliminarySteps".
	 * If the method returns true it means that the player have enough goods to perform the action,
	 * it executes the action and broadcasts the result message to the other players.
	 *
	 * In any case it ends calling "actionLeftHandler"
	 *
	 * @throws InterruptedException if the timer is over
	 */
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
			Broadcast.printlnBroadcastOthers(action.getResultMsg(), players, player);
		}
		
		actionsLeftHandler();
			
	}
	
	/**
	 * This method asks the player which main action he wants to do and create the object of the corresponding Action
	 *
	 * Then it calls "action.preliminarySteps".
	 * If the method returns true it means that the player have enough goods to perform the action,
	 * it executes the action and broadcasts the result message to the other players.
	 *
	 * In any case it ends calling "actionLeftHandler"
	 *
	 * @throws InterruptedException if the timer is over
	 */
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
			player.decrementMainActionLeft(1);
			Broadcast.printlnBroadcastOthers(action.getResultMsg(), players, player);
		}

		actionsLeftHandler();
		
	}

	private void broadcastOthers(String string, ArrayList<Player> players, Player playerNot) throws InterruptedException{
			Broadcast.printlnBroadcastOthers(string, players, playerNot);
	}
	
}
