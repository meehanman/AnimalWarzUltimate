package com.threeml.awu.engine;

import android.os.CountDownTimer;

/**
 * GameCountDownTimer is used for any countdowns in game. New countdowns must be
 * declared in the Game class then called in specific game screen.
 * 
 * Extends existing count down class because a "finished" type variable was
 * required.
 * 
 * Extends CountDownTimer
 * 
 * @author Mary-Jane
 * 
 */
public class GameCountDownTimer extends CountDownTimer {

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////

	/** Used to determine if the count down has finished */
	private boolean finished = false;
	private long count = 0;
	private boolean running = false;

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	public GameCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Called while count down is on in periods of time specified by
	 * countDownInterval
	 * 
	 * @param millisUntilFinished
	 *            The number of milliseconds left in count down
	 */
	@Override
	public void onTick(long millisUntilFinished) {
		finished = false;
		count = (millisUntilFinished / 1000);
		running = true;
	}

	/**
	 * Called when count down has finished
	 */
	@Override
	public void onFinish() {
		finished = true;
		running = false;
	}

	/**
	 * Returns value of finished to determine whether countdown has finished
	 * 
	 * @return finished
	 */
	public boolean hasFinished() {
		return finished;
	}

	public long getCountDownInSeconds() {
		return count;
	}

	public boolean isRunning() {
		return running;
	}
}
