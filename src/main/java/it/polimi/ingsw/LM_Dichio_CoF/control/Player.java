package it.polimi.ingsw.LM_Dichio_CoF.control;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import it.polimi.ingsw.LM_Dichio_CoF.model.PoliticCard;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.City;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.PermitCard;
import it.polimi.ingsw.LM_Dichio_CoF_PlayerSide.RMIPlayerSideInterface;

import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;


public class Player{
	
	private String nickname;
	
	private char typeOfConnection;
	private Socket playerSocket;
	private RMIPlayerSideInterface rmiPlayerSide;
	
	private char CLIorGUI;

	
	private Scanner inputSocket;
	private PrintWriter outputSocket;	
	
	// variables of the game
	private int richness;
	private int assistant;

	private ArrayList <PoliticCard> arrayListPoliticCard= new ArrayList<>();

	private ArrayList <PermitCard> arrayListPermitCard = new ArrayList<>();
	private ArrayList <PermitCard> arrayListUsedPermitCard = new ArrayList<>();

	private int mainActionsLeft;
	
	private ArrayList <City>  arrayListEmporiumBuilt = new ArrayList<>();

	
	/* The constructor assigns to the player the type of connection */
	public Player(char typeOfConnection){
		this.typeOfConnection=typeOfConnection; 	
	}

	public Socket getPlayerSocket() {return playerSocket;}
	public void setPlayerSocket(Socket playerSocket) { this.playerSocket = playerSocket; }
	
	public void openSocketStream(){
		try {
			outputSocket = new PrintWriter(playerSocket.getOutputStream());
			inputSocket = new Scanner(playerSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public RMIPlayerSideInterface getRmiPlayerSide() {return rmiPlayerSide;}
	public void setRmiPlayerSide(RMIPlayerSideInterface rmiPlayerSide) {this.rmiPlayerSide=rmiPlayerSide;}

	public char getCLIorGUI() {return CLIorGUI;}
	public void setCLIorGUI(char cLIorGUI) {CLIorGUI = cLIorGUI;}	

	public String getNickname() {return nickname;}
	public void setNickname(String nickname) {this.nickname = nickname;}
	
	public char getTypeOfConnection() {return typeOfConnection;}
	public void setTypeOfConnection(char typeOfConnection) {this.typeOfConnection = typeOfConnection;}
	
	public Scanner getInputSocket() {return inputSocket;}
	public PrintWriter getOutputSocket() {return outputSocket;}	
	
	public int getRichness() {return richness;}
	public void setRichness(int richness) {this.richness = richness;}

	public int getAssistant() {	return assistant;}
	public void setAssistant(int assistant) {this.assistant = assistant;}
	public void addAssistant(int increment)	{assistant +=increment;}
	public void decrementAssistant(int decrement)	{assistant -=decrement;}
	
	public ArrayList<PoliticCard> getArrayListPoliticCard() {return arrayListPoliticCard;}
	public void addPoliticCard(PoliticCard politicCard) {arrayListPoliticCard.add(politicCard);}
	public void discardPoliticCard(PoliticCard politicCard){arrayListPoliticCard.remove(politicCard);}

	public ArrayList<PermitCard> getArrayListPermitCard(){return arrayListPermitCard;}
	public void acquirePermitCard(PermitCard permitCard){arrayListPermitCard.add(permitCard);}
	public void usePermitCard (PermitCard permitCard){
		int i=arrayListPermitCard.indexOf(permitCard);
		arrayListUsedPermitCard.add(arrayListPermitCard.remove(i));
	}
	
	public ArrayList<City> getArrayListEmporiumBuilt(){return arrayListEmporiumBuilt;}
	public void addCityEmporiumBuilt (City city){arrayListEmporiumBuilt.add(city);}

	public int getMainActionsLeft() {
		return mainActionsLeft;
	}

	public void setMainActionsLeft(int mainActionsLeft) {
		this.mainActionsLeft = mainActionsLeft;
	}
}