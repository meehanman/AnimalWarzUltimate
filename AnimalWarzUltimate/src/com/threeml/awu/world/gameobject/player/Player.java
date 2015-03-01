package com.threeml.awu.world.gameobject.player;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BitmapFont;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.world.FrameHandler;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.GameObjectText;
import com.threeml.awu.world.gameobject.map.Terrain;
import com.threeml.awu.world.gameobject.weapon.Weapon;


/**
 * Player is any playable character on screen
 * It is controlled by the onscreen controls and affected by
 * droppables and weapons
 * 
 * Extends Sprite
 * 
 * @version 1.0
 */
public class Player extends Sprite {

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////
	
	/** Bitmap used to represent this Player */
	private Bitmap mFullImage;
	
	/** Player's health value */
	private int mHealth = 100;
	
	/** Player's name */
	private GameObjectText mNameText;
	private String mName;
	
	/** FrameHandler to handle spritesheet */
	private FrameHandler mFrameHandler;
	
	/** Health text that appears above the Player */
	private GameObjectText mHealthText;
	
	/** Max Health the player can have */
	private float MAX_HEALTH = 200.0f;
	
	/** Strength of gravity to apply along the y-axis */
	private static float GRAVITY = -100.0f;
	
	/** Acceleration with which the player can move along the x-axis */
	private float RUN_ACCELERATION = 200.0f;
	
	/** Maximum velocity of the player along the x-axis */
	private float MAX_X_VELOCITY = 300.0f;
	
	/** Scale factor that is applied to the x-velocity when the player is not moving left or right */
	private float RUN_DECAY = 0.8f;
	
	/** Instantaneous velocity with which the player jumps up */
	private float JUMP_VELOCITY = 200.0f;
	
	/** Scale factor that is used to turn the x-velocity into an angular velocity to give the visual appearance
	 * that the sphere is rotating as the player moves. */
	private float ANGULAR_VELOCITY_SCALE = 1.5f;
	
	/** The current weapon the Player is holding**/
	private Weapon mCurrentWeapon;
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Create the player's sphere
	 * 
	 * @param startX
	 *            	X location of the player
	 * @param startY
	 *            	Y location of the player
	 * @param columns
	 * 				No. of columns in the bitmap spritesheet
	 * @param rows
	 * 				No. of rows in the bitmap spritesheet
	 * @param bitmap
	 * 				Bitmap image used to represent the player
	 * @param gameScreen
	 *            Gamescreen to which player belongs
	 */
public Player(float startX, float startY, int columns, int rows, Bitmap bitmap, GameScreen gameScreen, String name) {		
	super(startX, startY, 20.0f, 20.0f, bitmap, gameScreen);
		mFullImage = bitmap;
		try {
			mName = name;
			mHealthText = new GameObjectText(gameScreen, Integer.toString(mHealth), this, (int)this.getBound().halfHeight);
			mNameText = new GameObjectText(gameScreen, mName, this, (int)this.getBound().halfHeight + 10);
		}
		catch(Exception e){
			Log.e("Text Error", "Player constructor error : " + e);
		}
		
		mFrameHandler = new FrameHandler(mFullImage, rows, columns);
}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Update the player
	 * 
	 * @param elapsedTime
	 *            	Elapsed time information
	 * @param moveLeft
	 *            	True if the move left control is active
	 * @param moveRight
	 *            	True if the move right control is active
	 * @param jumpUp
	 *            	True if the jump up control is active
	 * @param platforms
	 *            	Array of platforms in the world
	 */
	public void update(ElapsedTime elapsedTime, boolean moveLeft,
			boolean moveRight, boolean jumpUp, boolean weaponSelect,
			Terrain TerrainObj) {

		// Depending upon the left and right movement touch controls
		// set an appropriate x-acceleration. If the user does not
		// want to move left or right, then the x-acceleration is zero
		// and the velocity decays towards zero.
		if (moveLeft && !moveRight) {
			acceleration.x = -RUN_ACCELERATION;
			acceleration.y = RUN_ACCELERATION;
			this.mFrameHandler.setFullImage(mGameScreen.getGame().getAssetManager().getBitmap("PlayerWalk"));
			if(mFrameHandler != null && this.mFrameHandler.getRows() > 1){
				this.mFrameHandler.nextFrameVertical();
			}
			
		} else if (moveRight && !moveLeft) {
			acceleration.x = RUN_ACCELERATION;
			acceleration.y = RUN_ACCELERATION;
			
			Matrix matrix = new Matrix();
			matrix.preScale(-1, 1);
			Bitmap bitmap = mGameScreen.getGame().getAssetManager().getBitmap("PlayerWalk");
			this.mFrameHandler.setFullImage(Bitmap.createBitmap(bitmap, 0,
					0, bitmap.getWidth(), bitmap.getHeight(), matrix, false));
			if(mFrameHandler != null && this.mFrameHandler.getRows() > 1){
				this.mFrameHandler.nextFrameVertical();
			}
		} else {
			acceleration.x = 0.0f;
			acceleration.y = 0.0f;
			velocity.x *= RUN_DECAY;
		}

		// If the user wants to jump up then providing an immediate
		// boost to the y velocity.
		if (jumpUp && velocity.y == 0.0f) {
			velocity.y = JUMP_VELOCITY;
		}
		else {
			velocity.y = GRAVITY;
		}

		// Call the sprite's update method to apply the defined 
		// accelerations and velocities to provide a new position
		// and orientation.
		super.update(elapsedTime,TerrainObj);

		// The player's sphere is constrained by a maximum x-velocity,
		// but not a y-velocity. Make sure we have not exceeded this.
		if (Math.abs(velocity.x) > MAX_X_VELOCITY)
			velocity.x = Math.signum(velocity.x) * MAX_X_VELOCITY;

		mHealthText.update(elapsedTime);
		mNameText.update(elapsedTime);
		
		
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
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
		try {
			if (GraphicsHelper.getSourceAndScreenRect(this, layerViewport,
					screenViewport, drawSourceRect, drawScreenRect)) {
	
				// Draw the image
				graphics2D.drawBitmap(mFrameHandler.getFrameImage(), drawSourceRect, drawScreenRect, null);
			}
		}
		catch (Exception e){
			Log.v("Text Error", e + " : Error in Player draw method");
		}
		
		mHealthText.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
		mNameText.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
	}
	
	/**
	 * Sets the health of the player
	 */
	public void setHealth(int value)
	{
		mHealth += value;
		if(mHealth <= (int)MAX_HEALTH){
			mHealth = (int)MAX_HEALTH;
		}
		mHealthText.updateText(Integer.toString(mHealth));
	}
	
	/**
	 * Returns the health of the player
	 * @return health
	 */
	public int getHealth() 
	{
		return mHealth;
	}

	/**
	 * Returns the current weapon the player is holding
	 * @return Weapon
	 */
	public Weapon getCurrentWeapon() {
		return mCurrentWeapon;
	}

	/**
	 * @param Sets a new weapon for the user to hold
	 */
	public void setCurrentWeapon(Weapon mCurrentWeapon) {
		this.mCurrentWeapon = mCurrentWeapon;
	}

}