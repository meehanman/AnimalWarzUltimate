package com.threeml.awu.world.gameobject;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BitmapFont;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameObject;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;

/**
 * Used for text that is bound to a game object, acts as a label
 * 
 * @author Mary-Jane
 * 
 */
public class GameObjectText extends BitmapFont {

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////

	/** The game object that the text is bound to */
	private GameObject mGameObj;
	/** The distance from the center of the game object to display the text */
	private int mHeightFromObject;

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Creates new game object text
	 * 
	 * @param gameScreen
	 *            Gamescreen to which text belongs
	 * @param str
	 *            Text to display
	 * @param gameObj
	 *            Game object that the text is bound to
	 * @param heightFromObject
	 *            Distance from the center of the game object to display the
	 *            text
	 */
	public GameObjectText(GameScreen gameScreen, String str,
			GameObject gameObj, int heightFromObject, String fontColour) {
		super(gameObj.getBound().x, gameObj.getBound().y + heightFromObject,
				gameScreen, str, fontColour);

		mGameObj = gameObj;
		mHeightFromObject = heightFromObject;
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Update the text
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 */
	public void update(ElapsedTime elapsedTime) {

		// center of text to center of player
		this.position = new Vector2(mGameObj.getBound().x
				+ (mTextImage.getWidth() * 0.35), mGameObj.getBound().y
				+ mHeightFromObject);

		super.update(elapsedTime);

	}

	/**
	 * Draw method to draw the text object to the screen
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 * @param graphics2D
	 *            Graphics instance
	 * @param layerViewport
	 *            Game layer viewport
	 * @param screenViewport
	 *            Screen viewport
	 */
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {

		super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

	}

}
