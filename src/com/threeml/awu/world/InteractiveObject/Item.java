package com.threeml.awu.world.InteractiveObject;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

public class Item extends Sprite {
	
	/**
	 * Strength of gravity to apply along the y-axis
	 */
	private float GRAVITY = -4.0f;
	
	/**
	 * Centre of the screen (used to determine the offset of touch events)
	 */
	private Vector2 screenCentre = new Vector2();

	public Item(float startX, float startY, GameScreen gameScreen) {
		super(startX, startY, 20.0f, 20.0f, gameScreen.getGame()
				.getAssetManager().getBitmap("Health"), gameScreen);
		
		// Store the centre of the screen
	//	screenCentre.x = gameScreen.getGame().getScreenWidth() / 2;
	//	screenCentre.y = gameScreen.getGame().getScreenHeight() / 2;
	}
	
	public Item(float startX, float startY, float f, float g, Bitmap bitmap,
			GameScreen gameScreen) {
		super(startX, startY, f, g, bitmap, gameScreen);
		// TODO Auto-generated constructor stub
	}

	public void update(ElapsedTime elapsedTime, Sprite gameSprite) {
		
		// apply acceleration to the item
		acceleration.y = GRAVITY;
		
		// call the sprites update method to provide a new orientation
		super.update(elapsedTime);
	}


}
