package it.polimi.ingsw.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is designed to offer methods that use timers.
 * There are two ways to use them:
 * 
 * CountDown: 
 * - startCountDown: this method starts a timer that will be cancelled when it expires
 * - isCountDownFinished: this method tells the caller if the timer has finished
 * 
 * ThreadOrTimerWaiter:
 * - waitForThreadUntilTimerExpires: this method permits to start a timer and
 *  check every second if it has expired or if the Thread passed as parameter is not alive anymore
 */
public class ControlTimer {

	
	// ***************** COUNTDOWN METHODS ***************** //
	
	private Timer timer;
	private boolean finished = false;	
	
	/**
	 * This method permits to start the CountDown
	 * 
	 * @param seconds : the seconds of the CountDown
	 */
	public void startCountDown(int seconds){
		timer = new Timer();
		timer.schedule(new TimeIsFinished(), (long)seconds*1000);
	}
	
	/**
	 * This class extends TimerTask.
	 * It only sets the boolean "finished" to true when the timer expires
	 */
	class TimeIsFinished extends TimerTask{
		
		@Override
		public void run(){
			finished = true;
			timer.cancel();
		}
	}
	
	/**
	 * This method permits to check if the CountDown has finishes
	 * 
	 * @return true if CountDown finished, otherwise false
	 */
	public boolean isCountDownFinished(){ return finished; }
	
	
	
	// ***************** THREAD-TIMER METHOD ***************** //
	
	/**
	 * When called this method starts a timer of "seconds" (parameter).
	 * It checks every second if the Thread passed as parameter is alive and, if not, it returns.
	 * The method continues checking the Thread until the timer has expired,
	 * then it returns.
	 * 
	 * @param t : the Thread to be checked
	 * @param seconds : the seconds of the timer
	 */
	public void waitForThreadUntilTimerExpires(Thread t, int seconds){
		
		long startTime = System.currentTimeMillis();
		long endTime = startTime + (seconds)*1000;
		
		/*
		 * This "while" permits to check every second if the timer
		 * to set configurations has expired
		 */
		while (System.currentTimeMillis() < endTime) {
		    try {
		         Thread.sleep(1000);  // Sleep 1 second
		        
		         if(!t.isAlive())
		        	 break;
		         
		    } catch (InterruptedException e) {
		    	//Impossible to reach
		    }	
		}
	}
	
	
	
}
