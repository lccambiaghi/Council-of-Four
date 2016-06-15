package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.*;

public class InfoMatch {
	
	
	
	Match match;
	Field field;
	
	City[] arrayCity;
	List <Integer>[] arrayCityLinks; 
	ArrayList<Player> arrayListPlayer;
	
	Player player;
	
	int[] posLow =  
		   	{1,	0, 	4, 	-3, 6, 	0, 	9, 	-3,	11,	0,	14,	-1,
			0,	0,	0,	-3,	0,	0,	0,	-3,	0,	0,	0,	-1,
		   	2, 	0, 	5, 	-3, 7, 	0,	10, -3,	12,	0,	15,	-1,
			0,	0,	0,	-3,	0,	0,	0,	-3,	0,	0,	0,	-1,
			0, 	3, 	0, 	-3, 0, 	8, 	0, 	-3,	 0,	13,	0,	-2};
	
	int[] posMedium =
			{1,	0,	4,	-3,	7,	0,	10,	-3,	13,	0,	16,	-1,
			0,	0,	0,	-3,	0,	0,	0,	-3,	0,	0,	0,	-1,
			2,	0,	5,	-3,	8,	0,	11,	-3,	14,	0,	17,	-1,
			0,	0,	0,	-3,	0,	0,	0,	-3,	0,	0,	0,	-1,
			3,	0,	6,	-3,	9,	0,	12,	-3,	15,	0,	18,	-2};
	
	int[] posHigh =
			{1,	0,	5,	-3,	8,	0,	12,	-3,	15,	0,	19,	-1,
			0,	4,	0,	-3,	0,	11,	0,	-3,	0,	18,	0,	-1,		
			2,	0,	6,	-3,	9,	0,	13,	-3,	16,	0,	20,	-1,
			0,	0,	0,	-3,	0,	0,	0,	-3,	0,	0,	0,	-1,
			3,	0,	7,	-3,	10,	0,	14,	-3,	17,	0,	21,	-2};
	
	
	private void print(String string){
		System.out.print(string);
		//Broker.print(string, player);
	}
	
	private void println(){
		System.out.println();
		//Broker.println("", player);
	}
	
	private void println(String string){
		System.out.println(string);
		//Broker.println(string, player);
	}
	
	
	public InfoMatch(Match match){
		this.match = match;
		this.field = match.getField();
		this.arrayListPlayer = match.getArrayListPlayer();
		this.arrayCity = field.getArrayCity();
		this.arrayCityLinks = field.getArrayCityLinks();	
	}
	
	public void printInfoAllCities(Player player){
		println();
		printCities();
		printCityLinks();
		println();
	}
	
	public void printInfoAllPlayers(Player player){
		println();
		printPlayersInRoutes();
		println();
	}
	
	public void printInfoBalconies(Player player){
		println();
		printBalconies();
		printAvailableCouncillors();
		println();	
	}
	
	public void printInfoPlayer(Player player){
		
		this.player=player;
		
		println();
		println("You have...");
		printPolitcCards();
		println("- Richness points: "+ field.getRichnessRoute().getPosition(player));
		println("- Victory points: "+ field.getVictoryRoute().getPosition(player));
		println("- Nobility points: "+ field.getNobilityRoute().getPosition(player));
		printAssistantNumber();
		printPermitCards();
		printYourCities();
		println();
		
	}
	
	public void printInfoCity(Player player, int indexCity){
		
		City chosenCity= arrayCity[indexCity];
		println("- City: "+ chosenCity.getCityName());
		println("- Color: "+ chosenCity.getCityColor());
		print("- Bonus: ");
		if(!chosenCity.hasBonus()){
			println("-- none --");
		}else{
			println();
			Bonus[] arrayBonus = chosenCity.getArrayBonus();
			for(Bonus bonus: arrayBonus){
				println(bonus.getBonusName() + " " + bonus.getIncrement());
			}
		}
		print("- Emporiums built by: ");
		ArrayList<Player> arrayListEmporium = chosenCity.getArrayListEmporium();
		if(arrayListEmporium==null){
			print("-- none --");
		}else{
			println();
			for(Player emporium: arrayListEmporium){
				println(emporium.getNickname());
			}
		}
		println();
	}
	
