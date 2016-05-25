package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;

public class TestNobilityRouteCell {

	@Test
	public void Costruttore() {
		
		NobilityRouteCell [] arrayNobilityRouteCell = new NobilityRouteCell[Constant.MAX_NOBILITY];

		for (int i=0; i<Constant.MAX_NOBILITY; i++){
			arrayNobilityRouteCell[i] = new NobilityRouteCell(i); 
		}
		Collections.shuffle(Arrays.asList(arrayNobilityRouteCell));
		for (NobilityRouteCell a : arrayNobilityRouteCell){
			if (a.hasBonus()){
				for(Bonus bonus : a.getArrayBonus()){
					System.out.print(bonus.getBonusName()+" ");
				}
				System.out.println();
				System.out.println();
			}
			else {
				System.out.println(0);
				System.out.println();
			}
		}
	}

}
