package it.polimi.ingsw.LM_Dichio_CoF.work.field;

public enum NameRegion {

	Sea, Hill, Mountain;
	
	public static int getIndex (NameRegion nameRegion){
		return nameRegion.ordinal();
	}
	
}
