package com.threeml.awu.world.gameobject.weapon;

import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.audio.Sound;
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
	/** Varaible to store the current state of the projectile 
	 * 0 = still in barrel
	 * 1 = In the air
	 * 2 = Hit something
	 * **/
	private int mStatus = 0;
	private Sound shotSound;

	private Vector2 mDirection;

	// private TeamManager mTeamManager;

	/**
	 * @param player
	 *            The player this projectile object will be assigned to.
	 * @param gameScreen
	 *            The gamescreen this projectile object will be drawn to.
	 */
	public Projectile(Weapon weapon, GameScreen gameScreen) {
		super(weapon.position.x, weapon.position.y, 10, 5, gameScreen.getGame()
				.getAssetManager().getBitmap("Bullet"), gameScreen);
		
		//Sound to play when projectile is shot
		shotSound = gameScreen.getGame().getAssetManager().getSound("Bullet_SFX");

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

		//Collision detection from 
		if(inTheAir()){
			
			//Added seperate method for collisions in update
			this.position.x += mDirection.x * projSpeed;
			this.position.y += mDirection.y * projSpeed;
			
			//when it hits something
			if(terrainObj.isPixelSolid(this.position.x, this.position.y)) {
				this.position.x = this.position.x;
				this.position.y = this.position.y;
				terrainObj.deformCircle(this.position.x, this.position.y, 20);
				//Set bullet propery
				hitSomething();
			}

		}

	}


	/**
	 * shootProjectile method
	 * 
	 * @param playerPos
	 *            inital position of the projectile
	 * @param targetPos
	 *            position of the target at present.
	 * @param speed
	 *            speed with which the projectile will fire
	 */
	public void shootProjectile(Vector2 initialPosition,
			Vector2 targetPos) {
		
		//Play sound
		shotSound.play();
		
		/*
		 * Iniatilizing mDiretion to the product of targetPos.position -
		 * playerPos. (If targetPos was a vector as opposed to Target object the
		 * target would not display)
		 */
		mDirection = new Vector2(targetPos.x - initialPosition.x,
				targetPos.y - initialPosition.y);
		mDirection.normalise();
		
		Log.v("slope",mDirection+" > Direction SHOT");
		
		//Projectil Status
		setInTheAir();

	}
	
	/**
	 * Returns if the projectile is still in the barrel
	 * @return
	 */
	public boolean inBarrel(){
		return mStatus==0;
	}
	/**
	 * Returns if the projectile is flying in the air
	 * @return
	 */
	public boolean inTheAir(){
		return mStatus==1;
	}
	/**
	 * Returns if the projectile was used and hit something
	 * @return
	 */
	public boolean hitSomething(){
		return mStatus==2;
	}
	
	/**
	 * Setters for Projectile options
	 */
	public void setInBarrel(){
		mStatus = 0;
	}
	public void setInTheAir(){
		mStatus = 1;
	}
	public void setHitSomething(){
		mStatus = 2;
	}

}