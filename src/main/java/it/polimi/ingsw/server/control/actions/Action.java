package it.polimi.ingsw.server.control.actions;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Match;

/**
 *
 */
public abstract class Action {

    protected Player player;
    protected Match match;
    protected String resultMsg;

    public abstract boolean preliminarySteps() throws InterruptedException;

    public abstract void execute();

    public abstract String getResultMsg();

}
