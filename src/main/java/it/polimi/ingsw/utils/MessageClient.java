package it.polimi.ingsw.utils;

public final class MessageClient {

	public static String chooseConnection_1_2(){
		return "Choose connection:\n"
				+ "1. Socket\n"
				+ "2. RMI";
	}
	
	public static String chooseToCreateConfigurations_1_2(){
		return "Do you want to create your own configurations?\n"
				+ "1. Yes\n"
				+ "2. No";
	}
	
	public static String enterYourNickname(){
		return "Enter your nickname";
	}
	
	public static String nicknameAlreadyInUse(){
		return "Nickname already in use, enter another one";
	}
	
	public static String loginSuccesfully(){
		return "You have logged in successfully!";
	}
	
}
