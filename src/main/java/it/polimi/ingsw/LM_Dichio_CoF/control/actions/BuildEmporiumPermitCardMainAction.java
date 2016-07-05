package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;

import java.util.*;

public class BuildEmporiumPermitCardMainAction extends Action {

	private PermitCard chosenPermitCard;

	private City chosenCity;

	public BuildEmporiumPermitCardMainAction(Match match, Player player){

		this.match=match;

		this.player=player;

    }

    @Override
    public boolean preliminarySteps() throws InterruptedException{

		int usablePermitCards = sortPermitCards();

		if (usablePermitCards==0){

			player.getBroker().println(Message.youCantBuild());

			return false;

		}

		ArrayList<PermitCard> playerPermitCards = player.getArrayListPermitCard();

		player.getBroker().println(Message.choosePermitCard(playerPermitCards, usablePermitCards));

		chosenPermitCard = playerPermitCards.get(player.getBroker().askInputNumber(1,usablePermitCards)-1); //array positioning

		int buildableCities = sortBuildableCities(chosenPermitCard);

		City[] arrayBuildableCities = chosenPermitCard.getArrayBuildableCities();

		player.getBroker().println(Message.chooseCityToBuild(arrayBuildableCities, buildableCities));

		chosenCity = arrayBuildableCities[player.getBroker().askInputNumber(1, buildableCities)];
		
		return true;

    }

	/**
	 *  This method puts usablePermitCards in front,
	 *  so we can use the input of the user to pick the corresponding card
	 *  */
	private int sortPermitCards() {

		ArrayList<PermitCard> playerPermitCards = player.getArrayListPermitCard();

		int usablePermitCards=0;

		for (int i = 0; i < playerPermitCards.size(); i++) {

			PermitCard permitCard = playerPermitCards.get(i);

			City[] arrayCity = permitCard.getArrayBuildableCities();

			for (int j = 0; j < arrayCity.length; j++) {

				if (!arrayCity[j].isEmporiumAlreadyBuilt(player)) {

					Collections.swap(playerPermitCards, i, usablePermitCards);

					usablePermitCards++;

					break;

				}
			}

		}

		return usablePermitCards;

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

	@Override
    public void execute(){

		Field field=match.getField();

		chosenCity.buildEmporium(player);

		player.usePermitCard(chosenPermitCard);

		// applying bonuses
		ArrayList<City> nearbyBuiltCities = getAdjacentBuiltCities(chosenCity);

		for (City city: nearbyBuiltCities)
			for (Bonus bonus : city.getArrayBonus())
				bonus.applyBonus(player, field);

        resultMsg="Player "+player.getNickname() +" has built an emporium in "
        		+ chosenCity.getCityName().toString() + " City.";

		//check on bonus tiles
		checkBonusTiles();

	}

	/* Queue of adjacent built cities. It starts only with chosenCity.
		Elements of queue are removed and analysed one at a time.
		If a city is linked to the analysed element of the queue and emporium is present,
		the city is also added to the queue */
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