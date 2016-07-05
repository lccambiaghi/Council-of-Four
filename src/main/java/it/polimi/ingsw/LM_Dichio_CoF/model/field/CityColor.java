package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import java.util.Random;

public enum CityColor {

    Red,
    Purple,
    Blue,
    Bronze,
    Gold,
    Silver;

    public static CityColor getCityColorFromIndex(int index){ return CityColor.values()[index]; }

    public static CityColor getRandomCityColor(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
