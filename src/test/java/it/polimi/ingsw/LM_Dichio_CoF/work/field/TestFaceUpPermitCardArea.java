package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import junit.framework.TestCase;

public class TestFaceUpPermitCardArea {

	TestCases testCases = new TestCases();
	
	@Test
	public void constructor() {
		
		FaceUpPermitCardArea faceUpPermitCardArea = new FaceUpPermitCardArea(testCases.arrayCity(), testCases.configurations());
		
		
		PermitCard[] arrayPermitCard = new PermitCard[faceUpPermitCardArea.getArrayPermitCard().length];
		
		arrayPermitCard = faceUpPermitCardArea.getArrayPermitCard();
		
		for(PermitCard permitCard: arrayPermitCard){
			
			City[] arrayCity = new City[permitCard.getArrayCityWhereBuild().length];
			arrayCity = permitCard.getArrayCityWhereBuild();
			for(City city: arrayCity){
				System.out.print(city.getNameCity() +" ");
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
