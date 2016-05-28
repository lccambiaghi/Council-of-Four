package it.polimi.ingsw.LM_Dichio_CoF.work.field;

public enum RegionName {

	Sea, Hill, Mountain;
	
	public static int getIndex (RegionName regionName){
		return regionName.ordinal();
	}
	
	public static RegionName getNameRegion (int index){
		return values()[index];
	}
	
}
