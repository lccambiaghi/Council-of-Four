package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.util.*;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.BonusName;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.CityBonus;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.CityName;
import it.polimi.ingsw.LM_Dichio_CoF.model.Configurations;

public class CreateConfigurations extends Thread{
	
	PlayerSide playerSide;
	
	public CreateConfigurations(PlayerSide playerSide){ this.playerSide=playerSide; }
	Scanner inCLI = new Scanner(System.in);
	
	public void run(){
		
		System.out.println("You are the first player");
		System.out.println("You have to create the configurations of a match\n");
		
		int playersMaxNumber = 0;
		System.out.println("First of all, enter the max number of players you want (min 2, max 8)");
		playersMaxNumber= InputHandler.inputNumber(2, 8);
		
		Configurations config = new Configurations();
		System.out.println("How many cities would you like to have in your match?");
		System.out.print("1. 15 Cities");
		System.out.print("2. 18 Cities");
		System.out.print("3. 21 Cities");
		
		config.setCitiesNumber(citiesNumber(InputHandler.inputNumber(1, 3)));
		
		System.out.println("How many bonuses would you like to set as minimum on the Permit Cards?"
				+"Insert a value between 0 and 5");
		int numberBonusesMin=InputHandler.inputNumber(0, 5);
		
		config.setPermitCardBonusNumberMin(numberBonusesMin);
		System.out.println("How many bonuses would you like to set as maximum on the Permit Cards?"
				+"Insert a value between" + numberBonusesMin + " and 5");
		config.setPermitCardBonusNumberMax(InputHandler.inputNumber(numberBonusesMin, 5));
		
		System.out.println("Would you like to play with a random number of bonuses on the Nobility Route?"
				+ "1. Yes 2. No");
		int nobilityBonusRandom=InputHandler.inputNumber(1, 2);
		if (nobilityBonusRandom==1){
			config.setNobilityBonusRandom(false);
			System.out.println("How many cells of Nobility Route would you like with a bonus?"
					+ "Insert a value between 0 and 11");
			config.setNobilityBonusNumber(InputHandler.inputNumber(0, 11));
		}
		else 
			config.setNobilityBonusRandom(true);
		
		
		System.out.println("Would you like to play with preconfigured links?"
				+ "1. Yes 2. No");
		if (InputHandler.inputNumber(1, 2)==2)
			config.setCityLinksPreconfigured(false);
		else
			config.setCityLinksPreconfigured(true);
		if(config.isCityLinksPreconfigured()==false){
			int[][]cityLinksMatrix =  new int[][]{
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
			for(int i=0; i<config.getCitiesNumber(); i++){
				for(int j=i; j<config.getCitiesNumber();j++){
					cityLinksMatrix[j][i]=cityLinksMatrix[i][j];
				}
			}
			config.setCityLinksMatrix(cityLinksMatrix);
			
		}else{
			System.out.println("Select the difficulty (from lower to higher links)");
			System.out.println("1. low number of linked cities");
			System.out.println("2. medium number of linked cities");
			System.out.println("3. higher number of linked cities");
			config.setDifficulty(chooseDifficulty(InputHandler.inputNumber(1,3)));
		}
		
		System.out.print("Would you like to play with random bonuses on the cities?"
				+ "1. Yes 2. No");
		if(InputHandler.inputNumber(1, 2)==1)
			config.setCityBonusRandom(true);
		
		if(config.isCityBonusRandom()==false){
		    ArrayList<CityBonus> arrayListCityBonus[] = new ArrayList[config.getCitiesNumber()];
		    for (int i=0; i<arrayListCityBonus.length; i++){
		    	arrayListCityBonus[i] = new ArrayList<>();
		    	arrayListCityBonus[i]=askForBonus(i);
			}
		    config.setArrayListCityBonus(arrayListCityBonus);
		}else{
			System.out.println("How many bonuses would you like to set as minimum on the Cities?"
					+"Insert a value between 0 and 5");
			numberBonusesMin=InputHandler.inputNumber(0, 5);
			
			config.setCityBonusNumberMin(numberBonusesMin);
			System.out.println("How many bonuses would you like to set as maximum on the Cities?"
					+"Insert a value between" + numberBonusesMin + " and 5");
			config.setCityBonusNumberMax(InputHandler.inputNumber(numberBonusesMin, 5));
		}
		
		playerSide.setPlayersMaxNumber(playersMaxNumber);
		playerSide.setConfigurations(config);
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
		int choice=0;
		int oldChoice=0;
		
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
				do {
					System.out.println("Select an other bonus, you have alredy used it");
					choice = InputHandler.inputNumber(0, 5);
				}
				while (choice==oldChoice);
				chosenBonus=BonusName.getBonusNameFromIndex(choice-1);
				System.out.println("Choose the increment: min 1 max "+BonusName.getMaxIncrement(chosenBonus));
				increment = InputHandler.inputNumber(1, BonusName.getMaxIncrement(chosenBonus));
				cityBonus.add(new CityBonus(chosenBonus, increment));
				oldChoice=choice;
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
