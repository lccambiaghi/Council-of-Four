package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Bonus;

public class City {

	private NameCity nameCity;
	private NameRegion nameRegion;
	private Bonus[] arrayBonus;
	private City[] nearbyCity;
	private CityColor cityColor;
	private String bonusName;
	private Integer bonusIncrement;
	

	/*
	 * The constructor receives the configurations, the name of the city given by the Region
	 * and the name of the region itself
	 */
	public City(Configurations config, NameCity nameCity, NameRegion nameRegion, CityColor cityColor){
		int numberBonus;
		Random random = new Random();
		
		this.nameCity=nameCity;
		this.nameRegion=nameRegion;
		this.cityColor=cityColor;
		
		/* 
		 * This "if" refers to the city of the king: it doesn't have to have bonuses
		 */
		if(!nameCity.equals(Constant.INITIAL_KING_CITY)){
			numberBonus= random.nextInt(config.getMaxNumberBonusPerCity()-
					config.getMinNumberBonusPerCity()+1) +
					config.getMinNumberBonusPerCity();
			
			if(numberBonus!=0){
				arrayBonus = BonusCity.getArrayBonusCity(numberBonus);
			}
		}	
	}	
	
	public City (Map <String, Integer> bonusMap, NameCity nameCity, NameRegion nameRegion, CityColor cityColor){
		this.nameCity=nameCity;
		this.nameRegion=nameRegion;
		this.cityColor=cityColor;
				
		Iterator <Entry<String, Integer>> mapIterator = bonusMap.entrySet().iterator();
		while(mapIterator.hasNext()){
			Map.Entry entry = (Map.Entry)mapIterator.next();
			bonusName = (String)entry.getKey();
			bonusIncrement = (Integer)entry.getValue();
			
			
		}
		
	}
	
	public NameCity getNameCity() {
		return nameCity;
	}

	public void setNameCity(NameCity nameCity) {
		this.nameCity = nameCity;
	}
	
	public NameRegion getNameRegion() {
		return nameRegion;
	}

	public void setNameRegion(NameRegion nameRegion) {
		this.nameRegion = nameRegion;
	}

	public Bonus[] getArrayBonus() {
		return arrayBonus;
	}
	
	public City[] getNearbyCity() {
		return nearbyCity;
	}

	public void setNearbyCity(City[] nearbyCity) {
		this.nearbyCity = nearbyCity;
	}
	
	
	/*
	 * This methods tells if the current city has bonus or not (if it is null)
	 */
	public boolean hasBonus(){
		if (arrayBonus== null)
			return false;
		return true;
	}

	public CityColor getCityColor() {
		return cityColor;
	}

}
