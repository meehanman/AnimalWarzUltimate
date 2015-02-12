package com.threeml.awu.world;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.gameobject.map.Terrain;
import com.threeml.awu.world.gameobject.map.Terrain.CollisionDirection;

/**
 * Simple sprite class (supporting rotation)
 * 
 * @version 1.0
 */
public class Sprite extends GameObject {

	// /////////////////////////////////////////////////////////////////////////
	// Default values
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Default maximum acceleration and velocity
	 */
	public static float DEFAULT_MAX_ACCELERATION = Float.MAX_VALUE;
	public static float DEFAULT_MAX_VELOCITY = Float.MAX_VALUE;

	/**
	 * Default maximum angular acceleration and velocity
	 */
	public static float DEFAULT_MAX_ANGULAR_ACCELERATION = Float.MAX_VALUE;
	public static float DEFAULT_MAX_ANGULAR_VELOCITY = Float.MAX_VALUE;

	// /////////////////////////////////////////////////////////////////////////
	// Properties
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Acceleration and velocity of the sprite, alongside maximum values.
	 * Position is inherited from game object.
	 */
	public Vector2 velocity = new Vector2();
	public Vector2 acceleration = new Vector2();

	public float maxAcceleration = DEFAULT_MAX_ACCELERATION;
	public float maxVelocity = DEFAULT_MAX_VELOCITY;
	/**
	 * Strength of gravity to apply along the y-axis
	 */
	public float GRAVITY = -400.0f;
	/**
	 * Orientation alongside angular velocity and acceleration, with maximum
	 * values.
	 */
	public float orientation;
	public float angularVelocity;
	public float angularAcceleration;

	public float maxAngularAcceleration = DEFAULT_MAX_ANGULAR_ACCELERATION;
	public float maxAngularVelocity = DEFAULT_MAX_ANGULAR_VELOCITY;

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
	// Configuration Methods
	// /////////////////////////////////////////////////////////////////////////
	
	
	// /////////////////////////////////////////////////////////////////////////
	// Collision Detection
	////////////////////////////////////////////////////////////////////////////
	public Boolean checkForAndResolveTerrainCollisions(Terrain TerrainObj) {
		
		Boolean collisionResolved = false;
		BoundingBox SpriteBB = this.getBound();
		
		if(acceleration.x > 0){ //Travelling Right
			if(TerrainObj.isPixelSolid(SpriteBB.x + SpriteBB.halfWidth,SpriteBB.y,CollisionDirection.Right,this)){
				collisionResolved=true;
			}
		}
		
		if(acceleration.x < 0){ //Travelling Left
			if(TerrainObj.isPixelSolid(SpriteBB.x - SpriteBB.halfWidth,SpriteBB.y,CollisionDirection.Left,this)){
				collisionResolved=true;
			}
		}
		
		if(acceleration.y > 0){ //Travelling Up
			if(TerrainObj.isPixelSolid(SpriteBB.x,SpriteBB.y - SpriteBB.halfHeight,CollisionDirection.Up,this)){
				collisionResolved=true;
			}
		}
		
		//Always check downwards for collisions
		if(TerrainObj.isPixelSolid(SpriteBB.x,SpriteBB.y + SpriteBB.halfHeight,CollisionDirection.Down,this)){
			collisionResolved=true;
		}
		
		return collisionResolved;
	
	}
	// /////////////////////////////////////////////////////////////////////////
	// Update and Draw
	// /////////////////////////////////////////////////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.qub.eeecs.gage.world.GameObject#update(uk.ac.qub.eeecs.gage.engine
	 * .ElapsedTime)
	 */
	@Override
	public void update(ElapsedTime elapsedTime) {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.qub.eeecs.gage.world.GameObject#draw(uk.ac.qub.eeecs.gage.engine
	 * .ElapsedTime, uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D,
	 * uk.ac.qub.eeecs.gage.world.LayerViewport,
	 * uk.ac.qub.eeecs.gage.world.ScreenViewport)
	 */
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
		
		if (GraphicsHelper.getSourceAndScreenRect(this, layerViewport,
				screenViewport, drawSourceRect, drawScreenRect)) {

			float scaleX = 
					(float) drawScreenRect.width() 
						/ (float) drawSourceRect.width();
			float scaleY = 
					(float) drawScreenRect.height() 
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
	
	////////////////////////////////////////////////////////////////////////////
	// GET X and Y
	////////////////////////////////////////////////////////////////////////////
	/*
	 * Gets the X position of the sprint bound
	 * 
	 * Used in Collision Detection
	 * */
	public float getX(){
		return this.mBound.x;
	}
	public void setX(float x){
		this.mBound.x = x;
	}
	/*
	 * Gets the Y position of the sprint bound
	 * 
	 * Used in Collision Detection
	 * */
	public float getY(){
		return this.mBound.y;
	}
	public void setY(float y){
		this.mBound.y = y;
	}
}