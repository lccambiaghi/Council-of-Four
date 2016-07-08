package it.polimi.ingsw.server.model.field;

import it.polimi.ingsw.server.model.City;
import it.polimi.ingsw.server.model.Region;
import it.polimi.ingsw.server.model.RegionName;
import org.junit.Test;

import it.polimi.ingsw.server.TestCases;

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
