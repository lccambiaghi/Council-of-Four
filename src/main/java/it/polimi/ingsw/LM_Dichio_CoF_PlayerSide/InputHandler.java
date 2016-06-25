package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.model.field.CityName;

public class InputHandler {

	public static int inputNumber(int lowerBound, int upperBound){ //TODO throws RemoteException + spostare nella classe della CLI

		Scanner in = new Scanner(System.in);
		int inputNumber;
		boolean eligibleInput=false;

		do {
			while(!in.hasNextInt()){
				System.out.println("Insert an integer value!");
				in.nextLine();
			}
			inputNumber=in.nextInt();
			in.nextLine();

			if(inputNumber>=lowerBound && inputNumber<=upperBound)
				eligibleInput=true;
			else
				System.out.println("Insert a value between "+ lowerBound
									+ " and " + upperBound);
		} while(!eligibleInput);

		return inputNumber;

	}
	public static String inputCity (CityName currentCity, CityName lastCity){
		Scanner in = new Scanner(System.in);
		String string;
		boolean correct=true;
		ArrayList <Character> temp;
		ArrayList <Character> outChar = new ArrayList<>();
		for (int i=lastCity.ordinal(); i<CityName.W.ordinal();i++){
			outChar.add(CityName.getCityNameFromIndex(i).toString().charAt(0));
		}
		
		do{
			
			string = in.nextLine();
			string.replaceAll("\\s", "");
			char [] chars = string.toCharArray();
			temp=new ArrayList <>();
			
			for(Character ch : chars){
				int enumPosition=CityName.valueOf(String.valueOf(ch)).ordinal();
				if(temp.contains(ch)){
					System.out.println("You have inserted the same city more than once.");
					System.out.println("Please insert another set of cities");
					correct=false;
				}else if (enumPosition==currentCity.ordinal()){
					System.out.println("You have inserted the same city of the started.");
					System.out.println("Please insert another set of cities");
					correct=false;
				}else if(outChar.contains(ch)){
					System.out.println("You have inserted a city out of range");
					System.out.println("Please insert another set of cities");
					correct=false;	
				}else{
					temp.add(ch);
				}
			}
		}while(!correct);
		return string;
	}
}
