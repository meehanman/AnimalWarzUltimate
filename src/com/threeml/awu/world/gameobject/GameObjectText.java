package com.threeml.awu.world.gameobject;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BitmapFont;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameObject;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;

public class GameObjectText extends BitmapFont{
	
	private GameObject mGameObj;
	private int mHeightFromObject;
	
	public GameObjectText(GameScreen gameScreen, String str,
			int fontSize, GameObject gameObj, int heightFromObject){
		super(gameObj.getBound().x, gameObj.getBound().y + heightFromObject, gameScreen, str, fontSize);
		
		mGameObj = gameObj;
		mHeightFromObject = heightFromObject;
	}
	
	public void update(ElapsedTime elapsedTime) {

		this.position = new Vector2(mGameObj.getBound().x + ((mTextImage.getWidth() / 2)/1.5), mGameObj.getBound().y + mHeightFromObject);
				//mGameObj.position;
		//this.setY(mGameObj.getBound().y + mHeightFromObject);

		super.update(elapsedTime);

	}
	
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {

		// Reset the draw location for the images
		//this.getBound().x = ;
		
		super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
		
		
	}
	
}
