package com.threeml.awu.engine;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * @author Mary-Jane
 *	
 */
public class GameCountDownTimer extends CountDownTimer{
	private boolean finished = false;
	public GameCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onTick(long millisUntilFinished) {
    	 Log.v("CountDownTimer", "Count Down : " + Long.toString(millisUntilFinished/1000));
    	 finished = false;
     }
	@Override
     public void onFinish() {
		 Log.v("CountDownTimer", "Count Down : Finished");
		 finished = true;
     }
	public boolean hasFinished(){
		return finished;
	}
}
