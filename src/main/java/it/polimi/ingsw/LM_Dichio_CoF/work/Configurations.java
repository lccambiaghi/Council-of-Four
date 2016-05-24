package it.polimi.ingsw.LM_Dichio_CoF.work;

public class Configurations {

	int numberCities;
	char difficulty;
	
	int minNumberBonusPerCity;
	int maxNumberBonusPerCity;
	
	int minNumberBonusPerPermitCard;
	int maxNumberBonusPerPermitCard;
	
	public Configurations(int numberCities, char difficulty, int minNumberBonusPerCity,
			int maxNumberBonusPerCity,int minNumberBonusPerPermitCard, int maxNumberBonusPerPermitCard){
		
		this.numberCities=numberCities;
		this.difficulty=difficulty;
		this.minNumberBonusPerCity=minNumberBonusPerCity;
		this.maxNumberBonusPerCity=maxNumberBonusPerCity;
		this.minNumberBonusPerPermitCard=minNumberBonusPerPermitCard;
		this.maxNumberBonusPerPermitCard=maxNumberBonusPerPermitCard;
		
	}


	public char getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(char difficulty) {
		this.difficulty = difficulty;
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
	
	public int getMinNumberBonusPerPermitCard() {
		return minNumberBonusPerPermitCard;
	}


	public void setMinNumberBonusPerPermitCard(int minNumberBonusPerPermitCard) {
		this.minNumberBonusPerPermitCard = minNumberBonusPerPermitCard;
	}


	public int getMaxNumberBonusPerPermitCard() {
		return maxNumberBonusPerPermitCard;
	}


	public void setMaxNumberBonusPerPermitCard(int maxNumberBonusPerPermitCard) {
		this.maxNumberBonusPerPermitCard = maxNumberBonusPerPermitCard;
	}
}
