package it.polimi.ingsw.LM_Dichio_CoF.work.field;

public class NobilityRouteCell {
	
	Bonus [] arrayBonus;
	int i;
	
	public NobilityRouteCell(int index){
		this.i=index;
		
		switch (i){
		case 2:
			arrayBonus = new Bonus [2];
			arrayBonus[0] = new NobilityCellBonus(BonusName.Richness, 2);
			arrayBonus[1] = new NobilityCellBonus(BonusName.Victory, 2);
			break;
		case 4:
			arrayBonus = new Bonus [1];
			arrayBonus[0] = new NobilityCellBonus(BonusName.BuiltCityBonus, 1);
			break;
		case 6:
			arrayBonus = new Bonus [1];
			arrayBonus[0] = new NobilityCellBonus(BonusName.MainMove, 1);
			break;
		case 8:
			arrayBonus = new Bonus [2];
			arrayBonus[0] = new NobilityCellBonus(BonusName.Victory, 3);
			arrayBonus[1] = new NobilityCellBonus(BonusName.Cards, 1);
			break;
		case 10:
			arrayBonus = new Bonus [1];
			arrayBonus[0] = new NobilityCellBonus(BonusName.FaceUpPermitCard, 1);
			break;
		case 12:
			arrayBonus = new Bonus [2];
			arrayBonus[0] = new NobilityCellBonus(BonusName.Victory, 5);
			arrayBonus[1] = new NobilityCellBonus(BonusName.Assistant, 1);
			break;
		case 14:
			arrayBonus = new Bonus [1];
			arrayBonus[0] = new NobilityCellBonus(BonusName.OwnedPermitCardBonus, 1);
			break;
		case 16:
			arrayBonus = new Bonus [1];
			arrayBonus[0] = new NobilityCellBonus(BonusName.TwoBuiltDifferentBonus, 1);
			break;
		case 18:
			arrayBonus = new Bonus [1];
			arrayBonus[0] = new NobilityCellBonus(BonusName.Victory, 8);
			break;
		case 19:
			arrayBonus = new Bonus [1];
			arrayBonus[0] = new NobilityCellBonus(BonusName.Victory, 2);
			break;
		case 20:
			arrayBonus = new Bonus [1];
			arrayBonus[0] = new NobilityCellBonus(BonusName.Victory, 3);
			break;
		default:
			arrayBonus = null;
		}

	}

	public boolean hasBonus(){
		if (arrayBonus== null)
			return false;
		return true;
	}
	
	public Bonus[] getArrayBonus() {
		return arrayBonus;
	}
	
}
