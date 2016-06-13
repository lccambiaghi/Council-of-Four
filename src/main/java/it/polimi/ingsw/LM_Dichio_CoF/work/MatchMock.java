package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.ArrayList;
import java.util.Scanner;

/**
    This Mock is for testing purposes. It overrides the startGame() in the constructor
    and handles the reset of the Scanner which would break tests.
 */
public class MatchMock extends Match {

    private Scanner in = new Scanner(System.in);

    public MatchMock(ArrayList<Player> arrayListPlayer){
        super(arrayListPlayer);
    }

    @Override
    public void startGame(){}

    @Override
    public int inputNumber(int lowerBound, int upperBound){

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

}


