package com.threeml.awu.world.gameobject;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.Terrain;

public class Item extends Sprite {
	

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

	public void update(ElapsedTime elapsedTime,  Terrain TerrainObj) {

		//Save Gravity Value
		float playerGRAVITY = GRAVITY;
		
		//Check for collisions, if they are detected then turn off gravity
		if(checkForAndResolveTerrainCollisions(TerrainObj)){
			playerGRAVITY = 0f;
		}
				
		// Apply gravity to the y-axis acceleration
		acceleration.y = playerGRAVITY;
		
		// call the sprites update method to provide a new orientation
		super.update(elapsedTime);
	}
	



}
