package it.polimi.ingsw.LM_Dichio_CoF.work.field;
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
	
	private Color(String rgb) {
		this.rgb=rgb;
	}
	public String getRgb() {
		return rgb;
	}
	public void setRgb(String rgb) {
		this.rgb = rgb;
	}
	
	// the method getRandomColor select a Color from the enum list 
	public static Color getRandomColor(){
		Random random = new Random ();
		return values()[random.nextInt(values().length)];
	}
	
	public static Color getColorFromIndex(int index){
		return Color.values()[index];
	}
}
