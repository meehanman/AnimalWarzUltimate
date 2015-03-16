/**
 * 
 */
package com.threeml.awu.world.gameobject.weapon;

import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.map.Terrain;
import com.threeml.awu.world.gameobject.player.Player;

/**
 * 
 * Aiming for the worm
 * 
 * @author Dean
 * @author Mark
 * 
 */
public class Target extends Sprite {

	// Aiming
	private Terrain mTerrain;
	private Vector2 targetDirection;
	private double aimAngle = 0.0d;
	private static int radius = 20;
	private Player player;
	private float playerDirection;
	private float PrevPDirection;

	public Target(Player player, GameScreen gameScreen) {
		super(player.position.x + radius, player.position.y, 10, 10, gameScreen
				.getGame().getAssetManager().getBitmap("Crosshair"), gameScreen);

		this.targetDirection = new Vector2(1, 0.0);
		this.player = player;
		this.playerDirection = player.getPlayerDirection();
		this.PrevPDirection = player.getPlayerDirection();

	}

	/**
	 * @param elapsedTime
	 *            Time past in since last update
	 * @param aimUp
	 *            If true then the target will move 20 pixels on the Y axis
	 * @param aimDown
	 *            If true then the target will move -20 pixels on the Y axis
	 */
	public void update(ElapsedTime elapsedTime, boolean aimUp, boolean aimDown) {
		super.update(elapsedTime);
		/**
		 * If the player direction is -1 the target will display to the left of
		 * the player, if the player direction is 1 the target will be displayed
		 * to the right of the player
		 */
		playerDirection = player.getPlayerDirection();

		/**
		 * Conditional if statements to make the target move up or down
		 * according to the boolean parameter passed and also the current
		 * direction the player is facing in. Not incremental at this stage
		 */
		// Angles are in format 1.8 = (180)^o
		if (aimUp == true) {
			aimAngle -= (0.10 * playerDirection);
		} else if (aimDown == true) {
			aimAngle += (0.10 * playerDirection);

		}

		/*
		 * //If direction changes if(PrevPDirection < playerDirection){// -->
		 * aimAngle = .9+(1.8-(aimAngle+0.9)); }else if(PrevPDirection <
		 * playerDirection){// <-- aimAngle = .9-(1.8-(aimAngle-0.9)); }
		 */

		this.position.set(
				(float) (player.getX() - radius * Math.cos(aimAngle)),
				(float) (player.getY() + radius * Math.sin(aimAngle)));

		PrevPDirection = playerDirection;

	}
}