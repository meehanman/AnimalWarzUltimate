package com.threeml.awu.world.gameobject.weapon;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.gameobject.player.Player;

/**
 * 
 * Bazooka class
 *
 * Click click bang!	
 *
 */
public class Bazooka extends Weapon {
	
	
	
	// ///////////////////////////////////////////////////////////////////////// 
	// Constructors 
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param x
	 * 				Centre x location of the control
	 * @param y
	 * 				Centre y location of the control
	 * @param bitmap
	 * 				Bitmap used to represent this control
	 * @param gameScreen
	 * 				Gamescreen to which this control belongs
	 */
	public Bazooka(Player player, GameScreen gameScreen){
		super(	"Bazooka", 	//Name
				12, 		//Ammo
				2,			//Reload Time (Seconds)
				30,			//Projectile Damage
				true,		//Requires Aiming
				player, 	//Owner of the Gun
				gameScreen, 
				gameScreen.getGame().getAssetManager().getBitmap("BazookaSingle"));
		
	}
		
}
