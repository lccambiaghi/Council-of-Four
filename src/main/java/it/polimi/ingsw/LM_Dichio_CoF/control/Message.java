package it.polimi.ingsw.LM_Dichio_CoF.work;

import it.polimi.ingsw.LM_Dichio_CoF.work.field.Balcony;

public final class Message {
	
	public static void ifQuickAction_True_False(){
		System.out.println("Would you like to perform Quick Action?");
	}
	
	public static void chooseQuickAction_1_4(){
		System.out.println("Which Quick Action would you like to do?");
		System.out.println("1. Engage an Assistant");
		System.out.println("2. Change Building Permit Tiles for a Region");
		System.out.println("3. Send an Assistant to Elect a Councillor");
		System.out.println("4. Perform an additional Main Action");
	}
	
	public static void chooseMainAction_1_4(){
		System.out.println("Which Main Action would you like to do?");
		System.out.println("1. Elect a Councillor");
		System.out.println("2. Acquire a Permit Tile");
		System.out.println("3. Build an Emporium using a Permit Tile");
		System.out.println("4. Build an Emporium with the Help of the King");
	}
	
	public static void chooseRegion_1_3(){
		System.out.println("Choose a region");
		System.out.println("1. Sea");
		System.out.println("2. Hill");
		System.out.println("3. Mountain");
	}
	
	public static void chooseBalcony_1_4(){
		System.out.println("Choose a Balcony");
		System.out.println("1. Sea Balcony");
		System.out.println("2. Hill Balcony");
		System.out.println("3. Mountain Balcony");
		System.out.println("4. King Balcony");
	}
	
	public static void choosePermitCard(){
		System.out.println("Choose a Permit Tile");
	}
	
	public static void chooseCity(){
		System.out.println("Choose a city");
	}
	
	public static void choosePoliticsCards(){
		System.out.println("Choose Politic Cards" );
	}
	
	public static void youCantBuild(){
		System.out.println("You either have no Business Permit Tiles" +
				" or you have already built in every city they avail you to");
	}
	
	public static void askNewCouncillor(){
		System.out.println("What color would you like the new councillor to be?");
	}
	
	public static void askHowManyPoliticsCards(){
		System.out.println("How many Politic Cards do you want to use?");
	}
	
	
	public static void noEnoughRichness(){
		System.out.println("You don't have enough richness");
	}
	
	public static void noEnoughAssistant(){
		System.out.println("You don't have enough assistants");
	}
	
}
