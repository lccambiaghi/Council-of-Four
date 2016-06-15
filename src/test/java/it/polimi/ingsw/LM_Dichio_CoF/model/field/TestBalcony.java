package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.model.field.AvailableCouncillors;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Balcony;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Councillor;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.RegionName;

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
