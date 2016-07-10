package it.polimi.ingsw.server.control.actions;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.Message;

import java.util.*;

/**
 * Preliminary Steps: checks if there exists a reachable city and a set of politics cards
 * such that kingCost + satisfactionCost <= playerRichness
 *
 * Execute: decrement richness, discard chosen politics cards, moves king to
 * chosen city, build emporium, apply bonuses to adjacent cities
 */
public class BuildEmporiumWithKingMainAction extends Action {

    private ArrayList<PoliticCard> chosenPoliticCards;

    private int satisfactionCost;

    private int kingCost;

    private City chosenCity;

    private int numberAssistantsToPay;

    /**
     * @param match : the match in which the move is being executed
     * @param player : the player who executes the move
     */
    public BuildEmporiumWithKingMainAction(Match match, Player player) {

        this.match = match;

        this.player = player;

    }

    @Override
    public boolean preliminarySteps() throws InterruptedException {

        if (player.getArrayListEmporiumBuilt().size() == Constant.NUMBER_EMPORIUMS_TO_WIN){

            player.getBroker().println(Message.youCantBuildMaxEmporium());

            return false;

        }

        Map<City, Integer> reachableCities = getReachableCities(0);

        //TODO fixed?
        Iterator itReachableCities = reachableCities.entrySet().iterator();

        Map.Entry firstReachableCity;

        int minimumKingCost;

        if(itReachableCities.hasNext()) {

            firstReachableCity = (Map.Entry) itReachableCities.next();

            minimumKingCost = (int) firstReachableCity.getValue();

        } else {

            player.getBroker().println(Message.youCantReachAnyCity());

            return false;
        }

        ArrayList<PoliticCard> usablePoliticCards = getUsablePoliticCards();

        if (usablePoliticCards.isEmpty()) {

            player.getBroker().println(Message.noEligibleSet());

            return false;

        }

        if (calculateSatisfactionCost(usablePoliticCards, minimumKingCost) < 0) {

            player.getBroker().println(Message.noEligibleSet());

            return false;

        }

        boolean eligibleSet = false;

        do {
            chosenPoliticCards = choosePoliticCardsUntilEligible(usablePoliticCards);
            satisfactionCost = calculateSatisfactionCost(chosenPoliticCards, minimumKingCost);
            if (player.getRichness() - satisfactionCost >= 0)
                eligibleSet = true;
            else
                player.getBroker().println(Message.notEnoughRichnessForThisSet());
        } while (!eligibleSet);

        Map<City, Integer> movableCities = getReachableCities(satisfactionCost);

        player.getBroker().println(Message.chooseDestinationCity(movableCities));

        int chosenIndex = player.getBroker().askInputNumber(1, movableCities.size());

        Iterator itCities = movableCities.entrySet().iterator();
        for (int i = 1; i < chosenIndex; i++)
            itCities.next();
        Map.Entry chosenEntry = (Map.Entry) itCities.next();

        chosenCity = (City) chosenEntry.getKey();

        for(Player otherPlayer : chosenCity.getArrayListEmporium())
            if(otherPlayer!=player)
                numberAssistantsToPay++;

        if(numberAssistantsToPay>player.getAssistant()){
            player.getBroker().println(Message.notEnoughAssistant());
            return false;
        }

        kingCost = (int) chosenEntry.getValue();

        return true;

    }

    @Override
    public void execute(){

        Field field=match.getField();

        player.decrementRichness(satisfactionCost+kingCost);

        player.decrementAssistant(numberAssistantsToPay);

        for (PoliticCard politicCard : chosenPoliticCards)
            player.discardPoliticCard(politicCard);

        field.getKing().setCurrentCity(chosenCity);

        chosenCity.buildEmporium(player);

        ArrayList<City> nearbyBuiltCities = getAdjacentBuiltCities(chosenCity);

        for (City city: nearbyBuiltCities)
        	if(city.hasBonus())
        		for (Bonus bonus : city.getArrayBonus())
        			bonus.applyBonus(player, field);

        resultMsg="Player " + player.getNickname() + " has moved the king to " +
                chosenCity.getCityName().toString() + " city and built an emporium there.";

        checkBonusTiles();

    }

