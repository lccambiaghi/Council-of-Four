package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Color;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;

import java.util.*;

public class BuildEmporiumWithKingMainAction extends Action {

    private ArrayList<PoliticCard> chosenPoliticCards;

    private int satisfactionCost;

    private int kingCost;

    private City chosenCity;

    public BuildEmporiumWithKingMainAction(Match match, Player player) {

        this.match = match;

        this.player = player;

    }

    @Override
    public boolean preliminarySteps() throws InterruptedException {

        Map<City, Integer> reachableCities = getReachableCities(0);

        int minimumKingCost = reachableCities.entrySet().iterator().next().getValue();

        ArrayList<PoliticCard> usablePoliticCards = getUsablePoliticCards();

        if (usablePoliticCards.isEmpty()) {

            player.getBroker().println(Message.notEligibleForMove());

            return false;

        }

        if (calculateSatisfactionCost(usablePoliticCards, minimumKingCost) < 1) {

            player.getBroker().println(Message.notEnoughRichnessForThisSet());

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
        for (int i = 1; i <= chosenIndex; i++)
            itCities.next();
        Map.Entry chosenEntry = (Map.Entry) itCities.next();

        chosenCity = (City) chosenEntry.getKey();
        kingCost = (int) chosenEntry.getValue();

        return true;

    }

    /**
     * First call: get every reachable buildableCity assuming the player pays 0 for election
     * Second call: get every reachable buildableCity given that the player has to pay for satisfaction
     */
    private Map<City, Integer> getReachableCities(int satisfactionCost) {

        Field field = match.getField();

        City[] arrayCity = field.getArrayCity();

        int indexCurrentCity = field.getKing().getCurrentCity().getCityName().ordinal();

        List<Integer>[] arrayCityLinks = field.getArrayCityLinks();

        boolean[] visitedCities = new boolean[arrayCityLinks.length];

        Queue<Integer> visitingLevelQueue = new LinkedList<>();

        Queue<Integer> nextLevelQueue = new LinkedList<>();

        visitingLevelQueue.add(indexCurrentCity);
        visitedCities[indexCurrentCity] = true;

        Map<City, Integer> movableCities = new LinkedHashMap<>();

        int levelCost = 0;

        movableCities.put(arrayCity[indexCurrentCity], levelCost);

        while (player.getRichness() - satisfactionCost >= levelCost) {
            while (!visitingLevelQueue.isEmpty()) {
                int visitingCity = visitingLevelQueue.remove();
                for (Integer adjCity : arrayCityLinks[visitingCity])
                    if (!visitedCities[adjCity]) {
                        nextLevelQueue.add(adjCity);
                        visitedCities[adjCity] = true;
                        if (!arrayCity[adjCity].isEmporiumAlreadyBuilt(player))
                            movableCities.put(arrayCity[adjCity], levelCost);
                    }
            }
            while (!nextLevelQueue.isEmpty())
                visitingLevelQueue.add(nextLevelQueue.remove());
            levelCost += 2;
        }

        return movableCities;

    }

    /* First it adds Multicolor cards to usable cards, then for each councillor,
	   if it finds a matching color card, it adds it and goes to next councillor */
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
     * Second call: if the specified set is eligible, the method returns what the player has to pay
     * @param arrayListPoliticCards politic cards the player wants to use to satisfy council
     * @param minimumKingCost minimum cost the player has to pay to move the king
     * @return
     */
    private int calculateSatisfactionCost(ArrayList<PoliticCard> arrayListPoliticCards, int minimumKingCost) {

        Route richnessRoute = match.getField().getRichnessRoute();
        int playerRichness= richnessRoute.getPosition(player);

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

        Field field=match.getField();

        Route richnessRoute = field.getRichnessRoute();

        richnessRoute.movePlayer(-(satisfactionCost+kingCost), player);

        for (PoliticCard politicCard : chosenPoliticCards)
            player.discardPoliticCard(politicCard);

        field.getKing().setCurrentCity(chosenCity);

        chosenCity.buildEmporium(player);

        resultMsg="Player " + player.getNickname() + " has moved the king to " +
                chosenCity.getCityName().toString() + " city and built an emporium there.";

        //check on bonus tiles
        checkBonusTiles();

    }

    private void checkBonusTiles() {

        int playerRichness = player.getRichness();

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

            player.setRichness(playerRichness+increment);

            for(City city: match.getField().getArrayCity())
                if(city.getCityColor() == chosenCity.getCityColor())
                    city.setColorBonusSatisfied(true);

            resultMsg = resultMsg + "\nDoing so, he acquired "
                    + chosenCity.getCityColor().toString() +
                    " Bonus Tile.";

            if(kingRewardTiles.peekFirst()!=null) {
                increment = kingRewardTiles.pollFirst();
                player.setRichness(playerRichness + kingRewardTiles.pollFirst());
                resultMsg = resultMsg + "\n He also acquired "
                        + increment + " victory points thanks to King's Reward Tile";
            }

        }

        if(isEligibleForRegionTile()){

            player.setRichness(playerRichness +Constant.REGION_TILE_VICTORY_INCREMENT);

            int chosenRegionIndex=RegionName.getIndex(chosenCity.getRegionName());
            Region chosenRegion = match.getField().getRegionFromIndex(chosenRegionIndex);
            chosenRegion.setRegionBonusSatisfied(true);

            resultMsg = resultMsg + "\nDoing so, he acquired "
                    + chosenCity.getRegionName().toString() +
                    " Bonus Tile.";

            if(kingRewardTiles.peekFirst()!=null) {
                increment = kingRewardTiles.pollFirst();
                player.setRichness(playerRichness + kingRewardTiles.pollFirst());
                resultMsg = resultMsg + "\n He also acquired "
                        + increment + " victory points thanks to King's Reward Tile";
            }

        }

    }

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

    private boolean isEligibleForColorTile() {

        if(chosenCity.isColorBonusSatisfied() || chosenCity.getCityColor()==CityColor.Purple)
            return false;

        City[] arrayCity = match.getField().getArrayCity();

        for (City city : arrayCity)
            if (city.getCityColor() == chosenCity.getCityColor() && !city.isEmporiumAlreadyBuilt(player))
                return false;

        return true;

    }

    @Override
    public String getResultMsg(){return resultMsg;}

}