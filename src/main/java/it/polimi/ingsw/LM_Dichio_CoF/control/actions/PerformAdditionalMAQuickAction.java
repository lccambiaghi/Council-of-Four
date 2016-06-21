package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
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
    public boolean preliminarySteps(){

        if (player.getAssistant()<Constant.ADDITIONAL_MAIN_MOVE_ASSISTANT_COST){
            Broker.sendString(Message.notEnoughAssistant(), player);
			return false;
		}
		
        return true;

    }

    @Override
    public void execute(){

        player.setMainActionsLeft(player.getMainActionsLeft() + 1);

        player.decrementAssistant(Constant.ADDITIONAL_MAIN_MOVE_ASSISTANT_COST);
		
		resultMsg="Player "+ player.getNickname() +" paid " +  Constant.ADDITIONAL_MAIN_MOVE_ASSISTANT_COST
                + " and will perform another main action";

    }
    
    @Override
    public String getResultMsg(){return resultMsg;}

}