package it.polimi.ingsw.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.server.model.CityName;

public class InputHandler {

	private Scanner in;
	private boolean stopped = false;
	
	public Object lockScanner = new Object();
	public Thread threadScanner;
	
	public InputHandler(Scanner in, Object lockScanner, Thread threadScanner){
		this.in=in;
		this.lockScanner=lockScanner;
		this.threadScanner=threadScanner;
	}
	
	public void stopInputNumber(){
		stopped=true;
	}
	
	public int inputNumber(int lowerBound, int upperBound){
		
		int inputNumber = 0;
		
		try{
			
			threadScanner.interrupt();
			
			synchronized (lockScanner) {
				try {
					lockScanner.wait();
				} catch (InterruptedException e) {}
			}
			
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
		}
		
		synchronized (lockScanner) {
			lockScanner.notify();
		}
		
		return inputNumber;
		
	}
	
	private boolean isInteger(String s){
		try{
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
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
	
	
	public Character[] inputCity (CityName currentCity, CityName lastCity){
		
		threadScanner.interrupt();
		
		synchronized (lockScanner) {
			try {
				lockScanner.wait();
			} catch (InterruptedException e) {}
		}
		
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
		
		synchronized (lockScanner) {
			lockScanner.notify();
		}
		
		Character [] result = temp.toArray(new Character [temp.size()]);
		return result;
	
	}
	
	
}
