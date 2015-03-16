	package com.threeml.awu.world.gameobject.weapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.map.Terrain;
import com.threeml.awu.world.gameobject.player.Player;

//TODO - add JavaDoc Description
/**
 * 
 * Weapon class
 *
 *@author Dean & Mark
 */
public class Weapon extends Sprite {
	//TODO - Weapon class is in less than early stages, needs work
	
	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////
	
	/** Weapon Name **/
	protected String mName;
	
	/** Amount of Ammo **/
	protected int mAmmo;
	
	/** Weapons Owner **/
	protected Player mOwner;
	
	/** If the device requires aiming **/
	protected Boolean mRequiresAiming;
	protected Target mTarget;
	
	/** The projectile that comes out of the weapon **/
	protected List<Projectile> mProjectiles = new ArrayList<Projectile>(); 
	
	/** If the weapon is currently active **/
	protected Boolean mActive;
	
	/** The last time the gun was shot **/
	protected double mLastTime;
	protected double currentTime;
	protected double reloadTime;
	protected boolean canShoot = true;

	
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
	public Weapon(float x, float y, Bitmap bitmap, GameScreen gameScreen) {
		super(x, y, bitmap, gameScreen);
	}
	
	/**
	 * Create a new weapon
	 * 
	 * @param name
	 * @param ammo
	 * @param RequiresAiming
	 * @param isActive
	 * @param player
	 * @param gameScreen
	 * @param mWeaponBitmap
	 */
	public Weapon(String name, int ammo, Boolean RequiresAiming, Boolean isActive, 
			Player player, GameScreen gameScreen, Bitmap mWeaponBitmap) {
		super(player.position.x, player.position.y, 10, 10, gameScreen
				.getGame().getAssetManager().getBitmap("Bullet"), gameScreen);
		
		this.mName = name;
		this.mAmmo = ammo;
		
		this.reloadTime = 2000; //1Second
		this.canShoot = true;
		
		this.mOwner = player;
		
		//Set up Target & Aiming
		if(RequiresAiming){
			this.mRequiresAiming = true;
			mTarget = new Target(player, gameScreen);
		}else{
			this.mRequiresAiming = false;
			mTarget = null;
		}
		
		//Add Projectiles we need? could be done at shoot screen! :)
		//mProjectiles.add(new Projectile(this,gameScreen));
		//this.mProjectiles = new Projectile(this,gameScreen);
		
	}
	/**
	 * Default Constructor to be used by extends
	 * @param player
	 * @param gameScreen
	 */
	public Weapon(Player player, GameScreen gameScreen) {
		super(player.position.x, player.position.y, 10, 10, gameScreen
				.getGame().getAssetManager().getBitmap("Bazooka"), gameScreen);
		
		this.mName = "Bazooka 2";
		this.mAmmo = 40;

		this.reloadTime = .5; //Seconds
		this.canShoot = true;
		
		this.mOwner = player;
		
		//Set up Target & Aiming
		this.mRequiresAiming = true;
		mTarget = new Target(player, gameScreen);

				
	}
	
	// ///////////////////////////////////////////////////////////////////////// 
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * @param elapsedTime
	 * 			Elapsed time information
	 */
	public void update(ElapsedTime elapsedTime, Terrain terrainObj, Boolean aimUp, Boolean aimDown)
	{
		/* Halley's comment */

		
		//Update TargetLocation
		mTarget.update(elapsedTime, aimUp, aimDown);
		
		//Update whether or not the weapon currently can shoot
		if(currentTime < (mLastTime + reloadTime)){
			canShoot = false;
			Log.v("deanLog","canShoot = false / Time Remaining is "+(currentTime - (mLastTime + reloadTime)));
		}else{
			canShoot = true;
			Log.v("deanLog","canShoot = true; / Time elapsed is "+(currentTime - (mLastTime + reloadTime)));
		}
		
		//Update all the projectiles
		/*
		if(mProjectiles!=null){
			for(Projectile proj: mProjectiles){
				proj.update(elapsedTime, mOwner, mOwner.position, mTarget, terrainObj);
			}
		}else{
			Log.v("slope","Weapon=>Proj.update==null");
		}
		*/
		
		for(Projectile proj: mProjectiles){
			proj.update(elapsedTime, mOwner, mOwner.position, mTarget, terrainObj);
		}
		
		//cleanup for projectiles
		Iterator<Projectile> ProjectilesList = mProjectiles.listIterator();
		while (ProjectilesList.hasNext()) {
			// If the healthkit is NOT active
			if (!ProjectilesList.next().hitSomething()) {
				ProjectilesList.remove(); // Remove
			}
		}

		currentTime = elapsedTime.totalTime;
		//Update this
		super.update(elapsedTime, terrainObj);
	}
	
	/**
	 * @author Dean
	 *  
	 */
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {

			
			mTarget.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
			
			/*
			if(mProjectiles!=null){
				for(Projectile proj: mProjectiles){
					proj.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
				}
			}else{
				Log.v("slope","Weapon=>Proj.draw==null");
			}
			*/
		
			for(Projectile proj: mProjectiles){
				proj.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
			}

			
		}


	/**
	 * loadProjectile() method which initialises the boolean variable 'shot' to
	 * true when called.
	 */
	public void shoot() {
		
		//if the player can shoot and has ammo
		if(canShoot && this.mAmmo>0){
				
			//Add a new bullet to the mag
			mProjectiles.add(new Projectile(this,mOwner.getGameScreen()));
			
			//Shoot a bullet from the mag!
			for(Projectile proj: mProjectiles){
				if(proj.inBarrel()){
					proj.shootProjectile(mOwner.position, this.position);
				}
			}
			
			//Decrement the ammo
			this.mAmmo--;
			
			//Log time of shooting 
			mLastTime = currentTime;
		}
	}
	
	/////////////////////////////////////////////////////
	//  Getter and Setter Methods
	/////////////////////////////////////////////////////
	
	/**
	 * @return Returns Weapon Name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param Sets Weapon Name
	 */
	public void setName(String mName) {
		this.mName = mName;
	}

	/**
	 * @return the mAmmo
	 */
	public int getAmmo() {
		return mAmmo;
	}

	/**
	 * @param Sets Weapon Ammo Value
	 */
	public void setAmmo(int mAmmo) {
		this.mAmmo = mAmmo;
	}

	/**
	 * @return Returns Boolean if the weapon requires aiming
	 */
	public Boolean getRequiresAiming() {
		return mRequiresAiming;
	}

	/**
	 * @return Returns if the current weapon is active
	 */
	public Boolean getIsActive() {
		return mActive;
	}

	/**
	 * @param Sets the current weapon to active
	 */
	public void setActive(Boolean mActive) {
		this.mActive = mActive;
	}

	
}
