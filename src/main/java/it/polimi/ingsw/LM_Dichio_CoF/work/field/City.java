package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;
import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public class City {

	private CityName cityName;
	private RegionName regionName;
	private Bonus[] arrayBonus;
	private City[] nearbyCity;
	private CityColor cityColor;
	private ArrayList<Player> arrayListEmporium;

	/* Random bonus constructor: it receives the configurations, the name of the city given by the Region
	 * and the name of the region itself */
	public City(Configurations config, CityName cityName, RegionName regionName, CityColor cityColor){
		
		this.cityName = cityName;
		this.regionName = regionName;
		this.cityColor=cityColor;
		arrayListEmporium = new ArrayList<>();

		int numberBonus;
		Random random = new Random();
		
		/* This "if" refers to the city of the king: it's assigned no bonuses*/
		if(!cityName.equals(Constant.KING_CITY_INITIAL)){
			numberBonus= random.nextInt(config.getCityBonusNumberMax()-
					config.getCityBonusNumberMin()+1) +
					config.getCityBonusNumberMin();
			
			if(numberBonus!=0){
				arrayBonus = CityBonus.getArrayBonusCity(numberBonus);
			}
		}	
	}	

	/* Costruttore nuovo: riceve in ingresso la mappa con i bonus e gli incrementi
	 * Crea un arraylist di bonus (unico modo per gestire la dinamicità dei valori
	 * perchè non sappiamo quanti bonus l'utente inserirà. L'iterator scorre la mappa
	 * fin tanto che c'è un elemento. Passa poi i valori al nuovo costruttore dei Bonus 
	 * che riceve la stringa e l'incremento, dopo di che aggiunge il bonus all'arrayList
	 * di Bonus della città
	 */

	/* Assigned bonus constructor: it receives bonusMap, it creates arrayListBonusCity
	  and assigns them to the city */
	public City (Map <String, Integer> bonusMap, CityName cityName, RegionName regionName, CityColor cityColor){

		this.cityName = cityName;
		this.regionName = regionName;
		this.cityColor=cityColor;
					
		ArrayList <Bonus> arrayListBonusCity = new ArrayList <> ();

		for (Entry<String, Integer> stringIntegerEntry : bonusMap.entrySet()) {
			Entry entry = (Entry) stringIntegerEntry;
			String bonusName = (String) entry.getKey();
			Integer bonusIncrement = (Integer) entry.getValue();
			Bonus bonus = new Bonus(bonusName, bonusIncrement);
			arrayListBonusCity.add(bonus);
		}
		
		this.arrayBonus=arrayListBonusCity.toArray(new Bonus[arrayListBonusCity.size()]);
				
	}

	public int buildEmporium (Player player){

		if(isEmporiumAlreadyBuilt(player)) {
			arrayListEmporium.add(player);
			return arrayListEmporium.size();
		}
		else
			return -1; //emporium already built

	}

	public boolean isEmporiumAlreadyBuilt(Player player){

		for (int i=0; i< arrayListEmporium.size()-1;i++)
			if (arrayListEmporium.get(i)==player)
				return true;

		return false;
		// throw EmporiumAlreadyBuiltException??

	}

	/* This methods checks if the city has bonus */
	public boolean hasBonus(){ return arrayBonus != null; }
	
	public CityName getCityName() {
		return cityName;
	}

	public void setCityName(CityName cityName) {
		this.cityName = cityName;
	}
	
	public RegionName getRegionName() {
		return regionName;
	}

	public void setRegionName(RegionName regionName) {
		this.regionName = regionName;
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

	public CityColor getCityColor() {
		return cityColor;
	}

}
