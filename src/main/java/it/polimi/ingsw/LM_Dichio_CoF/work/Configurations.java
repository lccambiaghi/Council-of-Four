package it.polimi.ingsw.LM_Dichio_CoF.work;

public class Configurations {

	int numberCities;
	int minNumberBonusPerCity;
	int maxNumberBonusPerCity;
	
	public Configurations(int numberCities, int minNumberBonusPerCity,
			int maxNumberBonusPerCity){
		
		this.numberCities=numberCities;
		this.minNumberBonusPerCity=minNumberBonusPerCity;
		this.maxNumberBonusPerCity=maxNumberBonusPerCity;
		
	}
	
	
	public int getMinNumberBonusPerCity() {
		return minNumberBonusPerCity;
	}

	public void setMinNumberBonusPerCity(int minNumberBonusPerCity) {
		this.minNumberBonusPerCity = minNumberBonusPerCity;
	}

	public int getMaxNumberBonusPerCity() {
		return maxNumberBonusPerCity;
	}

	public void setMaxNumberBonusPerCity(int maxNumberBonusPerCity) {
		this.maxNumberBonusPerCity = maxNumberBonusPerCity;
	}

	public int getNumberCities() {
		return numberCities;
	}

	public void setNumberCities(int numberCities) {
		this.numberCities = numberCities;
	}
	
}
