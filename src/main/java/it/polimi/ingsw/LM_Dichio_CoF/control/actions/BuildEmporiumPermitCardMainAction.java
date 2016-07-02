package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;

public class BuildEmporiumPermitCardMainAction extends Action {

	private int indexChosenCity;
	private PermitCard chosenPermitCard;
	private City chosenCity;

	public BuildEmporiumPermitCardMainAction(Match match, Player player){

		this.match=match;

		this.player=player;

    }

    @Override
    public boolean preliminarySteps() throws InterruptedException{

		ArrayList <PermitCard> usablePermitCards = getUsablePermitCards();

		if (usablePermitCards.isEmpty()){

			player.getBroker().println(Message.youCantBuild());
			return false;
		}

		player.getBroker().println(Message.choosePermitCard(usablePermitCards));

		chosenPermitCard = usablePermitCards.get(player.getBroker().askInputNumber(1,usablePermitCards.size())-1); //array positioning

		City[] buildableCities = chosenPermitCard.getArrayBuildableCities();

		player.getBroker().println(Message.chooseCity(buildableCities));

		chosenCity = buildableCities[player.getBroker().askInputNumber(1, buildableCities.length)];
		
		return true;

    }

    @Override
    public void execute(){

		Field field=match.getField();

		chosenCity.buildEmporium(player);

		player.usePermitCard(chosenPermitCard);

		// applying bonuses
		ArrayList<City> nearbyBuiltCities = getAdjacentBuiltCities(indexChosenCity);

		for (City city: nearbyBuiltCities)
			for (Bonus bonus : city.getArrayBonus())
				bonus.applyBonus(player, field);

        resultMsg="Player "+player.getNickname() +" has built an emporium in "
        		+ chosenCity.getCityName().toString() + " City.";

		//check on bonus tiles
		if(isEligibleForColorTile()){

			int increment;

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

			player.setRichness(player.getRichness()+increment);

			for(City city: field.getArrayCity())
				if(city.getCityColor() == chosenCity.getCityColor())
					city.setColorBonusSatisfied(true);

			resultMsg = resultMsg + "\nDoing so, he acquired "
					+ chosenCity.getCityColor().toString() +
					" Bonus Tile.";

		}

		if(isEligibleForRegionTile()){

			player.setRichness(player.getRichness()+Constant.REGION_TILE_VICTORY_INCREMENT);

			int chosenRegionIndex=RegionName.getIndex(chosenCity.getRegionName());
			Region chosenRegion = match.getField().getRegionFromIndex(chosenRegionIndex);
			chosenRegion.setRegionBonusSatisfied(true);

			resultMsg = resultMsg + "\nDoing so, he acquired "
					+ chosenCity.getRegionName().toString() +
					" Bonus Tile.";

		}

    }

	@Override
    public String getResultMsg(){return resultMsg;}

	/*  This method creates an arrayList of usablePermitCards setting actualBuildableCities.
           It moves these permitCards in the front of the player's hand to make them
           easily removable once the player selects one of them  */
	private ArrayList<PermitCard> getUsablePermitCards() {

		ArrayList <PermitCard> usablePermitCards= new ArrayList<>();

		ArrayList<PermitCard> playerPermitCards = player.getArrayListPermitCard();
		for (int i = 0; i < playerPermitCards.size(); i++) {

			PermitCard permitCard = playerPermitCards.get(i);

			ArrayList <City> actualBuildableCities = new ArrayList<>();
			for (City buildableCity : permitCard.getArrayBuildableCities()) {
				if (!buildableCity.isEmporiumAlreadyBuilt(player))
					actualBuildableCities.add(buildableCity);
			}

			if (actualBuildableCities.isEmpty()) {
				Collections.swap(playerPermitCards,i,usablePermitCards.size());
				usablePermitCards.add(permitCard);
				permitCard.setArrayBuildableCities(actualBuildableCities);
			}
		}

		return usablePermitCards;
    }

	/* Queue of adjacent built cities. It starts only with chosenCity.
		Elements of queue are removed and analysed one at a time.
		If a city is linked to the analysed element of the queue and emporium is present,
		the city is also added to the queue */
	private ArrayList<City> getAdjacentBuiltCities(int indexChosenCity) {

		Field field = match.getField();

		List<Integer>[] arrayCityLinks = field.getArrayCityLinks();

		boolean[] visitedCities = new boolean[arrayCityLinks.length];

		Queue<Integer> toVisitQueue = new LinkedList<>();

		toVisitQueue.add(indexChosenCity);
		visitedCities[indexChosenCity] = true;

		ArrayList<City> nearbyBuiltCities = new ArrayList<>();
		City[] arrayCity=field.getArrayCity();
		while (!toVisitQueue.isEmpty()) {

			int visitingCity = toVisitQueue.remove();

			nearbyBuiltCities.add(arrayCity[visitingCity]);

			for (Integer adjCity : arrayCityLinks[visitingCity])
				if (arrayCity[adjCity].isEmporiumAlreadyBuilt(player) && !visitedCities[adjCity]) {
					toVisitQueue.add(adjCity);
					visitedCities[adjCity] = true;
				}

		}

		return nearbyBuiltCities;

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
	
}