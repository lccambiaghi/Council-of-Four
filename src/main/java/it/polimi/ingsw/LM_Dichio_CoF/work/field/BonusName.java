package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

/**
 * Created by Luca on 22/05/16.
 */
public enum BonusName {

    Assistant(3),
    Richness(5),
    Nobility(2),
    Victory(5),
    Cards(3),
    MainMove(1);

    private int maxIncrement;

    BonusName(int maxIncrement) {this.maxIncrement=maxIncrement;}

    /*
    public static BonusName getBonusName (int index){return values()[index];}
     */

    public int getMaxIncrement(BonusName bonusName){return bonusName.maxIncrement;}

    public static BonusName getRandomBonusName(){
    	Random random = new Random();
    	return values()[random.nextInt(values().length)];
    }
    
}
