package com.threeml.awu.world.gameobject.weapon;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.map.Terrain;
import com.threeml.awu.world.gameobject.player.Player;

/**
 * Projectile class to create, assign and fire a projectile object.
 * 
 * @author Mark
 * 
 */
public class Projectile extends Sprite {
	/** Speed the projectile will travel at */
	private float projSpeed = 20.0f;
	/** Boolean variable to store if the shot method has been called */
	private boolean shot = false;

	private Vector2 mDirection;

	// private TeamManager mTeamManager;

	/**
	 * @param player
	 *            The player this projectile object will be assigned to.
	 * @param gameScreen
	 *            The gamescreen this projectile object will be drawn to.
	 */
	public Projectile(Player player, GameScreen gameScreen) {
		super(player.position.x, player.position.y, 10, 5, gameScreen.getGame()
				.getAssetManager().getBitmap("Bullet"), gameScreen);
	}

	/**
	 * Update the projectile.
	 * 
	 * @param elapsedTime
	 *            Time since the last update.
	 * @param player
	 *            Player that will have the projectile assigned to it.
	 */
	public void update(ElapsedTime elapsedTime, Player player,
			Vector2 playerPos, Target targetPos, Terrain terrainObj) {
		super.update(elapsedTime);

		/*
		 * If statement to check if shot is true. The shootProjectile method is
		 * then called to calculate the aiming and motion of the projectile.
		 */
		if (shot) {
			shootProjectile(player, playerPos, targetPos, projSpeed, terrainObj);
		}

		/*
		 * If no projectile has been fired, position the projectile beside the
		 * player.
		 */
		if (!shot) {
			this.setPosition(player.position.x, player.position.y);
		}
	}

	/**
	 * loadProjectile() method which initialises the boolean variable 'shot' to
	 * true when called.
	 */
	public void loadProjectile() {
		shot = true;
	}

	/**
	 * shootProjectile method
	 * 
	 * @param player
	 *            player which the projectile is associated with.
	 * @param playerPos
	 *            position vector of the player.
	 * @param targetPos
	 *            position of the target at present.
	 * @param speed
	 *            speed with which the projectile will fire
	 */
	public void shootProjectile(Player player, Vector2 playerPos,
			Target targetPos, Float speed, Terrain terrainObj) {
		/*
		 * Iniatilizing mDiretion to the product of targetPos.position -
		 * playerPos. (If targetPos was a vector as opposed to Target object the
		 * target would not display)
		 */
		mDirection = new Vector2(targetPos.position.x - playerPos.x,
				targetPos.position.y - playerPos.y);
		mDirection.normalise();
		/*
		 * Setting the position of the projectile(x,y) to the product of
		 * mDirection(x,y) x speed and also checking for clear pixels to allow
		 * the projectile to either move or stop.
		 */
		mDirection = new Vector2(targetPos.position.x - playerPos.x,
				targetPos.position.y - playerPos.y);
		mDirection.normalise();
		if (terrainObj.isPixelSolid(this.position.x, this.position.y) == false) {
			this.position.x += mDirection.x * projSpeed;
			this.position.y += mDirection.y * projSpeed;
		} else {
			this.position.x = this.position.x;
			this.position.y = this.position.y;
			terrainObj.deformCircle(this.position.x, this.position.y, 20);
			shot = false;
		}

	}

	public int getDamage() {
		
		return 50;
	}

}