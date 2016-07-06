package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class AcquirePermitCardMainAction extends Action {

    private Region chosenRegion;
    private ArrayList<PoliticCard> chosenPoliticCards;
    private int satisfactionCost;
    private int indexChosenPermitCard;

    public AcquirePermitCardMainAction(Match match, Player player){
        this.match=match;
        this.player=player;
    }

    @Override
    public boolean preliminarySteps() throws InterruptedException{

        Field field=match.getField();

        Balcony[] arrayBalcony=field.getArrayBalcony();

        player.getBroker().println(Message.chooseBalcony(arrayBalcony));

        int chosenIndex = player.getBroker().askInputNumber(1, 3) -1; //-1 for array positioning

        Balcony chosenBalcony = field.getBalconyFromIndex(chosenIndex); //-1 for array positioning

        chosenRegion = field.getRegionFromIndex(chosenIndex);

        ArrayList<PoliticCard> usablePoliticCards = getUsablePoliticCards(chosenBalcony);

        if (usablePoliticCards.isEmpty()) {
            player.getBroker().println(Message.notEligibleForMove());
            return false;
        }

        if (calculateSatisfactionCost(usablePoliticCards)<1){
            player.getBroker().println(Message.notEligibleForMove());
            return false;
        }

        boolean eligibleSet=false;
        do {
            chosenPoliticCards = choosePoliticCardsUntilEligible(usablePoliticCards);
            satisfactionCost = calculateSatisfactionCost(chosenPoliticCards);
            if (player.getRichness() - satisfactionCost >=0)
                eligibleSet=true;
            else
                player.getBroker().println(Message.notEnoughRichnessForThisSet());
        }while(!eligibleSet);

        PermitCard[] choosablePermitCards = chosenRegion.getFaceUpPermitCardArea().getArrayPermitCard();
        ArrayList<PermitCard> arrayListChoosablePermitCards = new ArrayList<>(Arrays.asList(choosablePermitCards));

        player.getBroker().println(Message.choosePermitCard(arrayListChoosablePermitCards));
        indexChosenPermitCard = player.getBroker().askInputNumber(1, choosablePermitCards.length)-1;

        return true;

    }

    /* First it adds Multicolor cards to usable cards, then for each councillor,
	   if it finds a matching color card, it adds it and goes to next councillor */
    private ArrayList<PoliticCard> getUsablePoliticCards(Balcony chosenBalcony) {

        ArrayList <PoliticCard> playerHand = new ArrayList<>(player.getArrayListPoliticCard());

        ArrayList <PoliticCard> usableCards = new ArrayList<>();

        for (Iterator<PoliticCard> iterator = playerHand.iterator(); iterator.hasNext(); ) {
            PoliticCard politicCard = iterator.next();
            if (politicCard.getCardColor() == Color.Multicolor) {
                usableCards.add(politicCard);
                iterator.remove();
            }
        }

        for (Councillor councillor : chosenBalcony.getArrayListCouncillor()) {
            boolean councillorSatisfied = false;
            for (Iterator<PoliticCard> iterator = playerHand.iterator(); iterator.hasNext(); ) {
                PoliticCard politicCard = iterator.next();
                if (councillor.getColor() == politicCard.getCardColor() && !councillorSatisfied ) {
                    usableCards.add(politicCard);
                    iterator.remove();
                    councillorSatisfied = true;
                }
            }
        }

        return usableCards;

    }

    /* First call: if a set of cards that allows the player to perform the move exists,
		the method returns a positive number.
		Second call: if the specified set is eligible, the method returns what the player has to pay */
    private int calculateSatisfactionCost(ArrayList<PoliticCard> usablePoliticCards) {

        int playerRichness= player.getRichness();

        int numberMulticolor=0;
        for (PoliticCard politicCard:usablePoliticCards )
            if(politicCard.getCardColor()==Color.Multicolor)
                numberMulticolor++;

        int numberSingleColor=usablePoliticCards.size()-numberMulticolor;

        switch (numberSingleColor+numberMulticolor) {
            case 1:
                if (playerRichness >= 10 + numberMulticolor)
                    return 10 + numberMulticolor;
                break;
            case 2:
                if (playerRichness >= 7 + numberMulticolor)
                    return 7 + numberMulticolor;
                break;
            case 3:
                if (playerRichness >= 4 + numberMulticolor)
                    return 4 + numberMulticolor;
                break;
            default: // >3
                if (playerRichness >= 4 - numberSingleColor)
                    return 4 - numberSingleColor;
                break;
        }

        return -1;

    }

    private ArrayList<PoliticCard> choosePoliticCardsUntilEligible(ArrayList<PoliticCard> usablePoliticCards) throws InterruptedException {

        ArrayList<PoliticCard> selectablePoliticCards = new ArrayList<>(usablePoliticCards);
        ArrayList<PoliticCard> selectedPoliticCards = new ArrayList<>();

        player.getBroker().println("Choose one card at a time to a maximum of four. Choose 0 when done.");

        int indexSelectedCard;
        int numberSelectedCards=0;
        int lowerBoundInput=1;

        do {

            if(numberSelectedCards==1)
                lowerBoundInput = 0;

            if(numberSelectedCards>=1)
                player.getBroker().println("0. [Done] ");

            player.getBroker().println(Message.choosePoliticCard(selectablePoliticCards));

            indexSelectedCard = player.getBroker().askInputNumber(lowerBoundInput, selectablePoliticCards.size());

            if (indexSelectedCard > 0)
                selectedPoliticCards.add(selectablePoliticCards.remove(indexSelectedCard - 1)); // -1 for array positioning

            numberSelectedCards++;

        } while (indexSelectedCard > 0 && numberSelectedCards < 4);

        return selectedPoliticCards;

    }

    @Override
    public void execute(){

        //discard politics cards
        for (PoliticCard politicCard:chosenPoliticCards)
            player.discardPoliticCard(politicCard);

        //decrease richness
        Field field = match.getField();
        player.decrementRichness(satisfactionCost);

        //applying bonuses
        FaceUpPermitCardArea faceUpPermitCardArea=chosenRegion.getFaceUpPermitCardArea();
        for (Bonus bonus: faceUpPermitCardArea.getArrayPermitCard()[indexChosenPermitCard].getArrayBonus())
            bonus.applyBonus(player, field);

        //acquiring permit card and replacing it on the board
        player.acquirePermitCard(faceUpPermitCardArea.acquirePermitCard(indexChosenPermitCard));

        resultMsg="Player " + player.getNickname() + " has acquired a new Permit Card in " +
                chosenRegion.getRegionName();

    }

    @Override
    public String getResultMsg(){return resultMsg;}

}