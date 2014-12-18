package com.threeml.awu.world.InteractiveObject;

import android.graphics.Rect;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BitmapFont;
import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.BackgroundObject.Terrain;
import com.threeml.awu.world.BackgroundObject.Terrain.CollisionDirection;


/**
 * Player controlled sphere (that's not really a sphere)
 * 
 * @version 1.0
 */
public class Player extends Sprite {

	// /////////////////////////////////////////////////////////////////////////
	// Properties
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Health the player has and Maximum that can be
	 */
	private int MaxHealth = 200;
	private int health = 100;
	
	private int currentFrame = 0;
	private int mRows = 0;
	private int mColumns = 0;
	/**
	 * Strength of gravity to apply along the y-axis
	 */
	private float GRAVITY = -800.0f;
	
	/**
	 * Acceleration with which the player can move along
	 * the x-axis
	 */
	private float RUN_ACCELERATION = 150.0f;
	
	/**
	 * Maximum velocity of the player along the x-axis
	 */
	private float MAX_X_VELOCITY = 200.0f;
	
	/**
	 * Scale factor that is applied to the x-velocity when
	 * the player is not moving left or right
	 */
	private float RUN_DECAY = 0.8f;

	/**
	 * Instantaneous velocity with which the player jumps up
	 */
	private float JUMP_VELOCITY = 200.0f;
	
	/**
	 * Scale factor that is used to turn the x-velocity into
	 * an angular velocity to give the visual appearance
	 * that the sphere is rotating as the player moves.
	 */
	private float ANGULAR_VELOCITY_SCALE = 1.5f;
	
	//HealthFont
	private BitmapFont healthText;
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Create the player's sphere
	 * 
	 * @param startX
	 *            x location of the sphere
	 * @param startY
	 *            y location of the sphere
	 * @param gameScreen
	 *            Gamescreen to which sphere belongs
	 */
public Player(float startX, float startY, int columns, int rows, GameScreen gameScreen) {		super(startX, startY, 20.0f, 20.0f, gameScreen.getGame()
				.getAssetManager().getBitmap("Player"), gameScreen);
		
		healthText = new BitmapFont(startX, startY, gameScreen, health+"");
		
		mColumns = columns;
		mRows = rows;
		
		//for when animation finally works... but for now, just use 20x20 for player size
		/*Bitmap bitmap = gameScreen.getGame().getAssetManager().getBitmap("Player");
		
		this.mBound.halfHeight = (bitmap.getHeight() / mRows) / 2;
		this.mBound.halfWidth = (bitmap.getWidth() / mColumns) / 2;*/
		
		
		int width = (int) (this.mBound.halfWidth * 2);
		int height = (int) (this.mBound.halfHeight * 2);
		int srcY = currentFrame * height;
		int  srcX = currentFrame * width;
		Rect src = new Rect(srcX, height, srcX + width, srcY + height);
		
		this.drawSourceRect.set(src);
		
		//this.mBound.halfHeight = bitmap.getHeight() / 2;
		//this.mBound.halfWidth = bitmap.getWidth() / 2;
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Update the player
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 * @param moveLeft
	 *            True if the move left control is active
	 * @param moveRight
	 *            True if the move right control is active
	 * @param jumpUp
	 *            True if the jump up control is active
	 * @param platforms
	 *            Array of platforms in the world
	 */
	public void update(ElapsedTime elapsedTime, boolean moveLeft,
			boolean moveRight, boolean jumpUp, Terrain TerrainObj) {

		float playerGRAVITY = GRAVITY;
		
		if(checkForAndResolveTerrainCollisions(TerrainObj)){
			playerGRAVITY = 0f;
		}
		
		
		// Apply gravity to the y-axis acceleration
		acceleration.y = playerGRAVITY;

		// Depending upon the left and right movement touch controls
		// set an appropriate x-acceleration. If the user does not
		// want to move left or right, then the x-acceleration is zero
		// and the velocity decays towards zero.
		if (moveLeft && !moveRight) {
			acceleration.x = -RUN_ACCELERATION;
			this.nextFrame();
		} else if (moveRight && !moveLeft) {
			acceleration.x = RUN_ACCELERATION;
		} else {
			acceleration.x = 0.0f;
			velocity.x *= RUN_DECAY;
		}

		// If the user wants to jump up then providing an immediate
		// boost to the y velocity.
		if (jumpUp && velocity.y == 0.0f) {
			velocity.y = JUMP_VELOCITY;
		}

		// Call the sprite's update method to apply the defined 
		// accelerations and velocities to provide a new position
		// and orientation.
		super.update(elapsedTime);

		// The player's sphere is constrained by a maximum x-velocity,
		// but not a y-velocity. Make sure we have not exceeded this.
		if (Math.abs(velocity.x) > MAX_X_VELOCITY)
			velocity.x = Math.signum(velocity.x) * MAX_X_VELOCITY;

		healthText.update(elapsedTime,this);
		
		
	}

	/*
	 * skips to the next frame of the image
	 */
	private void nextFrame(){
		if(mColumns > 0){
			currentFrame = ++currentFrame % mColumns;
		}
	}


	private Boolean checkForAndResolveTerrainCollisions(Terrain TerrainObj) {
		Boolean collisionResolved = false;
		BoundingBox PlayerBB = this.getBound();
		
		if(acceleration.x > 0){ //Travelling Right
			if(TerrainObj.isPixelSolid(PlayerBB.x + PlayerBB.halfWidth,PlayerBB.y,CollisionDirection.Right,this)){
				collisionResolved=true;
			}
		}
		
		if(acceleration.x < 0){ //Travelling Left
			if(TerrainObj.isPixelSolid(PlayerBB.x - PlayerBB.halfWidth,PlayerBB.y,CollisionDirection.Left,this)){
				collisionResolved=true;
			}
		}
		
		if(acceleration.y > 0){ //Travelling Up
			if(TerrainObj.isPixelSolid(PlayerBB.x,PlayerBB.y - PlayerBB.halfHeight,CollisionDirection.Up,this)){
				collisionResolved=true;
			}
		}
		
		//Always check downwards for collisions
		if(TerrainObj.isPixelSolid(PlayerBB.x,PlayerBB.y + PlayerBB.halfHeight,CollisionDirection.Down,this)){
			collisionResolved=true;
		}
		
		return collisionResolved;
		
		
	}
	
	/**
	 * Draw Method Override to encapsulate draw methods connected to player i.e
	 * Player Health
	 * **/
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
		
		super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
		healthText.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
	}
	
	/**
	 * Sets the health of the player
	 */
	public void setHealth(int value)
	{
		health += value;
		if(health <= MaxHealth){
			health = MaxHealth;
		}
	}
	
	/**
	 * Returns the health of the player
	 */
	public int getHealth() 
	{
		return health;
	}
}
