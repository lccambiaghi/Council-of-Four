package it.polimi.ingsw.server.control;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class is used to show the status of the match at the players. The three final methods are used
 * to print the map, the first is used when there is a match with 15 cities, and so on.
 * 
 * Furthermore the user can see the info of all cities, how the cities are linked, the position of the king,
 * and the info of a specific city, to know the color and who has already built an emporium in it, and
 * the bonuses that it has.
 *   
 * In addition the player can know the others player status and their positions on the routes and his status
 * as for example how many assistants he has and the Permit Card that he has.
 *
 */

public class InfoMatch {

	private final String noneMsg = "-- none --";
	
	private final Field field;
	
	private final City[] arrayCity;
	private final ArrayList<Player> arrayListPlayer;
	
	private Player player;
	
	/**
	 * The positive numbers, from 1 to 15 (18 or 21), are the index of the cities in the Enumeration
	 * CityName. The number -3 is the border of a region. 
	 */
	
	private final int[] posLow =
		   	{1,	0, 	4, 	-3, 6, 	0, 	9, 	-3,	11,	0,	14,	-1,
			0,	0,	0,	-3,	0,	0,	0,	-3,	0,	0,	0,	-1,
		   	2, 	0, 	5, 	-3, 7, 	0,	10, -3,	12,	0,	15,	-1,
			0,	0,	0,	-3,	0,	0,	0,	-3,	0,	0,	0,	-1,
			0, 	3, 	0, 	-3, 0, 	8, 	0, 	-3,	 0,	13,	0,	-2};
	
	private final int[] posMedium =
			{1,	0,	4,	-3,	7,	0,	10,	-3,	13,	0,	16,	-1,
			0,	0,	0,	-3,	0,	0,	0,	-3,	0,	0,	0,	-1,
			2,	0,	5,	-3,	8,	0,	11,	-3,	14,	0,	17,	-1,
			0,	0,	0,	-3,	0,	0,	0,	-3,	0,	0,	0,	-1,
			3,	0,	6,	-3,	9,	0,	12,	-3,	15,	0,	18,	-2};
	
	private final int[] posHigh =
			{1,	0,	5,	-3,	8,	0,	12,	-3,	15,	0,	19,	-1,
			0,	4,	0,	-3,	0,	11,	0,	-3,	0,	18,	0,	-1,		
			2,	0,	6,	-3,	9,	0,	13,	-3,	16,	0,	20,	-1,
			0,	0,	0,	-3,	0,	0,	0,	-3,	0,	0,	0,	-1,
			3,	0,	7,	-3,	10,	0,	14,	-3,	17,	0,	21,	-2};
	
	
	private void print(String string){

		player.getBroker().print(string);

	}
	
	private void println(){

		player.getBroker().println("");

	}
	
	private void println(String string){

		player.getBroker().println(string);

	}
	
	public void setPlayer(Player player){
		this.player=player;
	}
	
	
	public InfoMatch(Match match){
		this.field = match.getField();
		this.arrayListPlayer = match.getArrayListPlayer();
		this.arrayCity = field.getArrayCity();
	}
	
	/**
	 * This method shows the info of the cities. It prints all the cities and how the cities are linked.
	 * In addition it prints in which city there is the king.
	 */
	
	public void printInfoAllCities() {
		println();
		printCities();
		printCityLinks();
		printKingPosition();
		println();
	}
	
	public void printInfoAllPlayers() {
		println();
		printPlayersInRoutes();
		println();
	}
	
	public void printInfoBalconies() {
		println();
		printBalconies();
		printAvailableCouncillors();
		println();	
	}
	
	public void printInfoPlayer(Player player) {

		println();
		println("You have...");
		printPolitcCards();
		println("- Richness points: "+ player.getRichness());
		println("- Victory points: "+ player.getVictory());
		println("- Nobility points: "+ field.getNobilityRoute().getPosition(player));
		printAssistantNumber();
		printPermitCards();
		printYourCities();
		println();
		
	}
	/**
	 * This method is used to know the attributes of a specific city. The city is passed as a parameter
	 * 
	 * @param indexCity: the index of the city in the Enumeration
	 * 
	 * Then it shows the bonuses that are in the city, whos has already built an emporium in it, 
	 * the color of the city. 
	 */
	public void printInfoCity(int indexCity) {
		
		println();
		City chosenCity= arrayCity[indexCity];
		println("- City: "+ chosenCity.getCityName().toString());
		println("- Color: "+ chosenCity.getCityColor());
		print("- Bonus: ");
		if(!chosenCity.hasBonus()){
			println(noneMsg);
		}else{
			println();
			Bonus[] arrayBonus = chosenCity.getArrayBonus();
			for(Bonus bonus: arrayBonus){
				println(bonus.getBonusName() + " " + bonus.getIncrement());
			}
		}
		print("- Emporiums built by: ");
		ArrayList<Player> arrayListEmporium = (ArrayList<Player>) chosenCity.getArrayListEmporium();
		if(arrayListEmporium==null){
			print(noneMsg);
		}else{
			println();
			for(Player emporium: arrayListEmporium){
				println(emporium.getNickname());
			}
		}
		println();
	}
	
