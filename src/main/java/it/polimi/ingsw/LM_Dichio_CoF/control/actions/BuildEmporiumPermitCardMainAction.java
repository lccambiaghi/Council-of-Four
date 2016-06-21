package it.polimi.ingsw.LM_Dichio_CoF.control.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.control.Message;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Bonus;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Field;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.PermitCard;

public class BuildEmporiumPermitCardMainAction extends Action {

	private int indexChosenCity;
	private PermitCard chosenPermitCard;
	private City chosenCity;

	public BuildEmporiumPermitCardMainAction(Match match, Player player){

		this.match=match;

		this.player=player;

    }

    @Override
    public boolean preliminarySteps(){

		ArrayList <PermitCard> usablePermitCards = getUsablePermitCards();

		if (usablePermitCards.size()<1){

			Broker.sendString(Message.youCantBuild(),player);
			return false;
		}

		Broker.sendString(Message.choosePermitCard(usablePermitCards),player);

		chosenPermitCard = usablePermitCards.get(Broker.askInputNumber(1,usablePermitCards.size(), player)-1); //array positioning

		City[] buildableCities = chosenPermitCard.getArrayBuildableCities();

		Broker.sendString(Message.chooseCity(buildableCities), player);

		chosenCity = buildableCities[Broker.askInputNumber(1, buildableCities.length, player)];
		
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
        		+ chosenCity.getCityName() + " City" + ".\n";

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

			if (actualBuildableCities.size() > 0) {
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
	
	
	
	
	
}