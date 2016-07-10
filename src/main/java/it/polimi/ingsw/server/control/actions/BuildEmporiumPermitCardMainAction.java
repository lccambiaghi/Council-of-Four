package it.polimi.ingsw.server.control.actions;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.utils.Constant;
import it.polimi.ingsw.utils.Message;

import java.util.*;

/**
 * Preliminary steps: checks if there exists a permitCard - among all of the player's - which allows the player to build
 * in a city where he has not yet built.
 *
 * Execute: build the emporium in the chosenCity, applies bonuses to adjacent cities,
 * discards used permitCard,
 */
public class BuildEmporiumPermitCardMainAction extends Action {

	private PermitCard chosenPermitCard;

	private City chosenCity;

	private int numberAssistantsToPay;

	/**
	 * @param match : the match in which the move is being executed
	 * @param player : the player who executes the move
	 */
	public BuildEmporiumPermitCardMainAction(Match match, Player player){

		this.match=match;

		this.player=player;

    }

    @Override
    public boolean preliminarySteps() throws InterruptedException{

		if (player.getArrayListEmporiumBuilt().size() == Constant.NUMBER_EMPORIUMS_TO_WIN){

			player.getBroker().println(Message.youCantBuildMaxEmporium());

			return false;

		}

		int usablePermitCards = sortPermitCards();

		if (usablePermitCards==0){

			player.getBroker().println(Message.youCantBuild());

			return false;

		}

		ArrayList<PermitCard> playerPermitCards = player.getArrayListPermitCard();

		player.getBroker().println(Message.choosePermitCardNoBonus(playerPermitCards, usablePermitCards));

		chosenPermitCard = playerPermitCards.get(player.getBroker().askInputNumber(1,usablePermitCards)-1); //array positioning

		int buildableCities = sortBuildableCities(chosenPermitCard);

		City[] arrayBuildableCities = chosenPermitCard.getArrayBuildableCities();

		player.getBroker().println(Message.chooseCityToBuild(arrayBuildableCities, buildableCities));

		chosenCity = arrayBuildableCities[player.getBroker().askInputNumber(1, buildableCities) - 1 ]; //-1 for array positioning

		for(Player otherPlayer : chosenCity.getArrayListEmporium())
			if(otherPlayer!=player)
				numberAssistantsToPay++;

		if(numberAssistantsToPay>player.getAssistant()){
			player.getBroker().println(Message.notEnoughAssistant());
			return false;
		}

		return true;

    }

	@Override
    public void execute(){

		Field field=match.getField();

		player.decrementAssistant(numberAssistantsToPay);

		chosenCity.buildEmporium(player);

		player.usePermitCard(chosenPermitCard);

		ArrayList<City> nearbyBuiltCities = getAdjacentBuiltCities(chosenCity);

		for (City city: nearbyBuiltCities)
			if(city.hasBonus())
				for (Bonus bonus : city.getArrayBonus())
					bonus.applyBonus(player, field);

        resultMsg="Player "+player.getNickname() +" has built an emporium in "
        		+ chosenCity.getCityName().toString() + " City.";

		checkBonusTiles();

	}

	/**
	 *  This method puts usablePermitCards in front,
	 *  so we can use the input of the user to pick the corresponding card
	 *  */
	private int sortPermitCards() {

		ArrayList<PermitCard> playerPermitCards = player.getArrayListPermitCard();

		int numberUsablePermitCards=0;

		for (int i = 0; i < playerPermitCards.size(); i++) {

			PermitCard permitCard = playerPermitCards.get(i);

			City[] arrayCity = permitCard.getArrayBuildableCities();

			for (int j = 0; j < arrayCity.length; j++) {

				if (!arrayCity[j].isEmporiumAlreadyBuilt(player)) {

					Collections.swap(playerPermitCards, i, numberUsablePermitCards);

					numberUsablePermitCards++;

					break;

				}
			}

		}

		return numberUsablePermitCards;

	}

	/**
	 *  This method puts buildableCities in front,
	 *  so we can use the input of the user to pick the corresponding city
	 *  */
	private int sortBuildableCities(PermitCard permitCard) {

		City[] arrayCity = permitCard.getArrayBuildableCities();

		int buildableCities=0;

		for (int j = 0; j < arrayCity.length; j++) {

			City city = arrayCity[j];

			/**
			 * cityToSwap is the first non-buildable city in the array
			 * for example: [X] [√] [√]
			 * at second iteration j=2 and buildableCities=0, which is the index of the city to swap
			 * we swap and the result is [√] [X] [√]
			 * next iteration : [√] [√] [X]
			 */
			City cityToSwap;

			if (!city.isEmporiumAlreadyBuilt(player)) {

				cityToSwap = arrayCity[buildableCities];

				arrayCity[buildableCities] = city;

				arrayCity[j] = cityToSwap;

				buildableCities++;

			}

		}

		return buildableCities;

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
				resultMsg = resultMsg + "\n He also acquired "
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