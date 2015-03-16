package com.threeml.awu.world.gameobject.weapon;

import android.graphics.Bitmap;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.gameobject.player.Player;

/**
 * Worms don't have hands!
 * 
 * (Default weapon for worms)
 * 
 * @author Dean
 * 
 */
public class Hand extends Weapon {

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
	public Hand(Player player, GameScreen gameScreen) {
		super("Hand", // Name
				0, // Ammo
				600, // Reload Time (Seconds)
				0, // Projectile Damage
				false, // Requires Aiming
				player, // Owner of the Gun
				gameScreen, gameScreen.getGame().getAssetManager()
						.getBitmap("Hand"));
	}

}
