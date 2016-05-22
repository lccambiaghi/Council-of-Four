package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import org.junit.Test;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Region;
import junit.framework.Assert;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.NameCity;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.NameRegion;

public class TestRegion {
	
	@Test
	public void citiesInRegion(){
		
		Region region = new Region(8, NameRegion.Mountain,16);
		
		
		City[] arrayCity = new City[7];
		
		arrayCity = region.getArrayCity();
		
		for (City city : arrayCity){
			System.out.println(city.getName());
		}
		
	}
	
	
	
}
