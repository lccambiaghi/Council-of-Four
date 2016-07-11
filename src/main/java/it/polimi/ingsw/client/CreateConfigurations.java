package it.polimi.ingsw.client;

import it.polimi.ingsw.server.model.BonusName;
import it.polimi.ingsw.server.model.CityBonus;
import it.polimi.ingsw.server.model.CityName;
import it.polimi.ingsw.server.model.Configurations;
import it.polimi.ingsw.utils.Constant;

import java.util.ArrayList;

/**
 * This class is used to interact with the first player of the match, it allows to choose
 * how many custom parameters he wants into the match. 
 * In detail our game allow to choose an existing configurations or to create the custom
 * configurations. So we have implemented the match creation with parameters and the
 * arbitrary creations of the configurations by the first player. 
 * 
 *  The first player first of all choose the number of the Cities with which to play
 *  (15,18,21), to set the bonuses number minimum and maximum that are on the PermitCards,
 *  the number of cells of the Nobility Route with bonuses.
 *  
 *  Then we ask to the player if he wants to play with pre-configured links into the cities,
 *  if yes he can choose 3 different levels of connections (low, medium, high), so from
 *  lower connection to higher. Otherwise he can creates his own map, connecting as he wants
 *  the cities each other, respecting some costraints, as for example the city that he can 
 *  insert only following cities. This is important because the data is stored into a 
 *  square matrix with dimension "number cities". Next, the matrix is made symmetric in order to
 *  link cities on a one-to-one correspondence. Our assumption was that the minimum number of links
 *  of each city must be 1.
 *  
 *  In the same way are managed the bonuses of the cities. If the user decides to play with 
 *  random bonuses, the server asks to him how many bonuses he wants to set as minimum and maximum.
 *  Otherwise the player can choose the bonuses and the increments. In addition he can choose if
 *  don't set any bonus in a city or insert all of them.
 * 
 */

public class CreateConfigurations{

	private Configurations config;
	private final InputHandler inputHandler;
	
	/**
	 * Constructor of the class
	 * @param inputHandler:
	 */
	public CreateConfigurations(InputHandler inputHandler){
		this.inputHandler=inputHandler;
	}
	
	public Configurations getConfigurations(){
		return config;
	}
	
