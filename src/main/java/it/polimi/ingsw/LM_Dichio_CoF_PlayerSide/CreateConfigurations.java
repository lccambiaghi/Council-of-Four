package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.util.*;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.BonusName;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.CityBonus;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.CityName;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class CreateConfigurations{
	
	private PlayerSide playerSide;
	private Configurations config;
	
	public CreateConfigurations(PlayerSide playerSide){ 
		this.playerSide=playerSide;
		this.config = new Configurations();
	}
	
	public Configurations getCustomConfig(){
		
		System.out.println("How many cities would you like to have in your match?");
		System.out.println("1. 15 Cities");
		System.out.println("2. 18 Cities");
		System.out.println("3. 21 Cities");
		
		int cityNumber = citiesNumber(InputHandler.inputNumber(1, 3));
		config.setCitiesNumber(cityNumber);
		
		System.out.println("How many bonuses would you like to set as minimum on the Permit Cards?\n"
				+"Insert a value between 0 and 5");
		int numberBonusesMin=InputHandler.inputNumber(0, 5);
		
		config.setPermitCardBonusNumberMin(numberBonusesMin);
		System.out.println("How many bonuses would you like to set as maximum on the Permit Cards?\n"
				+"Insert a value between " + numberBonusesMin + " and 5");
		config.setPermitCardBonusNumberMax(InputHandler.inputNumber(numberBonusesMin, 5));
		
		System.out.println("Would you like to play with a random number of bonuses on the Nobility Route?\n"
				+ "1. Yes 2. No");
		int nobilityBonusRandom=InputHandler.inputNumber(1, 2);
		if (nobilityBonusRandom==1){
			config.setNobilityBonusRandom(false);
			System.out.println("How many cells of Nobility Route would you like with a bonus?\n"
					+ "Insert a value between 0 and 11");
			config.setNobilityBonusNumber(InputHandler.inputNumber(0, 11));
		}
		else 
			config.setNobilityBonusRandom(true);
		
		
		System.out.println("Would you like to play with preconfigured links?\n"
				+ "1. Yes 2. No");
		if (InputHandler.inputNumber(1, 2)==2)
			config.setCityLinksPreconfigured(false);
		else
			config.setCityLinksPreconfigured(true);
		if(config.isCityLinksPreconfigured()==false){
			int[][]cityLinksMatrix =  new int[cityNumber][cityNumber];
			
			
			for(int i=0; i<cityNumber-1; i++){
				CityName cityName = CityName.getCityNameFromIndex(i);
				
				System.out.println("Which cities would you like to connect with city " + cityName +" ?");
				System.out.println("Insert a set of cities from this:");
				for (int j=cityName.ordinal()+1; j<CityName.getCityNameFromIndex(cityNumber).ordinal(); j++){
					System.out.print(CityName.getCityNameFromIndex(j).toString()+" ");
				}
				Character[] input=InputHandler.inputCity(cityName,CityName.getCityNameFromIndex(cityNumber-1));
				for (int j=0; j<input.length;j++){
					    cityLinksMatrix[i][CityName.valueOf(String.valueOf(input[j])).ordinal()]=1;				    
					}
				}
				
			for(int i=0; i<cityNumber; i++){
				for(int j=i; j<cityNumber;j++){
					cityLinksMatrix[j][i]=cityLinksMatrix[i][j];
				}
			}		
			
						
			config.setCityLinksMatrix(cityLinksMatrix);
			
			for(int i=0; i<cityNumber; i++){
				for(int j=0; j<cityNumber;j++){
					System.out.print(cityLinksMatrix[i][j]+" ");
				}
				System.out.println();
			}
			
			/*{
				{0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	1,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0}
			};
			/*
    		 * This fir cycle is for making the matrix specular,
    		 * because in the txt file it is only upper triangular set
    		 */
			/*for(int i=0; i<cityNumber; i++){
				for(int j=i; j<cityNumber;j++){
					cityLinksMatrix[j][i]=cityLinksMatrix[i][j];
				}
			}*/
			
			
		}else{
			System.out.println("Select the difficulty (from lower to higher links)");
			System.out.println("1. low number of linked cities");
			System.out.println("2. medium number of linked cities");
			System.out.println("3. higher number of linked cities");
			config.setDifficulty(chooseDifficulty(InputHandler.inputNumber(1,3)));
		}
		
		System.out.print("Would you like to play with random bonuses on the cities?\n"
				+ "1. Yes 2. No");
		if(InputHandler.inputNumber(1, 2)==1)
			config.setCityBonusRandom(true);
		
		if(config.isCityBonusRandom()==false){
		    ArrayList<CityBonus> arrayListCityBonus[] = new ArrayList[cityNumber];
		    for (int i=0; i<arrayListCityBonus.length; i++){
		    	arrayListCityBonus[i] = new ArrayList<>();
		    	arrayListCityBonus[i]=askForBonus(i);
			}
		    config.setArrayListCityBonus(arrayListCityBonus);
		}else{
			System.out.println("How many bonuses would you like to set as minimum on the Cities?\n"
					+"Insert a value between 0 and 5");
			numberBonusesMin=InputHandler.inputNumber(0, 5);
			
			config.setCityBonusNumberMin(numberBonusesMin);
			System.out.println("How many bonuses would you like to set as maximum on the Cities?\n"
					+"Insert a value between" + numberBonusesMin + " and 5");
			config.setCityBonusNumberMax(InputHandler.inputNumber(numberBonusesMin, 5));
		}
		
		return config;
	}
	
	
	private char chooseDifficulty(int inputNumber) {
		char difficulty;
		switch (inputNumber){
		case (1):
			difficulty='l';
			break;
		case (2):
			difficulty='n';
			break;
		default:
			difficulty='h';		
		}
		return difficulty;
	}
	
	private ArrayList <CityBonus> askForBonus (int i){
		CityName cityName = CityName.getCityNameFromIndex(i) ;
		ArrayList <CityBonus> cityBonus = new ArrayList <>();
		int increment=0;
		BonusName chosenBonus=null;
		int choice;
		ArrayList <Integer> oldChoices = new ArrayList <>();
		
		System.out.println("Choose the types of bonus to set in the city " + cityName);
		do{
			System.out.println("0. Go to next City");
			System.out.println("1. Assistants");
			System.out.println("2. Richness");
			System.out.println("3. Nobility");
			System.out.println("4. Victory");
			System.out.println("5. Cards");
			choice = InputHandler.inputNumber(0, 5);
			
			if (choice!=0){
				while (oldChoices.contains(choice)){
					System.out.println("Select an other bonus, you have alredy used it");
					choice = InputHandler.inputNumber(0, 5);
				}
				if(choice!=0){		
				chosenBonus=BonusName.getBonusNameFromIndex(choice-1);
				System.out.println("Choose the increment: min 1 max "+BonusName.getMaxIncrement(chosenBonus));
				increment = InputHandler.inputNumber(1, BonusName.getMaxIncrement(chosenBonus));
				cityBonus.add(new CityBonus(chosenBonus, increment));
				oldChoices.add(choice);
				}
			}
		}
		while (choice!=0);
		return cityBonus;
	}

	private int citiesNumber(int choice){
		int numberCitiesMatch;
		switch (choice){
		case (1):
			numberCitiesMatch=Constant.CITIES_NUMBER_LOW;
			break;
		case (2):
			numberCitiesMatch=Constant.CITIES_NUMBER_MEDIUM;
			break;
		default:
			numberCitiesMatch=Constant.CITIES_NUMBER_HIGH;
		}
		return numberCitiesMatch;
	}
	
}
