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
			Message.notEnoughAssistant(player);
			return false;
		}
		chosenBalcony = chooseBalcony();
		ArrayList<Color> choosableColors = getChoosableColors();
		
		if (choosableColors.size()<1)
	        return false;
		
		chosenCouncillorColor=chooseCouncillorColor(choosableColors);

        return true;
    }
    
    @Override
    public void execute(){
    	Field field = match.getField();
    	AvailableCouncillors availableCouncillors = field.getAvailableCouncillors();
		Councillor chosenCouncillor = availableCouncillors.removeAvailableCouncillor(chosenCouncillorColor); //NullPointerException?
		
		chosenBalcony.electCouncillor(chosenCouncillor,availableCouncillors);
    	player.decrementAssistant(Constant.ELECTION_ASSISTANT_COST);
    	
    	resultMsg="Player "+player.getNickname() +"has elected a councillor paying"
    			+" an Assistant"+ ".\n";
    }

    @Override
    public String getResultMsg(){return resultMsg;}
       
    private Balcony chooseBalcony(){

        Field field=match.getField();
        Balcony[] arrayBalcony = field.getArrayBalcony();

        Message.chooseBalcony(player, arrayBalcony);

        return field.getBalconyFromIndex(Broker.askInputNumber(1, 4, player)-1); //-1 for array positioning

    }

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

    private Color chooseCouncillorColor(ArrayList<Color> choosableColors){

        Message.askCouncillorColor(player, choosableColors);

        for (int i=0; i<choosableColors.size(); i++)
            Broker.println(i + 1 + ". " + choosableColors.get(i), player);

        return choosableColors.get(Broker.askInputNumber(1, choosableColors.size(), player)-1);

    }


}