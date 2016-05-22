package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class TestCity {
	
	TestCases testCases = new TestCases();
	
	@Test
	public void bonus() {
		
		
		City[] arrayCity = testCases.arrayCity();
		
		for(City city: arrayCity){
			System.out.println(city.getNameCity());
			Bonus[] arrayBonus = city.getArrayBonus();
			for(Bonus bonus: arrayBonus){
				System.out.print(bonus.getBonusName() +" " + bonus.getIncrement() + " ");
			}
			System.out.println();
		}
		
	}

}
