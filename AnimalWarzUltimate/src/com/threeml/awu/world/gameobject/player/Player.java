package com.threeml.awu.world.gameobject.player;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.util.SpritesheetHandler;
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
	private int mHealth;
	
	/** Player's name */
	private GameObjectText mNameText;
	private String mName;
	
	/** SpritesheetHandler to handle spritesheet */
	private SpritesheetHandler mSpritesheetHandler;
	
	/** Health text that appears above the Player */
	private GameObjectText mHealthText;
	
	/** Max Health the player can have */
	private int MAX_HEALTH = 100;
	
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
		
	/** The current weapon the Player is holding**/
	private Weapon mCurrentWeapon;
	
	/** Boolean value determines whether Player is alive or dead */
	private boolean mAlive;
	
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
			mHealth = MAX_HEALTH;
			mHealthText = new GameObjectText(gameScreen, Integer.toString(mHealth), this, (int)this.getBound().halfHeight);
			mNameText = new GameObjectText(gameScreen, mName, this, (int)this.getBound().halfHeight + 10);
			setAlive(true);
		}
		catch(Exception e){
			Log.e("Text Error", "Player constructor error : " + e);
		}
		
		mSpritesheetHandler = new SpritesheetHandler(mFullImage, rows, columns);
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
			boolean moveRight, boolean jumpLeft, boolean jumpRight, boolean weaponSelect,
			Terrain TerrainObj) {
		
		//if Player health is not full depleted
		if(mHealth > 0){
			// Depending upon the left and right movement touch controls
			// set an appropriate x-acceleration. If the user does not
			// want to move left or right, then the x-acceleration is zero
			// and the velocity decays towards zero.
			if (moveLeft && !moveRight) {
					acceleration.x = -RUN_ACCELERATION;
	
				this.mSpritesheetHandler.setFullImage(mGameScreen.getGame().getAssetManager().getBitmap("PlayerWalk"));
				if(mSpritesheetHandler != null && this.mSpritesheetHandler.getRows() > 1){
					this.mSpritesheetHandler.nextFrameVertical();
				}
				
			} else if (moveRight && !moveLeft) {
					acceleration.x = RUN_ACCELERATION;
	
				Matrix matrix = new Matrix();
				matrix.preScale(-1, 1);
				Bitmap bitmap = mGameScreen.getGame().getAssetManager().getBitmap("PlayerWalk");
				this.mSpritesheetHandler.setFullImage(Bitmap.createBitmap(bitmap, 0,
						0, bitmap.getWidth(), bitmap.getHeight(), matrix, false));
				if(mSpritesheetHandler != null && this.mSpritesheetHandler.getRows() > 1){
					this.mSpritesheetHandler.nextFrameVertical();
				}
			} else {
				acceleration.x = 0.0f;
				acceleration.y = 0.0f;
				velocity.x *= RUN_DECAY;
			}
	
			// If the user wants to jump up then providing an immediate
			// boost to the y velocity.
			if (jumpLeft && velocity.y == 0.0f) {
				velocity.y = JUMP_VELOCITY;
				velocity.x = -JUMP_VELOCITY;
			
			}else if (jumpRight && velocity.y == 0.0f) {
				velocity.y = JUMP_VELOCITY;
				velocity.x = -JUMP_VELOCITY;
			}
			else {
				velocity.y = GRAVITY;
			}
		} 
		//if Player health is fully depleted
		else {
			kill();
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
	 * Does Player death animation, changes bitmap to grave and sets 
	 * Alive to false
	 */
	public void kill(){
		//if player is not in water/bottom of the screen
		if(position.y != 0){
			//do death animation
			//change bitmap to grave
		}
		setAlive(false);
		Log.v("Kill", this.mName + " has been KILLED");
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
				graphics2D.drawBitmap(mSpritesheetHandler.getFrameImage(), drawSourceRect, drawScreenRect, null);
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
		Log.v("Kill", "Health value of " + this.mHealth + " and value " + value);
		if(mHealth >= (int)MAX_HEALTH){
			mHealth = (int)MAX_HEALTH;
		}
		mHealthText.updateText(Integer.toString(mHealth));
		Log.v("Kill", "Health value of " + this.mName + " has been updated to : " + this.mHealth);
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

	/**
	 * Get if Player is alive
	 * @return Alive
	 * 				
	 */
	public boolean isAlive() {
		return mAlive;
	}

	/**
	 * Set if Player is alive
	 * @param alive 
	 * 				Set true or false, determines if Player is alive
	 */
	public void setAlive(boolean mAlive) {
		this.mAlive = mAlive;
	}

}
