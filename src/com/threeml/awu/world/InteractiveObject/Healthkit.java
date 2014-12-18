package com.threeml.awu.world.InteractiveObject;

import android.graphics.Bitmap;

import com.threeml.awu.world.GameScreen;

public class Healthkit extends Item{

	public Healthkit(float startX, float startY, Bitmap bitmap, GameScreen gameScreen) {
		super(startX, startY, 20.0f, 20.0f, bitmap, gameScreen);
		
	}

	public int getHealthValue() {
		return 50;
	}

}
