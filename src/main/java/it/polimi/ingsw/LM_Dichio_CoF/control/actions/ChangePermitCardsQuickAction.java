package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.FaceUpPermitCardArea;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Field;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.PermitCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Region;

public class ChangePermitCardsQuickAction extends Action {

	private Region chosenRegion;
	
    public ChangePermitCardsQuickAction(Match match, Player player){
        this.match=match;
        this.player=player;
    }

    @Override
    public boolean preliminarySteps(){

		if (player.getAssistant()<Constant.PERMIT_CARD_CHANGE_ASSISTANT_COST){
			Broker.sendString(Message.notEnoughAssistant(), player);
    		return false;
    	}

		Field field = match.getField();

		Broker.sendString(Message.chooseRegion_1_3(), player);

    	chosenRegion = field.getRegionFromIndex(Broker.askInputNumber(1, 3, player) - 1); // -1 for array positioning

        return true;
    }

    @Override
    public void execute(){

		FaceUpPermitCardArea chosenArea=chosenRegion.getFaceUpPermitCardArea();

		PermitCard[] arrayPermitCard = chosenArea.getArrayPermitCard();

		for (int i = 0; i < arrayPermitCard.length; i++)
			chosenArea.changePermitCard(i);

		player.decrementAssistant(Constant.PERMIT_CARD_CHANGE_ASSISTANT_COST);
		
		resultMsg="Player "+ player.getNickname() +" has changed the face up Permit Cards in "
				 + chosenRegion.getRegionName();

    }
    
    @Override
    public String getResultMsg(){return resultMsg;}

}