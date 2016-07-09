package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Bonus;
import it.polimi.ingsw.server.model.City;
import it.polimi.ingsw.server.model.FaceUpPermitCardArea;
import it.polimi.ingsw.server.model.PermitCard;
import org.junit.Test;

import it.polimi.ingsw.server.TestCases;

public class TestFaceUpPermitCardArea {

	TestCases testCases = new TestCases();
	
	@Test
	public void constructor() {
		
		FaceUpPermitCardArea faceUpPermitCardArea = new FaceUpPermitCardArea(testCases.arrayCity(), testCases.configurations());
		
		
		PermitCard[] arrayPermitCard = new PermitCard[faceUpPermitCardArea.getArrayPermitCard().length];
		
		arrayPermitCard = faceUpPermitCardArea.getArrayPermitCard();
		
		for(PermitCard permitCard: arrayPermitCard){
			
			City[] arrayCity = new City[permitCard.getArrayBuildableCities().length];
			arrayCity = permitCard.getArrayBuildableCities();
			for(City city: arrayCity){
				System.out.print(city.getCityName() +" ");
			}
			System.out.println();
			Bonus[] arrayBonus = permitCard.getArrayBonus();
			if (permitCard.hasBonus()){
				for(Bonus bonus: arrayBonus){
					System.out.print(bonus.getBonusName().toString() +" ");
					System.out.println(bonus.getIncrement());
				}
			}
			System.out.println();
		}
	}

}
