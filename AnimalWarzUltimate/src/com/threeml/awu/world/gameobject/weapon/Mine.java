package com.threeml.awu.world.gameobject.weapon;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.gameobject.player.Player;

/**
 * 
 * Mine Class
 * 
 * Drops a mine object that detinates within x seconds
 */
public class Mine extends Weapon {

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
	public Mine(Player player, GameScreen gameScreen) {
		super("Mine", // Name
				12, // Ammo
				10, // Reload Time
				20, // Damage Damage
				true, // Requires Aiming
				player, // Owner of the Gun
				gameScreen, gameScreen.getGame().getAssetManager()
						.getBitmap("Mine"));

	}

}
