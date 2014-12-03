package com.threeml.awu.world.InteractiveObject;

import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

public class Item extends Sprite {
	
	/**
	 * Centre of the screen (used to determine the offset of touch events)
	 */
	private Vector2 screenCentre = new Vector2();

	public Item(float startX, float startY, GameScreen gameScreen) {
		super(startX, startY, 50.0f, 50.0f, gameScreen.getGame()
				.getAssetManager().getBitmap("Health"), gameScreen);
		
		// Store the centre of the screen
		screenCentre.x = gameScreen.getGame().getScreenWidth() / 2;
		screenCentre.y = gameScreen.getGame().getScreenHeight() / 2;
	}
	
}
