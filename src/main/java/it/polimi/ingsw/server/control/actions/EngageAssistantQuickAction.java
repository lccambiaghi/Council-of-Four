package it.polimi.ingsw.server.control.actions;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.Message;

/**
 * Preliminary steps: the method checks if the player has enough richness
 *
 * Execute: adds the assistant to the player and decrease his richness
 */
public class EngageAssistantQuickAction extends Action {

    /**
     * @param match : the match in which the move is being executed
     * @param player : the player who executes the move
     */
    public EngageAssistantQuickAction(Match match, Player player){

        this.match=match;

        this.player=player;

    }

    @Override
    public boolean preliminarySteps() throws InterruptedException{

		if(player.getRichness()<Constant.ASSISTANT_ENGAGEMENT_RICHNESS_COST){

            player.getBroker().println(Message.notEnoughRichness());

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