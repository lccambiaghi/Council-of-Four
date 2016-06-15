package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.model.field.AvailableCouncillors;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Balcony;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.Councillor;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.KingBalcony;

;

public class TestKingBalcony {

	@Test
	public void councilorsInKingBalcony() {
		
		AvailableCouncillors availableCouncillors = new AvailableCouncillors();
		
		// Polymorphism
		Balcony kingBalcony = new KingBalcony (availableCouncillors,"KingBalcony");
		
		ArrayList<Councillor> arrayListCouncillor = kingBalcony.getArrayListCouncillor();
		
		System.out.println(kingBalcony.getNameBalcony());
		for(Councillor c: arrayListCouncillor){
			System.out.println(c.getColor());
		}
		
		assertEquals(4, arrayListCouncillor.size());
		
		
	}
	
}