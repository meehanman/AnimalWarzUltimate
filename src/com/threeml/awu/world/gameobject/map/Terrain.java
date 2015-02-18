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
		mBound.halfWidth = 1000.0f;
		mBound.halfHeight = 300.0f;

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
		
		//Get the bounding box for the terrain
		BoundingBox TerrainBoundingBox = this.getBound();
		
		//Set up the search location around the BoundingBox Edge from Center X Y Values
		double spriteXPixel = SpriteBound.x, spriteYPixel = SpriteBound.y;
		//Change the velocity to an angle of direction in degrees
		int directionAngle = (int)((Math.atan2(velocity.y,velocity.x))*(180/3.14));
		
		/*
		if(SpriteCD==CollisionDirection.Down){
			spriteXPixel += SpriteBound.halfWidth;
		}else if(SpriteCD==CollisionDirection.Up){
			spriteXPixel -= SpriteBound.halfWidth;
		}else if(SpriteCD==CollisionDirection.Left){
			spriteYPixel -= SpriteBound.halfHeight;
		}else if(SpriteCD==CollisionDirection.Right){
			spriteYPixel += SpriteBound.halfHeight;
		}
		*/
		
		//Scale of Terrain image vs Viewport as we are searching Pixels within Viewport after
		spriteXPixel *= mBitmap.getWidth() / TerrainBoundingBox.getWidth();
		spriteYPixel *= mBitmap.getHeight() / TerrainBoundingBox.getHeight();
		
		//Change y position to accompany Y starting at 0 at the top of the screen  
		spriteYPixel = mBitmap.getHeight() - spriteYPixel;
		
		//Valadation: If Pixel is outside of range then return false
		if((spriteXPixel<0||spriteYPixel<0)||(spriteXPixel>mBitmap.getWidth()||spriteYPixel>mBitmap.getHeight())){return false;}
		
		Log.v("IPSLocation", "x:"+spriteXPixel+"px y:"+spriteYPixel+"px: @:"+directionAngle);
		Log.v("pixelColor","Alpha:"+Color.alpha(mBitmap.getPixel((int)spriteXPixel, (int)spriteYPixel)));
		
		//Return if there is a collision at this point
		return (Color.alpha(mBitmap.getPixel((int)spriteXPixel, (int)spriteYPixel)) > 150);
		
	}

}
