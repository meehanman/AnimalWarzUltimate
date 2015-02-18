package com.threeml.awu.world;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.util.Vector2;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Game object superclass
 * 
 * @version 1.0
 */
public class GameObject {

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////

	/** Game screen to which this game object belongs */
	protected GameScreen mGameScreen;

	/** Bitmap used to render this game object */
	protected Bitmap mBitmap;

	/** Position of this game object */
	public Vector2 position = new Vector2();

	/** Bounding box for this game object */
	protected BoundingBox mBound = new BoundingBox();

	/** Reusable Rect used to draw this game object */	//TODO - needs more specific commenting
	protected Rect drawSourceRect = new Rect();
	
	/** Reusable Rect used to draw this game object */
	protected Rect drawScreenRect = new Rect();

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Create a new game object
	 * 
	 * @param gameScreen
	 *            Gamescreen to which this object belongs
	 */
	public GameObject(GameScreen gameScreen) {
		mGameScreen = gameScreen;
	}

	/**
	 * Create a new game object
	 * 
	 * @param x
	 *            x location of the object
	 * @param y
	 *            y location of the object
	 * @param bitmap
	 *            Bitmap used to represent this object
	 * @param gameScreen
	 *            Gamescreen to which this object belongs
	 */
	public GameObject(float x, float y, Bitmap bitmap, GameScreen gameScreen) {
		mGameScreen = gameScreen;

		position.x = x;
		position.y = y;
		mBitmap = bitmap;

		mBound.x = x;
		mBound.y = y;
		mBound.halfWidth = bitmap.getWidth() / 2.0f;
		mBound.halfHeight = bitmap.getHeight() / 2.0f;
	}

	/**
	 * Create a new game object
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
	public GameObject(float x, float y, float width, float height,
			Bitmap bitmap, GameScreen gameScreen) {
		mGameScreen = gameScreen;

		position.x = x;
		position.y = y;
		mBitmap = bitmap;

		mBound.x = x;
		mBound.y = y;
		mBound.halfWidth = width / 2.0f;
		mBound.halfHeight = height / 2.0f;
	}
	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Return the bounding box for this game object.
	 * 
	 * Note: The values within the bounding box should not be modified.
	 * 
	 * @return
	 */
	public BoundingBox getBound() {
		// Ensure the bound is centred on the sprite's current location
		mBound.x = position.x;
		mBound.y = position.y;
		return mBound;
	}

	/**
	 * Return the bitmap used for this game object.
	 * 
	 * @return Bitmap associated with this sprite.
	 */
	public Bitmap getBitmap() {
		return mBitmap;
	}

	/**
	 * Set the position of the game object
	 * 
	 * @param x
	 *            x-location of the game object
	 * @param y
	 *            y-location of the game object
	 */
	public void setPosition(float x, float y) {

		mBound.x = position.x = x;
		mBound.y = position.y = y;
	}

	/**
	 * Update the game object
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 */
	public void update(ElapsedTime elapsedTime) {

	}

	/**
	 * Draw the game object
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
		if (GraphicsHelper.getClippedSourceAndScreenRect(this, layerViewport,
				screenViewport, drawSourceRect, drawScreenRect)) {
			graphics2D
					.drawBitmap(mBitmap, drawSourceRect, drawScreenRect, null);
		}
	}
}