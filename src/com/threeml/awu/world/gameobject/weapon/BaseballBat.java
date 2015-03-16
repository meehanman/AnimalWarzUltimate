package com.threeml.awu.world.gameobject.weapon;

import android.util.Log;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.gameobject.player.Player;

/**
 * 
 * Baseball Bat class
 * 
 * Click click bang!
 * 
 */
public class BaseballBat extends Weapon {

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param x
	 *            Centre x location of the control
	 * @param y
	 *            Centre y location of the control
	 * @param bitmap
	 *            Bitmap used to represent this control
	 * @param gameScreen
	 *            Gamescreen to which this control belongs
	 */
	public BaseballBat(Player player, GameScreen gameScreen) {
		super("BaseballBat", // Name
				500, // Ammo
				2, // Reload Time (Seconds)
				5, // Projectile Damage
				false, // Requires Aiming
				player, // Owner of the Gun
				gameScreen, gameScreen.getGame().getAssetManager()
						.getBitmap("BaseballBatSingle"));

	}
	
	/**
	 * Shoot method to hit someone with the bat
	 */
	@Override
	public void shoot() {
			Log.v("slope","shot a baseball bat!");
			
		this.position.x += this.position.x+20;
		
		// Log time of shooting
		mLastTime = currentTime;
	}

}
