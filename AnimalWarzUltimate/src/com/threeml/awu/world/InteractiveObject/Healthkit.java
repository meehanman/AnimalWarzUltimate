package com.threeml.awu.world.InteractiveObject;

import android.graphics.Bitmap;

import com.threeml.awu.world.GameScreen;

public class Healthkit extends Item{

	/**
	 * Strength of gravity to apply along the y-axis
	 */
	private float GRAVITY = -4.0f;
	
	public Healthkit(float startX, float startY, Bitmap bitmap, GameScreen gameScreen) {
		super(startX, startY, 20.0f, 20.0f, bitmap, gameScreen);
		
	}
	

	public int getHealthValue() {
		// TODO Auto-generated method stub
		return 50;
	}
}