    /**
     * First call: get every reachable buildableCity assuming the player pays 0 for election. Doing so,
     * the first entry's value is the minimumKingCost
     *
     * Second call: get every reachable buildableCity given that the player has to pay for satisfaction
     *
     * @param satisfactionCost cost the player has to pay for the satisfaction of the council
     * @return a Map containing the reachable city and the cost to reach it
     */
    private Map<City, Integer> getReachableCities(int satisfactionCost) {

        Field field = match.getField();

        City[] arrayCity = field.getArrayCity();

        City currentCity =field.getKing().getCurrentCity();

        int indexCurrentCity = currentCity.getCityName().ordinal();

        List<Integer>[] arrayCityLinks = field.getArrayCityLinks();

        boolean[] visitedCities = new boolean[arrayCityLinks.length];

        Queue<Integer> visitingLevelQueue = new LinkedList<>();

        Queue<Integer> nextLevelQueue = new LinkedList<>();

        visitingLevelQueue.add(indexCurrentCity);
        visitedCities[indexCurrentCity] = true;

        Map<City, Integer> movableCities = new LinkedHashMap<>();

        int levelCost = 0;

        while (player.getRichness() - satisfactionCost >= levelCost) {
            while (!visitingLevelQueue.isEmpty()) {

                int visitingCity = visitingLevelQueue.remove();

                //add visiting city to movable cities with cost of visiting level
                //if not already built and enough assistants
                if (!arrayCity[visitingCity].isEmporiumAlreadyBuilt(player) &&
                        player.getAssistant()>=arrayCity[visitingCity].getArrayListEmporium().size())
                    movableCities.put(arrayCity[visitingCity], levelCost);

                //add cities linked with visiting city to the next level queue
                for (Integer adjCity : arrayCityLinks[visitingCity])
                    if (!visitedCities[adjCity]) {
                        nextLevelQueue.add(adjCity);
                        visitedCities[adjCity] = true;
                    }

            }
            while (!nextLevelQueue.isEmpty())
                visitingLevelQueue.add(nextLevelQueue.remove());
            levelCost += 2;
        }

        return movableCities;

    }

    /**
     * First the method adds Multicolor cards to usable cards
     *
     * Then, for each councillor, if it finds a matching color card, it adds it and goest to next councillor
     *
     * @return potential politics card the player can use to satisfy the council
     */
    private ArrayList<PoliticCard> getUsablePoliticCards() {

        Balcony[] arrayBalcony = match.getField().getArrayBalcony();

        Balcony kingBalcony = match.getField().getBalconyFromIndex(arrayBalcony.length - 1);

        ArrayList<PoliticCard> playerHand = new ArrayList<>(player.getArrayListPoliticCard());

        ArrayList<PoliticCard> usableCards = new ArrayList<>();

        for (Iterator<PoliticCard> iterator = playerHand.iterator(); iterator.hasNext(); ) {
            PoliticCard politicCard = iterator.next();
            if (politicCard.getCardColor() == Color.Multicolor) {
                usableCards.add(politicCard);
                iterator.remove();
            }
        }

        for (Councillor councillor : kingBalcony.getArrayListCouncillor()) {
            boolean councillorSatisfied = false;
            for (Iterator<PoliticCard> iterator = playerHand.iterator(); iterator.hasNext(); ) {
                PoliticCard politicCard = iterator.next();
                if (councillor.getColor() == politicCard.getCardColor() && !councillorSatisfied) {
                    usableCards.add(politicCard);
                    iterator.remove();
                    councillorSatisfied = true;
                }
            }
        }

        return usableCards;

    }

    /**
     * First call: if a set of cards that allows the player to perform the move exists,
     * the method returns a positive number.
     *
     * Second call: if the specified set is eligible, the method returns what the player has to pay
     *
     * @param arrayListPoliticCards politic cards the player wants to use to satisfy council
     * @param minimumKingCost minimum cost the player has to pay to move the king
     * @return cost the player has to pay to perform the move
     */
    private int calculateSatisfactionCost(ArrayList<PoliticCard> arrayListPoliticCards, int minimumKingCost) {

        int playerRichness= player.getRichness();

        int numberMulticolor=0;
        for (PoliticCard politicCard:arrayListPoliticCards )
            if(politicCard.getCardColor()==Color.Multicolor)
                numberMulticolor++;

        int numberSingleColor= arrayListPoliticCards.size()-numberMulticolor;

        switch (numberSingleColor+numberMulticolor) {
            case 1:
                if (playerRichness - minimumKingCost >= 10 + numberMulticolor)
                    return 10 + numberMulticolor;
                break;
            case 2:
                if (playerRichness - minimumKingCost >= 7 + numberMulticolor)
                    return 7 + numberMulticolor;
                break;
            case 3:
                if (playerRichness - minimumKingCost >= 4 + numberMulticolor)
                    return 4 + numberMulticolor;
                break;
            default: // >3
                if (playerRichness - minimumKingCost >= 4 - numberSingleColor)
                    return 4 - numberSingleColor;
                break;
        }

        return -1;

    }

    /**
     * The method asks the user to select a set of politics card to satisfy the council among usablePolitcCards
     *
     * @param usablePoliticCards potential politics cards the player can select
     * @return chosen politics cards
     * @throws InterruptedException
     */
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

            player.getBroker().println(Message.choosePoliticCardWithDone(selectablePoliticCards, numberSelectedCards));

            indexSelectedCard = player.getBroker().askInputNumber(lowerBoundInput, selectablePoliticCards.size());

            if (indexSelectedCard > 0)
                selectedPoliticCards.add(selectablePoliticCards.remove(indexSelectedCard - 1)); // -1 for array positioning

