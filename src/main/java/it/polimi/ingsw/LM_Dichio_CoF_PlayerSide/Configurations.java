package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.model.field.CityBonus;

public class Configurations implements Serializable{

	private int citiesNumber;

	private int permitCardBonusNumberMin;
	private int permitCardBonusNumberMax;
	
	boolean nobilityBonusRandom;

	int nobilityBonusNumber; //how many bonus the user wants on the nobilityRoute

	boolean cityLinksPreconfigured;
	private char difficulty; // e, n, h
	int cityLinksMatrix[][];

	boolean cityBonusRandom;
	private int cityBonusNumberMin;
	private int cityBonusNumberMax;
    private ArrayList<CityBonus> arrayListCityBonus[];

	public Configurations(){}

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
		return citiesNumber;
	}

	public void setCitiesNumber(int numberCities) {
		this.citiesNumber = numberCities;
	}
	
	public int getPermitCardBonusNumberMin() {
		return permitCardBonusNumberMin;
	}

	public void setPermitCardBonusNumberMin(int permitCardBonusNumberMin) {
		this.permitCardBonusNumberMin = permitCardBonusNumberMin;
	}

	public int getPermitCardBonusNumberMax() {
		return permitCardBonusNumberMax;
	}

	public void setPermitCardBonusNumberMax(int permitCardBonusNumberMax) {
		this.permitCardBonusNumberMax = permitCardBonusNumberMax;
	}
	
	public boolean isNobilityBonusRandom() {
		return nobilityBonusRandom;
	}

	public int getNobilityBonusNumber() {
		return nobilityBonusNumber;
	}

	public boolean isCityLinksPreconfigured() {
		return cityLinksPreconfigured;
	}

	public void setCityLinksPreconfigured(boolean cityLinksPreconfigured) {
		this.cityLinksPreconfigured = cityLinksPreconfigured;
	}

	public int[][] getCityLinksMatrix() {
		return cityLinksMatrix;
	}

	public void setCityLinksMatrix(int[][] cityLinksMatrix) {
		this.cityLinksMatrix = cityLinksMatrix;
	}

	public boolean isCityBonusRandom() {
		return cityBonusRandom;
	}

	public void setCityBonusRandom(boolean cityBonusRandom) {
		this.cityBonusRandom = cityBonusRandom;
	}

	public ArrayList<CityBonus>[] getArrayListCityBonus() {
		return arrayListCityBonus;
	}

	public void setArrayListCityBonus(ArrayList<CityBonus>[] arrayListCityBonus) {
		this.arrayListCityBonus = arrayListCityBonus;
	}

	public int getCitiesNumber() {
		return citiesNumber;
	}

	public void setNobilityBonusRandom(boolean nobilityBonusRandom) {
		this.nobilityBonusRandom = nobilityBonusRandom;
	}

	public void setNobilityBonusNumber(int nobilityBonusNumber) {
		this.nobilityBonusNumber = nobilityBonusNumber;
	}
	
	
}
