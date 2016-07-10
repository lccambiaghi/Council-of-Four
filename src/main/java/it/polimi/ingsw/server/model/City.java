package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The City class offers methods to build an emporium and to get nearby cities in order to apply them
 * bonuses. It also has colorBonusSatisfied attribute to check for satisfaction of the Color Bonus Tiles condition
 */
public class City {

	private final CityName cityName;

	private final RegionName regionName;

	private Bonus[] arrayBonus;

	private City[] nearbyCity;

	private final CityColor cityColor;

	private ArrayList<Player> arrayListEmporium;

	private boolean colorBonusSatisfied;

	/**
	 * This is the constructor called when no configurations are created
	 *
	 * @param config from which to take number of bonus of the city
	 * @param cityName to assign to the newly created city
	 * @param regionName of the belonging region
	 * @param cityColor to assign to the newly created city
     */
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

	/**
	 * This is the constructor used when new configurations are created by the user
	 *
	 * @param arrayListCityBonus to assign to the city
	 * @param cityName to assign to the city
	 * @param regionName of the belonging region
	 * @param cityColor to assign to the city
     */
	public City (List<CityBonus> arrayListCityBonus, CityName cityName, RegionName regionName, CityColor cityColor){

		this.cityName = cityName;
		this.regionName = regionName;
		this.cityColor=cityColor;
		if(arrayListCityBonus!=null)
			this.arrayBonus=arrayListCityBonus.toArray(new Bonus[arrayListCityBonus.size()]);
		else
			this.arrayBonus=null;
		arrayListEmporium = new ArrayList<>();
				
	}

	/**
	 * @param player who builds the emporium
     */
	public void buildEmporium (Player player){

		if(!isEmporiumAlreadyBuilt(player)) {
			arrayListEmporium.add(player);
			player.addCityEmporiumBuilt(this);
		}

	}

	/**
	 * @param player to check
	 * @return true if already built
     */
	public boolean isEmporiumAlreadyBuilt(Player player){

		if (arrayListEmporium==null)
			return false;

		for (Player anArrayListEmporium : arrayListEmporium)
			if (anArrayListEmporium == player)
				return true;

		return false;

	}

	/**
	 * @return true if the city has bonus, false otherwise
     */
	public boolean hasBonus(){
		return !(arrayBonus == null || arrayBonus.length == 0);
	}
	public Bonus[] getArrayBonus() {
		return arrayBonus;
	}
	
	public CityName getCityName() {
		return cityName;
	}
	
	public RegionName getRegionName() {
		return regionName;
	}

	/**
	 * @return array of nearby cities
     */
	public City[] getNearbyCity() {
		return nearbyCity;
	}

	/**
	 * @param nearbyCity array of cities to set as this.nearbyCities
     */
	public void setNearbyCity(City[] nearbyCity) {
		this.nearbyCity = nearbyCity;
	}

	public CityColor getCityColor() {
		return cityColor;
	}

	/**
	 * @return players who have built in this city
     */
	public List<Player> getArrayListEmporium() {
		return arrayListEmporium;
	}

	public boolean isColorBonusSatisfied() {
		return colorBonusSatisfied;
	}

	public void setColorBonusSatisfied(boolean colorBonusSatisfied){
		this.colorBonusSatisfied=colorBonusSatisfied;
	}

}
