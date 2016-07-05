package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import java.util.Random;

public enum BonusName {

    Assistant(3),
    Richness(4),
    Nobility(1),
    Victory(5),
    Cards(3),
    MainMove(1);

    private int maxIncrement;

    /* The constructor assigns the increment to the bonus*/
    BonusName(int maxIncrement) {this.maxIncrement=maxIncrement;}

    /*
    public static BonusName getBonusName (int index){return values()[index];}
     */

    public static BonusName getRandomPermitCardBonusName(){
    	Random random = new Random();
    	return values()[random.nextInt(values().length)];
    }
    
    public static BonusName getRandomCityBonusName(){
    	Random random = new Random();
    	return values()[random.nextInt(values().length-1)];
    }

    /* This method checks if the String parameter is a bonusName */
    public static boolean containString(String test) {

        for (BonusName bonusName : BonusName.values()) {
            if (bonusName.name().equals(test)) {
            	return true;
            }
        }
        return false;
    }
    
    public static BonusName stringToBonusName(String test) {

        for (BonusName bonusName : BonusName.values()) {
            if (bonusName.name().equals(test)) {
            	return bonusName;
            }
        }
		return null;
    }
    
    public static BonusName getBonusName(BonusName bonusName){
    	return bonusName;
    }

    public static int getMaxIncrement(BonusName bonusName){return bonusName.maxIncrement;}

	public static BonusName getBonusNameFromIndex(int index){ return values()[index]; }

}
