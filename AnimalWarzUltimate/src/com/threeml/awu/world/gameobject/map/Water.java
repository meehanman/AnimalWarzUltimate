package com.threeml.awu.world.gameobject.map;

import android.graphics.Bitmap;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

/**
 * 
 * The water appears at the bottom of the screen
 * 
 * Initilised from Map
 * 
 * @author Dean
 *
 */
public class Water extends Sprite {

	/**
	 * Create a new Water object
	 * 
	 * @param x
	 *            	x location of the object
	 * @param y
	 *            	y location of the object
	 * @param width
	 *            	width of the object
	 * @param height
	 *            	height of the object
	 * @param bitmap
	 *            	Bitmap used to represent this object
	 * @param gameScreen
	 *            	Gamescreen to which this object belongs
	 */
	public Water(float x, float y, float width, float height,
			Bitmap bitmap, GameScreen gameScreen) {
		super(x,y,width,height,bitmap,gameScreen);
		
		//Initially create AABB bounding boxes for the terrain
		//CreateTerrainPhysics();
	}
}
