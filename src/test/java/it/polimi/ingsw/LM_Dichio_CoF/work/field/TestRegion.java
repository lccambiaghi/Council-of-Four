package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;

public class TestRegion {

	TestCases testCases = new TestCases();
	
	@Test
	public void constructor() {
		
		Region region = new Region(RegionName.Mountain, testCases.arrayCity(), testCases.configurations());
		
		City[] arrayCity = region.getArrayCity();
		
		for(City city: arrayCity){
			System.out.println(city.getCityName().toString());
		}
		
	}

}
