package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class TestField {

	TestCases testCases = new TestCases();
	
	@Test
	public void constructor() {
		
		Field field = new Field(testCases.configurations(),testCases.arrayListPlayer());
		
		City[] arrayCity = field.getArrayCity();
		
		for(City city: arrayCity){
			
			System.out.println("Region: " + city.getNameRegion());
			System.out.println("City: " + city.getNameCity());
			System.out.println("Color: " + city.getCityColor());
			
			City[] arrayCityConnected = city.getNearbyCity();
			for(City cityConnected: arrayCityConnected){
				System.out.println("City connected: " + cityConnected.getNameCity());
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
