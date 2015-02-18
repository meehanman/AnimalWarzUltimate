package com.threeml.awu.world.gameobject.droppable;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.map.Terrain;

/**
 * 
 * Default Item class, other droppable items should inherit
 * from this class
 * 
 * Extends Sprite
 *
 */
public class Item extends Sprite {
	//TODO    - add some attributes that are relevant to all droppable classes
	//			or ditch this class because it's unnecessary
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param startX
	 * 			The x coordinate of the starting position
	 * @param startY
	 * 			The y coordinate of the starting position
	 * @param width
	 * 			width of object, as it appears on screen
	 * @param height
	 * 			height of object, as it appears on screen
	 * @param bitmap
	 * 			Bitmap used to represent this control
	 * @param gameScreen
	 * 			Gamescreen to which this control belongs
	 */
	public Item(float startX, float startY, float width, float height, Bitmap bitmap,
			GameScreen gameScreen) {
		super(startX, startY, width, height, bitmap, gameScreen);
	}
	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
	//TODO - Is this method needed? If no, get rid of it
	/**
	 * @param elapsedTime
	 * 			Elapsed time information
	 * @param TerrainObj
	 * 			
	 */
	public void update(ElapsedTime elapsedTime,  Terrain TerrainObj) {
		// Call the sprite's update method to apply the defined 
		// accelerations and velocities to provide a new position
		// and orientation.
		super.update(elapsedTime,TerrainObj);
		
	}
	



}
