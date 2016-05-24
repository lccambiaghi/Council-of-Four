package it.polimi.ingsw.LM_Dichio_CoF.work.field;

public class NobilityRouteCell {
	
	Bonus [] bonus;
	boolean hasBonus;
	int i;
	
	public NobilityRouteCell(int index){
		this.i=index;
		
		switch (i){
		case 2:
			bonus = new Bonus [2];
			bonus[0] = new BonusNobilityCell(BonusName.Richness, 2);
			bonus[1] = new BonusNobilityCell(BonusName.Victory, 2);
			break;
		case 4:
			bonus = new Bonus [1];
			bonus[0] = new BonusNobilityCell(BonusName.BuiltCityBonus, 1);
			break;
		case 6:
			bonus = new Bonus [1];
			bonus[0] = new BonusNobilityCell(BonusName.MainMove, 1);
			break;
		case 8:
			bonus = new Bonus [2];
			bonus[0] = new BonusNobilityCell(BonusName.Victory, 3);
			bonus[1] = new BonusNobilityCell(BonusName.Cards, 1);
			break;
		case 10:
			bonus = new Bonus [1];
			bonus[0] = new BonusNobilityCell(BonusName.FaceUpPermitCard, 1);
			break;
		case 12:
			bonus = new Bonus [2];
			bonus[0] = new BonusNobilityCell(BonusName.Victory, 5);
			bonus[1] = new BonusNobilityCell(BonusName.Assistant, 1);
			break;
		case 14:
			bonus = new Bonus [1];
			bonus[0] = new BonusNobilityCell(BonusName.OwnedPermitCardBonus, 1);
			break;
		case 16:
			bonus = new Bonus [1];
			bonus[0] = new BonusNobilityCell(BonusName.TwoBuiltDifferentBonus, 1);
			break;
		case 18:
			bonus = new Bonus [1];
			bonus[0] = new BonusNobilityCell(BonusName.Victory, 8);
			break;
		case 19:
			bonus = new Bonus [1];
			bonus[0] = new BonusNobilityCell(BonusName.Victory, 2);
			break;
		case 20:
			bonus = new Bonus [1];
			bonus[0] = new BonusNobilityCell(BonusName.Victory, 3);
			break;
		default:
			bonus = null;
		}

		
	}

}
