package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Bonus;
import it.polimi.ingsw.server.model.City;
import org.junit.Test;

import it.polimi.ingsw.server.TestCases;

public class TestCity {
	
	private TestCases testCases = new TestCases();
	
	@Test
	public void bonus() {
				
		City[] arrayCity = testCases.arrayCity();
		
		for(City city: arrayCity){
			System.out.println(city.getCityName());
			if(city.hasBonus()){
				Bonus[] arrayBonus = city.getArrayBonus();
				
			for(Bonus bonus: arrayBonus){
					System.out.print(bonus.getBonusName() +" " + bonus.getIncrement() + " ");
				}
				System.out.println();
			}
		}
		
	} 

}
