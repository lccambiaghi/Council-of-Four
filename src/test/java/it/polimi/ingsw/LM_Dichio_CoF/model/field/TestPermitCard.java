package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.PermitCard;

public class TestPermitCard {

	@Test
	public void constructor() {
		
		
		TestCases testCases = new TestCases();
		
		City[] arrayCity = testCases.arrayCity();
		
		int n=10;
		PermitCard[] arrayPermitCard = new PermitCard[n];
		
		
		
		for(PermitCard p: arrayPermitCard){
			
			p = new PermitCard(arrayCity,testCases.configurations());
			City[] arrayCityWhereBuild = p.getArrayBuildableCities();
			
			for(City c: arrayCityWhereBuild){
				System.out.print(c.getCityName().toString());
			}
			
			System.out.println();
		}
		
	}

}
