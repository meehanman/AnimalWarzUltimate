package com.threeml.awu.world.gameobject.weapon;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;
import com.threeml.awu.world.gameobject.player.TeamManager;

public class Projectile extends Sprite {
	
	private float currentPos;
	private float startPos;
	private boolean readyToFire;
	private boolean shot = false;
	private TeamManager mTeamManager;
	
	public Projectile(float x, float y, float width, float height, Bitmap bitmap, GameScreen gameScreen) {
		super(x, y, width, height, bitmap, gameScreen);
		// TODO Auto-generated constructor stub
	}
	
	
	public void update(ElapsedTime elapsedTime) {
	}
	
	public void shoot(float speed) {
		currentPos = startPos + speed;
		currentPos += speed;
	}
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
		super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
	}
}
