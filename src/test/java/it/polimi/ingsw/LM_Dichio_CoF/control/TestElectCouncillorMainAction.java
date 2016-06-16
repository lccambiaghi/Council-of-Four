package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.Action;
import it.polimi.ingsw.LM_Dichio_CoF.control.actions.ElectCouncillorMainAction;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Councillor;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestElectCouncillorMainAction extends TestAction {

    @Ignore
    public void ElectCouncillorMainAction(){

        ArrayList<Councillor> oldCouncillors=new ArrayList<>(match.getField().getBalconyFromIndex(0).getArrayListCouncillor());

        Action action = new ElectCouncillorMainAction(match,player);

        action.execute();

        ArrayList<Councillor> newCouncillors = match.getField().getBalconyFromIndex(0).getArrayListCouncillor();

        assertEquals(oldCouncillors.subList(0,oldCouncillors.size()-2), newCouncillors.subList(1,newCouncillors.size()-1));

        assertEquals(14, player.getRichness());

    }

}
