package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import org.junit.Test;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Region;
import junit.framework.Assert;
import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.NameRegion;

public class TestRegion {
	
	@Test
	public void citiesInRegion(){
		
		TestCases testCases = new TestCases();
		
		Configurations config =testCases.configurations();
		
		Region region = new Region(config, NameRegion.Mountain);
		
		City[] arrayCityPerRegion = region.getArrayCity();
		
		for (City city : arrayCityPerRegion){
			System.out.println(city.getNameCity());
		}
		
	}
	
	
	
}
