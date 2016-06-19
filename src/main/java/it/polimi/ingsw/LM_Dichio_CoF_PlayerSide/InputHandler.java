package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.util.Scanner;

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
	
	public static char inputCharacter (){
		Scanner in = new Scanner(System.in);
		char inputChar;
		boolean eligibleInput=false;
		
		do {
			inputChar=in.next().charAt(0);
			in.nextLine();

			if(inputChar=='l' || inputChar=='h' || inputChar=='n')
				eligibleInput=true;
			else
				System.out.println("Insert an other char: only l, n, h are correct");
		} while(!eligibleInput);
		
		return inputChar;
	}
}
