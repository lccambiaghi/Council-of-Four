package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;

public class PerformAdditionalMAQuickAction extends Action {

    public PerformAdditionalMAQuickAction(Match match, Player player){

        this.match=match;

        this.player=player;

    }

    @Override
    public boolean preliminarySteps() throws InterruptedException{

        if (player.getAssistant()<Constant.ADDITIONAL_MAIN_MOVE_ASSISTANT_COST){

            player.getBroker().println(Message.notEnoughAssistant());

			return false;

		}
		
        return true;

    }

    @Override
    public void execute(){

        player.addMainActionLeft(1);

        player.decrementAssistant(Constant.ADDITIONAL_MAIN_MOVE_ASSISTANT_COST);
		
		resultMsg="Player "+ player.getNickname() +" paid " +  Constant.ADDITIONAL_MAIN_MOVE_ASSISTANT_COST
                + " assistants in order to perform an additional main action";

    }
    
    @Override
    public String getResultMsg(){return resultMsg;}

}