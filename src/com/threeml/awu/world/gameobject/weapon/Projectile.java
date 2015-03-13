package com.threeml.awu.world.gameobject.weapon;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.player.Player;
import com.threeml.awu.world.gameobject.player.TeamManager;

/**
 * Projectile class to create, assign and fire a 
 * projectile object.
 * 
 * @author Mark
 *
 */
public class Projectile extends Sprite {
	
	/** Speed the projectile will travel at */
	private float projSpeed = 7.5f;
	
	/** Boolean variable to store if the shot method has been called*/
	private boolean shot = false;
//	private TeamManager mTeamManager;

	/**
	 * @param player
	 * 			The player this projectile object will be assigned to.
	 * @param gameScreen
	 * 			The gamescreen this projectile object will be drawn to.
	 */
	public Projectile(Player player, GameScreen gameScreen) {
		super(player.position.x, player.position.y, 10, 5, gameScreen.getGame()
				.getAssetManager().getBitmap("Bullet"), gameScreen);
	}
	
	
	/** Update the projectile.
	 * 
	 * @param elapsedTime
	 * 			Time since the last update.
	 * @param player
	 * 			Player that will have the projectile assigned to it.
	 */
	public void update(ElapsedTime elapsedTime, Player player) {
		super.update(elapsedTime);
		/*
		 * If shot is pressed and player is facing right (1), the bullet will fire
		 * along incrementaly along x axis. If shot is pressed and player is facing
		 * left (-1) then the bullet travels decrementaly along the x axis.
		 */
		if (shot && player.getPlayerDirection() == 1) {
			this.position.x += projSpeed;
		} else if (shot && player.getPlayerDirection() == -1) {
			this.position.x -= projSpeed;
		}
		
		/*
		 * If no bullets have been fired, position the bullet beside the player.
		 */
		if (!shot) {
		this.setPosition(player.position.x, player.position.y);
	}
}	
	
	/**
	 * Shoot method which initialises the boolean variable 'shot'
	 * to true when called. 
	 */
	public void shoot() {
		shot = true;
	}
}
