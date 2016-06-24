package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Field;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Route;

public class EngageAssistantQuickAction extends Action {

    public EngageAssistantQuickAction(Match match, Player player){

        this.match=match;

        this.player=player;
    }

    @Override
    public boolean preliminarySteps(){

        Field field= match.getField();

        Route richnessRoute = field.getRichnessRoute();

		if(richnessRoute.getPosition(player)<Constant.ASSISTANT_ENGAGEMENT_RICHNESS_COST){
            Broker.println(Message.notEnoughAssistant(), player);
			return false;
		}
    	
        return true;

    }

    @Override
    public void execute(){
    	
    	Field field= match.getField();

    	Route richnessRoute = field.getRichnessRoute();
    
    	richnessRoute.movePlayer(-(Constant.ASSISTANT_ENGAGEMENT_RICHNESS_COST),player);

        player.addAssistant(1);
		
		resultMsg="Player "+ player.getNickname() +" has engaged an Assistant paying "
                + Constant.ASSISTANT_ENGAGEMENT_RICHNESS_COST + " coins";

    }

    @Override
    public String getResultMsg(){return resultMsg;}

}