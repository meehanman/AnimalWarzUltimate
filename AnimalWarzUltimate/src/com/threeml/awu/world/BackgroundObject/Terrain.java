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
	int DontShowMeEveryLog = 100;
	public boolean isPixelSolid(float xPos, float yPos, CollisionDirection cd, Sprite p){
		
		//Scale of Terrain image vs Viewport as we are searching Pixels within Viewport after
		float scaleX = mBitmap.getWidth() / this.getBound().getWidth();
		float scaleY = mBitmap.getHeight() / this.getBound().getHeight();
		
		//Depending on direction, amend the search location Left Down Right Up
		if(cd==CollisionDirection.Right){		xPos += 1;}
		else if(cd==CollisionDirection.Down){	yPos -= 1;}
		else if(cd==CollisionDirection.Left){	xPos -= 1;}
		else{									yPos += 1;}
		
		//Apply Scaling
		xPos *= scaleX;
		yPos *= scaleY;
		
		//Change y position to accompany Y starting at 0 at the top of the screen  
		yPos = mBitmap.getHeight() - yPos;

		//Valadation pixels must be within bitmap location
		if((xPos >= mBitmap.getWidth() || yPos >= mBitmap.getHeight()) && (xPos <= 0 || yPos <= 0)) {
			Log.v("ise","IPS Returning ("+xPos+","+yPos+")");
			return false;
		}
		
		
		//Checks if the color below the object is non-Alpha this walkable
		if(Color.alpha(mBitmap.getPixel((int)xPos, (int)yPos)) > 150){
			
			if(cd==CollisionDirection.Down || cd==CollisionDirection.Up){
				p.velocity.y = 0f;
			}else{
				p.velocity.x = 0f;
			}	
			//Return true if collision detected
			return true;
		}
		return false;
	}

}
