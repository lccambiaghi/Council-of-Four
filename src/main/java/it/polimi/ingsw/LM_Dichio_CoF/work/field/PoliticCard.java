package it.polimi.ingsw.LM_Dichio_CoF.work.field;
import it.polimi.ingsw.LM_Dichio_CoF.work.field.Colors;
import java.util.ArrayList;

public class PoliticCard {
	
	Colors cardColor;
	
	public PoliticCard (){
		cardColor = Colors.getRandomColor(); 
	}
	
	public Colors getCardColor() {
		return cardColor;
	}

	public ArrayList <PoliticCard> InitiallyHandCards(){
		
		ArrayList <PoliticCard> InitiallyHandCards = new ArrayList <PoliticCard> ();
		for (int i=0; i<6; i++){
			PoliticCard card = new PoliticCard();
			InitiallyHandCards.add(card);
		}
		return InitiallyHandCards;
	}
}
