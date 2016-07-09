package it.polimi.ingsw.server.control.actions;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.Message;

/**
 * Preliminary steps: the method checks if the player has enough assistants
 *
 * Execute: the method adds an additional main action to the player
 */
public class PerformAdditionalMAQuickAction extends Action {

    /**
     * @param match : the match in which the move is being executed
     * @param player : the player who executes the move
     */
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
                + " assistants in order to gain an additional main action";

    }
    
    @Override
    public String getResultMsg(){return resultMsg;}

}