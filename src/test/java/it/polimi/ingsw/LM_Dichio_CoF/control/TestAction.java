package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;

public class TestAction {

    private TestCases testCases= new TestCases();

    protected Player player= testCases.arrayListPlayer().get(0);
    protected Match match=testCases.match();

}
