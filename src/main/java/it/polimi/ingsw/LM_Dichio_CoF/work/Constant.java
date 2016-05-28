package it.polimi.ingsw.LM_Dichio_CoF.work;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.BonusName;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.CityColor;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.CityName;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.NobilityCellBonus;

public class Constant {
	
	public static final int SOCKET_PORT =3000;
	public static final int COUNCILLORS_PER_BALCONY_NUMBER =4;
	public static final int BALCONIES_NUMBER=4;
	public static final int COLORS_NUMBER =6; // COUNCILORS
	public static final int COUNCILORS_NUMBER_TOTAL =24; //IT HAS TO BE A MULTIPLE OF THREE
	public static final int POLITIC_CARDS_INITIAL_NUMBER =6;
	public static final int RICHNESS_INITIAL_FIRST_PLAYER =10;
	public static final int REGIONS_NUMBER =3;
	public static final int FACE_UP_PERMIT_CARD_PER_REGION_NUMBER =2;
	public static final int PERMIT_CARD_BONUS_NUMBER =2;
	public static final int PERMIT_CARD_CITIES_NUMBER_MAX =3;
	public static final int PERMIT_CARD_CITIES_NUMBER_MIN =1;
	public static final int CITIES_PER_REGION_MIN =5;
	public static final int RICHNESS_MAX =20;
	public static final int NOBILITY_MAX =21;
	public static final int VICTORY_MAX =100;
	
	public static final CityName KING_CITY_INITIAL = CityName.J;

	public static final CityColor[][] CITIES_COLOR = {
			{CityColor.Blue, CityColor.Blue, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold},
			{CityColor.Blue, CityColor.Blue, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold},
			{CityColor.Red, CityColor.Red, CityColor.Blue, CityColor.Blue, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Bronze, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Silver, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold, CityColor.Gold},
	};

	public static final NobilityCellBonus[][] NOBILITY_CELL_BONUSES = {
			   {new NobilityCellBonus(BonusName.Richness, 2), new NobilityCellBonus(BonusName.Victory, 2)},
			   {new NobilityCellBonus(BonusName.BuiltCityBonus, 1)},
			   {new NobilityCellBonus(BonusName.MainMove, 1)},
			   {new NobilityCellBonus(BonusName.Victory, 3), new NobilityCellBonus(BonusName.Cards, 1)},
			   {new NobilityCellBonus(BonusName.FaceUpPermitCard, 1)},
			   {new NobilityCellBonus(BonusName.Victory, 5), new NobilityCellBonus(BonusName.Assistant, 1)},
			   {new NobilityCellBonus(BonusName.OwnedPermitCardBonus, 1)},
			   {new NobilityCellBonus(BonusName.TwoBuiltDifferentBonus, 1)},
			   {new NobilityCellBonus(BonusName.Victory, 8)},
			   {new NobilityCellBonus(BonusName.Victory, 2)},
			   {new NobilityCellBonus(BonusName.Victory, 3)},
			 };
	
	public static final int CITIES_NUMBER_LOW = 15;
	public static final int CITIES_NUMBER_MEDIUM = 18;
	public static final int CITIES_NUMBER_HIGH = 21;
}
