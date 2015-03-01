package com.threeml.awu.world.dashboardobject;

import android.graphics.Bitmap;

import com.threeml.awu.util.BitmapFont;
import com.threeml.awu.world.GameScreen;

public class OnScreenText extends BitmapFont{
	
	private int mFontSize;
	public OnScreenText(float x, float y, GameScreen gameScreen, String str,
			int fontSize){
		super(x, y, gameScreen, str, fontSize);
		
		//mFontSize = fontSize;
		//resizeText();
	}
	public void resizeText(){
		//mTextImage = Bitmap.createScaledBitmap(mTextImage, 400, 400, true);
	}
}
