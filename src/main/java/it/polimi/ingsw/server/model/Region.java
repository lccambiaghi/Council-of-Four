package it.polimi.ingsw.server.model;

/**
 * The class Region stores the RegionName, the ArrayCity of the Cities belonging to the Region and 
 * FaceUpPermitCardArea where there are two usable Permit Cards. 
 */

public class Region {

	private final RegionName regionName;
	private final City[] arrayCity;
	private final FaceUpPermitCardArea faceUpPermitCardArea;
	private boolean regionBonusSatisfied;
	
	/**
	 * The constructor takes as parameters the name of the region
	 * and the array of city that belongs to it.
	 * It also creates the faceUpPermitCardArea, calling his constructor
	 * 
	 * @param regionName: Name of the region took from the Enum Region Name
	 * @param arrayCity: the ArrayCity of the Cities belonging to the Region
	 * @param config: configurations of the match
	 */
	 
	public Region(RegionName regionName, City[] arrayCity, Configurations config){

		this.regionName = regionName;
		this.arrayCity = arrayCity;
		regionBonusSatisfied=false;

		faceUpPermitCardArea = new FaceUpPermitCardArea(arrayCity, config);
		
	}
	
	public City[] getArrayCity() {
		return arrayCity;
	}
	
	public FaceUpPermitCardArea getFaceUpPermitCardArea(){
		return faceUpPermitCardArea;
	}
	
	public RegionName getRegionName(){
		return regionName;
	}

	public boolean isRegionBonusSatisfied() {
		return regionBonusSatisfied;
	}

	/**
	 * This method is used when a player has built an emporium in each city of the Region
	 * and he took the Victory Bonus, so no one can win an other time the same bonus.
	 * @param regionBonusSatisfied
	 */
	public void setRegionBonusSatisfied(boolean regionBonusSatisfied) {
		this.regionBonusSatisfied = regionBonusSatisfied;
	}
}
