package com.threeml.awu.world.gameobject.map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

/**
 * 
 * Terrain holds the location of the walkable terrain
 * By scanning on create of the terrain, we are able to draw boxes
 * to represent the walkable location on the map allowing for AABB 
 * collision detection. The "walkable" rectangles then can be updated
 * only when the terrain bitmap changes (destructible).
 * 
 * AABB can then be easily detected by checking any collisions between
 * the bounding boxes and another boundingBox i.e. player, Healthpack etc
 * 
 * Extends Sprite
 * 
 * @author Dean
 * 
 */
public class Terrain extends Sprite {
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Create a new Terrain object
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
	public Terrain(float x, float y, float width, float height,
			Bitmap bitmap, GameScreen gameScreen) {
		super(x,y,width,height,bitmap,gameScreen);
		
		//Initially create AABB bounding boxes for the terrain
		//CreateTerrainPhysics();
	}

	/**
	 * Detects if there is a pixel at the location X Y
	 * NOTE: Applys scaling already and Y starts from bottom
	 * @param x - x location of bound to check
	 * 
	 * @param y - y location of bound to check
	 * 
	 * @return True if pixel is solid at location xy
	 * 
	 * @version 2
	 * 
	 * @author Dean
	 */
	public boolean isPixelSolid(double x,double y){
		
		//Scale of Terrain image vs Viewport as we are searching Pixels within Viewport after
		x *= mBitmap.getWidth() / this.getBound().getWidth();
		y *= mBitmap.getHeight() / this.getBound().getHeight();
		
		//Change y position to accompany Y starting at 0 at the top of the screen  
		y = mBitmap.getHeight() - y;
		
		//Validation: If Pixel is outside of range then return false
		if(((int)x<=0||(int)y<=0)||((int)x>=mBitmap.getWidth()||(int)y>=mBitmap.getHeight())){return false;}
		
		//Return if there is a collision at this point
		return (Color.alpha(mBitmap.getPixel((int)x, (int)y)) > 150);
	}
}
