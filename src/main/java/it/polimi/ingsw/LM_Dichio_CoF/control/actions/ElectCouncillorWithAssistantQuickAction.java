package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import java.util.ArrayList;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.AvailableCouncillors;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Balcony;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Councillor;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Field;

public class ElectCouncillorWithAssistantQuickAction extends Action {

	private Balcony chosenBalcony;
	private Color chosenCouncillorColor;
	
    public ElectCouncillorWithAssistantQuickAction(Match match, Player player){

        this.match=match;

        this.player=player;

    }

    @Override
    public boolean preliminarySteps(){

        if (player.getAssistant()<Constant.ELECTION_ASSISTANT_COST){
            Broker.println(Message.notEnoughAssistant(), player);
            return false;
        }

        Field field=match.getField();

        Balcony[] arrayBalcony = field.getArrayBalcony();

        Broker.println(Message.chooseBalcony(arrayBalcony), player);

        chosenBalcony = field.getBalconyFromIndex(Broker.askInputNumber(1, 4, player)-1); //-1 for array positioning

        ArrayList<Color> choosableColors = getChoosableColors();

        if (choosableColors.size()<1)
            return false;

        Broker.println(Message.askCouncillorColor(choosableColors), player);

        chosenCouncillorColor=choosableColors.get(Broker.askInputNumber(1, choosableColors.size(), player)-1); //-1 for array positioning

        return true;

    }
    
    @Override
    public void execute(){

        Field field = match.getField();

    	AvailableCouncillors availableCouncillors = field.getAvailableCouncillors();

		Councillor chosenCouncillor = availableCouncillors.removeAvailableCouncillor(chosenCouncillorColor);
		
		chosenBalcony.electCouncillor(chosenCouncillor,availableCouncillors);

    	player.decrementAssistant(Constant.ELECTION_ASSISTANT_COST);
    	
    	resultMsg="Player "+player.getNickname() +" elected a councillor using"
    			+" an Assistant";

    }

    @Override
    public String getResultMsg(){return resultMsg;}

    private ArrayList<Color> getChoosableColors() {

        Field field=match.getField();

        ArrayList<Councillor> availableCouncillors = field.getAvailableCouncillors().getArrayListCouncillor();

        boolean[] seen = new boolean[Constant.COLORS_NUMBER];

        ArrayList<Color> choosableColors = new ArrayList<>();
        for (Councillor councillor: availableCouncillors)
            if (!seen[Color.valueOf(councillor.getColor().toString()).ordinal()]) {
                choosableColors.add(councillor.getColor());
                seen[Color.valueOf(councillor.getColor().toString()).ordinal()]=true;
            }

        return choosableColors;

    }

}