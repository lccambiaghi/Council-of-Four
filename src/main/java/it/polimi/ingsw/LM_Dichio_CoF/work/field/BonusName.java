package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

public enum BonusName {

    Assistant(3),
    Richness(4),
    Nobility(1),
    Victory(5),
    Cards(3),
    MainMove(1),
    BuiltCityBonus(1),
    FaceUpPermitCard(1),
    OwnedPermitCardBonus(1),
    TwoBuiltDifferentBonus(1);

    private int maxIncrement;

    /* The constructor assigns the increment to the bonus*/
    BonusName(int maxIncrement) {this.maxIncrement=maxIncrement;}

    /*
    public static BonusName getBonusName (int index){return values()[index];}
     */

    public static BonusName getRandomBonusName(){
    	Random random = new Random();
    	return values()[random.nextInt(values().length-4)];
    }
    
    public static BonusName getRandomBonusNameWithoutMainMove(){
    	Random random = new Random();
    	return values()[random.nextInt(values().length-5)];
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

    public int getMaxIncrement(BonusName bonusName){return bonusName.maxIncrement;}

}
