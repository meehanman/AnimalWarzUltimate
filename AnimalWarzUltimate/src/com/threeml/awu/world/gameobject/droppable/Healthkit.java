package com.threeml.awu.world.gameobject.droppable;

import android.graphics.Bitmap;

import com.threeml.awu.world.GameScreen;

/**
 * Healthkit object that falls from the top of the screen When player picks it
 * up, the player's health is increased by the Health Value
 * 
 * Extends Item
 * 
 */
public class Healthkit extends Item {

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////

	/** Health Value the player will receive */
	private int mHealthValue;

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * @param healthValue
	 *            Sets the health value the player will receive Health Value
	 *            cannot be less than 0 or it will be set the default value of 0
	 * @param startX
	 *            The x coordinate of the starting position
	 * @param startY
	 *            The y coordinate of the starting position
	 * @param bitmap
	 *            Bitmap used to represent this control
	 * @param gameScreen
	 *            Gamescreen to which this control belongs
	 */
	public Healthkit(int healthValue, float startX, float startY,
			Bitmap bitmap, GameScreen gameScreen) {
		super(startX, startY, 10.0f, 10.0f, bitmap, gameScreen);

		// Set the default value for healthValue
		setHealthValue(healthValue);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	private void setHealthValue(int healthValue) {
		if (healthValue < 0) {
			mHealthValue = 0;
		} else {
			mHealthValue = healthValue;
		}
	}

	/**
	 * Gets the Health Value
	 * 
	 * @return Health Value
	 */
	public int getHealthValue() {
		return mHealthValue;
	}

}
