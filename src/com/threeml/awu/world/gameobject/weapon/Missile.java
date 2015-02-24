package com.threeml.awu.world.gameobject.weapon;

import android.graphics.Bitmap;

import com.threeml.awu.world.GameScreen;

//TODO - add JavaDoc Description
/**
 * 
 * Gun class
 *
 */
public class Missile extends Weapon {
	
	
	
	// ///////////////////////////////////////////////////////////////////////// 
	// Constructors 
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param x
	 * 				Centre x location of the control
	 * @param y
	 * 				Centre y location of the control
	 * @param bitmap
	 * 				Bitmap used to represent this control
	 * @param gameScreen
	 * 				Gamescreen to which this control belongs
	 */
	public Missile(float x, float y, Bitmap bitmap, GameScreen gameScreen) {
		super(x, y, bitmap, gameScreen);
	}
	
	// ///////////////////////////////////////////////////////////////////////// 
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
}
