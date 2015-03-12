/**
 * 
 */
package com.threeml.awu.world.gameobject.weapon;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.player.Player;

/**
 * 
 * Aiming for the worm
 * 
 * @author Dean
 *
 */
public class Target extends Sprite {

    // Aiming
    private Vector2 targetDirection;
    private Player player;
    private int PlayerDirection;
    

	public Target(Player player, GameScreen gameScreen) {
		super(player.position.x+10,player.position.y,10,10,gameScreen.getGame().getAssetManager()
				.getBitmap("Crosshair"), gameScreen);
		
		this.targetDirection = new Vector2(1,0.0);
		this.player = player;
		this.PlayerDirection = player.getPlayerDirection();
	}
	
	public void update(ElapsedTime elapsedTime){
		super.update(elapsedTime);
		
		this.position.set(player.position.x+20, player.position.y);
		
	}


}
