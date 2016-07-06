package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.LM_Dichio_CoF.model.field.CityName;

public class InputHandler {

	private Scanner in;
	private boolean stopped = false;
	private Thread t;
	
	public InputHandler(){	}
	
	public void stopInputNumber(){
		stopped=true;
		System.out.println("The signal to stop has been received, but I don't know how to do it...");
	}
	
	public int inputNumber(int lowerBound, int upperBound){
		
		int inputNumber = 0;
		//try{
			
			in = new Scanner(System.in);
			
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
			
		//}catch (InterruptedException e) {
			//System.out.println("Too late!");
		//}
		
		return inputNumber;
		
	}
	
	
	public static Character[] inputCity (CityName currentCity, CityName lastCity){
		Scanner in = new Scanner(System.in);
		String string;
		boolean correct=true;
		ArrayList <Character> temp;
		
		int asciiLowerBound= (int) currentCity.toString().charAt(0);
		int asciiUpperBound= (int) lastCity.toString().charAt(0);;
				
		do{
			correct=true;
			string = in.nextLine();
			char [] chars = string.toUpperCase().replaceAll("\\s+", "").toCharArray();
			temp=new ArrayList <>();
			
			if(chars.length==0){
				System.out.println("You have inserted a void input");
				System.out.println("Please insert a valid set of cities");
				correct=false;
			}else{
				for(int i=0; i<chars.length && correct; i++){
					char ch = chars[i];
					if(temp.contains(ch)){
						System.out.println("You have inserted the same city more than once.");
						System.out.println("Please insert another set of cities");
						correct=false;
					}else if (((int)ch)<=asciiLowerBound || ((int) ch)>asciiUpperBound){
						System.out.println("Invalid set of cities");
						System.out.println("Please insert another one");
						correct=false;
					}else{
						temp.add(ch);
					}
				}
			}
		}while(!correct);
		
		Character [] result = temp.toArray(new Character [temp.size()]);
		return result;
	}
	
	
}
