package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

import java.util.ArrayList;
import java.util.Random;

public class City {

	private CityName cityName;
	private RegionName regionName;
	private Bonus[] arrayBonus;
	private City[] nearbyCity;
	private CityColor cityColor;
	private ArrayList<Player> arrayListEmporium;
	private boolean colorBonusSatisfied;

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
				arrayBonus = CityBonus.createArrayCityBonus(numberBonus);
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
	public City (ArrayList <CityBonus> cityBonus, CityName cityName, RegionName regionName, CityColor cityColor){

		this.cityName = cityName;
		this.regionName = regionName;
		this.cityColor=cityColor;
		this.arrayBonus=cityBonus.toArray(new Bonus[cityBonus.size()]);
		arrayListEmporium = new ArrayList<>();
				
	}

	public void buildEmporium (Player player){

		if(!isEmporiumAlreadyBuilt(player)) {
			arrayListEmporium.add(player);
			player.addCityEmporiumBuilt(this);
		}

	}

	public boolean isEmporiumAlreadyBuilt(Player player){

		if (arrayListEmporium==null)
			return false;

		for (int i=0; i< arrayListEmporium.size()-1;i++)
			if (arrayListEmporium.get(i)==player)
				return true;

		return false;
		// throw EmporiumAlreadyBuiltException??

	}

	/* This methods checks if the city has bonus */
	public boolean hasBonus(){ return arrayBonus != null; }
	public Bonus[] getArrayBonus() {
		return arrayBonus;
	}
	
	public CityName getCityName() {
		return cityName;
	}
	
	public RegionName getRegionName() {
		return regionName;
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
	
	public ArrayList<Player> getArrayListEmporium() {
		return arrayListEmporium;
	}

	public boolean isColorBonusSatisfied() {
		return colorBonusSatisfied;
	}

	public void setColorBonusSatisfied(boolean colorBonusSatisfied){
		this.colorBonusSatisfied=colorBonusSatisfied;
	}

}
