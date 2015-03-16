package com.threeml.awu.game;

import java.util.ArrayList;
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
import com.threeml.awu.world.gameobject.map.MapHelper;
import com.threeml.awu.util.TeamManager;

/**
 * Team Selection Menu allowing user to choose settings before playing the game
 * 
 * If there was time, we would have liked to have included a way of letting the
 * user enter team names
 * 
 * @author Dean
 * @author Mary-Jane
 */
public class TeamSelectionScreen extends GameScreen {

	/**
	 * Define the trigger touch region for playing the 'games'
	 */
	private Rect mPlayGameBound;
	private Rect mBackgroundBound, mBackgroundLogoBound, mChooseMapBound,
			mNumberPlayersBound, mNumberBound;
	private List<SmallMap> mSmallMaps;

	private ScreenViewport mScreenViewport;
	private LayerViewport mDashboardViewport;

	private OnScreenText mChooseNoOfPlayers;

	private TeamManager mTeamManager;

	private int mNoOfPlayers = 5;
	private Control mIncreaseButton, mDecreaseButton;

	private PreferenceStore mPreferenceStore;

	private double mLastTime = 0;

	private boolean mMapSelected = false;

	private String mSelectedMap;

	// private EditText mTextName;
	/**
	 * Define Assets to be used in Main Menu
	 */
	AssetStore assetManager = mGame.getAssetManager();

	OnScreenText numbers;

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

		// Get Number of players stored in device
		mPreferenceStore = new PreferenceStore(game.getActivity()
				.getApplicationContext());
		int storePlayers = mPreferenceStore.RetrieveInt("NoOfPlayers");
		// When first run, set a default value
		if (storePlayers != -1) {
			mNoOfPlayers = storePlayers;
		} else {
			mNoOfPlayers = 5;
		}

		// loadAssets();
		Log.v("slope", "Value of mNoOfPlayers is: " + mNoOfPlayers);

		// mTextName = new EditText(this.getGame().getActivity());

		float screenWidthCell = (screenWidth / 100);
		float screenHeightCell = (screenHeight / 100);

		float x, y;
		float height = screenWidthCell * 4f;
		float width = height * 2f;

		x = screenWidthCell * 63;
		y = screenHeightCell * 50;

		mIncreaseButton = new Control("Increase Button", x, y, width, height,
				"LeftArrow", this);

		numbers = new OnScreenText(x, y, this, "" + mNoOfPlayers, 240,"White");
		y = screenHeightCell * 50;
		x = screenWidthCell * 80;
		mDecreaseButton = new Control("Decrease Button", x, y, width, height,
				"RightArrow", this);

		// Initialise initial variables
		int left, top, right, bottom, scaling;
		int pageColumns = getGame().getScreenWidth() / 12;

