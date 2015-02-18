package com.threeml.awu.world.gameobject.map;

import android.graphics.Bitmap;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;
/**
 * Background is the full screen image behind the terrain
 * The player does not interact with the background in
 * any way
 * 
 * Extends Sprite
 *
 */
public class Background extends Sprite {
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Creates new Background object
	 * 
	 * @param x
	 *            x location of the object
	 * @param y
	 *            y location of the object
	 * @param width
	 *            width of the object
	 * @param height
	 *            height of the object
	 * @param bitmap
	 *            Bitmap used to represent this object
	 * @param gameScreen
	 *            Gamescreen to which this object belongs
	 */
	public Background(float x, float y, float width, float height, Bitmap bitmap, GameScreen gameScreen){
		super(x, y, width, height, bitmap, gameScreen);
		mGameScreen = gameScreen;
		
		mBound.x = x;
		mBound.y = y;
		mBound.halfWidth = bitmap.getWidth() / 2.0f;
		mBound.halfHeight = bitmap.getHeight() / 2.0f;
	}

}
