package it.polimi.ingsw.LM_Dichio_CoF;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.AvailableCouncilor;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Balcony;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Councilor;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.NameRegion;;

public class TestBalcony {

	@Test
	public void test() {
		
		AvailableCouncilor availableCouncilor = new AvailableCouncilor();
		
		Balcony balcony = new Balcony (availableCouncilor, NameRegion.Sea.toString()+"Balcony");
		
		ArrayList<Councilor> arrayListCouncilor = balcony.getArrayListCouncilor();
		
		System.out.println(balcony.getNameBalcony());
		for(Councilor c: arrayListCouncilor){
			System.out.println(c.getColor());
		}
		
		assertEquals(4, arrayListCouncilor.size());
		
		
	}

}
