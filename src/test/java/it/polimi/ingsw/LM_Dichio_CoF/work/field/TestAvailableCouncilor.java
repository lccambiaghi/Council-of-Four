package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.*;

public class TestAvailableCouncilor {

	@Test
	public void initialCouncilors() {
		/*
		 * This test controls the correct implementation of the singleton and the 
		 * list of councilers that are created the first time
		 */
		AvailableCouncilor availableCouncilor = new AvailableCouncilor();
		
		ArrayList<Councilor> a = availableCouncilor.getArrayListCouncilor();
		
		for(Councilor c: a){
			System.out.println(c.getColor());
		}
	
		assertEquals(24, a.size());
	}

}
