package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.*;

public class TestPermitCard {

	@Test
	public void constructor() {
		
		
		TestCases testCases = new TestCases();
		
		City[] arrayCity = testCases.arrayCity();
		
		int n=10;
		PermitCard[] arrayPermitCard = new PermitCard[n];
		
		
		
		for(PermitCard p: arrayPermitCard){
			
			p = new PermitCard(arrayCity);
			City[] arrayCityWhereBuild = p.getArrayCityWhereBuild();
			
			for(City c: arrayCityWhereBuild){
				System.out.print(c.getName().toString());
			}
			
			System.out.println();
		}
		
	}

}
