package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.polimi.ingsw.server.control.Constant;
import it.polimi.ingsw.server.model.AvailableCouncillors;
import it.polimi.ingsw.server.model.Councillor;
import org.junit.Test;

public class TestAvailableCouncilor {

	private AvailableCouncillors availableCouncillors = new AvailableCouncillors();
	private ArrayList<Councillor> arrayListCouncillor = availableCouncillors.getArrayListCouncillor();
	
	@Test
	public void initialCouncilors() {
		/*
		 * This test controls the correct
		 * list of councilers that are created the first time
		 */
		ArrayList <Color> arrayListColors = new ArrayList <>();
		for (int i=0; i<7; i++){
			arrayListColors.add(Color.getColorFromIndex(i));
		}		
	
		for(Councillor c: arrayListCouncillor){
			assertNotNull(c);
			assertTrue(arrayListColors.contains(c.getColor()));
			assertFalse(c.equals(arrayListColors.get(6)));
		}
	
		assertEquals(Constant.COUNCILLORS_NUMBER_TOTAL, arrayListCouncillor.size());
	}
	
	@Test
	public void addAndRemoveAvalilableCouncillor(){
		Councillor c = arrayListCouncillor.remove(Constant.COUNCILLORS_NUMBER_TOTAL-1);
		assertEquals(Constant.COUNCILLORS_NUMBER_TOTAL-1, arrayListCouncillor.size());
		arrayListCouncillor.add(c);
		assertEquals(Constant.COUNCILLORS_NUMBER_TOTAL, arrayListCouncillor.size());
		
		c = availableCouncillors.removeAvailableCouncillor(Color.getColorFromIndex(0));
		assertEquals(Color.getColorFromIndex(0), c.getColor());
		assertEquals("White", c.getColor().toString());
	}
	

}
