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

	int indexChosenCity;
	PermitCard chosenPermitCard;
	int indexChosenPermitCard;
	
    public BuildEmporiumPermitCardMainAction(Match match, Player player){
        this.match=match;
        this.player=player;
    }

    @Override
    public boolean preliminarySteps(){
    	ArrayList <PermitCard> usablePermitCards = getUsablePermitCards();

		if (usablePermitCards.size()<1){
			Message.notEnoughPoliticsCardsAndRichness(player);
			return false;
		}
		
		indexChosenPermitCard = choosePermitCard(usablePermitCards);
		chosenPermitCard = usablePermitCards.get(indexChosenPermitCard);
		indexChosenCity = chooseBuildableCity(chosenPermitCard);
		
		return true;
    }

    @Override
    public void execute(){
    	Field field = match.getField();
    	City[] arrayCity = field.getArrayCity();
    	
		arrayCity[indexChosenCity].buildEmporium(player);
		player.usePermitCard(player.getArrayListPermitCard().get(indexChosenPermitCard));

		// applying bonuses
		ArrayList<City> nearbyBuiltCities = getAdjacentBuiltCities(indexChosenCity);

		for (City city: nearbyBuiltCities)
			for (Bonus bonus : city.getArrayBonus())
				bonus.applyBonus(player, field);
        resultMsg="Player "+player.getNickname() +"has built an emporium in "
        		+ arrayCity[indexChosenCity].getCityName().toString() + " City" + ".\n";
    }
    
    @Override
    public String getResultMsg(){return resultMsg;}

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
    
	private int choosePermitCard(ArrayList<PermitCard> usablePermitCards) {

		Message.choosePermitCard(player);

		for(int i=0; i<usablePermitCards.size();i++) {

			PermitCard usablePermitCard = usablePermitCards.get(i); // ArrayIndexOutOfBoundException?

			System.out.println(i + 1 + ". ");
			System.out.println("Buildable Cities:");

			City[] arrayBuildableCities = usablePermitCard.getArrayBuildableCities();
			for (City buildableCity : arrayBuildableCities)
				System.out.print(buildableCity.getCityName() + " ");

			System.out.println("Bonus:");
			Bonus[] arrayBonus = usablePermitCard.getArrayBonus();
			for (Bonus bonus : arrayBonus)
				System.out.print(bonus.getBonusName() + " ");
		}

		return Broker.askInputNumber(1, usablePermitCards.size(), player)-1; // -1 for array positioning
	}
    
	private int chooseBuildableCity(PermitCard chosenPermitCard) {

		Message.chooseCity(player);

		City[] actualBuildableCities = chosenPermitCard.getArrayBuildableCities();
		for (int i = 0; i < actualBuildableCities.length; i++) {
			City buildableCity = actualBuildableCities[i];
			System.out.println(i + 1 + ". " + buildableCity.getCityName());
		}

		return Broker.askInputNumber(1, actualBuildableCities.length, player) - 1; // -1 for array positioning

	}

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
			for (Integer adjCity : arrayCityLinks[visitingCity]) {
				if (arrayCity[adjCity].isEmporiumAlreadyBuilt(player) && !visitedCities[adjCity]) {
					toVisitQueue.add(adjCity);
					visitedCities[adjCity] = true;
				}
			}
		}

		return nearbyBuiltCities;

	}
	
	
	
	
	
}