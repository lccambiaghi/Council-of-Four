package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Region;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.RegionName;

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