	public void printInfoRegions(Player player){
		println();
		for(int i=0; i<Constant.REGIONS_NUMBER; i++){
			Region region = field.getRegionFromIndex(i);
			println("Name of the region: " + region.getRegionName());
			FaceUpPermitCardArea face = region.getFaceUpPermitCardArea();
			PermitCard[] arrayPermitCard = face.getArrayPermitCard();
			for(int j=0; j<arrayPermitCard.length; j++){
				println("Permit Card " + (j+1) + ":");
				print("- Buildable Cities: ");
				for(City city: arrayPermitCard[j].getArrayBuildableCities())
					print(city.getCityName() + " ");
				println();
				print("- Bonus: ");
				if(!arrayPermitCard[j].hasBonus()){
					println("-- none --");
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
	
	private void printCities(){
		
		int[] positions;
		
		switch(arrayCity.length){
		case Constant.CITIES_NUMBER_LOW:
			positions=posLow;
			break;
		case Constant.CITIES_NUMBER_MEDIUM:
			positions=posMedium;
			break;
		case Constant.CITIES_NUMBER_HIGH:
			positions=posHigh;
			break;
		default:{
			println("ERROR");
			positions=null;
		}
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
	
	private void printRegions(){
		println(" " + RegionName.Sea + "    " + RegionName.Hill + "   " + RegionName.Mountain);
	}
	
	
	private void printCityLinks(){
		List<Integer>[] arrayCityLinks = field.getArrayCityLinks();
		for(int i=0; i<arrayCityLinks.length; i++){
			print(arrayCity[i].getCityName() + ": ");
			for(int j=0;j<arrayCityLinks[i].size();j++){
			    print(arrayCity[arrayCityLinks[i].get(j)].getCityName().toString());
			    if(j!=arrayCityLinks[i].size()-1)
			    	print(", ");
			} 
			println();
		}
		
		println();
	}
	
	private void printBalconies(){
		Balcony[] arrayBalconies = field.getArrayBalcony();
		for(Balcony balcony: arrayBalconies){
			println( balcony.getNameBalcony() + ":");
			ArrayList<Councillor> arrayListCouncillor = balcony.getArrayListCouncillor();
			for(Councillor councillor: arrayListCouncillor){
				print("-> " + councillor.getColor() + "  ");
			}
			println();
		}
		println();
			
	}
	
	private void printAvailableCouncillors(){
		ArrayList<Councillor> arrayListCouncillor = field.getAvailableCouncillors().getArrayListCouncillor();
		println("Available councillors:");
		for(Councillor councillor: arrayListCouncillor){
			print(councillor.getColor() + "  ");
		}
		println();
		println();
	}
	
	private void printPlayersInRoutes(){
		println("Players status:");
		for(Player player: arrayListPlayer){
			println(player.getNickname() + ": " + 
					"Richness [" + field.getRichnessRoute().getPosition(player) + "], "+
					"Victory [" + field.getVictoryRoute().getPosition(player) + "], " +
					"Nobility [" +field.getNobilityRoute().getPosition(player) + "]");
		}
	}
	
	private void printPolitcCards(){
		print("- Politic cards: ");
		for(PoliticCard politicCard: player.getArrayListPoliticCard()){
			print(politicCard.getCardColor() + " ");
		}
		println();
	}
	
	private void printAssistantNumber(){
		println("- Number of assistants: " + player.getAssistant());
	}
	
	private void printPermitCards(){
		print("- Permit cards: ");
		ArrayList<PermitCard> arrayListPermitCard = player.getArrayListPermitCard();
		if(arrayListPermitCard.size()==0)
			println("-- none --");
		else{
			println();
			for(int i=0; i<arrayListPermitCard.size(); i++){
				PermitCard permitCard =  arrayListPermitCard.get(i);
				print(i+1 + ". Buildable Cities: ");
				for(City city: permitCard.getArrayBuildableCities())
					print(city.getCityName() + " ");
				println();
			}
		}
	}
	
	private void printYourCities(){
		print("- Emporiums built in these cities: ");
		ArrayList<City> arrayListEmporiumBuilt = player.getArrayListEmporiumBuilt();
		if(arrayListEmporiumBuilt.size()==0)
			println("-- none --");
		else{
			for(City city: player.getArrayListEmporiumBuilt()){
				print(city.getCityName() + " ");
			}
			println();
		}
	}
	
	

}
