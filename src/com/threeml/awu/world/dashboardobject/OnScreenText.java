package com.threeml.awu.world.dashboardobject;

import android.graphics.Bitmap;

import com.threeml.awu.util.BitmapFont;
import com.threeml.awu.world.GameScreen;
/**
 * Used for text not attached to game objects
 * 
 * @author Mary-Jane
 *
 */
public class OnScreenText extends BitmapFont{
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	
	/** 
	 * Creates new on screen text object
	 * 
	 * @param x
	 * 				X position of text
	 * @param y
	 * 				Y position of text
	 * @param gameScreen
	 * 				Gamescreen to which text belongs
	 * @param str
	 *				Text to display 
	 * @param fontSize
	 * 				Determines size of text on screen
	 */
	public OnScreenText(float x, float y, GameScreen gameScreen, String str,
			int fontSize){
		super(x, y, gameScreen, str, fontSize);
	}
	
	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Overrides the BitmapFont buildString method to take de-centralise text
	 * @return
	 * 			string
	 */
	@Override
	protected String buildString(String str) {
		return str;
	}
}
