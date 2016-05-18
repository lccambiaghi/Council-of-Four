package it.polimi.ingsw.LM_Dichio_CoF.work.field;
import java.util.Random;

public enum Colors {
	
	White("255, 255, 255"),
	Black("0, 0, 0"), 
	Cyan ("0, 128, 255"), 
	Orange ("255, 128, 0"), 
	Pink("255, 153, 255"), 
	Purple("88, 86, 214");
	
	public String rgb;
		 
	private Colors (final String rgb){
		this.rgb=rgb;		
	}
	public String getRGB (){
		return rgb;
	}
	
	// the method getRandomColor select a Color from the enum list 
	public static Colors getRandomColor(){
		Random random = new Random ();
		return values()[random.nextInt(values().length)];
	}
}
