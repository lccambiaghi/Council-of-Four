package it.polimi.ingsw.utils;

import java.util.Timer;
import java.util.TimerTask;

public class ControlTimer {

	private Timer timer;
	private boolean finished = false;
	
	public ControlTimer(){}
	
	
	/**
	 * 
	 * @param seconds
	 */
	public void startCountDown(int seconds){
		timer = new Timer();
		timer.schedule(new TimeIsFinished(), seconds*1000);
	}
	
	class TimeIsFinished extends TimerTask{
		public void run(){
			finished = true;
			timer.cancel();
		}
	}
	
	public boolean isCountDownFinished(){ return finished; }
	
	
	/**
	 * 
	 * @param t
	 * @param seconds
	 */
	public void waitForThreadUntilTimerExpires(Thread t, int seconds){
		/**
		 * This "while" permits to check every second if the timer
		 * to set configurations has expired
		 */
		long startTime = System.currentTimeMillis();
		long endTime = startTime + (seconds)*1000;
		while (System.currentTimeMillis() < endTime) {
		    try {
		         Thread.sleep(1000);  // Sleep 1 second
		         
		         // If the player has set players before the timer expires
		         if(!t.isAlive())
		        	 break;
		         
		    } catch (InterruptedException e) {}	
		}
	}
	
	
	
}
