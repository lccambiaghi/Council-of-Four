package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class TestRegion {

	TestCases testCases = new TestCases();
	
	@Test
	public void constructor() {
		
		Region region = new Region(NameRegion.Mountain, testCases.arrayCity());
		
		City[] arrayCity = region.getArrayCity();
		
		for(City city: arrayCity){
			System.out.println(city.getNameCity().toString());
		}
		
	}

}
