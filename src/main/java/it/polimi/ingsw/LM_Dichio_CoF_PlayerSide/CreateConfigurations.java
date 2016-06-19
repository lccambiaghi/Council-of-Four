package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.util.HashMap;
import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.control.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.control.GameSide;
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
		
		config.setCitiesNumber(citiesNumber(playersMaxNumber));
		
		System.out.println("How many bonuses would you like to set as minimum on the Permit Cards?"
				+"Insert a value between 0 and 5");
		int numberBonusesMin=InputHandler.inputNumber(0, 5);
		
		config.setPermitCardBonusNumberMin(numberBonusesMin);
		System.out.println("How many bonuses would you like to set as maximum on the Permit Cards?"
				+"Insert a value between" + numberBonusesMin + " and 5");
		config.setPermitCardBonusNumberMax(InputHandler.inputNumber(numberBonusesMin, 5));
		
		System.out.println("Would you like to play with a random number of bonuses on the Nobility Route? 1. Yes 2. No");
		int nobilityBonusRandom=InputHandler.inputNumber(1, 2);
		if (nobilityBonusRandom==1){
			config.setNobilityBonusRandom(false);
			System.out.println("How many cells of Nobility Route would you like with a bonus?"
					+ "Insert a value between 0 and 11");
			config.setNobilityBonusNumber(InputHandler.inputNumber(0, 11));
		}
		else 
			config.setNobilityBonusRandom(true);
		
		
		System.out.println("Would you like to play with preconfigured links? 1. Yes 2. No");
		if (InputHandler.inputNumber(1, 2)==2)
			config.setCityLinksPreconfigured(false);
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
			System.out.println("l. low number of linked cities");
			System.out.println("n. medium number of linked cities");
			System.out.println("h. higher number of linked cities");
			config.setDifficulty(InputHandler.inputCharacter());
		}
		
		System.out.print("Would you like to play with random bonuses on the cities? 1. Yes 2. No");
		if(InputHandler.inputNumber(1, 2)==1)
			config.setCityBonusRandom(true);
		
		if(config.isCityBonusRandom()==false){
			HashMap<String, Integer>[] cityBonusArray = new HashMap[config.getCitiesNumber()];
			for(int i=0; i<cityBonusArray.length; i++){
				
				
			}

		
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
	
	
	private int citiesNumber(int playersNumber){
		switch (playersNumber){
		case (5):
			return Constant.CITIES_NUMBER_MEDIUM;
		case (6): 
			return Constant.CITIES_NUMBER_MEDIUM;
		case (7):
			return Constant.CITIES_NUMBER_HIGH;
		case (8): 
			return Constant.CITIES_NUMBER_HIGH;
		default :
			return Constant.CITIES_NUMBER_LOW;
		}
	}
	
}
