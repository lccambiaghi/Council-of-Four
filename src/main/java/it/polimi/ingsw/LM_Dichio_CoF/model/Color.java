package it.polimi.ingsw.LM_Dichio_CoF.model;
import java.util.Random;

public enum Color {
	
	White,
	Black, 
	Cyan, 
	Orange, 
	Pink, 
	Purple,
	Multicolor;
	
	/* The constructor assigns rgb*/

	public static Color getRandomColor(){
		Random random = new Random ();
		return values()[random.nextInt(values().length)];
	}
	
	public static Color getColorFromIndex(int index){
		return Color.values()[index];
	}

}
