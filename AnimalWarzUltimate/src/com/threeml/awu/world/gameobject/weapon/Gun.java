package com.threeml.awu.world.gameobject.weapon;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;

//TODO - add JavaDoc Description
/**
 * 
 * Gun class
 *
 */
public class Gun extends Weapon {
	
	
	
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
	public Gun(float x, float y, Bitmap bitmap, GameScreen gameScreen) {
		super(x, y, bitmap, gameScreen);
	}
	
	// ///////////////////////////////////////////////////////////////////////// 
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
}
