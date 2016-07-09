package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Bonus;
import it.polimi.ingsw.server.model.City;
import it.polimi.ingsw.server.model.Field;
import org.junit.Test;

import it.polimi.ingsw.server.TestCases;

public class TestField {

	TestCases testCases = new TestCases();
	
	@Test
	public void constructor() {
		
		Field field = new Field(testCases.configurations(),testCases.arrayListPlayer());
		
		City[] arrayCity = field.getArrayCity();
		
		for(City city: arrayCity){
			
			System.out.println("Region: " + city.getRegionName());
			System.out.println("City: " + city.getCityName());
			System.out.println("Color: " + city.getCityColor());
			
			City[] arrayCityConnected = city.getNearbyCity();
			for(City cityConnected: arrayCityConnected){
				System.out.println("City connected: " + cityConnected.getCityName());
			}	
			
			if(city.hasBonus()){
				Bonus[] arrayBonus = city.getArrayBonus();
				for(Bonus bonus: arrayBonus){
					System.out.print(bonus.getBonusName() + " " + bonus.getIncrement()+ "  ");
				}
			}
			System.out.println();
			System.out.println();
		}
		
	}	
	
	
	/*
	@Test
	public void creationNearbyCities(){
		
		assignNearbyCities("n".charAt(0), testCases.arrayCity());
	
	
	}
	*/

}
