package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.control.Player;

/**
 * Bonus class offers methods useful for the creation phase of the field
 * and those to apply bonuses to players
 */
public class Bonus {

	BonusName bonusName;

	int increment;


	public Bonus(){
		/**
		 * Default constructor, offered to implement the subclasses
		 */
	}

	/**
	 * @param bonusName assigned to the newly created bonus
	 * @param increment assigned to the newly created bonus
     */
	public Bonus(BonusName bonusName, int increment){
			this.bonusName=bonusName;
			this.increment=increment;
	}

	/**
	 * This method checks if this.bonusName is present in arrayBonus
	 *
	 * @param arrayBonus in which to look for this.bonusName
	 * @return true if found, false if not
     */
	/*  */
	public boolean bonusNameIsIn(Bonus[] arrayBonus){

		for (Bonus bonus : arrayBonus)
			if (bonus != null && bonusName == bonus.getBonusName())
				return true;

		return false;

	}

	/**
	 * @param player whom to apply the bonus
	 * @param field in which apply changes
     */
	public void applyBonus(Player player, Field field) {

		NobilityRoute nobilityRoute;
		
		switch (bonusName){
			case Assistant:
				player.addAssistant(increment);
				break;
			case Richness:
				player.addRichness(increment);
				break;
			case Nobility:
				nobilityRoute = field.getNobilityRoute();
				nobilityRoute.movePlayer(increment, player);
				break;
			case Victory:
				player.addVictory(increment);
				break;
			case Cards:
				for (int i=0; i<increment; i++)
					player.addPoliticCard(new PoliticCard(Color.getRandomColor()));
				break;
			default: //case MainMove:
				player.addMainActionLeft(increment);
		}

	}

	public BonusName getBonusName() {
		return bonusName;
	}

	public int getIncrement() {
		return increment;
	}

}