	/**
	 * This method prints all the regions and the Permit Card that are in the face up permit card area
	 * of the regions, with their bonuses and their buildable cities.
	 */
	public void printInfoRegions() {
		println();
		for(int i=0; i<Constant.REGIONS_NUMBER; i++){
			Region region = field.getRegionFromIndex(i);
			println("REGION: " + region.getRegionName());
			FaceUpPermitCardArea face = region.getFaceUpPermitCardArea();
			PermitCard[] arrayPermitCard = face.getArrayPermitCard();
			for(int j=0; j<arrayPermitCard.length; j++){
				println("Permit Card " + (j+1) + ":");
				print("- Buildable Cities: ");
				for(City city: arrayPermitCard[j].getArrayBuildableCities())
					print(city.getCityName().toString() + " ");
				println();
				print("- Bonus: ");
				if(!arrayPermitCard[j].hasBonus()){
					println(noneMsg);
				}else{
					println();
					Bonus[] arrayBonus = arrayPermitCard[j].getArrayBonus();
					for(Bonus bonus: arrayBonus){
						println(bonus.getBonusName() + " " + bonus.getIncrement());
					}
				}
				println();
			}
			
		}
	}
	
	/**
	 * This is the method to print the Map from the arrays poslow, posmedium e poshigh. 
	 * 
	 * As I explained before, the numbers that are in the arrays represent the borders of the map.
	 */
	private void printCities() {
		
		int[] positions;
		
		switch(arrayCity.length){
			case Constant.CITIES_NUMBER_LOW:
				positions=posLow;
				break;
			case Constant.CITIES_NUMBER_MEDIUM:
				positions=posMedium;
				break;
			default: // high
				positions=posHigh;
				break;
		}

		for(int pos: positions){
			switch(pos){
			case	0:
				print("  ");
				break;
			case	-1:
			case	-2:
				println();
				break;
			case 	-3:
				print("| ");
				break;
			default:
				print(arrayCity[pos-1].getCityName().toString() +" ");
			}
		}
		
		printRegions();
		println();
		
	}
	
	private void printRegions() {
		println(" " + RegionName.Sea + "    " + RegionName.Hill + "   " + RegionName.Mountain);
	}
	
	/**
	 * This method read the matrix where are the cities links.
	 */
	private void printCityLinks() {
		
		println("City links:\n ");
		
		List<Integer>[] arrayCityLinks = field.getArrayCityLinks();
		for(int i=0; i<arrayCityLinks.length; i++){
			print(arrayCity[i].getCityName().toString() + ": ");
			for(int j=0;j<arrayCityLinks[i].size();j++){
			    print(arrayCity[arrayCityLinks[i].get(j)].getCityName().toString());
			    if(j!=arrayCityLinks[i].size()-1)
			    	print(", ");
			} 
			println();
		}
		
		println();
	}
	
	private void printKingPosition(){
		println("King position: " + field.getKing().getCurrentCity().getCityName().toString());
		println();
	}
	
	/**
	 * This method print all the balconies with their councillor. The first is at the left in the balcony
	 * the last is in the right side.
	 */
	private void printBalconies() {
		println();
		Balcony[] arrayBalconies = field.getArrayBalcony();
		for(Balcony balcony: arrayBalconies){
			println( balcony.getNameBalcony() + ":");
			ArrayList<Councillor> arrayListCouncillor = (ArrayList<Councillor>) balcony.getArrayListCouncillor();
			for(Councillor councillor: arrayListCouncillor){
				print("-> " + councillor.getColor() + "  ");
			}
			println();
		}
		println();
			
	}
	
	/**
	 * It shows the available councillors that players can elects.
	 */
	private void printAvailableCouncillors() {
		
		println();
		ArrayList<Councillor> arrayListCouncillor = (ArrayList<Councillor>) field.getAvailableCouncillors().getArrayListCouncillor();
		println("Available councillors:");
		for(Councillor councillor: arrayListCouncillor){
			print(councillor.getColor() + "  ");
		}
		println();
	}
	
	/**
	 * It shows the position of the players in the Richness Route, Victory Route and Nobility Route. 
	 */
	private void printPlayersInRoutes() {
		println("Players status:");
		for(Player aPlayer: arrayListPlayer){
			println(aPlayer.getNickname() + ": " +
					"Richness [" + aPlayer.getRichness() + "], "+
					"Victory [" + aPlayer.getVictory() + "], " +
					"Nobility [" +field.getNobilityRoute().getPosition(aPlayer) + "]");
		}
		println();
	}
	
	private void printPolitcCards() {
		print("- Politic cards: ");
		for(PoliticCard politicCard: player.getArrayListPoliticCard()){
			print(politicCard.getCardColor() + " ");
		}
		println();
	}
	
	private void printAssistantNumber() {
		println("- Number of assistants: " + player.getAssistant());
	}
	
	private void printPermitCards() {
		print("- Permit cards: ");
		ArrayList<PermitCard> arrayListPermitCard = player.getArrayListPermitCard();
		if(arrayListPermitCard.isEmpty())
			println(noneMsg);
		else{
			println();
			for(int i=0; i<arrayListPermitCard.size(); i++){
				PermitCard permitCard =  arrayListPermitCard.get(i);
				print(i+1 + ". Buildable Cities: ");
				for(City city: permitCard.getArrayBuildableCities())
					print(city.getCityName().toString() + " ");
				println();
			}
		}
	}
	
	private void printYourCities() {
		print("- Emporiums built in these cities: ");
		ArrayList<City> arrayListEmporiumBuilt = player.getArrayListEmporiumBuilt();
		if(arrayListEmporiumBuilt.isEmpty())
			println(noneMsg);
		else{
			for(City city: player.getArrayListEmporiumBuilt()){
				print(city.getCityName().toString() + " ");
			}
			println();
		}
		println();
	}


	

}
