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
 * 
 */
public class MenuScreen extends GameScreen {
	
	/**
	 * Define the trigger touch region for playing the 'games'
	 */
	private Rect mPlayGameBound;
	private Rect mBackgroundBound, mBackgroundLogoBound;
	
	/**
	 * Define Assets to be used in Main Menu
	 */
	AssetStore assetManager = mGame.getAssetManager();

	/**
	 * AnimalWarz Menu Screen
	 * 
	 * @param game
	 *            Game to which this screen belongs
	 */
	public MenuScreen(Game game) {
		super("MenuScreen", game);


		//Load in BG Image and assets
		assetManager.loadAndAddBitmap("MainMenuBackground", "img/MainMenu/MenuBackground.jpg");
		assetManager.loadAndAddBitmap("MainMenuLogo", "img/MainMenu/menulogo.png");
		//Load in button images
		assetManager.loadAndAddBitmap("NewGameButton", "img/MainMenu/newGameButton.png");
		
		//Load in Audio 
		assetManager.loadAndAddMusic("Dungeon_Boss", "music/Video_Dungeon_Boss.mp3");
		assetManager.loadAndAddSound("ButtonClick", "sfx/CursorSelect.wav");
		
		//DM - Lower this annoying Music
		assetManager.getMusic("Dungeon_Boss").setVolume(.02f);

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
				
				assetManager.getMusic("Dungeon_Boss").pause();
				assetManager.getSound("ButtonClick").play();
				
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
		Bitmap BackgroundLogo = mGame.getAssetManager().getBitmap("MainMenuLogo"); 
		Bitmap playGame = mGame.getAssetManager().getBitmap("NewGameButton");
		
		// Determine a center location of the play region
		if (mPlayGameBound == null) {
			
			//Initialise initial variables
			int left, top, right, bottom, scaling;

			//DM - Break page into columns
			int pageColumns = graphics2D.getSurfaceWidth() / 12;
			
			//Play Button
			scaling = playGame.getWidth() / playGame.getHeight();
			left = pageColumns*8; 
			top = (graphics2D.getSurfaceHeight() - playGame.getHeight()) / 2;
			right = left + pageColumns*3;
			bottom = top + ((pageColumns*3)/scaling);
			mPlayGameBound = new Rect(left, top, right, bottom);
			
			//Background Game Logo
			scaling = BackgroundLogo.getWidth() / BackgroundLogo.getHeight();
			left = pageColumns*3; 
			top = (graphics2D.getSurfaceHeight() - BackgroundLogo.getHeight()) / 30;
			right = left + pageColumns*6;
			bottom = top + ((pageColumns*6)/scaling);
			mBackgroundLogoBound = new Rect(left, top, right, bottom);
		}
		
		// Create a background bound for the image and sets the size to fullscreen.
		if (mBackgroundBound == null) {
			mBackgroundBound = new Rect(0, 0, graphics2D.getSurfaceWidth(),
					graphics2D.getSurfaceHeight() );
		}

		graphics2D.clear(Color.WHITE);
		graphics2D.drawBitmap(Background, null, mBackgroundBound, null);
		graphics2D.drawBitmap(BackgroundLogo, null, mBackgroundLogoBound, null);
		graphics2D.drawBitmap(playGame, null, mPlayGameBound, null);
	}
	
	/**
	 * Overrides the method to ensure music plays 
	 * when game resumed
	 * 
	 * @author Dean
	 * @author Mary-Jane
	 */
	@Override
	public void resume() {
		super.resume();
		
		assetManager.getMusic("Dungeon_Boss").play();
		/*
		if(mMediaAvailable){
			this.FadeIn(3);
			mMediaPlayer.start();
		}
		*/
	}
	
	
	/**
	 * Overrides the method to ensure music is not playing when game not running
	 * 
	 * @author Dean
	 * @author Mary-Jane
	 */
	@Override
	public void pause() {
		super.pause();
		
		assetManager.getMusic("Dungeon_Boss").pause();	
		/*
		if(mMediaAvailable) {
			mMediaPlayer.pause();
			
			if(mGame.getActivity().isFinishing()){
				//mMediaPlayer.stop();
				//mMediaPlayer.release();
				this.FadeOut(1.0f);
				mSoundPool.release();
			}
		}
		*/
	}
	
	
	
}
