package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

/**
 * Created by Luca on 24/05/16.
 */
public enum CityColor {

    Red("255, 0, 0"), // Red solo per numberCities=21
    Purple("88, 86, 214"),
    Blue("0, 0, 255"),
    Bronze("205, 127, 50"),
    Gold("255, 215, 0"),
    Silver("192, 192, 192");

    private String rgb;

    CityColor(String rgb) {
        this.rgb=rgb;
    }
    public String getRgb() {
        return rgb;
    }
    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public static CityColor getCityColorFromIndex(int index){
        return CityColor.values()[index];
    }

    public static CityColor getRandomCityColor(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    /*
    public static CityColor getRandomCityColorWithoutRed(){
        Random random = new Random();
        return values()[random.nextInt(values().length-1)];
    }
    */

}
