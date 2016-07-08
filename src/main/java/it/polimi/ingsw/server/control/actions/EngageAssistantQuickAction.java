package it.polimi.ingsw.server.control.actions;

import it.polimi.ingsw.server.control.Constant;
import it.polimi.ingsw.server.control.Message;
import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Match;

public class EngageAssistantQuickAction extends Action {

    public EngageAssistantQuickAction(Match match, Player player){

        this.match=match;

        this.player=player;

    }

    @Override
    public boolean preliminarySteps() throws InterruptedException{

		if(player.getRichness()<Constant.ASSISTANT_ENGAGEMENT_RICHNESS_COST){

            player.getBroker().println(Message.notEnoughAssistant());

            return false;

		}
    	
        return true;

    }

    @Override
    public void execute(){
    
    	player.decrementRichness(Constant.ASSISTANT_ENGAGEMENT_RICHNESS_COST);

        player.addAssistant(1);
		
		resultMsg="Player "+ player.getNickname() +" has engaged an Assistant paying "
                + Constant.ASSISTANT_ENGAGEMENT_RICHNESS_COST + " coins";

    }

    @Override
    public String getResultMsg(){return resultMsg;}

}