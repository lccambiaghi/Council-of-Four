package it.polimi.ingsw.LM_Dichio_CoF_PlayerSide;

import java.util.Scanner;
import it.polimi.ingsw.LM_Dichio_CoF.work.Configurations;

public class CreateConfigurations extends Thread{

	PlayerSide playerSide;
	
	public CreateConfigurations(PlayerSide playerSide){ this.playerSide=playerSide; }
	Scanner inCLI = new Scanner(System.in);
	
	public void run(){
		
		System.out.println("You are the first player");
		System.out.println("You have to create the configurations of a match\n");
		
		int playersMaxNumber = 0;
		boolean repeat = true;
		System.out.println("First of all, enter the max number of players you want (min 2, max 8)");
		do {
	        while (!inCLI.hasNextInt()){
	        	System.out.println("Enter a number!");
	        	inCLI.next();
	        }
	        playersMaxNumber= inCLI.nextInt();
	        if(2<=playersMaxNumber&&playersMaxNumber<=8){
				repeat = false;
			}else System.out.println("min 2, max 8!");
	    } while (repeat);
		
		Configurations config = new Configurations();
		
		/*
		 * Do not change this parameter and the difficulty one until we haven't create 
		 * new maps for those combination missing
		 */
		config.setCitiesNumber(15);
		
		config.setPermitCardBonusNumberMin(2);
		config.setPermitCardBonusNumberMax(3);
		
		config.setNobilityBonusRandom(false);
		if(config.isNobilityBonusRandom()==false){
			config.setNobilityBonusNumber(7);
		}
		
		config.setCityLinksPreconfigured(false);
		if(config.isCityLinksPreconfigured()==false){
			int[][]cityLinksMatrix =  new int[][]{
				{0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	1,	0,	1,	0,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	1,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1},
				{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0}
			};
			/*
    		 * This fir cycle is for making the matrix specular,
    		 * because in the txt file it is only upper triangular set
    		 */
			for(int i=0; i<config.getCitiesNumber(); i++){
				for(int j=i; j<config.getCitiesNumber();j++){
					cityLinksMatrix[j][i]=cityLinksMatrix[i][j];
				}
			}
			config.setCityLinksMatrix(cityLinksMatrix);
			
		}else{
			config.setDifficulty("n".charAt(0));
		}
		
		config.setCityBonusRandom(true);
		if(config.isCityBonusRandom()==false){
			//da implementare, ma essendo un esempio non ha senso farlo ora (l'array di mappe)
		}else{
			config.setCityBonusNumberMin(2);
			config.setCityBonusNumberMax(3);
		}
		
		playerSide.setPlayersMaxNumber(playersMaxNumber);
		playerSide.setConfigurations(config);
		
	}
	
}
