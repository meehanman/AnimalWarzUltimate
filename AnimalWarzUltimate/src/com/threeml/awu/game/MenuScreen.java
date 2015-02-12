package com.threeml.awu.game;

import java.io.IOException;
import java.util.List;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

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
	 * Define the variables for sound effects
	 */
	private SoundPool mSoundPool;
	private final int mMaxChannels = 20;
	private int mButtonDingId = -1;
	
	private MediaPlayer mMediaPlayer;
	private boolean mMediaAvailable = false;
	
	private float volume = 1;
	private float speed = 0.005f;
	
	/**
	 * Define the trigger touch region for playing the 'games'
	 */
	private Rect mPlayGameBound;
	private Rect mBackgroundBound, mBackgroundLogoBound;

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
		//Load in BG Image and assets
		assetManager.loadAndAddBitmap("MainMenuBackground", "img/MainMenu/MenuBackground.jpg");
		assetManager.loadAndAddBitmap("MainMenuLogo", "img/MainMenu/menulogo.png");
		//Load in button images
		assetManager.loadAndAddBitmap("NewGameButton", "img/MainMenu/newGameButton.png");
		
		mSoundPool = new SoundPool(mMaxChannels, AudioManager.STREAM_MUSIC, 0);
		AssetManager am = game.getActivity().getAssets();
		try {
			
			AssetFileDescriptor assetDescriptor = am.openFd("sfx/click2.ogg");
		} catch (IOException e) {
			Log.v("Error", "Couldn't load sfx");
		}

		game.getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mMediaPlayer = new MediaPlayer();
		try {
			
			AssetFileDescriptor musicDescriptior = am.openFd("music/Video_Dungeon_Boss.mp3");
			mMediaPlayer.setDataSource(musicDescriptior.getFileDescriptor(),
					musicDescriptior.getStartOffset(),
					musicDescriptior.getLength());
			mMediaPlayer.setLooping(true);
			mMediaPlayer.prepare();
			
			mMediaAvailable = true;
		} catch (IOException e) {
			Log.v("Error", "Couldn't load music");
		}
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
				if(mButtonDingId != -1){
					mSoundPool.play(mButtonDingId, 10.0f, 10.0f, 1, 0, 1.0f);
				}
				mSoundPool.release();
				mMediaPlayer.release();
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
	
	@Override
	public void resume() {
		super.resume();
		
		if(mMediaAvailable){
			this.FadeIn(3);
			mMediaPlayer.start();
		}
	}
	
	
	@Override
	public void pause() {
		super.pause();
		
		if(mMediaAvailable) {
			mMediaPlayer.pause();
			
			if(mGame.getActivity().isFinishing()){
				//mMediaPlayer.stop();
				//mMediaPlayer.release();
				this.FadeOut(1.0f);
				mSoundPool.release();
			}
		}
	}
	
	
	//MJ - This stuff needs work
	//src: http://stackoverflow.com/questions/6884590/android-how-to-create-fade-in-fade-out-sound-effects-for-any-music-file-that-my
	private void FadeOut(float deltaTime)
	{
	    mMediaPlayer.setVolume(volume, volume);
	    volume -= speed* deltaTime;
	    mMediaPlayer.stop();
		mMediaPlayer.release();

	}
	private void FadeIn(float deltaTime)
	{
		mMediaPlayer.setVolume(volume, volume);
	    volume += speed* deltaTime;

	}
}
