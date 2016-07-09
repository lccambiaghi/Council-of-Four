package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.polimi.ingsw.server.control.Constant;
import it.polimi.ingsw.server.model.AvailableCouncillors;
import it.polimi.ingsw.server.model.Balcony;
import it.polimi.ingsw.server.model.Councillor;
import it.polimi.ingsw.server.model.RegionName;
import org.junit.Test;

public class TestBalcony {
	
	AvailableCouncillors availableCouncillors = new AvailableCouncillors();
	Balcony balcony1 = new Balcony (availableCouncillors, RegionName.Sea.toString()+"Balcony");
	ArrayList<Councillor> arrayListCouncillor1=balcony1.getArrayListCouncillor();
	ArrayList <Councillor> arrayListAvailableCouncillors = availableCouncillors.getArrayListCouncillor();
	
	@Test
	public void councilorsInBalcony() {
		
		Balcony balcony2 = new Balcony (availableCouncillors, RegionName.Hill.toString()+"Balcony");
		Balcony balcony3 = new Balcony (availableCouncillors, RegionName.Mountain.toString()+"Balcony");
		Balcony balcony4 = new Balcony (availableCouncillors, "KingBalcony");
		
		ArrayList<Councillor> arrayListCouncillor2 = balcony2.getArrayListCouncillor();
		ArrayList<Councillor> arrayListCouncillor3 = balcony3.getArrayListCouncillor();
		ArrayList<Councillor> arrayListCouncillor4 = balcony4.getArrayListCouncillor();
		
		assertEquals(4, arrayListCouncillor1.size());
		assertEquals(4, arrayListCouncillor2.size());
		assertEquals(4, arrayListCouncillor3.size());
		assertEquals(4, arrayListCouncillor4.size());
		
		assertEquals(Constant.COUNCILLORS_NUMBER_TOTAL-(Constant.COUNCILLORS_PER_BALCONY_NUMBER*4),
				arrayListAvailableCouncillors.size());
	}
	
	@Test
	public void addCouncillor(){
		Councillor c = arrayListAvailableCouncillors.get(0);
		Councillor oldCouncillor= balcony1.getArrayListCouncillor().get(Constant.COUNCILLORS_PER_BALCONY_NUMBER-1);
		
		balcony1.electCouncillor(c, availableCouncillors);
		
		assertTrue(arrayListCouncillor1.contains(c));
		assertFalse(arrayListCouncillor1.contains(oldCouncillor));
		assertTrue(arrayListAvailableCouncillors.contains(oldCouncillor));
	}

}
