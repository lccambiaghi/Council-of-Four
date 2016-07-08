package it.polimi.ingsw.server.model.field;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.polimi.ingsw.server.model.AvailableCouncillors;
import it.polimi.ingsw.server.model.Balcony;
import it.polimi.ingsw.server.model.Councillor;
import it.polimi.ingsw.server.model.RegionName;
import org.junit.Test;

;

public class TestBalcony {

	@Test
	public void councilorsInBalcony() {
		
		AvailableCouncillors availableCouncillors = new AvailableCouncillors();
		
		Balcony balcony = new Balcony (availableCouncillors, RegionName.Sea.toString()+"Balcony");
		
		ArrayList<Councillor> arrayListCouncillor = balcony.getArrayListCouncillor();
		
		System.out.println(balcony.getNameBalcony());
		for(Councillor c: arrayListCouncillor){
			System.out.println(c.getColor());
		}
		
		assertEquals(4, arrayListCouncillor.size());
		
		
	}

}
