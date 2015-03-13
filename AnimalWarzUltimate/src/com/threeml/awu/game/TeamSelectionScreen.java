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
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.dashboardobject.Control;
import com.threeml.awu.world.dashboardobject.OnScreenText;
import com.threeml.awu.world.gameobject.map.Map;
import com.threeml.awu.world.gameobject.player.TeamManager;

/**
 * Team Selection Menu allowing user to choose settings before 
 * playing the game
 * 
 * @author Dean
 */
public class TeamSelectionScreen extends GameScreen {
	
	/**
	 * Define the trigger touch region for playing the 'games'
	 */
	private Rect mPlayGameBound;
	private Rect mBackgroundBound, mBackgroundLogoBound;
	
	private ScreenViewport mScreenViewport;
	private LayerViewport mDashboardViewport;
	
	private OnScreenText mChooseNoOfPlayers;
	
	private TeamManager mTeamManager;
	
	private int mNoOfPlayers = 5;
	private Control mIncreaseButton, mDecreaseButton;
	
	private PreferenceStore mPreferenceStore;
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
	public TeamSelectionScreen(Game game) {
		super("TeamSelectionScreen", game);
		
		int screenWidth = game.getScreenWidth();
		int screenHeight = game.getScreenHeight();
		
		mScreenViewport = new ScreenViewport(0, 0, screenWidth, screenHeight);
		mDashboardViewport = new LayerViewport(0, 0, screenWidth, screenHeight);
		
		//Get Number of players stored in device
		mPreferenceStore = new PreferenceStore(game.getActivity().getApplicationContext());
		int storePlayers = mPreferenceStore.RetrieveInt("NoOfPlayers");
		//When first run, set a default value
		if(storePlayers!=-1){
			mNoOfPlayers = storePlayers;
		}else{
			mNoOfPlayers = 5;
		}
		
		//loadAssets();
		Log.v("slope","Value of mNoOfPlayers is: "+mNoOfPlayers);
		
		float screenWidthCell = (screenWidth / 100);
		float screenHeightCell = (screenHeight / 100);
		
		float x = screenWidthCell * 10;
		float y = screenHeightCell * -35;
		mChooseNoOfPlayers = new OnScreenText(x, y, this, "No. of Players per Team 		" + mNoOfPlayers, 250);
		float height = screenWidthCell * 4f;
		float width = height * 2f;
		
		x = screenWidthCell * 56;
		y = screenHeightCell * 60;
		mIncreaseButton = new Control(x, y, width, height, "AimUp", this);
		y = screenHeightCell * 80;
		mDecreaseButton = new Control(x, y, width, height, "AimDown", this);
		
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
		mChooseNoOfPlayers.updateText("No. of Players per Team 		" + mNoOfPlayers);
		mChooseNoOfPlayers.update(elapsedTime);
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
				//TODO MJ This really should be in the GameScreen
				Map map = new Map("Castles", 1600f, 580f, mGame,
						this);
				mTeamManager = new TeamManager();
				mTeamManager.createNewTeam("Threeml", mNoOfPlayers, map, mGame, this);
				mTeamManager.createNewTeam("This shit is BANANAS", mNoOfPlayers, map, mGame, this);
				//Store the mNoOfPlayers to file
				//Save the Number of Players for next time
				mPreferenceStore.Save("NoOfPlayers",mNoOfPlayers);
				//Where the Map and Team Selection is passed
				AnimalWarzPlayScreen AnimalWarzPlayScreen = new AnimalWarzPlayScreen(mGame, map.getName(), mTeamManager);
				// As it's the only added screen it will become active.
				mGame.getScreenManager().addScreen(AnimalWarzPlayScreen);
			}
			if(mIncreaseButton.isActivated()){
				if(mNoOfPlayers < 8){
					mNoOfPlayers ++;
					Log.v("TeamError", "Players updated : " + mNoOfPlayers);
				}
			}
			else if(mDecreaseButton.isActivated()){
				if(mNoOfPlayers > 1){
					mNoOfPlayers --;
					Log.v("TeamError", "Players updated : " + mNoOfPlayers);
				}
			}
		}
		mChooseNoOfPlayers.updateText("No. of Players per Team 		" + mNoOfPlayers);
		mChooseNoOfPlayers.update(elapsedTime);
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
		mChooseNoOfPlayers.draw(elapsedTime, graphics2D, mDashboardViewport, mScreenViewport);
		Bitmap Background = mGame.getAssetManager().getBitmap("TSBackground");
		Bitmap BackgroundLogo = mGame.getAssetManager().getBitmap("TSTitle"); 
		Bitmap playGame = mGame.getAssetManager().getBitmap("ContinueButton");
		
		// Determine a center location of the play region
		if (mPlayGameBound == null) {
			
			//Initialise initial variables
			int left, top, right, bottom, scaling;

			//DM - Break page into columns
			int pageColumns = graphics2D.getSurfaceWidth() / 12;
			
			//Continue Button
			scaling = playGame.getWidth() / playGame.getHeight();
			left = pageColumns*8; 
			top = ((graphics2D.getSurfaceHeight() - playGame.getHeight()) / 10)*9;
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
		
		mChooseNoOfPlayers.draw(elapsedTime, graphics2D, mDashboardViewport, mScreenViewport);
		mIncreaseButton.draw(elapsedTime, graphics2D, mDashboardViewport, mScreenViewport);
		mDecreaseButton.draw(elapsedTime, graphics2D, mDashboardViewport, mScreenViewport);
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
