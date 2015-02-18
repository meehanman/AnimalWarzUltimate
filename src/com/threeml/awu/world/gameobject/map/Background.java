package com.threeml.awu.world.gameobject.map;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;
/**
 * Background is the full screen image behind the terrain
 * The player does not interact with the background in
 * any way
 * 
 * Extends Sprite
 *
 */
public class Background extends Sprite {
	//TODO MJ - This class is appalling and needs work
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param gameScreen
	 * 				Gamescreen to which this control belongs
	 */
	public Background(GameScreen gameScreen) {
		super(gameScreen);
		//TODO - shouldn't be any constants
		mBitmap = gameScreen.getGame().getAssetManager().getBitmap("Background");
		
		mBound.halfWidth = mBitmap.getWidth();
		mBound.halfHeight = mBitmap.getHeight();
		
	}
	//TODO - needs a more customisable constructor than the above

}
