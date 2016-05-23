package it.polimi.ingsw.LM_Dichio_CoF.work.field;

public class Region {

	NameRegion nameRegion;
	City[] arrayCity;
	FaceUpPermitCardArea faceUpPermitCardArea;
	
	/*
	 * The constructor of Region takes as parameters the name of the region
	 * and the array of city that belongs to it.
	 * It also creates the faceUpPermitCardArea, calling his constructor
	 */
	public Region(NameRegion nameRegion, City[] arrayCity){
		
		this.nameRegion = nameRegion;
		this.arrayCity = arrayCity;
		
		faceUpPermitCardArea = new FaceUpPermitCardArea(arrayCity);
		
	}

	
	public City[] getArrayCity() {
		return arrayCity;
	}
	
	public FaceUpPermitCardArea getFaceUpPermitCardArea(){
		return faceUpPermitCardArea;
	}

	
}
