package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class FieldTest {

	TestCases testCases = new TestCases();
	
	@Test
	public void constructor() {
		
		Field field = new Field(testCases.configurations(),testCases.arrayListPlayer());
		
		City[] arrayCity = field.getArrayCity();
		
		for(City city: arrayCity){
			System.out.println(city.getNameRegion());
			System.out.println(city.getNameCity());
			if(city.hasBonus()){
				Bonus[] arrayBonus = new Bonus[city.getArrayBonus().length];
				arrayBonus = city.getArrayBonus();
				for(Bonus bonus: arrayBonus){
					System.out.print(bonus.getBonusName() + " " + bonus.getIncrement()+ "  ");
				}
			}
			System.out.println();
			System.out.println();
		}
		
	}

}
