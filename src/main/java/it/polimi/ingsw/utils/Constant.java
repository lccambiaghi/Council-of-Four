package it.polimi.ingsw.utils;

import it.polimi.ingsw.server.model.Bonus;
import it.polimi.ingsw.server.model.BonusName;
import it.polimi.ingsw.server.model.CityColor;
import it.polimi.ingsw.server.model.CityName;

public class Constant {
	
	public static final boolean test = false;
	
	//socket connection
	public static final int SOCKET_PORT =3000;
	public static final String SOCKET_ADDRESS ="localhost";
	
	//rmi connection
	public static final String RMI_REGISTRY_ADDRESS = "127.0.0.1";
	public static final String RMI_REGISTRY_LOOKUP_ADDRESS = "rmi://127.0.0.1:1099/CoF";
	public static final int RMI_PORT =1099;
	
	//file configurations
	public static final String PATH_FILE_CONFIGURATIONS = "./src/configurations/config";

	public static final int PLAYERS_MIN_NUMBER = 2;
	public static final int PLAYERS_MAX_NUMBER = 8;
	
	//timers
	public static final int TIMER_SECONDS_TO_PERFORM_ACTION = 300;
	public static final int TIMER_SECONDS_NEW_MATCH = 20; 
	public static final int TIMER_SECONDS_WAITING_CONFIGURATIONS = 10;
	public static final int TIMER_SECONDS_MARKET_ACTION = 40;

	//assumptions
	public static final int CITIES_NUMBER_LOW = 15;
	public static final int CITIES_NUMBER_MEDIUM = 18;
	public static final int CITIES_NUMBER_HIGH = 21;

	//board game fixed parameters
	public static final int REGIONS_NUMBER =3;
	public static final int BALCONIES_NUMBER=4;
	public static final int COLORS_NUMBER =6; // COUNCILORS
	public static final int FACE_UP_PERMIT_CARD_PER_REGION_NUMBER =2;
	public static final int COUNCILLORS_PER_BALCONY_NUMBER =4;

	//board game easily changeable parameters
	public static final int COUNCILLORS_NUMBER_TOTAL =24; //IT HAS TO BE A MULTIPLE OF THREE
	public static final int POLITIC_CARDS_INITIAL_NUMBER =6;
	public static final int ASSISTANT_INITIAL_FIRST_PLAYER = 1;
	public static final int RICHNESS_INITIAL_FIRST_PLAYER =10;
	public static final int VICTORY_INITIAL_FIRST_PLAYER =0;
	public static final int RICHNESS_MAX =20;
	public static final int NOBILITY_MAX =20;
	public static final int VICTORY_MAX =100;
	public static final CityName KING_CITY_INITIAL = CityName.J;
	public static final int REGION_TILE_VICTORY_INCREMENT = 5;
	public static final int BLUE_BONUS_TILE_VICTORY_INCREMENT = 5;
	public static final int RED_BONUS_TILE_VICTORY_INCREMENT = 7;
	public static final int BRONZE_BONUS_TILE_VICTORY_INCREMENT = 8;
	public static final int SILVER_BONUS_TILE_VICTORY_INCREMENT = 12;
	public static final int GOLD_BONUS_TILE_VICTORY_INCREMENT = 20;
	public static final int NUMBER_EMPORIUMS_TO_WIN = 3; //TODO CHANGE IT BACK

	//reward tiles' increments
	public static final int NUMBER_KING_REWARD_TILES = 5;
	public static final int FIRST_KING_REWARD_VICTORY_INCREMENT = 25;
	public static final int SECOND_KING_REWARD_VICTORY_INCREMENT = 18;
	public static final int THIRD_KING_REWARD_VICTORY_INCREMENT = 12;
	public static final int FOURTH_KING_REWARD_VICTORY_INCREMENT = 7;
	public static final int FIFTH_KING_REWARD_VICTORY_INCREMENT = 3;

	//end of the game's increments
	public static final int VICTORY_INCREMENT_LAST_EMPORIUM = 3;
	public static final int FIRST_NOBILITY_VICTORY_INCREMENT = 5;
	public static final int SECOND_NOBILITY_VICTORY_INCREMENT = 2;
	public static final int MAX_PERMIT_CARD_VICTORY_INCREMENT = 3;

	//support
	public static final int PERMIT_CARD_CITIES_NUMBER_MAX =3;
	public static final int PERMIT_CARD_CITIES_NUMBER_MIN =1;
	public static final int CITIES_PER_REGION_MIN =5; //?????
	public static final int INITIALLY_KING_CITY = 9;

	//moves cost
	public static final int PERMIT_CARD_CHANGE_ASSISTANT_COST = 1;
	public static final int ELECTION_RICHNESS_INCREMENT = 4;
	public static final int ASSISTANT_ENGAGEMENT_RICHNESS_COST = 3;
	public static final int ELECTION_ASSISTANT_COST = 1;
	public static final int ADDITIONAL_MAIN_MOVE_ASSISTANT_COST = 3;

	//color distributions
	public static final CityColor[][] CITIES_COLOR = {
			{CityColor.Blue, CityColor.Blue, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold},
			{CityColor.Blue, CityColor.Blue, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold},
			{CityColor.Red, CityColor.Red, CityColor.Blue, CityColor.Blue, CityColor.Blue, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold},
	};

	//nobility bonuses
	public static final Bonus[][] NOBILITY_CELL_BONUSES = {
			   {new Bonus(BonusName.Richness, 2), new Bonus(BonusName.Victory, 2)},
			   {new Bonus(BonusName.Assistant, 1)},
			   {new Bonus(BonusName.MainMove, 1)},
			   {new Bonus(BonusName.Victory, 3), new Bonus(BonusName.Cards, 1)},
			   {new Bonus(BonusName.Cards, 2)},
			   {new Bonus(BonusName.Victory, 5), new Bonus(BonusName.Assistant, 1)},
			   {new Bonus(BonusName.MainMove, 1)},
			   {new Bonus(BonusName.Richness, 4)},
			   {new Bonus(BonusName.Victory, 8)},
			   {new Bonus(BonusName.Victory, 2)},
			   {new Bonus(BonusName.Victory, 3)},
			 };

}
