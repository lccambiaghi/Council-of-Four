package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.HashMap;

public class Configurations {

	int numberCities;

	int PermitCardBonusNumberMin;
	int PermitCardBonusNumberMax;
	
	boolean nobilityBonusRandom;
	int nobilityBonusNumber; //how many bonus the user wants on the nobilityRoute

	boolean cityLinksPreconfigured;
	char difficulty; // e, n, h
	int cityLinksMatrix[][];

	boolean cityBonusRandom;
	int cityBonusNumberMin;
	int cityBonusNumberMax;
	HashMap<String,Integer> cityBonusArray[];
	
	public Configurations(int numberCities, char difficulty, int cityBonusNumberMin,
						  int cityBonusNumberMax, int PermitCardBonusNumberMin, int PermitCardBonusNumberMax){
		
		this.numberCities=numberCities;
		this.difficulty=difficulty;
		this.cityBonusNumberMin = cityBonusNumberMin;
		this.cityBonusNumberMax = cityBonusNumberMax;
		this.PermitCardBonusNumberMin = PermitCardBonusNumberMin;
		this.PermitCardBonusNumberMax = PermitCardBonusNumberMax;
		
	}

	public char getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(char difficulty) {
		this.difficulty = difficulty;
	}

	public int getCityBonusNumberMin() {
		return cityBonusNumberMin;
	}

	public void setCityBonusNumberMin(int cityBonusNumberMin) {
		this.cityBonusNumberMin = cityBonusNumberMin;
	}

	public int getCityBonusNumberMax() {
		return cityBonusNumberMax;
	}

	public void setCityBonusNumberMax(int cityBonusNumberMax) {
		this.cityBonusNumberMax = cityBonusNumberMax;
	}

	public int getNumberCities() {
		return numberCities;
	}

	public void setNumberCities(int numberCities) {
		this.numberCities = numberCities;
	}
	
	public int getPermitCardBonusNumberMin() {
		return PermitCardBonusNumberMin;
	}

	public void setPermitCardBonusNumberMin(int permitCardBonusNumberMin) {
		this.PermitCardBonusNumberMin = permitCardBonusNumberMin;
	}

	public int getPermitCardBonusNumberMax() {
		return PermitCardBonusNumberMax;
	}

	public void setPermitCardBonusNumberMax(int permitCardBonusNumberMax) {
		this.PermitCardBonusNumberMax = permitCardBonusNumberMax;
	}
}
