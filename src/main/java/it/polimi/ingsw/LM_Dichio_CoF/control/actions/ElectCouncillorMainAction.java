package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;

import java.util.ArrayList;

public class ElectCouncillorMainAction extends Action {

    private Balcony chosenBalcony;
    private Color chosenCouncillorColor;

    public ElectCouncillorMainAction(Match match, Player player){
        this.match=match;
        this.player=player;
    }

    @Override
    public boolean preliminarySteps() throws InterruptedException{

        Field field=match.getField();

        Balcony[] arrayBalcony = field.getArrayBalcony();

        player.getBroker().println(Message.chooseBalcony(arrayBalcony));

        chosenBalcony = field.getBalconyFromIndex(player.getBroker().askInputNumber(1, 4)-1); //-1 for array positioning

        ArrayList<Color> choosableColors = getChoosableColors();

        if (choosableColors.size()<1)
            return false;

        player.getBroker().println(Message.askCouncillorColor(choosableColors));

        chosenCouncillorColor=choosableColors.get(player.getBroker().askInputNumber(1, choosableColors.size())-1);

        return true;
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

    @Override
    public void execute(){

        Field field=match.getField();

        AvailableCouncillors availableCouncillors = field.getAvailableCouncillors();

        Councillor chosenCouncillor = availableCouncillors.removeAvailableCouncillor(chosenCouncillorColor);

        chosenBalcony.electCouncillor(chosenCouncillor,availableCouncillors);

        Route richnessRoute = field.getRichnessRoute();

        richnessRoute.movePlayer(Constant.ELECTION_RICHNESS_INCREMENT, player);

        resultMsg= "Player "+ player.getNickname() + " elected a + " +
                chosenCouncillor + " Councillor in " +
                chosenBalcony.getNameBalcony() + ".\n";

    }

    @Override
    public String getResultMsg(){return resultMsg;}

}
