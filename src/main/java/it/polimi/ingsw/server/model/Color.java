package it.polimi.ingsw.server.model;
import java.util.Random;

/**
 * This class collects all colors of the game
 */
public enum Color {
	
	White,
	Black, 
	Cyan, 
	Orange, 
	Pink, 
	Purple,
	Multicolor;

	public static Color getRandomColor(){
		Random random = new Random ();
		return values()[random.nextInt(values().length)];
	}

	/**
	 * @param index of the color
	 * @return color at specified index
     */
	public static Color getColorFromIndex(int index){
		return Color.values()[index];
	}

}
