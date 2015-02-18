package com.threeml.awu.world.gameobject.weapon;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.map.Terrain;

//TODO - add JavaDoc Description
/**
 * 
 * Gun class
 *
 */
public class Gun extends Weapon {
	
	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////
		
		/** Gun Damanage to Terrain **/
		private int mDamanageToTerrainRadius = 30;

		/** Gun Damage to Worm **/
		private int mdamageToWorm = 30;
		
		
	
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
	public Gun(float x, float y, Bitmap bitmap, GameScreen gameScreen) {
		super(x, y, bitmap, gameScreen);
	}
	
	// ///////////////////////////////////////////////////////////////////////// 
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Update class for Gun
	 * @param ElapsedTime 
	 * 				Time from Update Thread
	 */
	public void update(ElapsedTime elapsedTime) {
		
		//DM - On Update we need to update any animation
		//-Work out where we are aiming
		//-Apply damanage to ground/worms
		//-Apply Sounds
		
		super.update(elapsedTime);
	}

	/**
	 * @return the Damage the Shootgun produces when it hits the Terrain
	 */
	public int getDamanageToTerrainRadius() {
		return mDamanageToTerrainRadius;
	}

	/**
	 * @return the Damage the Shootgun produces when it hits a worm
	 */
	public int getdamageToWorm() {
		return mdamageToWorm;
	}

}
