package it.polimi.ingsw.utils;

/**
 * This class contains all the messages used in the PlayerSide to interact with the user
 */
public final class MessageClient {

	/**
	 * @return message
	 */
	public static String chooseConnection_1_2(){
		return "Choose connection:\n"
				+ "1. Socket\n"
				+ "2. RMI";
	}
	
	/**
	 * @return message
	 */
	public static String chooseToCreateConfigurations_1_2(){
		return "Do you want to create your own configurations?\n"
				+ "1. Yes\n"
				+ "2. No";
	}
	
	/**
	 * @return message
	 */
	public static String enterYourNickname(){
		return "Enter your nickname";
	}
	
	/**
	 * @return message
	 */
	public static String nicknameAlreadyInUse(){
		return "Nickname already in use, enter another one";
	}
	
	/**
	 * @return message
	 */
	public static String loginSuccesfully(){
		return "You have logged in successfully!";
	}
	
}
