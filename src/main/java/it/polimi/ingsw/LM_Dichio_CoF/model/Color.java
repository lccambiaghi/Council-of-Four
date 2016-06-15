package it.polimi.ingsw.LM_Dichio_CoF.model;
import java.util.Random;

public enum Color {
	
	White("255, 255, 255"),
	Black("0, 0, 0"), 
	Cyan ("0, 128, 255"), 
	Orange ("255, 128, 0"), 
	Pink("255, 153, 255"), 
	Purple("88, 86, 214"),
	Multicolor("Multicolor"); //gestisci poi multicolor
	
	public String rgb;

	/* The constructor assigns rgb*/
	Color(String rgb) { this.rgb=rgb; }

	public static Color getRandomColor(){
		Random random = new Random ();
		return values()[random.nextInt(values().length)];
	}
	
	public static Color getColorFromIndex(int index){
		return Color.values()[index];
	}

	public String getRgb() {
		return rgb;
	}
	public void setRgb(String rgb) {
		this.rgb = rgb;
	}

}
