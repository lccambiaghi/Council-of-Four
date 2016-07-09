package it.polimi.ingsw.server.control.actions;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.Message;

import java.util.ArrayList;

/**
 * Preliminary steps: the method checks which color the player can choose.
 * In the unlikely event this list is empty, the player cannot perform the move
 *
 * Execute: the councillor is elected and the "fallen" councillor is put in availableCouncillors
 */
public class ElectCouncillorMainAction extends Action {

    private Balcony chosenBalcony;

    private Color chosenCouncillorColor;

    /**
     * @param match : the match in which the move is being executed
     * @param player : the player who executes the move
     */
    public ElectCouncillorMainAction(Match match, Player player){

        this.match=match;

        this.player=player;

    }

    @Override
    public boolean preliminarySteps() throws InterruptedException{

        Field field=match.getField();

        Balcony[] arrayBalcony = field.getArrayBalcony();

        player.getBroker().println(Message.chooseBalcony(arrayBalcony));

        chosenBalcony = field.getBalconyFromIndex(player.getBroker().askInputNumber(1, arrayBalcony.length)-1); //-1 for array positioning

        ArrayList<Color> choosableColors = getChoosableColors();

        if (choosableColors.isEmpty()){

            player.getBroker().println(Message.notEligibleForMove());

            return false;

        }

        player.getBroker().println(Message.askCouncillorColor(choosableColors));

        chosenCouncillorColor=choosableColors.get(player.getBroker().askInputNumber(1, choosableColors.size())-1);

        return true;

    }

    /**
     * This method collects all the different colors of availableCouncillors
     *
     * @return list of colors the player can choose
     */
    private ArrayList<Color> getChoosableColors() {

        ArrayList<Councillor> availableCouncillors = match.getField().getAvailableCouncillors().getArrayListCouncillor();

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

        AvailableCouncillors availableCouncillors = match.getField().getAvailableCouncillors();

        Councillor chosenCouncillor = availableCouncillors.removeAvailableCouncillor(chosenCouncillorColor);

        chosenBalcony.electCouncillor(chosenCouncillor,availableCouncillors);

        player.addRichness(Constant.ELECTION_RICHNESS_INCREMENT);
        resultMsg= "Player "+ player.getNickname() + " elected a " +
                chosenCouncillor.getColor() + " Councillor in " +
                chosenBalcony.getNameBalcony() + ".\n";

    }

    @Override
    public String getResultMsg(){return resultMsg;}

}
