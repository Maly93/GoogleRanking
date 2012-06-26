package project;

import java.util.Timer;
import java.util.TimerTask;

public class TimerExample extends TimerTask {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TimerTask task = new TimerExample();
		
		Timer timer = new Timer();
		
		timer.schedule(task, 1000);
	}

	@Override
	public void run() {
		
		System.out.println("Hello World.");
	}

}
