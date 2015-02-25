package com.threeml.awu.world.dashboardobject;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.engine.input.Input;
import com.threeml.awu.engine.input.TouchHandler;
import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.world.GameObject;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;

/**
 * Simple control that can detected if a touch event falls on it.
 * 
 * Important: It is assumed that the control is defined in terms
 * of screen coordinates (not layer coordinates).
 * 
 * Extends GameObject
 * 
 * @author Mary-Jane
 * @version 1.0
 */
public class Control extends GameObject {

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Create a new control.
	 * 
	 * @param x
	 *            Centre x location of the control
	 * @param y
	 *            Centre y location of the control
	 * @param width
	 *            Width of the control
	 * @param height
	 *            Height of the control
	 * @param bitmap
	 *            Bitmap used to represent this control
	 * @param gameScreen
	 *            Gamescreen to which this control belongs
	 */
	public Control(float x, float y, float width, float height,
			String bitmapName, GameScreen gameScreen) {
		super(x, y, width, height, gameScreen.getGame().getAssetManager()
				.getBitmap(bitmapName), gameScreen);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Determine if this control has been activated (touched).
	 * 
	 * @return boolean 
	 * 				true if the control has been touched, false otherwise
	 */
	public boolean isActivated() {

		// Consider any touch events occurring in this update
		Input input = mGameScreen.getGame().getInput();

		// Check if any of the touch events were on this control
		BoundingBox bound = getBound();
		for (int idx = 0; idx < TouchHandler.MAX_TOUCHPOINTS; idx++) {
			if (input.existsTouch(idx)) {
				if (bound.contains(input.getTouchX(idx), input.getTouchY(idx))) {
					return true;
				}
			}
		}

		return false;
	}
	
	public boolean isTouched() {
		// Consider any touch events occurring in this update
				Input input = mGameScreen.getGame().getInput();

				// Check if any of the touch events were on this control
				BoundingBox bound = getBound();
				for (int idx = 0; idx < TouchHandler.MAX_TOUCHPOINTS; idx++) {
					if (input.existsTouch(idx)) {
						if (bound.contains(input.getTouchX(idx), input.getTouchY(idx))) {
							return true;
						}
					}
				}

				return false;
	}

	/**
	 * Overrides the draw method from GameObject class
	 * 				Draws Control object on the game screen
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
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {

		// Assumed to be in screen space so just draw the whole thing
		drawScreenRect.set((int) (position.x - mBound.halfWidth),
				(int) (position.y - mBound.halfWidth),
				(int) (position.x + mBound.halfWidth),
				(int) (position.y + mBound.halfHeight));

		graphics2D.drawBitmap(mBitmap, null, drawScreenRect, null);
		
	}

	/**
	 * @param bitmap
	 * 			The bitmap that will be quartered into four segments.
	 * @return
	 * 		Bitmap divided into four segments.
	 */
	public Bitmap [] quarterBitmap(Bitmap bitmap) {
		
		Bitmap [] mBitmap = new Bitmap[4]; 
		mBitmap[0] = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth()/2, 
				bitmap.getHeight()/2);
		mBitmap[1] = Bitmap.createBitmap(bitmap, bitmap.getWidth()/2, 0,
				bitmap.getWidth()/2, bitmap.getHeight()/2);
		mBitmap[2] = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight()/2,
				bitmap.getWidth()/2, bitmap.getHeight()/2);
		mBitmap[3] = Bitmap.createBitmap(bitmap, bitmap.getWidth()/2, 
				bitmap.getHeight()/2, bitmap.getWidth()/2, bitmap.getHeight()/2);
		return mBitmap;
		
	}
	/*	
	/**
	 * 	Divides the Bitmap into a specified number of rows and columns
	 * 
	 * @param bitmap
	 * 			Bitmap that will be divided
	 * @param x
	 * 			Number of rows in the Bitmap
	 * @param y
	 * 			Number of columns in the Bitmap
	 * @return
	 * 			The divided Bitmap
	 
	public Bitmap [][] divideBitmap(Bitmap bitmap, int x, int y) {
		
		Bitmap[][] divideBitmap = new Bitmap[x][y];
		int width, height;
		width = bitmap.getWidth() / x;
		height = bitmap.getHeight() / y;
		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				divideBitmap[i][j] = Bitmap.createBitmap(bitmap, x * width, y * height,
						width, height);
			}
		}
		return divideBitmap;
		
	} */
}
