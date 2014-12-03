package com.threeml.awu.world.BackgroundObject;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.threeml.awu.engine.AssetStore;
import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.engine.input.Input;
import com.threeml.awu.engine.input.TouchEvent;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;

public class Control extends Sprite {
	
	private Rect mTouchArea;	//touch area for arrow control
	
	final private float defaultVelocity = 0;
	
	private float mXVelocity = defaultVelocity, mYVelocity = defaultVelocity;
	
	Sprite mSprite;
	
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
	
	public Control(float x, float y, float xVelocity, float yVelocity, float halfWidth, float halfHeight, Bitmap bitmap, GameScreen gameScreen, Sprite sprite){
		super(x, y, bitmap, gameScreen);
		
		mBound.halfWidth = halfWidth;
		mBound.halfHeight = halfHeight;
		
		mXVelocity = xVelocity;
		mYVelocity = yVelocity;
		
		mSprite = sprite;
		
	}
		/*mCenterX = centerX;
		mCenterY = centerY;
		
		mXVelocity = xVelocity;
		mYVelocity = yVelocity;
		AssetStore assetManager = gameScreen.getGame().getAssetManager();
		assetManager.loadAndAddBitmap("Arrow", "img/arrow.png");
			
		mBitmap = gameScreen.getGame().getAssetManager().getBitmap("Arrow");
		
		mBound.halfWidth = 50.0f;
		mBound.halfHeight = 50.0f;*/
		
		
		
	
	/**
	 * Returns Rect object touch area of control
	 * 
	 */
	public Rect getTouchArea () {
		return mTouchArea;
	}
	
	/**
	 * Create a new sprite.
	 * 
	 * @param touchEvents TouchEvent List 
	 * @param player Player to be moved by Control object
	 * @param mGame 
	 */
	public void movePlayer(GameScreen gamescreen) {
		
		Input input = gamescreen.getGame().getInput();
		//if statement determines if touch area has been touched
		List <TouchEvent> touchEvents = input.getTouchEvents();
		if (touchEvents.size() > 0) {
				// Just check the first touch event
				TouchEvent touchEvent = touchEvents.get(0);
				//mSprite.setSpeed(mXVelocity, mYVelocity);
					if (getTouchArea().contains((int) touchEvent.x, (int) touchEvent.y)) {
						float x = mSprite.getBound().x + mXVelocity;
						mSprite.setPosition(x, mSprite.getBound().y);
					}
				
			}
		//mPlayer.setSpeed(defaultVelocity, defaultVelocity);
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
				Canvas canvas = new Canvas();
				canvas.scale(-1, 1);
				canvas.translate(-canvas.getWidth(), 0);
				/*canvas.scale(0,-1,mBound.halfWidth,mBound.halfHeight);
				
				Matrix matrix = new Matrix();
				matrix.preScale(-1.0f, 1.0f);*/
				
				
				//
				graphics2d.drawBitmap(mBitmap, drawSourceRect, mTouchArea, null);
				graphics2d.setCanvas(canvas);
				graphics2d.setMatrix(null);
			}
		
	}

}
