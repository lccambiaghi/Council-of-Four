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
	
	public void printInfoField(Player player){
		
		this.player=player;
		
		printCities();
		//printBalconies();
		//printAvailableCouncillors();
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
				System.out.print(" ");
			case	-1:
			case	-2:
				System.out.println();
				break;
			case 	-3:
				System.out.print("|");
			default:
				System.out.print(arrayCity[pos-1] + "  ");
			}
		}
		
		
	}
		

}
