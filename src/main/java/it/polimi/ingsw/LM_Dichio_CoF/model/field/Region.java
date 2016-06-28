package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class Region {

	private RegionName regionName;
	private City[] arrayCity;
	private FaceUpPermitCardArea faceUpPermitCardArea;
	private boolean regionBonusSatisfied;
	
	/* The constructor takes as parameters the name of the region
	 * and the array of city that belongs to it.
	 * It also creates the faceUpPermitCardArea, calling his constructor */
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

	public void setRegionBonusSatisfied(boolean regionBonusSatisfied) {
		this.regionBonusSatisfied = regionBonusSatisfied;
	}
}
