package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;

import java.util.*;

import static it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.InputHandler.inputNumber;

public class BuildEmporiumWithKingMainAction extends Action {

    private ArrayList<PoliticCard> chosenPoliticCards;
    private int satisfactionCost;
    private Map.Entry<City, Integer> chosenCity;

    public BuildEmporiumWithKingMainAction(Match match, Player player){
        this.match=match;
        this.player=player;
    }

    @Override
    public boolean preliminarySteps(){

        Field field=match.getField();

        Balcony[] arrayBalcony=field.getArrayBalcony();

        Balcony chosenBalcony = field.getBalconyFromIndex(arrayBalcony.length-1);

        ArrayList<PoliticCard> usablePoliticCards = getUsablePoliticCards(chosenBalcony);

        if (usablePoliticCards.size()<1) {
            Broker.println(Message.notEnoughPoliticsCards(),player);
            return false;
        }

        if (eligibleMove(usablePoliticCards)<1){
            Broker.println(Message.notEnoughAssistant(), player);
            return false;
        }

        boolean eligibleSet=false;

        do {
            chosenPoliticCards = choosePoliticCardsUntilEligible(usablePoliticCards);
            satisfactionCost =eligibleMove(chosenPoliticCards);
            if (satisfactionCost >0)
                eligibleSet=true;
            else
                Broker.println(Message.notEnoughAssistant(), player);
        }while(!eligibleSet);

        Map<City, Integer> movableCities = getMovableCities();

        Broker.println(Message.chooseCity(movableCities), player);
        int chosenIndex=Broker.askInputNumber(1, movableCities.size(), player)-1;

        int i=0;
        for (Map.Entry<City, Integer> city : movableCities.entrySet()) {
            if (i == chosenIndex)
                chosenCity = city;
            i++;
        }

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

    private Map<City, Integer> getMovableCities() {

        Field field=match.getField();
        City[] arrayCity=field.getArrayCity();

        int indexCurrentCity= Arrays.asList(arrayCity).indexOf(field.getKing().getCurrentCity());

        List<Integer>[] arrayCityLinks = field.getArrayCityLinks();

        boolean[] visitedCities = new boolean[arrayCityLinks.length];

        Queue<Integer> visitingLevelQueue = new LinkedList<>();

        Queue<Integer> nextLevelQueue = new LinkedList<>();

        visitingLevelQueue.add(indexCurrentCity);
        visitedCities[indexCurrentCity] = true;

        Map<City, Integer> movableCities= new LinkedHashMap<>();

        int levelCost=0;

        movableCities.put(arrayCity[indexCurrentCity], levelCost);

        while (player.getRichness()>levelCost) {
            while(!visitingLevelQueue.isEmpty()) {
                int visitingCity = visitingLevelQueue.remove();
                for (Integer adjCity : arrayCityLinks[visitingCity])
                    if (!visitedCities[adjCity]) {
                        nextLevelQueue.add(adjCity);
                        visitedCities[adjCity] = true;
                        if (!arrayCity[adjCity].isEmporiumAlreadyBuilt(player))
                            movableCities.put(arrayCity[adjCity],levelCost);
                    }
            }
            while(!nextLevelQueue.isEmpty())
                visitingLevelQueue.add(nextLevelQueue.remove());
            levelCost+=2;
        }

        return movableCities;

    }

    @Override
    public void execute(){

        Field field=match.getField();

        Route richnessRoute = field.getRichnessRoute();

        richnessRoute.movePlayer(-satisfactionCost, player);

        field.getKing().setCurrentCity(chosenCity.getKey());

        chosenCity.getKey().buildEmporium(player);

        resultMsg="Player " + player.getNickname() + " has moved the king to " +
                chosenCity.getKey().getCityName() + " city and built an emporium there.";

    }
    
    @Override
    public String getResultMsg(){return resultMsg;}

}