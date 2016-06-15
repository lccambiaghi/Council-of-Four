package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;

import java.util.ArrayList;

import static it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.InputHandler.inputNumber;

public class ElectCouncillorMainAction extends Action {

    private Balcony chosenBalcony;
    private Color chosenCouncillorColor;

    public ElectCouncillorMainAction(Match match, Player player){
        this.match=match;
        this.player=player;
    }

    @Override
    public boolean preliminarySteps(){

        chosenBalcony = chooseBalcony();

        ArrayList<Color> choosableColors = getChoosableColors();

        if (choosableColors.size()<1)
            return false;

        chosenCouncillorColor=chooseCouncillorColor(choosableColors);

        return true;
    }

    private Balcony chooseBalcony(){

        Field field=match.getField();

        Message.chooseBalcony_1_4(player);

        return field.getBalconyFromIndex(inputNumber(1, 4)-1); //-1 for array positioning

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

        Message.askNewCouncillor(player);

        for (int i=0; i<choosableColors.size(); i++)
            Broker.println(i + 1 + ". " + choosableColors.get(i), player);

        return choosableColors.get(inputNumber(1, choosableColors.size()));

    }

    @Override
    public void execute(){

        Field field=match.getField();

        AvailableCouncillors availableCouncillors = field.getAvailableCouncillors();

        Councillor chosenCouncillor = availableCouncillors.removeAvailableCouncillor(chosenCouncillorColor); //NullPointerException?

        chosenBalcony.electCouncillor(chosenCouncillor,availableCouncillors);

        Route richnessRoute = field.getRichnessRoute();

        richnessRoute.movePlayer(Constant.ELECTION_RICHNESS_INCREMENT, player);

    }

}
