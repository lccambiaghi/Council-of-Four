package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;

import java.util.ArrayList;
import java.util.Arrays;

import static it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.InputHandler.inputNumber;

public class AcquirePermitCardMainAction extends Action {

    private Region chosenRegion;
    private ArrayList<PoliticCard> chosenPoliticCards;
    private int satisfactionCost;
    private PermitCard chosenPermitCard;

    public AcquirePermitCardMainAction(Match match, Player player){
        this.match=match;
        this.player=player;
    }

    @Override
    public boolean preliminarySteps(){

        Field field=match.getField();

        Balcony[] arrayBalcony=field.getArrayBalcony();

        Message.chooseBalcony(player, arrayBalcony);

        int chosenIndex = Broker.askInputNumber(1, 3, player) -1; //-1 for array positioning
        Balcony chosenBalcony = field.getBalconyFromIndex(chosenIndex); //-1 for array positioning
        chosenRegion = field.getRegionFromIndex(chosenIndex);

        ArrayList<PoliticCard> usablePoliticCards = getUsablePoliticCards(chosenBalcony);

        if (usablePoliticCards.size()<1) {
            Message.notEnoughPoliticsCards(player);
            return false;
        }

        if (eligibleMove(usablePoliticCards)<1){
            Message.notEnoughRichness(player);
            return false;
        }

        boolean eligibleSet=false;

        do {
            chosenPoliticCards = choosePoliticCardsUntilEligible(usablePoliticCards);
            satisfactionCost =eligibleMove(chosenPoliticCards);
            if (satisfactionCost >0)
                eligibleSet=true;
            else
                Message.notEnoughRichness(player);
        }while(!eligibleSet);

        PermitCard[] choosablePermitCards = chosenRegion.getFaceUpPermitCardArea().getArrayPermitCard();
        ArrayList<PermitCard> arrayListChoosablePermitCards = new ArrayList<>(Arrays.asList(choosablePermitCards));

        Message.choosePermitCard(player,arrayListChoosablePermitCards);

        chosenPermitCard =  choosablePermitCards[Broker.askInputNumber(1, choosablePermitCards.length, player)-1];

        return true;
    }

    /* First it adds Multicolor cards to usable cards, then for each councillor,
	   if it finds a matching color card, it adds it and goes to next councillor */
    private ArrayList<PoliticCard> getUsablePoliticCards(Balcony chosenBalcony) {

        Field field = match.getField();

        ArrayList <PoliticCard> playerHand = player.getArrayListPoliticCard();

        ArrayList <PoliticCard> usableCards = new ArrayList<>();

        for (PoliticCard politicCard: playerHand)
            if (politicCard.getCardColor()== Color.Multicolor) {
                usableCards.add(politicCard);
                playerHand.remove(politicCard);
            }

        for (Councillor councillor : chosenBalcony.getArrayListCouncillor()) {
            boolean councillorSatisfied = false;
            while (!councillorSatisfied)
                for (PoliticCard politicCard : playerHand)
                    if (councillor.getColor() == politicCard.getCardColor()) {
                        usableCards.add(politicCard);
                        playerHand.remove(politicCard);
                        councillorSatisfied = true;
                    }
        }

        return usableCards;

    }

    /* First call: if a set of cards that allows the player to perform the move exists,
		the method returns a positive number.
		Second call: if the specified set is eligible, the method returns what the player has to pay */
    private int eligibleMove(ArrayList<PoliticCard> usablePoliticCards) {

        Route richnessRoute = match.getField().getRichnessRoute();
        int playerRichness= richnessRoute.getPosition(player);

        int numberMulticolor=0;
        for (PoliticCard politicCard:usablePoliticCards )
            if(politicCard.getCardColor()==Color.Multicolor) {
                numberMulticolor++;
                usablePoliticCards.remove(politicCard);
            }
        int numberSingleColor=usablePoliticCards.size();

        switch (numberSingleColor+numberMulticolor) {
            case 1:
                if (playerRichness > 10 + numberMulticolor)
                    return (10 + numberMulticolor);
                break;
            case 2:
                if (playerRichness > 7 + numberMulticolor)
                    return (7 + numberMulticolor);
                break;
            case 3:
                if (playerRichness > 4 + numberMulticolor)
                    return (4 + numberMulticolor);
                break;
            default: // >3
                if (playerRichness > 4 - numberSingleColor)
                    return (4 - numberSingleColor);
                break;
        }

        return -1;

    }

    private ArrayList<PoliticCard> choosePoliticCardsUntilEligible(ArrayList<PoliticCard> usablePoliticCards) {

        ArrayList<PoliticCard> chosenPoliticCards = new ArrayList<>();

        Broker.println("Choose one card at a time to a maximum of four. Choose 0 when done.", player);
        for (int i = 0; i < usablePoliticCards.size(); i++) {
            Broker.println(i + 1 + ". " + usablePoliticCards.get(i).getCardColor(), player);
        }

        int indexChosenPermitCard = inputNumber(1, usablePoliticCards.size()) - 1; // -1 for array positioning
        chosenPoliticCards.add(usablePoliticCards.remove(indexChosenPermitCard));

        do {
            System.out.println("0. [Done] ");
            for (int i = 0; i < usablePoliticCards.size(); i++) {
                Broker.println(i + 1 + ". " + usablePoliticCards.get(i).getCardColor(), player);
            }
            indexChosenPermitCard = inputNumber(0, usablePoliticCards.size());

            if (indexChosenPermitCard > 0)
                chosenPoliticCards.add(usablePoliticCards.remove(indexChosenPermitCard - 1)); // -1 for array positioning

        } while (indexChosenPermitCard > 0 && chosenPoliticCards.size() < 4);

        return chosenPoliticCards;

    }

    @Override
    public void execute(){

        for (PoliticCard politicCard:chosenPoliticCards)
            player.discardPoliticCard(politicCard);

        Field field = match.getField();
        Route richnessRoute=field.getRichnessRoute();
        richnessRoute.movePlayer(-satisfactionCost, player);

        player.acquirePermitCard(chosenPermitCard);

        for (Bonus bonus: chosenPermitCard.getArrayBonus())
            bonus.applyBonus(player, field);

        resultMsg="Player " + player.getNickname() + " has acquired a new Permit Card in " +
                chosenRegion.getRegionName();

    }

    @Override
    public String getResultMsg(){return resultMsg;}

}