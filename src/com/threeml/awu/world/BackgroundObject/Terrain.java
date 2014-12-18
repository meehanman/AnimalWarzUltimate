package com.threeml.awu.world.BackgroundObject;

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
	
	public boolean isPixelSolid(float xPos, float yPos, CollisionDirection cd, Sprite p){
		//Valadation
		if(xPos <= 0 || yPos >= 0) {Log.v("ise","IPS Returning");return false;}
		
		//Variables for changing the search location
		int xloc =0,yloc =0;
		
		//Depending on direction, amend the search location Left Down Right Up
		if(cd==CollisionDirection.Right){		xloc = 1;}
		else if(cd==CollisionDirection.Down){	yloc = -1;}
		else if(cd==CollisionDirection.Left){	xloc = -1;}
		else{									yloc = 1;}
		
		//Checks if the color below the object is non-Alpha this walkable
		if(Color.alpha(mBitmap.getPixel((int)xPos+xloc, (int)yPos+yloc)) > 150){
			if(cd==CollisionDirection.Down || cd==CollisionDirection.Up){
				p.velocity.y = 0f;
				/*//Trying to draw a dot when there is a collision
				try{
				mBitmap.eraseColor(Color.GREEN);
				mBitmap.setPixel((int)xPos+xloc, (int)yPos+yloc,Color.RED);
				}catch(IllegalStateException ise){
					Log.v("ise","isMutable = "+mBitmap.isMutable());
				}
				*/
			}else{
				p.velocity.x = 0f;
			}	
			//Return true if collision detected
			return true;
		}
		return false;
	}

}