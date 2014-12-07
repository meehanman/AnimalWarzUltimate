package com.threeml.awu.world.BackgroundObject;

import android.graphics.Bitmap;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

public class Terrain extends Sprite {

	public Terrain(GameScreen gameScreen) {
		super(gameScreen);
		
		mBitmap = gameScreen.getGame().getAssetManager().getBitmap("Terrain");
		
		mBound.halfWidth = 1000.0f;
		mBound.halfHeight = 300.0f;
		
	}
	/**
	 * Create a new Terrain object
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
	public Terrain(float x, float y, float width, float height,
			Bitmap bitmap, GameScreen gameScreen) {
		super(x,y,width,height,bitmap,gameScreen);
	}

}
