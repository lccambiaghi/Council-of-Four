package it.polimi.ingsw.server.control.actions;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Match;

/**
 * An action is characterized by the player who tries to execute it,
 * the match in which it is trying to be executed
 * and a result message that reports what has changed
 *
 * Every action - main, quick or market one, extends this abstract class and adds the specific variables
 * which the move needs.
 *
 * First preliminarySteps are executed. While every step is completed succesfully,
 * private variables of the action are set interacting with the user.
 * If a precondition is not valid, the method returns false and execute never gets called.
 *
 * Execute actually modifies the Model according to the choices of the user in preliminarySteps
 */
public abstract class Action {

    protected Player player;

    protected Match match;

    protected String resultMsg;

    /**
     * @return true if all preconditions are met, false if player is not eligible to perform the move
     * @throws InterruptedException
     */
    public abstract boolean preliminarySteps() throws InterruptedException;

    /**
     * The method applies to the Model what has been set in preliminarySteps
     */
    public abstract void execute();

    public abstract String getResultMsg();

}
