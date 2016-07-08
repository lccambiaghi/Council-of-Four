package it.polimi.ingsw.server.model.field;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.polimi.ingsw.server.model.AvailableCouncillors;
import it.polimi.ingsw.server.model.Councillor;
import org.junit.Test;

public class TestAvailableCouncilor {

	@Test
	public void initialCouncilors() {
		/*
		 * This test controls the correct implementation of the singleton and the 
		 * list of councilers that are created the first time
		 */
		AvailableCouncillors availableCouncillors = new AvailableCouncillors();
		
		ArrayList<Councillor> a = availableCouncillors.getArrayListCouncillor();
		
		for(Councillor c: a){
			System.out.println(c.getColor());
		}
	
		assertEquals(24, a.size());
	}

}
