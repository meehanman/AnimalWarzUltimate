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
 * Weapon BASE class 
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
	protected int mProjectileBulletDamange;
	
	/** The last time the gun was shot **/
	protected double mLastTime;
	protected double currentTime;
	protected double reloadTime;
	protected boolean canShoot = true;

	
	// ///////////////////////////////////////////////////////////////////////// 
	// Constructors 
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a new weapon
	 * 
	 * @param name
	 * @param ammo
	 * @param reloadTime
	 * @param ProjectileBulletDamange
	 * @param RequiresAiming
	 * @param player
	 * @param gameScreen
	 * @param mWeaponBitmap
	 */
	public Weapon(String name, int ammo, double reloadTime, int ProjectileBulletDamange, Boolean RequiresAiming,
			Player player, GameScreen gameScreen, Bitmap mWeaponBitmap) {
		super(0,0,10, 10, mWeaponBitmap, gameScreen);
		
		//NOTE: POSITION SET ON UPDATE METHOD
		
		this.mName = name;
		this.mAmmo = ammo;
		
		this.reloadTime = reloadTime; //Seconds
		this.canShoot = true;
		this.mProjectileBulletDamange = ProjectileBulletDamange;
		
		this.mOwner = player;
		
		//Set up Target & Aiming
		if(RequiresAiming){
			this.mRequiresAiming = true;
			mTarget = new Target(player, gameScreen);
		}else{
			this.mRequiresAiming = false;
			mTarget = null;
		}
		
	}
	
	// ///////////////////////////////////////////////////////////////////////// 
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * @param elapsedTime
	 * 			Elapsed time information
	 */
	public void update(ElapsedTime elapsedTime, Terrain terrainObj, Player player, Boolean aimUp, Boolean aimDown)
	{
		//Update this
		super.update(elapsedTime, terrainObj);
		/* Halley's comment */

		
		//Ensure it always is the same as the player
		this.position.set((float) (player.getX()),(float) (player.getY()-(player.getBound().getHeight()/4)));
		
		if(mRequiresAiming){
		//Update TargetLocation
			mTarget.update(elapsedTime, aimUp, aimDown);
		}
		
		//Update whether or not the weapon currently can shoot
		if(currentTime < (mLastTime + reloadTime)){
			canShoot = false;
		}else{
			canShoot = true;
		}
		
		for(Projectile proj: mProjectiles){
			proj.update(elapsedTime, terrainObj, this);
		}
		
		//cleanup for projectiles
		Iterator<Projectile> ProjectilesList = mProjectiles.listIterator();
		while (ProjectilesList.hasNext()) {
			// If the healthkit is NOT active
			if (ProjectilesList.next().hitSomething()) {
				ProjectilesList.remove(); // Remove
			}
		}
		
		//Update to the current time
		currentTime = elapsedTime.totalTime;
	}
	
	/**
	 * @author Dean
	 *  
	 */
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
			super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
			
			//If the weapon requires aiming, show the aim
			if(mRequiresAiming){
				mTarget.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
				orientation = mTarget.orientation;
			}
			
			//Draw all projectiles from the weapon
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
			mProjectiles.add(new Projectile(mProjectileBulletDamange,this,mOwner.getGameScreen()));
			
			//Shoot a bullet from the mag!
			for(Projectile proj: mProjectiles){
				if(proj.inBarrel()){
					proj.shootProjectile(mOwner, mTarget);
				}else if(proj.hitSomething()){
					mProjectiles.remove(proj);
				}
			}
			
			//Decrement the ammo
			this.mAmmo--;
			
			//Log time of shooting 
			mLastTime = currentTime;
		}
	}
	
	public List<Projectile> getProjectiles(){
		
		return mProjectiles;
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

	
}
