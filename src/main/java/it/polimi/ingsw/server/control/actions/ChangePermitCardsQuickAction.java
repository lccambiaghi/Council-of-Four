package it.polimi.ingsw.server.control.actions;

import it.polimi.ingsw.server.control.Constant;
import it.polimi.ingsw.server.control.Message;
import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.FaceUpPermitCardArea;
import it.polimi.ingsw.server.model.PermitCard;
import it.polimi.ingsw.server.model.Region;

public class ChangePermitCardsQuickAction extends Action {

	private Region chosenRegion;
	
    public ChangePermitCardsQuickAction(Match match, Player player){

		this.match=match;

		this.player=player;

    }

    @Override
    public boolean preliminarySteps() throws InterruptedException{

		if (player.getAssistant()<Constant.PERMIT_CARD_CHANGE_ASSISTANT_COST){

			player.getBroker().println(Message.notEnoughAssistant());

			return false;

    	}

		player.getBroker().println(Message.chooseRegion_1_3());

    	chosenRegion = match.getField().getRegionFromIndex(player.getBroker().askInputNumber(1, 3) - 1); // -1 for array positioning

        return true;

    }

    @Override
    public void execute(){

		FaceUpPermitCardArea chosenArea=chosenRegion.getFaceUpPermitCardArea();

		PermitCard[] arrayPermitCard = chosenArea.getArrayPermitCard();

		for (int i = 0; i < arrayPermitCard.length; i++)
			chosenArea.replacePermitCard(i);

		player.decrementAssistant(Constant.PERMIT_CARD_CHANGE_ASSISTANT_COST);
		
		resultMsg="Player "+ player.getNickname() +" has changed the face up Permit Cards in "
				 + chosenRegion.getRegionName();

    }
    
    @Override
    public String getResultMsg(){return resultMsg;}

}