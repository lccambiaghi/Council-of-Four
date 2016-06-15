package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.model.field.AvailableCouncillors;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Councillor;

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
