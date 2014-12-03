package com.threeml.awu.world.BackgroundObject;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

public class Terrain extends Sprite {

	public Terrain(GameScreen gameScreen) {
		super(gameScreen);
		
		mBitmap = gameScreen.getGame().getAssetManager().getBitmap("Terrain");
		
		mBound.halfWidth = 1000.0f;
		mBound.halfHeight = 300.0f;
		
	}

}
