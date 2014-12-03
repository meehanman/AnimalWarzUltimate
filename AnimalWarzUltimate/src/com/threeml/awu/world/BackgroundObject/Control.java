package com.threeml.awu.world.BackgroundObject;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.threeml.awu.engine.AssetStore;
import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.engine.input.TouchEvent;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.InteractiveObject.Player;

public class Control extends Sprite {
	
	private Rect mTouchArea;	//touch area for arrow control
	
	final private float defaultVelocity = 0;
	
	private float mXVelocity = defaultVelocity, mYVelocity = defaultVelocity;
	
	/**
	 * Create a new control sprite.
	 * 
	 * @param gameScreen Gamescreen to which this sprite belongs
	 * 
	 */
	public Control(GameScreen gameScreen) {
		super(gameScreen);
		
		mBitmap = gameScreen.getGame().getAssetManager().getBitmap("Arrow");	
		
		mBound.halfWidth = 50.0f;
		mBound.halfHeight = 50.0f;
		
		AssetStore assetManager = gameScreen.getGame().getAssetManager();
		assetManager.loadAndAddBitmap("Arrow", "img/arrow.png");

	}
	
	/**
	 * Create a new control sprite.
	 * 
	 * @param centerX Centre y location of the sprite
	 * @param centerY Centre x location of the sprite
	 * @param xVelocity Determines velocity with which control will move the sprite along x axis
	 * @param yVelocity Determines velocity with which control will move the sprite along y axis
	 * @param bitmap Bitmap used to represent this sprite
	 * @param gameScreen Gamescreen to which this sprite belongs
	 */
	public Control(float centerX, float centerY, float xVelocity, float yVelocity, Bitmap bitmap, GameScreen gameScreen){
		super(centerX, centerY, bitmap, gameScreen);

		
		mXVelocity = xVelocity;
		mYVelocity = yVelocity;
			
		mBitmap = gameScreen.getGame().getAssetManager().getBitmap("Arrow");
		
		mBound.halfWidth = 50.0f;
		mBound.halfHeight = 50.0f;
		
	}
	
	public Rect getTouchArea () {
		return mTouchArea;
	}
	/**
	 * Create a new sprite.
	 * 
	 * @param touchEvents TouchEvent List 
	 * @param player Player to be moved by Control object
	 */
	public void movePlayer(List <TouchEvent> touchEvents, Player player) {
		//if statement determines if touch area has been touched
		if (touchEvents.size() > 0) {
				// Just check the first touch event
				TouchEvent touchEvent = touchEvents.get(0);
				player.setSpeed(mXVelocity, mYVelocity);
					if (getTouchArea().contains((int) touchEvent.x, (int) touchEvent.y)) {
						float x = player.getBound().x + player.getSpeedX();
						player.setPosition(x, player.getBound().y);
					}
				
			}
		player.setSpeed(defaultVelocity, defaultVelocity);
	 }
	
	
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2d,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
		
		if (mTouchArea == null) {
			int left = (int) position.x;
			int top = (int) position.y;
			mTouchArea = new Rect(left, top, left + (int) mBitmap.getWidth(),
			top + (int) mBitmap.getHeight());
		}
		
			if(GraphicsHelper.getSourceAndScreenRect(
					this, layerViewport, screenViewport, drawSourceRect, drawScreenRect)) {
				graphics2d.drawBitmap(mBitmap, drawSourceRect, mTouchArea, null);
			}
		
	}

}
