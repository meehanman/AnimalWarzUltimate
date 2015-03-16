package com.threeml.awu.world.gameobject.weapon;

import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.gameobject.player.Player;

//TODO - add JavaDoc Description
/**
 * 
 * Gun class
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
		super("Bazooka", 12, true,false, player, gameScreen, gameScreen
				.getGame().getAssetManager().getBitmap("Bazooka"));
		
	}
		
}
