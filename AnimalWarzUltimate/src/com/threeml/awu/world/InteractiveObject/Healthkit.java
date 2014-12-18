package com.threeml.awu.world.InteractiveObject;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

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
	
	public void update(ElapsedTime elapsedTime, Sprite gameSprite) {
		
		// apply acceleration to the item
		acceleration.y = GRAVITY;
		
		// call the sprites update method to provide a new orientation
		super.update(elapsedTime);
	}
}
