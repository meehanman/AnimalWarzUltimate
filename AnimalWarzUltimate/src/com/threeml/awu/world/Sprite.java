package com.threeml.awu.world;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.gameobject.map.Terrain;

/**
 *  
 * I went to the shop to buy 6 cans of
 *                                _|    _|                
 *   _|_|_|  _|_|_|    _|  _|_|      _|_|_|_|    _|_|    
 * _|_|      _|    _|  _|_|      _|    _|      _|_|_|_|  
 *     _|_|  _|    _|  _|        _|    _|      _|        
 * _|_|_|    _|_|_|    _|        _|      _|_|    _|_|_| .... 
 *           _|                                          
 *           _|   
 *             					only to realise when I got home i had picked 7up.
 *             
 * @version 1.0
 */
public class Sprite extends GameObject {

	// /////////////////////////////////////////////////////////////////////////
	// Default values
	// /////////////////////////////////////////////////////////////////////////

	/** Default maximum acceleration */
	public static float DEFAULT_MAX_ACCELERATION = Float.MAX_VALUE;
	/** Default maximum velocity */
	public static float DEFAULT_MAX_VELOCITY = Float.MAX_VALUE;

	/** Default maximum angular acceleration */
	public static float DEFAULT_MAX_ANGULAR_ACCELERATION = Float.MAX_VALUE;
	/** Default maximum angular velocity */
	public static float DEFAULT_MAX_ANGULAR_VELOCITY = Float.MAX_VALUE;

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////

	/*
	 * Acceleration and velocity of the sprite, alongside maximum values.
	 * Position is inherited from game object.
	 */
	/** Velocity of Sprite */
	public Vector2 velocity = new Vector2();
	/** Acceleration of Sprite */
	public Vector2 acceleration = new Vector2();

	/** Max Acceleration of Sprite */
	public float maxAcceleration = DEFAULT_MAX_ACCELERATION;
	/** Max Velocity of Sprite */
	public float maxVelocity = DEFAULT_MAX_VELOCITY;

	/** Strength of gravity to apply along the y-axis */
	public float GRAVITY = -400.0f;

	/*
	 * Orientation alongside angular velocity and acceleration, with maximum
	 * values.
	 */
	/** Orientation of Sprite */
	public float orientation;
	/** Angular Velocity of Sprite */
	public float angularVelocity;
	/** Angular Acceleration of Sprite */
	public float angularAcceleration;

	/** Max Angular Accelration of Sprite */
	public float maxAngularAcceleration = DEFAULT_MAX_ANGULAR_ACCELERATION;
	/** Max Angular Velocity of Sprite */
	public float maxAngularVelocity = DEFAULT_MAX_ANGULAR_VELOCITY;

	/** Previous Position used for Collision Detection **/
	public Vector2 mPreviousPosition = position;

	/**
	 * Internal matrix use to support draw requests
	 */
	protected Matrix drawMatrix = new Matrix();

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Create a new Sprite
	 * 
	 * @param gameScreen
	 *            Gamescreen to which this sprite belongs
	 */
	public Sprite(GameScreen gameScreen) {
		super(gameScreen);
	}

	/**
	 * Create a new sprite. The size will be set to that of the associated
	 * bitmap
	 * 
	 * @param x
	 *            Centre y location of the sprite
	 * @param y
	 *            Centre x location of the sprite
	 * @param bitmap
	 *            Bitmap used to represent this sprite
	 * @param gameScreen
	 *            Gamescreen to which this sprite belongs
	 */
	public Sprite(float x, float y, Bitmap bitmap, GameScreen gameScreen) {
		super(x, y, bitmap, gameScreen);
	}