	public void startCreating(){
		
		this.config = new Configurations();
		
		System.out.println("How many cities would you like to have in your match?");
		System.out.println("1. 15 Cities");
		System.out.println("2. 18 Cities");
		System.out.println("3. 21 Cities");
		
		int cityNumber = citiesNumber(inputHandler.inputNumber(1, 3));
		config.setCitiesNumber(cityNumber);
		
		System.out.println("How many bonuses would you like to set as minimum on the Permit Cards?\n"
				+"Insert a value between 0 and 5");
		int numberBonusesMin=inputHandler.inputNumber(0, 5);
		
		config.setPermitCardBonusNumberMin(numberBonusesMin);
		System.out.println("How many bonuses would you like to set as maximum on the Permit Cards?\n"
				+"Insert a value between " + numberBonusesMin + " and 5");
		config.setPermitCardBonusNumberMax(inputHandler.inputNumber(numberBonusesMin, 5));
		
		System.out.println("Would you like to play with a random number of bonuses on the Nobility Route?\n"
				+ "1. Yes\n2. No");
		int nobilityBonusRandom=inputHandler.inputNumber(1, 2);
		if (nobilityBonusRandom==1){
			config.setNobilityBonusRandom(false);
			System.out.println("How many cells of Nobility Route would you like with a bonus?\n"
					+ "Insert a value between 0 and 11");
			config.setNobilityBonusNumber(inputHandler.inputNumber(0, 11));
		}
		else 
			config.setNobilityBonusRandom(true);
		
		
		System.out.println("Would you like to play with preconfigured links?\n"
				+ "1. Yes\n2. No");
		if (inputHandler.inputNumber(1, 2)==2)
			config.setCityLinksPreconfigured(false);
		else
			config.setCityLinksPreconfigured(true);
		if(!config.isCityLinksPreconfigured()){
			int[][]cityLinksMatrix =  new int[cityNumber][cityNumber];
			
			
			for(int i=0; i<cityNumber-1; i++){
				CityName cityName = CityName.getCityNameFromIndex(i);
				
				System.out.println("Which cities would you like to connect with city " + cityName +" ?");
				System.out.println("Insert a set of cities from this:");
				for (int j=cityName.ordinal()+1; j<CityName.getCityNameFromIndex(cityNumber).ordinal(); j++){
					System.out.print(CityName.getCityNameFromIndex(j).toString()+" ");
				}
				Character[] input=inputHandler.inputCity(cityName,CityName.getCityNameFromIndex(cityNumber-1));
				for (Character anInput : input) {
					cityLinksMatrix[i][CityName.valueOf(String.valueOf(anInput)).ordinal()] = 1;
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
			
		}else{
			System.out.println("Select the difficulty (from lower to higher links)");
			System.out.println("1. low number of linked cities");
			System.out.println("2. medium number of linked cities");
			System.out.println("3. higher number of linked cities");
			config.setDifficulty(chooseDifficulty(inputHandler.inputNumber(1,3)));
		}
		
		System.out.print("Would you like to play with random bonuses on the cities?\n"
				+ "1. Yes\n2. No");
		if(inputHandler.inputNumber(1, 2)==1)
			config.setCityBonusRandom(true);
		
		if(!config.isCityBonusRandom()){
		    ArrayList<CityBonus> arrayListCityBonus[] = new ArrayList[cityNumber];
		    for (int i=0; i<arrayListCityBonus.length; i++){
		    	if(i!=Constant.INITIALLY_KING_CITY){
		    	arrayListCityBonus[i] = new ArrayList<>();
		    	arrayListCityBonus[i]=askForBonus(i);
		    	}
			}
		    config.setArrayListCityBonus(arrayListCityBonus);
		}else{
			System.out.println("How many bonuses would you like to set as minimum on the Cities?\n"
					+"Insert a value between 0 and 5");
			numberBonusesMin=inputHandler.inputNumber(0, 5);
			
			config.setCityBonusNumberMin(numberBonusesMin);
			System.out.println("How many bonuses would you like to set as maximum on the Cities?\n"
					+"Insert a value between " + numberBonusesMin + " and 5");
			config.setCityBonusNumberMax(inputHandler.inputNumber(numberBonusesMin, 5));
		}

	}
	
	/**
	 * This method is used to select which level of connections the players wants among
	 * the cities. 
	 * @param inputNumber: a number among 1 and 3, inserted by the player
	 * @return: the char combined to the input level
	 */
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
	
	/**
	 * It's the method used to interact with the player about the bonuses that he wants 
	 * to set in each city. If the player doesn't want bonuses or if he wants to chage 
	 * the city he can insert 0.
	 * @param i: the index of the city in the @Link arrayCity
	 * @return ArrayList CityBonus: all the bonuses set for the input city  
	 */	
	
	private ArrayList <CityBonus> askForBonus (int i){
		CityName cityName = CityName.getCityNameFromIndex(i) ;
		ArrayList <CityBonus> cityBonus = new ArrayList <>();
		int increment;
		BonusName chosenBonus;
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
			choice = inputHandler.inputNumber(0, 5);
			
			if (choice!=0){
				while (oldChoices.contains(choice)){
					System.out.println("Select an other bonus, you have alredy used it");
					choice = inputHandler.inputNumber(0, 5);
				}
				if(choice!=0){		
				chosenBonus=BonusName.getBonusNameFromIndex(choice-1);
				System.out.println("Choose the increment: min 1 max "+BonusName.getMaxIncrement(chosenBonus));
				increment = inputHandler.inputNumber(1, BonusName.getMaxIncrement(chosenBonus));
				cityBonus.add(new CityBonus(chosenBonus, increment));
				oldChoices.add(choice);
				}
			}
		}
		while (choice!=0);
		return cityBonus;
	}
	
	/**
	 * This method return the number of the city that will be in the game
	 * @param choice: the value inserted by the user among 1 and 3
	 * @return the effective number of the cities
	 */

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
