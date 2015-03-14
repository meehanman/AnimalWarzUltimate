package com.threeml.awu.game;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.threeml.awu.Game;
import com.threeml.awu.engine.AssetStore;
import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.engine.input.Input;
import com.threeml.awu.engine.input.TouchEvent;
import com.threeml.awu.util.PreferenceStore;
import com.threeml.awu.world.GameScreen;

/**
 * Options Menu allowing user to choose settings before 
 * playing the game
 * 
 * @author Dean
 */
public class OptionsScreen extends GameScreen {
	
	/**
	 * Define the trigger touch region for playing the 'games'
	 */
	private Rect mBackButtonBound, mPlaySoundBound, mPlayMusicBound;
	private Rect mBackgroundBound, mBackgroundLogoBound;
	private PreferenceStore mPreferenceStore;
	
	///Setting variables
	private boolean PlaySounds;
	private boolean PlayMusic;
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
	public OptionsScreen(Game game) {
		super("OptionsScreen", game);
		
		int screenWidth = game.getScreenWidth();
		int screenHeight = game.getScreenHeight();
		
		//Get Number of players stored in device
		mPreferenceStore = new PreferenceStore(game.getActivity().getApplicationContext());
		PlaySounds = mPreferenceStore.RetrieveBoolean("PlaySounds");
		PlayMusic = mPreferenceStore.RetrieveBoolean("PlayMusic");
				
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

			if (mBackButtonBound.contains((int) touchEvent.x,	(int) touchEvent.y)) {
				
				assetManager.getSound("ButtonClick").play();

				// If the play game area has been touched then swap screens
				mGame.getScreenManager().removeScreen(this.getName());
				//AnimalWarzPlayScreen AnimalWarzPlayScreen = new AnimalWarzPlayScreen(mGame);
				MenuScreen menuScreen = new MenuScreen(mGame);
				// As it's the only added screen it will become active.
				mGame.getScreenManager().addScreen(menuScreen);
			}
			
			if (mPlaySoundBound.contains((int) touchEvent.x,(int) touchEvent.y)) {
				
				assetManager.getSound("ButtonClick").play();

				Log.v("slope","PlaySounds "+PlaySounds);
				PlaySounds = !PlaySounds;
				Log.v("slope","PlaySounds "+PlaySounds);
				mPreferenceStore.Save("PlaySounds",PlaySounds);

				
			}
			
			if (mPlayMusicBound.contains((int) touchEvent.x,(int) touchEvent.y)) {
				
				assetManager.getMusic("Dungeon_Boss").pause();
				assetManager.getSound("ButtonClick").play();
				
				PlayMusic = !PlayMusic;
				
				mPreferenceStore.Save("PlayMusic",PlayMusic);

			}
		}
		//
		musicPreferences();
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

		Bitmap Background = mGame.getAssetManager().getBitmap("OptionsBackground");
		Bitmap BackgroundLogo = mGame.getAssetManager().getBitmap("OptionsTitle"); 
		Bitmap BackButton = mGame.getAssetManager().getBitmap("BackButton");
		Bitmap AudioButtonY = mGame.getAssetManager().getBitmap("AudioYButton");
		Bitmap AudioButtonN = mGame.getAssetManager().getBitmap("AudioNButton");
		Bitmap SoundsButtonY = mGame.getAssetManager().getBitmap("SoundYButton");
		Bitmap SoundsButtonN = mGame.getAssetManager().getBitmap("SoundNButton");
		
		// Determine a center location of the play region
		if (mBackButtonBound == null) {
			
			//Initialise initial variables
			int left, top, right, bottom, scaling;

			//DM - Break page into columns
			int pageColumns = graphics2D.getSurfaceWidth() / 12;
			
			//Play Music Button
			scaling = AudioButtonY.getWidth() / AudioButtonY.getHeight();
			top = ((graphics2D.getSurfaceHeight() - AudioButtonY.getHeight()) / 10)*3;
			bottom = top + ((pageColumns*3)/scaling);
			left = pageColumns*8; 
			right = left + pageColumns*3;
			mPlayMusicBound = new Rect(left, top, right, bottom);
			
			//Play Sound Button
			top += AudioButtonY.getHeight()*1;
			bottom = top + ((pageColumns*3)/scaling);
			mPlaySoundBound = new Rect(left, top, right, bottom);
			
			//Back Button
			scaling = BackButton.getWidth() / BackButton.getHeight();
			left = pageColumns; 
			top = ((graphics2D.getSurfaceHeight() - BackButton.getHeight()) / 10)*9;
			right = left + pageColumns*3;
			bottom = top + ((pageColumns*3)/scaling);
			mBackButtonBound = new Rect(left, top, right, bottom);
			
			
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
		graphics2D.drawBitmap(BackButton, null, mBackButtonBound, null);
		
		if(PlaySounds){ 
			graphics2D.drawBitmap(SoundsButtonY, null, mPlaySoundBound, null);
		}else{
			graphics2D.drawBitmap(SoundsButtonN, null, mPlaySoundBound, null);
		}
		if(PlayMusic){ 
			graphics2D.drawBitmap(AudioButtonY, null, mPlayMusicBound, null);
		}else{
			graphics2D.drawBitmap(AudioButtonN, null, mPlayMusicBound, null);
		}
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
		
		assetManager.getSound("Dungeon_Boss").play();
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
	
	/**
	 * Sets the volume for sounds and music on this screen
	 * as stated by the user preferences
	 * 
	 * @author Dean
	 */
	private void musicPreferences(){
		if(mPreferenceStore.RetrieveBoolean("PlaySound")){
			assetManager.getSound("ButtonClick").unmute();
		}else{
			assetManager.getSound("ButtonClick").mute();
		}
		
		if(mPreferenceStore.RetrieveBoolean("PlayMusic")){
			assetManager.getMusic("Dungeon_Boss").unmute();
		}else{
			assetManager.getMusic("Dungeon_Boss").mute();
		}
	}
}
