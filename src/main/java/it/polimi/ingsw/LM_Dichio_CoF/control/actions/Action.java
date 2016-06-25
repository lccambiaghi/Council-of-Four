package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;

public abstract class Action {

    protected Player player;
    protected Match match;
    protected String resultMsg;

    public abstract boolean preliminarySteps() throws InterruptedException;

    public abstract void execute();

    public abstract String getResultMsg();

}
