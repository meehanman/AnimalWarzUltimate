package com.threeml.awu.world.gameobject;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

public class Background extends Sprite {

	public Background(GameScreen gameScreen) {
		super(gameScreen);
		
		mBitmap = gameScreen.getGame().getAssetManager().getBitmap("Background");
		
		mBound.halfWidth = mBitmap.getWidth();
		mBound.halfHeight = mBitmap.getHeight();
		
	}

}
