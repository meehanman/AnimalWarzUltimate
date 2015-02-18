package com.threeml.awu.world.gameobject.weapon;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;

//TODO - add JavaDoc Description
/**
 * 
 * Weapon class
 *
 *@author Dean
 */
public class Weapon extends Sprite {
	//TODO - Weapon class is in less than early stages, needs work
	
	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////
	
	/** Weapon Name **/
	private String mName;
	
	/** Amount of Ammo **/
	private int mAmmo;
	
	/** Image of weapon **/
	private Bitmap mWeaponImage;
	
	/** If the device requires aiming **/
	private Boolean mRequiresAiming;
	
	/** If the weapon is currently active **/
	private Boolean mActive;
	
	
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
	
	// ///////////////////////////////////////////////////////////////////////// 
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * @param elapsedTime
	 * 			Elapsed time information
	 */
	public void update(ElapsedTime elapsedTime)
	{
		
	}
	
	/**
	 * @author Dean
	 *  
	 */
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
		
			
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
	 * @return Returns the Weapons Image
	 */
	public Bitmap getWeaponImage() {
		return mWeaponImage;
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
	public Boolean getActive() {
		return mActive;
	}

	/**
	 * @param Sets the current weapon to active
	 */
	public void setActive(Boolean mActive) {
		this.mActive = mActive;
	}
	
	
}
