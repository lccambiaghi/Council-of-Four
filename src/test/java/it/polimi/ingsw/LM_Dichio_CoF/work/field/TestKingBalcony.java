package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.AvailableCouncilor;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Balcony;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Councilor;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.KingBalcony;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.NameRegion;;

public class TestKingBalcony {

	@Test
	public void councilorsInKingBalcony() {
		
		AvailableCouncilor availableCouncilor = new AvailableCouncilor();
		
		// Polymorphism
		Balcony kingBalcony = new KingBalcony (availableCouncilor,"KingBalcony");
		
		ArrayList<Councilor> arrayListCouncilor = kingBalcony.getArrayListCouncilor();
		
		System.out.println(kingBalcony.getNameBalcony());
		for(Councilor c: arrayListCouncilor){
			System.out.println(c.getColor());
		}
		
		assertEquals(4, arrayListCouncilor.size());
		
		
	}
	
}