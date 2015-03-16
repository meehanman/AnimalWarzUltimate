package com.threeml.awu.world.gameobject.map;

import android.graphics.Bitmap;
import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.util.SpritesheetHandler;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
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
	private SpritesheetHandler mSpritesheetHandler;
	private Bitmap mFullImage;
	
	double mLastTime = 0;
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
		mFullImage = bitmap;
		mSpritesheetHandler = new SpritesheetHandler(mFullImage, 12, 0);
		//Initially create AABB bounding boxes for the terrain
		//CreateTerrainPhysics();
	}
	
	@Override
	public void update(ElapsedTime elapsedTime) {
		Log.v("TeamError", "Water updated");
		super.update(elapsedTime);
		if((mLastTime + 0.1) < elapsedTime.totalTime){
			Log.v("TeamError", "Water frame updated");
			mSpritesheetHandler.nextFrameVertical();
			mLastTime = elapsedTime.totalTime;
		}
		
	}
	
	/**
	 * Draw Method Override to encapsulate draw methods connected to player i.e
	 * Player Health
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 * @param graphics2D
	 *            Graphics instance
	 * @param layerViewport
	 *            Game layer viewport
	 * @param screenViewport
	 *            Screen viewport
	 */
	
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
		try {
			if (GraphicsHelper.getSourceAndScreenRect(this, layerViewport,
					screenViewport, drawSourceRect, drawScreenRect)) {
				// Draw the image
				graphics2D.drawBitmap(mSpritesheetHandler.getFrameImage(), drawSourceRect, drawScreenRect, null);
			}
		}
		catch (Exception e){
			 // move along, nothing to see here. 
		}
	}
	
	
}
