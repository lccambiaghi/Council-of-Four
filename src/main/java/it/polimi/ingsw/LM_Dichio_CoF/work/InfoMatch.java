package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.*;

public class InfoMatch {
	
	
	
	Match match;
	Field field;
	
	City[] arrayCity;
	List <Integer>[] arrayCityLinks; 
	ArrayList<Player> arrayListplayer;
	
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
	
	
	
	public InfoMatch(Match match){
		this.match = match;
		this.field = match.getField();
		this.arrayListplayer = match.getArrayListPlayer();
		this.arrayCity = field.getArrayCity();
		this.arrayCityLinks = field.getArrayCityLinks();	
	}
	
	public void printInfoField(/*Player player/*/){
		
		//this.player=player;
		
		printCities();
		printCityLinks();
		printBalconies();
		printAvailableCouncillors();
		printPlayersInRoutes();
		
	}
	
	public void printInfoPlayer(Player player){
		
		this.player=player;
		
		System.out.println("You have...");
		printPolitcCards();
		printAssistantNumber();
		printPermitCards();
		printYourCities();
		System.out.println();
		
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
			System.out.println("ERROR");
			positions=null;
		}
		}
		
		
		for(int pos: positions){
			switch(pos){
			case	0:
				System.out.print("  ");
				break;
			case	-1:
			case	-2:
				System.out.println();
				break;
			case 	-3:
				System.out.print("| ");
				break;
			default:
				System.out.print(arrayCity[pos-1].getCityName().toString() +" ");
			}
		}
		
		printRegions();
		System.out.println();
		
	}
	
	private void printRegions(){
		System.out.println(" " + RegionName.Sea + "    " + RegionName.Hill + "   " + RegionName.Mountain);
	}
	
	
	private void printCityLinks(){
		List<Integer>[] arrayCityLinks = field.getArrayCityLinks();
		for(int i=0; i<arrayCityLinks.length; i++){
			System.out.print(arrayCity[i].getCityName() + ": ");
			for(int j=0;j<arrayCityLinks[i].size();j++){
			    System.out.print(arrayCity[arrayCityLinks[i].get(j)].getCityName());
			    if(j!=arrayCityLinks[i].size()-1)
			    	System.out.print(", ");
			} 
			System.out.println();
		}
		
		System.out.println();
	}
	
	private void printBalconies(){
		Balcony[] arrayBalconies = field.getArrayBalcony();
		for(Balcony balcony: arrayBalconies){
			System.out.println( balcony.getNameBalcony() + ":");
			ArrayList<Councillor> arrayListCouncillor = balcony.getArrayListCouncillor();
			for(Councillor councillor: arrayListCouncillor){
				System.out.print("-> " + councillor.getColor() + "  ");
			}
			System.out.println();
		}
		System.out.println();
			
	}
	
	private void printAvailableCouncillors(){
		ArrayList<Councillor> arrayListCouncillor = field.getAvailableCouncillors().getArrayListCouncillor();
		System.out.println("Available councillors:");
		for(Councillor councillor: arrayListCouncillor){
			System.out.print(councillor.getColor() + "  ");
		}
		System.out.println();
		System.out.println();
	}
	
	private void printPlayersInRoutes(){
		System.out.println("Players status:");
		for(Player player: arrayListplayer){
			System.out.println(player.getNickname() + ": " + 
					"Richness [" + field.getRichnessRoute().getPosition(player) + "], "+
					"Victory [" + field.getVictoryRoute().getPosition(player) + "], " +
					"Nobility [" +field.getNobilityRoute().getPosition(player) + "]");
		}
	}
	
	private void printPolitcCards(){
		System.out.print("- Politic cards: ");
		for(PoliticCard politicCard: player.getArrayListPoliticCard()){
			System.out.print(politicCard.getCardColor() + " ");
		}
		System.out.println();
	}
	
	private void printAssistantNumber(){
		System.out.println("- Number of assistants: " + player.getAssistant());
	}
	
	private void printPermitCards(){
		System.out.print("- Permit cards: ");
		ArrayList<PermitCard> arrayListPermitCard = player.getArrayListPermitCard();
		if(arrayListPermitCard.size()==0)
			System.out.println("-- none --");
		else{
			System.out.println();
			for(int i=0; i<arrayListPermitCard.size(); i++){
				PermitCard permitCard =  arrayListPermitCard.get(i);
				System.out.print(i+1 + ". Buildable Cities: ");
				for(City city: permitCard.getArrayBuildableCities())
					System.out.print(city.getCityName() + " ");
				System.out.println();
			}
		}
	}
	
	private void printYourCities(){
		System.out.print("- Emporium built in these cities: ");
		ArrayList<City> arrayListEmporiumBuilt = player.getArrayListEmporiumBuilt();
		if(arrayListEmporiumBuilt.size()==0)
			System.out.println("-- none --");
		else{
			for(City city: player.getArrayListEmporiumBuilt()){
				System.out.print(city.getCityName() + " ");
			}
			System.out.println();
		}
	}
	
	

}
