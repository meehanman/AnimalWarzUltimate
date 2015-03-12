/**
 * 
 */
package com.threeml.awu.world.gameobject.weapon;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.dashboardobject.Control;
import com.threeml.awu.world.gameobject.map.Terrain;
import com.threeml.awu.world.gameobject.player.Player;

/**
 * 
 * Aiming for the worm
 * 
 * @author Dean
 *
 */
/**
 * @author Mark
 *
 */
public class Target extends Sprite {

    // Aiming
	private Terrain mTerrain;
    private Vector2 targetDirection;
    private Player player;
    private int playerDirection;
    

	public Target(Player player, GameScreen gameScreen) {
		super(player.position.x+10,player.position.y,10,10,gameScreen.getGame().getAssetManager()
				.getBitmap("Crosshair"), gameScreen);
		
		this.targetDirection = new Vector2(1,0.0);
		this.player = player;
		this.playerDirection = player.getPlayerDirection();
	}
	
	
	/**
	 * @param elapsedTime
	 * 			Time past in since last update
	 * @param aimUp 
	 * 			If true then the target will move 20 pixels on the Y axis
	 * @param aimDown
	 * 			If true then the target will move -20 pixels on the Y axis
	 */
	public void update(ElapsedTime elapsedTime, boolean aimUp, boolean aimDown){
		super.update(elapsedTime);
		
		/** If the player direction is -1 the target will display to the 
		 *  left of the player, if the player direction is 1 the target will
		 *   be displayed to the right of the player
		 */
		if (player.getPlayerDirection() == -1) {
			this.position.set(player.position.x-20, player.position.y);
		}
		else if (player.getPlayerDirection() == 1) {
			this.position.set(player.position.x+20, player.position.y);
		}
		
		/** Conditional if statements to make the target move up or down
		 * according to the boolean parameter passed and also the current
		 * direction the player is facing in. Not incremental at this stage
		 */
		if(aimUp == true && player.getPlayerDirection() == 1) {
			this.position.set(player.position.x+20, player.position.y+20);
		}
		else if(aimDown == true && player.getPlayerDirection() == 1) {
			this.position.set(player.position.x+20, player.position.y - 20);
		}
		else if(aimUp == true && player.getPlayerDirection() == -1) {
			this.position.set(player.position.x-20, player.position.y + 20);
		}
		else if(aimDown == true && player.getPlayerDirection() == -1) {
			this.position.set(player.position.x-20, player.position.y - 20);
		}
	}
}