package com.threeml.awu.world.gameobject.map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

/**
 * 
 * @author Dean
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
 */


public class Terrain extends Sprite {
	
	public enum CollisionDirection {
		Up, Down, Left, Right
	};
	
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
	int DontShowMeEveryLog = 100;
	public boolean isPixelSolid(float xPos, float yPos, CollisionDirection cd, Sprite sprite){
		
		//Variables for changing the search location
		int xloc =0,yloc =0;
		
		/*
		//Depending on direction, amend the search location Left Down Right Up
		if(cd==CollisionDirection.Right){		xloc = 1;}
		else if(cd==CollisionDirection.Down){	yloc = -1;}
		else if(cd==CollisionDirection.Left){	xloc = -1;}
		else{									yloc = 1;}
		*/
		/*
<<<<<<< .mine
		//Valadation pixels must be within bitmap location
		if((xPos+xloc <= mBitmap.getWidth() || yPos+yloc <= mBitmap.getHeight()) && (xPos+xloc >= 0 || yPos+yloc >= 0)) {
			Log.v("ise","IPS Returning");
=======
		//Validation pixels must be within bitmap boundaries. 
		//This checks if it is !NOT within these boundaries then return false
		if(!((xPos <= mBitmap.getWidth() && xPos >= 0) && (yPos <= mBitmap.getHeight() &&  yPos >= 0))){
			Log.v("ise","IPS Returning ("+xPos+","+yPos+")");
>>>>>>> .r75
			return false;
		}
		*/
		
		//Checks if the color below the object is non-Alpha this walkable
		if(Color.alpha(mBitmap.getPixel((int)xPos+xloc, (int)yPos+yloc)) > 150){
			/*
			if(DontShowMeEveryLog == 100){
				Log.v("cd","Bitmap c:"+mBitmap.getPixel((int)xPos+xloc, (int)yPos+yloc)+" Player c:"+p.getBitmap().getPixel((int)xPos+xloc, (int)yPos+yloc));
			}
			//Iterate
			if(DontShowMeEveryLog >= 100){
				DontShowMeEveryLog = 0;
			}else{
				DontShowMeEveryLog++;
			}
			*/
			
			if(cd==CollisionDirection.Down || cd==CollisionDirection.Up){
				sprite.velocity.y = 0f;
			}else{
				sprite.velocity.x = 0f;
			}	
			//Return true if collision detected
			return true;
		}
		return false;
	}

}