		mSmallMaps = new ArrayList<SmallMap>();
		int count = -1;
		for (String map : MapHelper.getMapNames()) {

			SmallMap temp = new SmallMap();
			temp.mapImage = mGame.getAssetManager().getBitmap(
					"small" + map + "Map");
			temp.mapName = map;
			Rect tempBound;

			scaling = temp.mapImage.getWidth() / temp.mapImage.getHeight();

			if (count < 5) {
				top = (int) (screenHeightCell * 40);
				left = pageColumns * (count + 2);
			} else {
				top = (int) (screenHeightCell * 60);
				left = pageColumns * ((count + 2) - 6);
			}
			right = left + (int) (pageColumns * 1.5);
			bottom = top + ((int) (pageColumns * 1.5) / scaling);
			tempBound = new Rect(left, top, right, bottom);

			temp.mapBound = tempBound;
			mSmallMaps.add(temp);
			count += 2;
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

			if (mPlayGameBound.contains((int) touchEvent.x, (int) touchEvent.y)) {

				assetManager.getMusic("Dungeon_Boss").pause();
				// Process user preferences for sound and music playing
				if (mPreferenceStore.RetrieveBoolean("PlaySound")) {
					assetManager.getSound("ButtonClick").play();
				}

				// If the play game area has been touched then swap screens
				mGame.getScreenManager().removeScreen(this.getName());

				// Store the mNoOfPlayers to file
				// Save the Number of Players for next time
				mPreferenceStore.Save("NoOfPlayers", mNoOfPlayers);
				// Where the Map and Team Selection is passed
				AnimalWarzPlayScreen AnimalWarzPlayScreen = new AnimalWarzPlayScreen(
						mGame, mSelectedMap, mNoOfPlayers);
				// As it's the only added screen it will become active.
				mGame.getScreenManager().addScreen(AnimalWarzPlayScreen);
			}
			if (elapsedTime.totalTime > (mLastTime + 0.1)) {
				if (mDecreaseButton.isActivated()) {
					if (mNoOfPlayers < 8) {
						mNoOfPlayers++;
						Log.v("TeamError", "Players updated : " + mNoOfPlayers);
					}

					// Process user preferences for sound and music playing
					if (mPreferenceStore.RetrieveBoolean("PlaySound")) {
						assetManager.getSound("ButtonClick").play();
					}
				} else if (mIncreaseButton.isActivated()) {
					if (mNoOfPlayers > 1) {
						mNoOfPlayers--;
						Log.v("TeamError", "Players updated : " + mNoOfPlayers);
					}

					// Process user preferences for sound and music playing
					if (mPreferenceStore.RetrieveBoolean("PlaySound")) {
						assetManager.getSound("ButtonClick").play();
					}
				}
				mLastTime = elapsedTime.totalTime;
			}
			numbers.updateText("" + mNoOfPlayers);

			for (SmallMap map : mSmallMaps) {
				if (map.mapBound.contains((int) touchEvent.x,
						(int) touchEvent.y)) {
					mMapSelected = true;
					mSelectedMap = map.mapName;
				}
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
		Bitmap Background = mGame.getAssetManager().getBitmap("TSBackground");
		Bitmap BackgroundLogo = mGame.getAssetManager().getBitmap("TSTitle");
		Bitmap playGame = mGame.getAssetManager().getBitmap("ContinueButton");
		Bitmap chooseMap = mGame.getAssetManager().getBitmap("ChooseMap");
		Bitmap numberPlayers = mGame.getAssetManager().getBitmap(
				"NumberPlayers");
		Bitmap number = numbers.getTextImage();
		Bitmap tick = mGame.getAssetManager().getBitmap("Tick");

		// Determine a center location of the play region
		if (mPlayGameBound == null) {

			// Initialise initial variables
			int left, top, right, bottom, scaling;

			// DM - Break page into columns
			int pageColumns = graphics2D.getSurfaceWidth() / 12;

			// Continue Button
			scaling = playGame.getWidth() / playGame.getHeight();
			left = pageColumns * 8;
			top = ((graphics2D.getSurfaceHeight() - playGame.getHeight()) / 10) * 9;
			right = left + pageColumns * 3;
			bottom = top + ((pageColumns * 3) / scaling);
			mPlayGameBound = new Rect(left, top, right, bottom);

			// Background Game Logo
			scaling = BackgroundLogo.getWidth() / BackgroundLogo.getHeight();
			left = pageColumns * 3;
			top = (graphics2D.getSurfaceHeight() - BackgroundLogo.getHeight()) / 30;
			right = left + pageColumns * 6;
			bottom = top + ((pageColumns * 6) / scaling);
			mBackgroundLogoBound = new Rect(left, top, right, bottom);

			scaling = chooseMap.getWidth() / chooseMap.getHeight();
			left = pageColumns * 2;
			top = ((graphics2D.getSurfaceHeight() - chooseMap.getHeight()) / 20) * 5;
			right = left + pageColumns * 3;
			bottom = top + ((pageColumns * 3) / scaling);
			mChooseMapBound = new Rect(left, top, right, bottom);

			scaling = numberPlayers.getWidth() / numberPlayers.getHeight();
			left = pageColumns * 7;
			top = ((graphics2D.getSurfaceHeight() - numberPlayers.getHeight()) / 20) * 5;
			right = left + pageColumns * 3;
			bottom = top + ((pageColumns * 3) / scaling);
			mNumberPlayersBound = new Rect(left, top, right, bottom);

			scaling = number.getWidth() / number.getHeight();
			left = (pageColumns * 8) + 40;
			top = (((graphics2D.getSurfaceHeight() - number.getHeight()) / 20) * 8) + 40;
			right = left + (number.getWidth() * 6);
			bottom = top + (number.getHeight() * 4);
			mNumberBound = new Rect(left, top, right, bottom);

		}

		// Create a background bound for the image and sets the size to
		// fullscreen.
		if (mBackgroundBound == null) {
			mBackgroundBound = new Rect(0, 0, graphics2D.getSurfaceWidth(),
					graphics2D.getSurfaceHeight());
		}

		graphics2D.clear(Color.WHITE);
		graphics2D.drawBitmap(Background, null, mBackgroundBound, null);
		graphics2D.drawBitmap(BackgroundLogo, null, mBackgroundLogoBound, null);
		graphics2D.drawBitmap(playGame, null, mPlayGameBound, null);
		graphics2D.drawBitmap(chooseMap, null, mChooseMapBound, null);
		graphics2D.drawBitmap(numberPlayers, null, mNumberPlayersBound, null);
		graphics2D.drawBitmap(number, null, mNumberBound, null);

		for (SmallMap map : mSmallMaps) {
			graphics2D.drawBitmap(map.mapImage, null, map.mapBound, null);
			if (mMapSelected) {
				if (map.mapName == mSelectedMap) {
					graphics2D.drawBitmap(tick, null, map.mapBound, null);
				}
			}
		}

		mIncreaseButton.draw(elapsedTime, graphics2D, mDashboardViewport,
				mScreenViewport);
		mDecreaseButton.draw(elapsedTime, graphics2D, mDashboardViewport,
				mScreenViewport);
	}

	/**
	 * Overrides the method to ensure music plays when game resumed
	 * 
	 * @author Dean
	 * @author Mary-Jane
	 */
	@Override
	public void resume() {
		super.resume();

		// Process user preferences for sound and music playing
		if (mPreferenceStore.RetrieveBoolean("PlayMusic")) {
			assetManager.getSound("Dungeon_Boss").play();
		}
		/*
		 * if(mMediaAvailable){ this.FadeIn(3); mMediaPlayer.start(); }
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
		 * if(mMediaAvailable) { mMediaPlayer.pause();
		 * 
		 * if(mGame.getActivity().isFinishing()){ //mMediaPlayer.stop();
		 * //mMediaPlayer.release(); this.FadeOut(1.0f); mSoundPool.release(); }
		 * }
		 */
	}

}
