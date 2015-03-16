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
	
	/**If the item should still be active on screen
	 *  i.e. an item is used up and is no longer needed
	 * **/
	private boolean active = true;
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

	/**
	 * @return if the current item is still active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param Set the item to not active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	



}
