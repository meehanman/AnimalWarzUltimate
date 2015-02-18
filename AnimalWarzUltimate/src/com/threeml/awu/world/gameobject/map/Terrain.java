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
	 * 
	 * @param gameScreen
	 * 				Gamescreen to which this control belongs
	 */
	public Terrain(GameScreen gameScreen) {
		super(gameScreen);
		mBitmap = gameScreen.getGame().getAssetManager().getBitmap("Terrain");
		
		//TODO - shouldn't be any constants here
		mBound.halfWidth = mBitmap.getWidth();
		mBound.halfHeight = mBitmap.getHeight();

	}
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

	}
	/**
	 * @author Dean
	 * @param SpriteBound
	 * 				The Sprite to check if pixel collides with Terrain
	 * @param SpriteCD
	 * 				The direction to check
	 * @return 
	 * 				Return if there is a collision at this point
	 */
	public boolean isPixelSolid(BoundingBox SpriteBound, Vector2 velocity){
		
		//Get the bounding box for the terrain for scaling
		BoundingBox TerrainBoundingBox = this.getBound();
		
		//Set up the search location around the BoundingBox Edge from Center X Y Values
		double spriteXPixel = SpriteBound.x;
		double spriteYPixel = SpriteBound.y - SpriteBound.halfHeight/2;
		
		//Change the velocity to an angle of direction in degrees
		int directionAngle = (int)((Math.atan2(velocity.y,velocity.x))*(180/3.14));
				
		//Scale of Terrain image vs Viewport as we are searching Pixels within Viewport after
		spriteXPixel *= mBitmap.getWidth() / TerrainBoundingBox.getWidth();
		spriteYPixel *= mBitmap.getHeight() / TerrainBoundingBox.getHeight();
		
		//Change y position to accompany Y starting at 0 at the top of the screen  
		spriteYPixel = mBitmap.getHeight() - spriteYPixel;
		
		//Validation: If Pixel is outside of range then return false
		if((spriteXPixel<0||spriteYPixel<0)||(spriteXPixel>mBitmap.getWidth()||spriteYPixel>mBitmap.getHeight())){return false;}
		
		//Logging of tests to determine direction and if alpha or not
		Log.v("IPSLocation", "x:"+(int)spriteXPixel+"px y:"+(int)spriteYPixel+"px: Angle:"+directionAngle);
		//Log.v("IPSLocation",(int)SpriteBound.y+" "+(int)SpriteBound.x);
		Log.v("pixelColor","Alpha:"+Color.alpha(mBitmap.getPixel((int)spriteXPixel, (int)spriteYPixel)));
		
		//Return if there is a collision at this point
		return (Color.alpha(mBitmap.getPixel((int)spriteXPixel, (int)spriteYPixel)) > 150);
		
	}

}
