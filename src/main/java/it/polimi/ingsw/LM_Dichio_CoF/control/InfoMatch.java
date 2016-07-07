package it.polimi.ingsw.LM_Dichio_CoF.control;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.LM_Dichio_CoF.connection.Broker;
import it.polimi.ingsw.LM_Dichio_CoF.model.Match;
import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.*;

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
	
	
	private void print(String string) throws InterruptedException{
		
		//FOR_TEST
		if(Constant.test)
			System.out.print(string);
		else
			player.getBroker().print(string);
	}
	
	private void println() throws InterruptedException{
		//FOR_TEST
		if(Constant.test)
			System.out.println();
		else
			player.getBroker().println("");
	}
	
	private void println(String string) throws InterruptedException{
		//FOR_TEST
		if(Constant.test)
			System.out.println(string);
		else
			player.getBroker().println(string);
	}
	
	public void setPlayer(Player player){
		this.player=player;
	}
	
	
	public InfoMatch(Match match){
		this.match = match;
		this.field = match.getField();
		this.arrayListPlayer = match.getArrayListPlayer();
		this.arrayCity = field.getArrayCity();
		this.arrayCityLinks = field.getArrayCityLinks();	
	}
	
	public void printInfoAllCities(Player player) throws InterruptedException{
		println();
		printCities();
		printCityLinks();
		println();
	}
	
	public void printInfoAllPlayers(Player player) throws InterruptedException{
		println();
		printPlayersInRoutes();
		println();
	}
	
	public void printInfoBalconies(Player player) throws InterruptedException{
		println();
		printBalconies();
		printAvailableCouncillors();
		println();	
	}
	
	public void printInfoPlayer(Player player) throws InterruptedException{

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
	
	public void printCityAndIndex(Player player) throws InterruptedException{
		println();
		for(int i=0; i<arrayCity.length; i++){
			println((i+1) +". City: " +arrayCity[i].getCityName());
		}
		println();
	}
	
	public void printInfoCity(Player player, int indexCity) throws InterruptedException{
		
		println();
		City chosenCity= arrayCity[indexCity];
		println("- City: "+ chosenCity.getCityName().toString());
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
	
	public void printInfoRegions(Player player) throws InterruptedException{
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
	
	private void printCities() throws InterruptedException{
		
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
	
	private void printRegions() throws InterruptedException{
		println(" " + RegionName.Sea + "    " + RegionName.Hill + "   " + RegionName.Mountain);
	}
	
	
	private void printCityLinks() throws InterruptedException{
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
	
	private void printBalconies() throws InterruptedException{
		println();
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
	
	private void printAvailableCouncillors() throws InterruptedException{
		
		println();
		ArrayList<Councillor> arrayListCouncillor = field.getAvailableCouncillors().getArrayListCouncillor();
		println("Available councillors:");
		for(Councillor councillor: arrayListCouncillor){
			print(councillor.getColor() + "  ");
		}
		println();
	}
	
	private void printPlayersInRoutes() throws InterruptedException{
		println("Players status:");
		for(Player player: arrayListPlayer){
			println(player.getNickname() + ": " + 
					"Richness [" + player.getRichness() + "], "+
					"Victory [" + player.getVictory() + "], " +
					"Nobility [" +field.getNobilityRoute().getPosition(player) + "]");
		}
		println();
	}
	
	private void printPolitcCards() throws InterruptedException{
		print("- Politic cards: ");
		for(PoliticCard politicCard: player.getArrayListPoliticCard()){
			print(politicCard.getCardColor() + " ");
		}
		println();
	}
	
	private void printAssistantNumber() throws InterruptedException{
		println("- Number of assistants: " + player.getAssistant());
	}
	
	private void printPermitCards() throws InterruptedException{
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
	
	private void printYourCities() throws InterruptedException{
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
		println();
	}


	

}