	/**
	 * Create a new sprite.
	 * 
	 * @param x
	 *            Centre y location of the sprite
	 * @param y
	 *            Centre x location of the sprite
	 * @param width
	 *            Width of the sprite
	 * @param height
	 *            Height of the sprite
	 * @param bitmap
	 *            Bitmap used to represent this sprite
	 * @param gameScreen
	 *            Gamescreen to which this sprite belongs
	 */
	public Sprite(float x, float y, float width, float height, Bitmap bitmap,
			GameScreen gameScreen) {
		super(x, y, width, height, bitmap, gameScreen);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param TerrainObj
	 *            Terrain Object that the sprite is checked against
	 * @return collisionResolved Returns if a collision was resolved
	 * 
	 * @author Dean
	 */
	public void checkForAndResolveTerrainCollisions(Terrain TerrainObj) {
	}

	/**
	 * Method can be used to determine if the player should move left or right
	 * checks if the pixel below it is solid (so cant move while in the air)
	 * 
	 * @return
	 */
	public boolean canMove(Terrain TerrainObj) {
		return TerrainObj.isPixelSolid(getX(), getY() - getBound().halfHeight);
	}

	/**
	 * Updates the sprite
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 */
	// @Override
	public void update(ElapsedTime elapsedTime, Terrain TerrainObj) {

		// If there should be a yCollision, do it now!
		if (TerrainObj.isPixelSolid(position.x, position.y
				+ (getBound().halfHeight * (int) Math.signum(velocity.y)))) {
			this.position = new Vector2(position.x, mPreviousPosition.y);
			velocity.y = 0;
		}

		float dt = (float) elapsedTime.stepTime;

		// Ensure the maximum acceleration isn't exceeded
		if (acceleration.lengthSquared() > maxAcceleration * maxAcceleration) {
			acceleration.normalise();
			acceleration.multiply(maxAcceleration);
		}

		// Update the velocity using the acceleration and ensure the
		// maximum velocity has not been exceeded
		velocity.add(acceleration.x * dt, acceleration.y * dt);

		if (velocity.lengthSquared() > maxVelocity * maxVelocity) {
			velocity.normalise();
			velocity.multiply(maxVelocity);
		}

		// Update the position using the velocity
		position.add(velocity.x * dt, velocity.y * dt);

		// Ensure the maximum angular acceleration isn't exceeded
		if (angularAcceleration < -maxAngularAcceleration
				|| angularAcceleration > maxAngularAcceleration) {
			angularAcceleration = Math.signum(angularAcceleration)
					* maxAngularAcceleration;
		}

		// Update the angular velocity using the angular acceleration and
		// ensure the maximum angular velocity has not been exceeded
		angularVelocity += angularAcceleration * dt;

		if (angularVelocity < -maxAngularVelocity
				|| angularVelocity > maxAngularVelocity) {
			angularVelocity = Math.signum(angularVelocity) * maxAngularVelocity;
		}

		// Update the orientation using the angular velocity
		orientation += angularVelocity * dt;

	}

	/**
	 * Overrides the draw method from GameObject class Draws Sprite object on
	 * the game screen
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

		if (GraphicsHelper.getSourceAndScreenRect(this, layerViewport,
				screenViewport, drawSourceRect, drawScreenRect)) {

			float scaleX = (float) drawScreenRect.width()
					/ (float) drawSourceRect.width();
			float scaleY = (float) drawScreenRect.height()
					/ (float) drawSourceRect.height();

			// Build an appropriate transformation matrix
			drawMatrix.reset();
			drawMatrix.postScale(scaleX, scaleY);

			drawMatrix.postRotate(orientation, scaleX * mBitmap.getWidth()
					/ 2.0f, scaleY * mBitmap.getHeight() / 2.0f);
			drawMatrix.postTranslate(drawScreenRect.left, drawScreenRect.top);

			// Draw the image
			graphics2D.drawBitmap(mBitmap, drawMatrix, null);
		}
	}

	/*
	 * Gets the X position of the sprint bound
	 * 
	 * Used in Collision Detection
	 */
	/**
	 * Get the x position
	 * 
	 * @return x position
	 */
	public float getX() {
		return this.position.x;
	}

	/**
	 * set x position
	 * 
	 * @param x
	 */
	public void setX(float x) {
		this.position.x = x;
	}

	/*
	 * Gets the Y position of the sprint bound
	 * 
	 * Used in Collision Detection
	 */
	/**
	 * Get the y position
	 * 
	 * @return y position
	 */
	public float getY() {
		return this.position.y;
	}

	/**
	 * set y position
	 * 
	 * @param y
	 */
	public void setY(float y) {
		this.position.y = y;
	}
}