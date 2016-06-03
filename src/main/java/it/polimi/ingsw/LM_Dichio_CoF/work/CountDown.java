package it.polimi.ingsw.LM_Dichio_CoF.work;

import java.util.Timer;
import java.util.TimerTask;

public class CountDown {

	Timer timer;
	boolean finished = false;
	
	
	public CountDown(int seconds){
		timer = new Timer();
		timer.schedule(new TimeIsFinished(), seconds*1000);
	}
	
	class TimeIsFinished extends TimerTask{
		public void run(){
			finished = true;
			timer.cancel();
		}
	}
	
	public boolean isTimeFinished(){ return finished; }
	
}
