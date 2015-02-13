package com.threeml.awu.world.gameobject.player;

import android.graphics.Bitmap;
import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BitmapFont;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.world.Constants.PlayerSpec;
import com.threeml.awu.world.FrameHandler;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.map.Terrain;


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
	private Bitmap fullImage;
	
	private int health = 100;
	
	private FrameHandler mFrameHandler;
	
	//HealthFont
	private BitmapFont healthText;
	
	private boolean mActive = false;
	
	private int mId;
	
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
public Player(float startX, float startY, int columns, int rows, Bitmap bitmap, GameScreen gameScreen, int id) {		
	super(startX, startY, 30.0f, 30.0f, bitmap, gameScreen);
		mId = id;
		fullImage = bitmap;
		healthText = new BitmapFont(startX, startY, gameScreen, health+"");
		
		mFrameHandler = new FrameHandler(fullImage, rows, columns);
		//mFrameHandler.enableAnimation(mColumns > 0 ? true : false);	
		//disabling animation (it doesn't work right now) for the sake of maintaining a running game for now
		mFrameHandler.enableAnimation(false);
		
		//for when animation finally works... but for now, just use 20x20 for player size
		/*Bitmap bitmap = gameScreen.getGame().getAssetManager().getBitmap("Player");
		
		this.mBound.halfHeight = (bitmap.getHeight() / mRows) / 2;
		this.mBound.halfWidth = (bitmap.getWidth() / mColumns) / 2;*/
		
		
		/*int width = (int) (this.mBound.halfWidth * 2);
		int height = (int) (this.mBound.halfHeight * 2);
		int srcY = currentFrame * height;
		int  srcX = currentFrame * width;
		Rect src = new Rect(srcX, height, srcX + width, srcY + height);
		
		this.drawSourceRect.set(src);*/
		
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

		// Depending upon the left and right movement touch controls
		// set an appropriate x-acceleration. If the user does not
		// want to move left or right, then the x-acceleration is zero
		// and the velocity decays towards zero.
		if (moveLeft && !moveRight) {
			acceleration.x = -PlayerSpec.RunAcceleration.getValue();
			
			if(this.mFrameHandler != null && this.mFrameHandler.getAnimation() != null){
				if(this.mFrameHandler.getAnimation().enabled()){
					this.mFrameHandler.getAnimation().nextFrame();
				}
			}
			
		} else if (moveRight && !moveLeft) {
			acceleration.x = PlayerSpec.RunAcceleration.getValue();
		} else {
			acceleration.x = 0.0f;
			velocity.x *= PlayerSpec.RunDecay.getValue();
		}

		// If the user wants to jump up then providing an immediate
		// boost to the y velocity.
		if (jumpUp && velocity.y == 0.0f) {
			velocity.y = PlayerSpec.JumpVelocity.getValue();
		}

		// Call the sprite's update method to apply the defined 
		// accelerations and velocities to provide a new position
		// and orientation.
		super.update(elapsedTime,TerrainObj);

		// The player's sphere is constrained by a maximum x-velocity,
		// but not a y-velocity. Make sure we have not exceeded this.
		if (Math.abs(velocity.x) > PlayerSpec.MaxXVelocity.getValue())
			velocity.x = Math.signum(velocity.x) * PlayerSpec.MaxXVelocity.getValue();

		healthText.update(elapsedTime,this);
		
		
	}
	
	/**
	 * Draw Method Override to encapsulate draw methods connected to player i.e
	 * Player Health
	 * **/
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
		try {
		//super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
		if (GraphicsHelper.getSourceAndScreenRect(this, layerViewport,
				screenViewport, drawSourceRect, drawScreenRect)) {

			// Draw the image
			graphics2D.drawBitmap(mFrameHandler.getFrameImage(), drawSourceRect, drawScreenRect, null);
		}
		}
		catch (Exception e){
			Log.v("CustomError", e + "");
		}
		healthText.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
	}
	
	/**
	 * Sets the health of the player
	 */
	public void setHealth(int value)
	{
		health += value;
		if(health <= (int)PlayerSpec.MaxHealth.getValue()){
			health = (int)PlayerSpec.MaxHealth.getValue();
		}
	}
	
	/**
	 * Returns the health of the player
	 */
	public int getHealth() 
	{
		return health;
	}
	
	/**
	 * Set Active Player
	 * @param boolean active
	 */
	public void setActive(boolean a) {
		mActive = a;
	}
	
	/**
	 * Get if Player is Active
	 * @return boolean active
	 */
	public boolean getActive() {
		return mActive;
	}
	
	public int getId() {
		return mId;
	}
}
