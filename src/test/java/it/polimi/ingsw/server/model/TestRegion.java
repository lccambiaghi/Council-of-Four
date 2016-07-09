package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.City;
import it.polimi.ingsw.server.model.Region;
import it.polimi.ingsw.server.model.RegionName;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.server.TestCases;

public class TestRegion {

	TestCases testCases = new TestCases();
	
	@Test
	public void constructor() {
		
		Region seaRegion = new Region(RegionName.Sea, testCases.arrayCity(), testCases.configurations());
		Region hillRegion = new Region(RegionName.Hill, testCases.arrayCity(), testCases.configurations());
		Region mountainRegion = new Region(RegionName.Mountain, testCases.arrayCity(), testCases.configurations());
		
		assertNotNull(seaRegion);
		assertNotNull(hillRegion);
		assertNotNull(mountainRegion);
		
		
	}

}
