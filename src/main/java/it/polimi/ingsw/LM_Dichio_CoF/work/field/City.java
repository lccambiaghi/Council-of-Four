package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.Random;

import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;

public class City {

	NameCity nameCity;
	int numberBonus;
	Bonus[] arrayBonus;
	City nearbyCity[];
	
	
	/*
	 * As we can see the Constructor only receives the name of the city given by the Region
	 */
	public City(Configurations config, NameCity nameCity){
		Random random = new Random();
		
		this.nameCity=nameCity;
		
		numberBonus= random.nextInt(config.getMaxNumberBonusPerCity()-
				config.getMinNumberBonusPerCity()+1) +
				config.getMinNumberBonusPerCity();
		
		arrayBonus = new Bonus[numberBonus];
		for(int i=0; i< numberBonus; i++){
			// Polymorphism
			arrayBonus[i]= new BonusCity();
		}
		
	}	
	
	public NameCity getNameCity() {
		return nameCity;
	}

	public void setNameCity(NameCity nameCity) {
		this.nameCity = nameCity;
	}
	
	public Bonus[] getArrayBonus() {
		return arrayBonus;
	}
	
	
}