            numberSelectedCards++;

        } while (indexSelectedCard > 0 && numberSelectedCards < 4);

        return selectedPoliticCards;

    }

    /**
     * This method returns cities adjacent to chosenCity.
     * It handles a queue of adjacent built cities which starts only with chosenCity.
     * Elements of queue are removed and analysed one at a time.
     * If a city is linked to the analysed element of the queue and emporium is present,
     * the city is also added to the queue
     *
     * @param chosenCity city in which the player wants to build the emporium
     * @return adjacent cities to chosenCity
     */
    private ArrayList<City> getAdjacentBuiltCities(City chosenCity) {

        int indexChosenCity = chosenCity.getCityName().ordinal();

        List<Integer>[] arrayCityLinks = match.getField().getArrayCityLinks();

        Queue<Integer> toVisitQueue = new LinkedList<>();
        toVisitQueue.add(indexChosenCity);

        boolean[] visitedCities = new boolean[arrayCityLinks.length];
        visitedCities[indexChosenCity] = true;

        ArrayList<City> nearbyBuiltCities = new ArrayList<>();

        City[] arrayCity=match.getField().getArrayCity();

        while (!toVisitQueue.isEmpty()) {

            int visitingCity = toVisitQueue.remove();

            nearbyBuiltCities.add(arrayCity[visitingCity]);

            for (Integer adjacentCity : arrayCityLinks[visitingCity])
                if (arrayCity[adjacentCity].isEmporiumAlreadyBuilt(player) && !visitedCities[adjacentCity]) {
                    toVisitQueue.add(adjacentCity);
                    visitedCities[adjacentCity] = true;
                }

        }

        return nearbyBuiltCities;

    }

    private void checkBonusTiles() {

        int playerVictory = player.getVictory();

        int increment;

        Deque <Integer> kingRewardTiles = match.getField().getKingRewardTiles();

        if(isEligibleForColorTile()){

            switch (chosenCity.getCityColor()){
                case Blue:
                    increment= Constant.BLUE_BONUS_TILE_VICTORY_INCREMENT;
                    break;
                case Bronze:
                    increment= Constant.BRONZE_BONUS_TILE_VICTORY_INCREMENT;
                    break;
                case Silver:
                    increment= Constant.SILVER_BONUS_TILE_VICTORY_INCREMENT;
                    break;
                case Gold:
                    increment= Constant.GOLD_BONUS_TILE_VICTORY_INCREMENT;
                    break;
                case Red:
                    increment= Constant.RED_BONUS_TILE_VICTORY_INCREMENT;
                    break;
                default:
                    increment=0; //should never be reached
            }

            player.addVictory(playerVictory+increment);

            for(City city: match.getField().getArrayCity())
                if(city.getCityColor() == chosenCity.getCityColor())
                    city.setColorBonusSatisfied(true);

            resultMsg = resultMsg + "\nDoing so, he acquired "
                    + chosenCity.getCityColor().toString() +
                    " Bonus Tile.";

            if(kingRewardTiles.peekFirst()!=null) {
                increment = kingRewardTiles.pollFirst();
                player.addVictory(playerVictory + increment);
                resultMsg = resultMsg + "\nHe also acquired "
                        + increment + " victory points thanks to King's Reward Tile";
            }

        }

        if(isEligibleForRegionTile()){

            player.addVictory(playerVictory +Constant.REGION_TILE_VICTORY_INCREMENT);

            int chosenRegionIndex=RegionName.getIndex(chosenCity.getRegionName());

            Region chosenRegion = match.getField().getRegionFromIndex(chosenRegionIndex);

            chosenRegion.setRegionBonusSatisfied(true);

            resultMsg = resultMsg + "\nDoing so, he acquired "
                    + chosenCity.getRegionName().toString() +
                    " Bonus Tile.";

            if(kingRewardTiles.peekFirst()!=null) {
                increment = kingRewardTiles.pollFirst();
                player.addVictory(playerVictory + increment);
                resultMsg = resultMsg + "\nHe also acquired "
                        + increment + " victory points thanks to King's Reward Tile";
            }

        }

    }

    /**
     * This method checks if the player has built in every city of the chosenCity card color
     * @return true if eligible, false if not
     */
    private boolean isEligibleForColorTile() {

        if(chosenCity.isColorBonusSatisfied() || chosenCity.getCityColor()==CityColor.Purple)
            return false;

        City[] arrayCity = match.getField().getArrayCity();

        for (City city : arrayCity)
            if (city.getCityColor() == chosenCity.getCityColor() && !city.isEmporiumAlreadyBuilt(player))
                return false;

        return true;

    }

    /**
     * This method checks if the player has built in every city of the chosenRegion
     * @return true if eligible, false if not
     */
    private boolean isEligibleForRegionTile() {

        int chosenRegionIndex=RegionName.getIndex(chosenCity.getRegionName());

        Region chosenRegion = match.getField().getRegionFromIndex(chosenRegionIndex);

        if(chosenRegion.isRegionBonusSatisfied())
            return false;

        City[] arrayCity = chosenRegion.getArrayCity();

        for (City city : arrayCity)
            if (!city.isEmporiumAlreadyBuilt(player))
                return false;

        return true;

    }

    @Override
    public String getResultMsg(){return resultMsg;}

}