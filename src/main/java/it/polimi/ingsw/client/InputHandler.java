package it.polimi.ingsw.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.server.model.CityName;


/**
 * This class offers methods that handle the input by the client.
 * 
 * inputNumber: the method expects the client to insert a integer value between the integer bounds
 * taken as parameters; it handles incorrect insertions, such as not integers or integers out of bounds
 * 
 * inputCity: the method expects the client to insert one or more letters (lower case and upper case)
 * that represents cities between the bounds taken as parameters; 
 * it handles incorrect values or repeated ones
 * 
 */

public class InputHandler {

	private Scanner in;
	private PlayerSide playerSide;
	
	private boolean stopped = false;
	
	/**
	 * @param playerSide : the playerSide that creates the object
	 * @param in : the scanner already created in the playerSide
	 */
	public InputHandler(PlayerSide playerSide, Scanner in){
		this.playerSide=playerSide;
		this.in=in;
	}
	
	/**
	 * The method is called remotely and permits to stop the current inputNumber going on
	 */
	public void stopInputNumber(){
		stopped=true;
	}
	
	/**
	 * First the method takes the scanner setting the boolean "freeScanner" to false.
	 * Then waits through the scanner for an input and handle incorrect values.
	 * It catches the InterruptedException thrown by the method "readWithSleep" when
	 * the boolean "stopped" is set to TRUE remotely.
	 * Finally it releases the scanner setting the boolean "freeScanner" to true.
	 * 
	 * @param lowerBound 
	 * @param upperBound
	 * @return the choice of the client
	 */
	public int inputNumber(int lowerBound, int upperBound){
		
		int inputNumber = 0;
		
		playerSide.setFreeScanner(false);
		
		try{
			
			String s;
			
			boolean eligibleInput=false;
	
			do {
				
				s = readWithSleep();
				while(!isInteger(s)){
					System.out.println("Insert an integer value!");
					s = readWithSleep();
				}
				inputNumber=Integer.parseInt(s);
	
				if(inputNumber>=lowerBound && inputNumber<=upperBound)
					eligibleInput=true;
				else
					System.out.println("Insert a value between "+ lowerBound
										+ " and " + upperBound);
			} while(!eligibleInput);
			
		}catch (InterruptedException e) {
			System.out.println("Too late!");
		}catch (IOException e1){
			e1.printStackTrace();
		
		}finally{
			playerSide.setFreeScanner(true);
		}
		
		return inputNumber;
		
	}
	
	/**
	 * Check if the string taken as parameter is an integer.
	 * 
	 * @param s, the string to be checked
	 * @return true if s is integer, otherwise false 
	 */
	private boolean isInteger(String s){
		try{
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
	/**
	 * The method reads from the scanner and returns the string read to the caller.
	 * If the scanner is empty it sleeps for 0.1 seconds.
	 * 
	 * @return the string read through the method "nextLine" of the scanner
	 * @throws InterruptedException if the boolean "stopped" is set to false
	 * @throws IOException if the scanner get closed
	 */
	private String readWithSleep() throws InterruptedException, IOException{
		boolean go = true;
		while(go){
			if(System.in.available() > 0){
				go=false;
				return in.nextLine();
			}else{
				Thread.sleep(100);
				if(stopped){
					stopped=false;
					throw new InterruptedException();
				}
			}
		}
		return null;
	}
	
	/**
	 * First the method takes the scanner setting the boolean "freeScanner" to false.
	 * The method expects the client to insert a string that represents one or more cities.
	 * It asks again when the input is incorrect: no input, not-letters or letters out of bounds or repetitions.
	 * At last it releases the scanner setting the boolean "freeScanner" to true.
	 * 
	 * @param currentCity : the first city (excluded) representing the lower bound
	 * @param lastCity : the last city (included) representing the upper bound
	 * @return the array of characters (ALL upper case) representing the cities chosen
	 */
	public Character[] inputCity (CityName currentCity, CityName lastCity){
		
		String string;
		boolean correct=true;
		ArrayList <Character> temp;
		
		int asciiLowerBound= (int) currentCity.toString().charAt(0);
		int asciiUpperBound= (int) lastCity.toString().charAt(0);
				
		playerSide.setFreeScanner(false);
		
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
		
		playerSide.setFreeScanner(true);
		
		return temp.toArray(new Character [temp.size()]);
	
	}
	
	
}
