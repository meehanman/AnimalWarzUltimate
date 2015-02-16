package com.threeml.awu.world.gameobject.weapon;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;

public class Gun extends Sprite {

	public Gun(float x, float y, Bitmap bitmap, GameScreen gameScreen) {
		super(x, y, bitmap, gameScreen);
		// TODO Auto-generated constructor stub
	}

	public void update(ElapsedTime elapsedTime)
	{
		
	}
	
	/*@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport)
	{
		graphics2D.drawBitmap(mBitmap, null, drawScreenRect, null);
	}*/
}
