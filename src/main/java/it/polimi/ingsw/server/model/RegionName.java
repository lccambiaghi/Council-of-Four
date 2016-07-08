package it.polimi.ingsw.server.model;

public enum RegionName {

	Sea, Hill, Mountain;
	
	public static int getIndex (RegionName regionName){return regionName.ordinal();}
	
	public static RegionName getRegionNameFromIndex(int index){return values()[index];}
	
}
