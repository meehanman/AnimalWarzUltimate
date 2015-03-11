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
		
		Vector2 scaledValues = fixVectorScaling(x,y);
		x=scaledValues.x;
		y=scaledValues.y;
		
    	if(!outOfBoundsValadation(x,y)){return false;}
		
		//Return if there is a collision at this point
		return (Color.alpha(mBitmap.getPixel((int)x, (int)y)) > 150);
	}
	
	/**
	 * Deforms a circle around a point x,y or Radius R
	 * 
	 * http://stackoverflow.com/questions/1201200/fast-algorithm-for-drawing-filled-circles
	 * @param x
	 * @param y
	 * @param radius
	 */
	public void deformCircle(double x0, double y0, int radius){
		Log.v("slope","deformCircle() fired at "+x0+", "+y0);
		
		Vector2 scaledValues = fixVectorScaling(x0,y0);
		x0=scaledValues.x;
		y0=scaledValues.y;
		
		for(int y=-radius; y<=radius; y++){
		    for(int x=-radius; x<=radius; x++){
		        if(x*x+y*y <= radius*radius){
		        	
		        	if(!outOfBoundsValadation(x0+x,y0+y)){continue;};
		        	
		        	mBitmap.setPixel((int)x0+x, (int)y0+y, Color.argb(0, 0, 0, 0));
		        }
		    }
		}
		
		
	}
	
	/**
	 * 
	 * Fixes scaling to move from actual position to position on the bitmap
	 * 
	 * @param x 
	 * @param y
	 * @return Vector2 with new location
	 * 
	 * @author Dean
	 */
	public Vector2 fixVectorScaling(double x, double y){
		
		//Scale of Terrain image vs Viewport as we are searching Pixels within Viewport after
		x *= mBitmap.getWidth() / this.getBound().getWidth();
		y *= mBitmap.getHeight() / this.getBound().getHeight();
		
		// I don't know why I need this, but it stops the people being upside-down
		y = mBitmap.getHeight() - y;
		
		return new Vector2(x,y);
	}
	
	/**
	 * 
	 * Fixes
	 * @param x
	 * @param y
	 * @return True if the values supplied works
	 * 
	 * @author Dean
	 */
	public Boolean outOfBoundsValadation(double x, double y){
		
    	if(((int)x<=0||(int)y<=0)||((int)x>=mBitmap.getWidth()||(int)y>=mBitmap.getHeight())){
    		return false;
		}else{
    		return true;
    	}

	}
	
	
}
