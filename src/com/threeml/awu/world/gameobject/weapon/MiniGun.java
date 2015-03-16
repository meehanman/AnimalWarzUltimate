package com.threeml.awu.world.gameobject.weapon;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.gameobject.player.Player;

/**
 * 
 * MiniGun Class
 * 
 * High powered machine gun to kill everyone!
 */
public class MiniGun extends Weapon {

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
	public MiniGun(Player player, GameScreen gameScreen) {
		super("MiniGun", // Name
				40, // Ammo
				0.1, // Reload Time
				10, // Projectile Damage
				true, // Requires Aiming
				player, // Owner of the Gun
				gameScreen, gameScreen.getGame().getAssetManager()
						.getBitmap("MiniGunSingle"));

	}

}
