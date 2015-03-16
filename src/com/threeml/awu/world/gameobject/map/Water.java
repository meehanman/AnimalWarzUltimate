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

	// Control that handles the subframes
	private SpritesheetHandler mSpritesheetHandler;
	// The full sprite image
	private Bitmap mFullImage;

	// Frame Count
	double mLastTime = 0;

	/**
	 * Create a new Water object
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
	public Water(float x, float y, float width, float height, Bitmap bitmap,
			GameScreen gameScreen) {
		super(x, y, width, height, bitmap, gameScreen);

		// Initilise the fullImage
		mFullImage = bitmap;
		// Create the sprite sheet handler
		mSpritesheetHandler = new SpritesheetHandler(mFullImage, 12, 1);
		// Go to next frame

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.threeml.awu.world.GameObject#update(com.threeml.awu.engine.ElapsedTime
	 * )
	 */
	@Override
	public void update(ElapsedTime elapsedTime) {
		super.update(elapsedTime);
		if ((mLastTime + 0.07) < elapsedTime.totalTime) {
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

				graphics2D.drawBitmap(mSpritesheetHandler.getFrameImage(), drawSourceRect, drawScreenRect, null);

			}
		} catch (Exception e) {
			// move along, nothing to see here.
		}
	}

}
