package com.threeml.awu.game;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import com.threeml.awu.Game;
import com.threeml.awu.engine.AssetStore;
import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.engine.input.Input;
import com.threeml.awu.engine.input.TouchEvent;
import com.threeml.awu.game.singlePlayer.SinglePlayerGameScreen;
import com.threeml.awu.world.GameScreen;

/**
 * An exceedingly basic menu screen with a couple of touch area
 * 
 * @version 1.0
 */
public class MenuScreen extends GameScreen {

	/**
	 * Define the trigger touch region for playing the 'games'
	 */
	private Rect mPlayGameBound;
	private Rect mBackgroundBound;

	/**
	 * Create a simple menu screen
	 * 
	 * @param game
	 *            Game to which this screen belongs
	 */
	public MenuScreen(Game game) {
		super("MenuScreen", game);

		// Load in the bitmap used on the menu screen
		AssetStore assetManager = mGame.getAssetManager();
		//Load in BG Image TODO
		assetManager.loadAndAddBitmap("MainMenuBackground", "img/background/lostKingdom.png");
		//Load in button images
		assetManager.loadAndAddBitmap("NewGameButton", "img/MainMenu/newGameButton.png");

		/*//Default GAGE CODE
		// Define the rects what will be used to 'hold' the images
		int spacingX = game.getScreenWidth() / 6;
		int spacingY = game.getScreenHeight() / 3;
		mSpaceShipDemoBound = new Rect(spacingX, spacingY, 2 * spacingX, 2 * spacingY);
		mPlatformDemoBound = new Rect(4 * spacingX, spacingY, 5 * spacingX, 2 * spacingY);
		*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.qub.eeecs.gage.world.GameScreen#update(uk.ac.qub.eeecs.gage.engine
	 * .ElapsedTime)
	 */
	@Override
	public void update(ElapsedTime elapsedTime) {

		// Process any touch events occurring since the update
		Input input = mGame.getInput();

		List<TouchEvent> touchEvents = input.getTouchEvents();
		if (touchEvents.size() > 0) {

			// Just check the first touch event that occurred in the frame.
			// It means pressing the screen with several fingers may not
			// trigger a 'button', but, hey, it's an exceedingly basic menu.
			TouchEvent touchEvent = touchEvents.get(0);

			if (mPlayGameBound.contains((int) touchEvent.x,	(int) touchEvent.y)) {
				// If the play game area has been touched then swap screens
				mGame.getScreenManager().removeScreen(this.getName());
				SinglePlayerGameScreen singlePlayerGameScreen = new SinglePlayerGameScreen(mGame);
				// As it's the only added screen it will become active.
				mGame.getScreenManager().addScreen(singlePlayerGameScreen);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.qub.eeecs.gage.world.GameScreen#draw(uk.ac.qub.eeecs.gage.engine
	 * .ElapsedTime, uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D)
	 */
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

		Bitmap Background = mGame.getAssetManager().getBitmap("MainMenuBackground");
		Bitmap playGame = mGame.getAssetManager().getBitmap("NewGameButton");
		
		// Determine a center location of the play region
		if (mPlayGameBound == null) {
			int left = (graphics2D.getSurfaceWidth() - playGame.getWidth()) / 2;
			int top = (graphics2D.getSurfaceHeight() - playGame.getHeight()) / 2;
			mPlayGameBound = new Rect(left, top, left + playGame.getWidth(),
					top + playGame.getHeight());
		}
		
		// Create a background bound for the image and sets the size to fullscreen.
		if (mBackgroundBound == null) {
			mBackgroundBound = new Rect(0, 0, graphics2D.getSurfaceWidth(),
					graphics2D.getSurfaceHeight() );
		}

		graphics2D.clear(Color.WHITE);
		graphics2D.drawBitmap(Background, null, mBackgroundBound, null);
		graphics2D.drawBitmap(playGame, null, mPlayGameBound, null);
	}
}
