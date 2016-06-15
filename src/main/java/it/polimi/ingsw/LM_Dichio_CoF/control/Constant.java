package it.polimi.ingsw.LM_Dichio_CoF.control;

import it.polimi.ingsw.LM_Dichio_CoF.model.field.Bonus;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.BonusName;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.CityColor;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.CityName;

public class Constant {
	
	public static final int SOCKET_PORT =3000;

	public static final int TIMER_SECONDS_NEW_MATCH = 2; //FOR TESTS
	public static final int PLAYERS_MIN_NUMBER = 2;


	//assumptions
	public static final int CITIES_NUMBER_LOW = 15;
	public static final int CITIES_NUMBER_MEDIUM = 18;
	public static final int CITIES_NUMBER_HIGH = 21;

	//board game parameters
	public static final int REGIONS_NUMBER =3;

	public static final int BALCONIES_NUMBER=4;
	public static final int COLORS_NUMBER =6; // COUNCILORS
	public static final int COUNCILLORS_NUMBER_TOTAL =24; //IT HAS TO BE A MULTIPLE OF THREE
	public static final int FACE_UP_PERMIT_CARD_PER_REGION_NUMBER =2;
	public static final int COUNCILLORS_PER_BALCONY_NUMBER =4;

	//easily changeable
	public static final int POLITIC_CARDS_INITIAL_NUMBER =6;
	public static final int ASSISTANT_INITIAL_FIRST_PLAYER = 1;
	public static final int RICHNESS_INITIAL_FIRST_PLAYER =10;
	public static final int RICHNESS_MAX =20;
	public static final int NOBILITY_MAX =20;
	public static final int VICTORY_MAX =100;
	public static final CityName KING_CITY_INITIAL = CityName.J;

	//support
	public static final int PERMIT_CARD_CITIES_NUMBER_MAX =3;
	public static final int PERMIT_CARD_CITIES_NUMBER_MIN =1;
	public static final int CITIES_PER_REGION_MIN =5; //?????

	//moves cost
	public static final int PERMIT_CARD_CHANGE_ASSISTANT_COST = 1;
	public static final int ELECTION_RICHNESS_INCREMENT = 4;
	public static final int ASSISTANT_ENGAGEMENT_RICHNESS_COST = 3;
	public static final int ELECTION_ASSISTANT_COST = 1;
	public static final int ADDITIONAL_MAIN_MOVE_ASSISTANT_COST = 3;
	public static final int MAXIMUM_COST_TO_BUY_PERMIT_TILES = 10;

	//color distributions
	public static final CityColor[][] CITIES_COLOR = {
			{CityColor.Blue, CityColor.Blue, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold},
			{CityColor.Blue, CityColor.Blue, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold},
			{CityColor.Red, CityColor.Red, CityColor.Blue, CityColor.Blue, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold},
	};

	//nobility bonuses
	public static final Bonus[][] NOBILITY_CELL_BONUSES = {
			   {new Bonus(BonusName.Richness, 2), new Bonus(BonusName.Victory, 2)},
			   {new Bonus(BonusName.BuiltCityBonus, 1)},
			   {new Bonus(BonusName.MainMove, 1)},
			   {new Bonus(BonusName.Victory, 3), new Bonus(BonusName.Cards, 1)},
			   {new Bonus(BonusName.FaceUpPermitCard, 1)},
			   {new Bonus(BonusName.Victory, 5), new Bonus(BonusName.Assistant, 1)},
			   {new Bonus(BonusName.OwnedPermitCardBonus, 1)},
			   {new Bonus(BonusName.TwoBuiltCitiesDifferentBonus, 1)},
			   {new Bonus(BonusName.Victory, 8)},
			   {new Bonus(BonusName.Victory, 2)},
			   {new Bonus(BonusName.Victory, 3)},
			 };

}
