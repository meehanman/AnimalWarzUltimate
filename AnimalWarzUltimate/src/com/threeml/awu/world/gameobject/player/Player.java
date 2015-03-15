package com.threeml.awu.world.gameobject.player;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.util.SpritesheetHandler;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.GameObjectText;
import com.threeml.awu.world.gameobject.map.Terrain;
import com.threeml.awu.world.gameobject.weapon.Projectile;
import com.threeml.awu.world.gameobject.weapon.Target;
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
	private float MAX_X_VELOCITY = 200.0f;
	
	/** Scale factor that is applied to the x-velocity when the player is not moving left or right */
	private float RUN_DECAY = 0.8f;
	
	/** Instantaneous velocity with which the player jumps up */
	private float JUMP_VELOCITY = 300.0f;
		
	/** The current weapon the Player is holding**/
	private Weapon mCurrentWeapon;
	
	/** The current Direction the Player is Facing **/
	/* All players spawn facing left(-1)*/
	private float playerDirection = -1;
	
	/** The Players Target Indicator **/
	private Target playerTarget;
	
	private Projectile mProjectile;
	
	/**
	 * Returns the player Target
	 * @return the playerTarget
	 */
	public Target getPlayerTarget() {
		return this.playerTarget;
	}
	
	public Projectile getProjectile() {
		return this.mProjectile;
	}
	/** Boolean value determines whether Player is alive or dead */
	/*DM - What if ghosts say "Boo" because they only haunt people they don't like, and all they do is 
	"Boo" them from the afterlife. So its not to scare you, its to show that they think you suck*/
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
			
			//Setup Crosshair
			playerTarget = new Target(this,	gameScreen);
			mProjectile = new Projectile(this, gameScreen);
		}
		catch(Exception e){
			//Move on, Nothing to see here
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
	 * @param jumpLeft | jumpRight
	 *            	True if the jump control are active
	 * @param aimUp | aimDown
	 * 				True if the aim controls are active
	 * @param platforms
	 *            	Array of platforms in the world
	 */
	public void update(ElapsedTime elapsedTime, boolean moveLeft,
			boolean moveRight, boolean jumpLeft, boolean jumpRight,
			boolean aimUp, boolean aimDown,
			boolean weaponSelect, Terrain TerrainObj) {
		//if Player health is not full depleted
		if(mHealth > 0){
			// Depending upon the left and right movement touch controls
			// set an appropriate x-acceleration. If the user does not
			// want to move left or right, then the x-acceleration is zero
			// and the velocity decays towards zero.
			if(!isAlive()){
				acceleration.x = 0;
				mSpritesheetHandler.updatePlayerSprite(0,mSpritesheetHandler,mGameScreen);
			} else if (moveLeft && !moveRight) {
				//Set direction
				setPlayerDirection("left");
				acceleration.x = -RUN_ACCELERATION;
				mSpritesheetHandler.updatePlayerSprite(-1,mSpritesheetHandler,mGameScreen);

				
			} else if (moveRight && !moveLeft) {
				//Set direction
				setPlayerDirection("right");
				acceleration.x = RUN_ACCELERATION;
				mSpritesheetHandler.updatePlayerSprite(1,mSpritesheetHandler,mGameScreen);
	
			} else {
				acceleration.x = 0.0f;
				velocity.x *= RUN_DECAY;
			}
	
			// If the user wants to jump up then providing an immediate
			// boost to the y velocity.
			if (jumpLeft && velocity.y == 0.0f) {
				velocity.y = JUMP_VELOCITY;
				velocity.x = -JUMP_VELOCITY;
			
			}else if (jumpRight && velocity.y == 0.0f) {
				velocity.y = JUMP_VELOCITY;
				velocity.x = JUMP_VELOCITY;
			}
			else {
				velocity.y = GRAVITY;
			}
			
		
	
		// Call the sprite's update method to apply the defined 
		// accelerations and velocities to provide a new position
		// and orientation.
		super.update(elapsedTime,TerrainObj);
		
		checkForAndResolveHorozontalCollisions(TerrainObj);
		} 
		//if Player health is fully depleted
		else {
			kill();
		}
		
		mHealthText.update(elapsedTime);
		mNameText.update(elapsedTime);
		playerTarget.update(elapsedTime, aimUp, aimDown);
		mProjectile.update(elapsedTime, this, this.position, playerTarget);
		
		//Keep previous Position
		//Save this position to be used as the previous position
		mPreviousPosition = this.position;
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
		velocity = new Vector2(0,0);
		setAlive(false);
		mFullImage = mGameScreen.getGame().getAssetManager().getBitmap("PlayerGrave");
		mSpritesheetHandler.setFullImage(mFullImage);
		mSpritesheetHandler.setRows(20);
		setPlayerDirection("0");
	}
	
	/**
	 * Does Player death animation, changes bitmap empty bitmap and sets 
	 * Alive to false
	 */
	public void killByWater(){
		//if player is not in water/bottom of the screen
		if(position.y != 0){
			//do death animation
			//change bitmap to grave
		}
		velocity = new Vector2(0,0);
		setAlive(false);
		mFullImage = Bitmap.createBitmap((int)this.getBound().getWidth(), (int)this.getBound().getHeight(), Config.ALPHA_8);
		mSpritesheetHandler.setFullImage(mFullImage);
		mSpritesheetHandler.setRows(1);
		setPlayerDirection("0");
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
			LayerViewport layerViewport, ScreenViewport screenViewport, boolean active) {
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
		
		mHealthText.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
		mNameText.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
		if(active){
			playerTarget.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
			mProjectile.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
		}
	}
	
	/**
	 * Sets the health of the player
	 */
	public void setHealth(int value)
	{
		mHealth += value;
		if(mHealth >= (int)MAX_HEALTH){
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
	 * Returns the name of the Player
	 * @return name
	 */
	public String getName(){
		return mName;
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

	/**
	 * 
	 * 	Return a direction string and sets direction to that string
	 * -1 for Left and 1 for Right, 2 for aiming up and 3 for aiming down
	 * 
	 * 
	 * @return the current Direction the Player is Facing
	 */
	public float getPlayerDirection() {
		return playerDirection;
	}

	/**
	 * Place in a direction string and sets direction to that string
	 * -1 for Left and 1 for Right, 2 for aiming up and 3 for aiming down
	 * 
	 * @param playerDirection the playerDirection to set
	 */
	public void setPlayerDirection(String direction) {
		if(direction=="left"){
			this.playerDirection = -1;
		}else if(direction=="right"){
			this.playerDirection = 1;
		}else{
			this.playerDirection = 0;
		}
	}
	
	
	
	
	private void checkForAndResolveHorozontalCollisions(Terrain TerrainObj){
		
		//checkForAndResolveTerrainCollisions(TerrainObj);
		/** - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
		/**
		 * Worms wouldn't be very fun if your worm stopped moving every time a pixel got in your way. 
		 * So to keep movement smoother, the game's physics coding initiates upto an 8 pixel check to 
		 * see if your worm can be moved ontop of the collided pixel(s). Your worm gets shifted upwards 
		 * if a negative collision is returned from the loop (1 to 8). If not, your worm will stop moving.
		 * The above image demonstrates how the game calculates what to do when a collision occurs in the walking 
		 * sequence. White refers to the worm mask, Green refers to the terrain mask, Blue refers to pixels where the 
		 * two layers have collided, and the Red Arrow refers to the horizontal shift occuring in the frame.
 		*/
		int boundHeight = (int)getBound().halfHeight*2;
		int direction = (int)Math.signum(velocity.x);
		//if moving left or right
		if(direction!=0){
			//For top to bottom
			for(int i = 0; i < boundHeight; i++){
				//if solid pixel and...
				if(TerrainObj.isPixelSolid(position.x+((getBound().halfWidth/3)*direction),position.y+getBound().halfHeight-i)){
					//we're looking at the first 2/3rds
					if(i<((boundHeight/10)*6)){
						//we're solid
						this.position = new Vector2(mPreviousPosition.x, position.y);
						velocity.x = 0;
						break;
						//else we're at the last bit
					}else{
						//We need to move up
						this.position = new Vector2(position.x, mPreviousPosition.y+boundHeight-(i+1));
						break;
					}
				}
			}
		}
		/** - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	}
	
}
