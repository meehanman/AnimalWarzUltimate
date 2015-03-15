package com.threeml.awu.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.threeml.awu.Game;
import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.GameCountDownTimer;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.util.InGameText;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.dashboardobject.BannerNotification;
import com.threeml.awu.world.dashboardobject.Control;
import com.threeml.awu.world.dashboardobject.OnScreenText;
import com.threeml.awu.world.gameobject.droppable.Healthkit;
import com.threeml.awu.world.gameobject.map.Background;
import com.threeml.awu.world.gameobject.map.Map;
import com.threeml.awu.world.gameobject.map.Terrain;
import com.threeml.awu.world.gameobject.player.Player;
import com.threeml.awu.world.gameobject.player.Team;
import com.threeml.awu.world.gameobject.player.TeamManager;
import com.threeml.awu.world.gameobject.weapon.Projectile;

/**
 * Simple steering game world
 * 
 * @version 1.0
 */
public class AnimalWarzPlayScreen extends GameScreen {

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////

	/** Width of the level (Changed later by width/height of backgroundimage) */
	private float LEVEL_WIDTH = 1600f;
	/** Height of the level (Changed later by width/height of backgroundimage) */
	private float LEVEL_HEIGHT = 580f;
	/** Width of the weapon icon */
	private final float WEAPON_WIDTH = getGame().getScreenWidth() / 5f;
	/** Length of the weapon icon */
	private final float WEAPON_HEIGHT = getGame().getScreenHeight() / 5f;
	/** Viewport for device screen */
	private ScreenViewport mScreenViewport;
	/** Viewport for terrain - all game objects on this viewport */
	private LayerViewport mTerrainViewport;
	/** Viewport for background image - should only contain background */
	private LayerViewport mBackgroundViewport;
	/** Viewport for all user controls or information */
	private LayerViewport mDashboardViewport;
	/** Define game objects used within game */
	private Map mCurrentMap;
	/** Background image - not interactable */
	private Background mBackground;
	/** Terrain image, all game objects interact with this object */
	private Terrain mTerrain;
	/**
	 * Banner Notification will appear when there is a notification for the user
	 */
	private BannerNotification mNotification;

	private Vector2 mBottomOfScreen;

	private OnScreenText mNonBannerText;

	// TODO MJ - Player management isn't complete
	/** players */
	// TODO MJ - remove this when completed team management
	/*
	 * private Player mPlayer; private Player mPlayer2;
	 */

	// TODO - should be a better way to add healthpacks to game
	// - I don't think there should be just one global healthpack
	// -Updated to array, we could have an array of healthpacks
	// -after certain goes, we then can "para" in some more
	private List<Healthkit> healthPacks = new ArrayList<Healthkit>();
	// TODO MP - there shouldn't be one global gun object, probably.
	// - need more work on weapon management
	// TODO MJ - Player management
	/*
	 * private List<Player> mPlayers = new ArrayList<Player>(); private Player
	 * mPlayer1; private Player mPlayer2;
	 */
	private TeamManager mTeamManager;

	/** Touch controls for player input */
	private Control mMoveLeftButton, mMoveRightButton, mJumpLeftButton,
			mJumpRightButton, mWeaponSelect, mShootButton, mAimUpButton,
			mAimDownButton, mWeaponsList, mGun, mGrenade, mRocket, mBat,
			mMainMenu;

	/** List array to handle controls */
	private List<Control> mControls = new ArrayList<Control>();

	/** List array to handle weapon selection */
	private List<Control> mWeaponSelection = new ArrayList<Control>();

	/** count down timer to change active user after 30 seconds */
	private GameCountDownTimer mCountDownTimer;

	private GameCountDownTimer mNotificationTimer;
	private GameCountDownTimer mNotification2Timer;

	private OnScreenText mDashboardTimer;
	private List<OnScreenText> mTeamHealthText;
	private boolean mGameOver = false;

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Create a simple steering game world
	 * 
	 * @param game
	 *            Game to which this screen belongs
	 */
	public AnimalWarzPlayScreen(Game game, String MapName,
			TeamManager teamManager) {
		super("AnimalWarzPlayScreen", game);
		mTeamManager = teamManager;
		// Get the current Map Ready
		mCurrentMap = new Map(MapName, LEVEL_WIDTH, LEVEL_HEIGHT, mGame, this);

		// Get Camera/Screen Width and Height
		int screenWidth = game.getScreenWidth();
		int screenHeight = game.getScreenHeight();

		// Change the scaling of the map on screen
		// TODO DM - We need to have a level width larger than images so stuff
		// can fly
		// around the game, not limited to just the map itself, unless we add
		// larger images
		// with large transparent borders
		// LEVEL_WIDTH =
		// getGame().getAssetManager().getBitmap("TerrainImage").getWidth();
		// LEVEL_HEIGHT =
		// getGame().getAssetManager().getBitmap("TerrainImage").getHeight();

		// TODO DM - apply scaling depending on screensize
		// LEVEL_WIDTH /= 1.2f;
		// LEVEL_HEIGHT /= 1.2f;

		// Create the screen viewport
		mScreenViewport = new ScreenViewport(0, 0, screenWidth, screenHeight);
		mDashboardViewport = new LayerViewport(0, 0, screenWidth, screenHeight);

		// Create the layer viewport, taking into account the orientation
		// and aspect ratio of the screen.
		if (mScreenViewport.width > mScreenViewport.height) {
			mBackgroundViewport = new LayerViewport(240.0f, 240.0f
					* mScreenViewport.height / mScreenViewport.width, 240,
					240.0f * mScreenViewport.height / mScreenViewport.width);
			mTerrainViewport = new LayerViewport(240.0f, 240.0f
					* mScreenViewport.height / mScreenViewport.width, 240,
					240.0f * mScreenViewport.height / mScreenViewport.width);
		} else {
			mBackgroundViewport = new LayerViewport(240.0f
					* mScreenViewport.height / mScreenViewport.width, 240.0f,
					240.0f * mScreenViewport.height / mScreenViewport.width,
					240);
			mTerrainViewport = new LayerViewport(240.0f
					* mScreenViewport.height / mScreenViewport.width, 240.0f,
					240.0f * mScreenViewport.height / mScreenViewport.width,
					240);
		}

		mBottomOfScreen = new Vector2(mTerrainViewport.getLeft(),
				(mTerrainViewport.getBottom() + getActivePlayer().getBound()
						.getHeight()));
		CreateGameObjects(screenHeight, screenWidth);
		mCountDownTimer = game.getPlayerCountDown();
		mNotificationTimer = game.getNotificationTimer();
		mNotification2Timer = game.getNotification2Timer();
		mCountDownTimer.start();
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Creates the objects for the game, such as controls, items, players, etc.
	 * 
	 * @param screenHeight
	 *            Height of the screen
	 */
	private void CreateGameObjects(int screenHeight, int screenWidth) {
		// Set Terrain and Background Objects
		mTerrain = mCurrentMap.getTerrain();
		mBackground = mCurrentMap.getBackground();

		// Create the objects

		// TODO - Add recursive loop to add players setting first player active
		// to coincide with
		// TODO - Add random spawn locations
		// Create the objects
		/*
		 * mPlayer1 = new Player(700, 400, 14, 1,
		 * getGame().getAssetManager().getBitmap("Player"), this, 0);
		 * mPlayer1.setActive(true); mPlayers.add(mPlayer1);
		 * 
		 * mPlayer2 = new Player(600, 400, 14, 1,
		 * getGame().getAssetManager().getBitmap("Player"), this, 1);
		 * mPlayers.add(mPlayer2);
		 */

		healthPacks.add(new Healthkit(-100, 750, 100, getGame()
				.getAssetManager().getBitmap("Health"), this));

		// Create Controls for game
		// TODO DM - move this to the controls class
		float x = 0, y = 0, width = 0, height = 0;
		float screenWidthCell = (screenWidth / 100);
		float screenHeightCell = (screenHeight / 100);

		height = screenWidthCell * 6f;
		width = height * 2f;

		x = screenWidthCell * 10;
		y = (screenHeight - (screenHeightCell * 21));
		mMoveLeftButton = new Control(x, y, width, height, "MoveLeft", this);
		mControls.add(mMoveLeftButton);

		x = screenWidthCell * 22.05f;
		mMoveRightButton = new Control(x, y, width, height, "MoveRight", this);
		mControls.add(mMoveRightButton);

		width = screenWidthCell * 8.5f;
		height = width;

		x = screenWidthCell * 16.05f;
		y = (screenHeight - (screenHeightCell * 38.2f));
		mAimUpButton = new Control(x, y, width, height, "AimUp", this);
		mControls.add(mAimUpButton);

		y = (screenHeight - (screenHeightCell * 8.6f));
		mAimDownButton = new Control(x, y, width, height, "AimDown", this);
		mControls.add(mAimDownButton);
		x = 0;
		y = 0;
		width = 0;
		height = 0;

		height = screenWidthCell * 12f;
		width = height * 0.95f;

		x = screenWidthCell * 40;
		y = (screenHeight - (screenHeightCell * 15f));

		mWeaponSelect = new Control(x, y, width, height, "WeaponsCrate", this);
		mControls.add(mWeaponSelect);

		x = screenWidthCell * 80;
		mShootButton = new Control(x, y, width, height, "Fireeee", this);
		mControls.add(mShootButton);

		x = screenWidthCell * 95;
		mJumpRightButton = new Control(x, y, width, height, "JumpRight", this);
		mControls.add(mJumpRightButton);

		y = (screenHeight - (screenHeightCell * 38.2f));
		mJumpLeftButton = new Control(x, y, width, height, "JumpLeft", this);
		mControls.add(mJumpLeftButton);

		mGun = new Control(getGame().getScreenWidth() / 5.2f, getGame()
				.getScreenHeight() / 3f, WEAPON_WIDTH, WEAPON_HEIGHT, "Gun",
				this);
		mWeaponSelection.add(mGun);

		mGrenade = new Control(getGame().getScreenWidth() / 2.5f, getGame()
				.getScreenHeight() / 3f, WEAPON_WIDTH, WEAPON_HEIGHT,
				"Grenade", this);
		mWeaponSelection.add(mGrenade);

		mRocket = new Control(getGame().getScreenWidth() / 1.65f, getGame()
				.getScreenHeight() / 3f, WEAPON_WIDTH, WEAPON_HEIGHT, "Rocket",
				this);
		mWeaponSelection.add(mRocket);

		mBat = new Control(getGame().getScreenWidth() / 1.20f, getGame()
				.getScreenHeight() / 3f, WEAPON_WIDTH, WEAPON_HEIGHT, "Bat",
				this);
		mWeaponSelection.add(mBat);

		mMainMenu = new Control(getGame().getScreenWidth() / 2f, getGame()
				.getScreenHeight() / 2f, WEAPON_WIDTH, WEAPON_HEIGHT,
				"MainMenu", this);

		// mProjectile = new Projectile(mTeamManager.getActivePlayer().getX(),
		// mTeamManager.getActivePlayer().getY(),
		// 20f, 5f, getGame().getAssetManager().getBitmap("Projectile"), this);
		/*
		 * mWeaponsList = new Control(getGame().getScreenWidth() / 2, getGame()
		 * .getScreenHeight() / 2, getGame().getScreenWidth() / 2, getGame()
		 * .getScreenHeight() / 2, "WeaponArchive", this);
		 */
		// mControls.add(mWeaponsList);

		x = screenWidthCell * 10.0f;
		y = (screenHeight - 300.0f);
		mNotification = new BannerNotification(x, y, this);
		x = screenWidthCell * 100.0f;
		y = (screenHeight - 200.0f);
		mDashboardTimer = new OnScreenText(x, y, this, "0", 250);

		x = screenWidthCell * 100.0f;
		mTeamHealthText = new ArrayList<OnScreenText>();
		for (Team t : mTeamManager.getTeams()) {

			mTeamHealthText.add(new OnScreenText(x, y, this, t.getTeamName()
					+ " : " + t.getCollectiveHealth(), 150));
			y -= 100;
		}

		x = screenWidthCell * 30.0f;
		y = (screenHeight - (screenHeightCell * 150f));
		mNonBannerText = new OnScreenText(x, y, this, "0", 150);
		mNonBannerText.setVisible(false);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Support methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Return the active player
	 * 
	 * @return Player Active player object
	 */
	private Player getActivePlayer() {
		return mTeamManager.getActivePlayer();
		/*
		 * for(Player p : mPlayers){ if(p.getActive()){ return p; } } return
		 * null;
		 */
	}

	// MJ ~
	private void changeActivePlayer() {
		mTeamManager.changeActivePlayer();
		displayNotificationWithBanner(InGameText
				.generateChangePlayerText(getActivePlayer().getName()));
		mCountDownTimer.cancel();
		mCountDownTimer.start();
	}

	/**
	 * Displays a notification for the user
	 * 
	 * @param text
	 *            Text to be displayed
	 */
	private void displayNotificationWithBanner(String text) {
		mNotification.updateText(text);
		mNotification.setVisible(true);
		mNotificationTimer.start();
	}

	private void displayNotificationWithoutBanner(String text) {
		mNonBannerText.updateText(text);
		mNonBannerText.setVisible(true);
		mNotification2Timer.start();
		Log.v("NotificationText", "Notification triggered");
	}

	/**
	 * Hides the notification for the user
	 */
	private void hideNotifications() {
		if (mNotificationTimer.hasFinished()) {
			mNotification.setVisible(false);
		}
		if (mNotification2Timer.hasFinished()) {
			mNonBannerText.setVisible(false);
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	// Update and Draw methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Update method
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 */
	@Override
	public void update(ElapsedTime elapsedTime) {

		hideNotifications();
		if(!mGameOver){
		if(getActivePlayer().isAlive()){
			//Rotate players after timers finished
			if (getActivePlayer() != null) {
				if (mCountDownTimer.hasFinished()) {
					changeActivePlayer();
				}
				// Ensure the player cannot leave the confines of the world
				BoundingBox playerBound = getActivePlayer().getBound();
				if (playerBound.getLeft() < 0)
					getActivePlayer().position.x -= playerBound.getLeft();
				else if (playerBound.getRight() > LEVEL_WIDTH)
					getActivePlayer().position.x -= (playerBound.getRight() - LEVEL_WIDTH);
	
				if (playerBound.getBottom() < 0)
					getActivePlayer().position.y -= playerBound.getBottom();
				else if (playerBound.getTop() > LEVEL_HEIGHT)
					getActivePlayer().position.y -= (playerBound.getTop() - LEVEL_HEIGHT);
	
				// Focus the layer viewport on the player
				mBackgroundViewport.x = getActivePlayer().position.x;
				mBackgroundViewport.y = getActivePlayer().position.y;
				
				// Ensure the viewport cannot leave the confines of the world
				if (mBackgroundViewport.getLeft() < 0)
					mBackgroundViewport.x -= mBackgroundViewport.getLeft();
				else if (mBackgroundViewport.getRight() > LEVEL_WIDTH)
					mBackgroundViewport.x -= (mBackgroundViewport.getRight() - LEVEL_WIDTH);
	
				if (mBackgroundViewport.getBottom() < 0)
					mBackgroundViewport.y -= mBackgroundViewport.getBottom();
				else if (mBackgroundViewport.getTop() > LEVEL_HEIGHT)
					mBackgroundViewport.y -= (mBackgroundViewport.getTop() - LEVEL_HEIGHT);
			} else {
				Log.v("Error",
						"Error occurred in AnimalWarzPlayScreen: update method. No active player");
			}
		}
		else {
			mCountDownTimer.cancel();
			Log.v("PlayerDeath", "Has alive players" + "");
			//if the current active team has no more alive players
			if(!mTeamManager.getActiveTeam().hasAlivePlayers()){
				Log.v("PlayerDeath", "Has no alive players" + "");
				//if team manager has playable teams, i.e. if any more
				if(mTeamManager.hasPlayableTeams()){
					Log.v("PlayerDeath", "Has playable teams" + "");
					changeActivePlayer();
				}
				//if no more playable teams
				else {
					Log.v("PlayerDeath", "game over" + "");
					gameOver(false);
				}
			}
			displayNotificationWithoutBanner(InGameText.generateDeathText(getActivePlayer().getName()));
			if(!mGameOver){
				changeActivePlayer();
			}
		}
 
		// Until we have a paralex effect, lets position forground and
		// background together
		mTerrainViewport.x = mBackgroundViewport.x;
		mTerrainViewport.y = mBackgroundViewport.y;

			for (Player p : mTeamManager.getAllNotActivePlayers()) {
				
				
				p.update(elapsedTime, false, false, false, false, false, false, false, mTerrain);

				// Log.v("UpdateMethod", "Player ID : " + p.getId());

				/*
				 * if(mWeaponsCrateButton.isActivated()){ mGun.setPosition(500,
				 * 120); }
				 */
				
				//mProjectile.setPosition(mTeamManager.getActivePlayer().getX(),
					//	mTeamManager.getActivePlayer().getY());
								
				
				
				// Update Items
				for (Healthkit h : healthPacks) {
					h.update(elapsedTime, mTerrain);

					// Ensure healthpacks cannot leave the confines of the world
					// TODO this should be applied to sprite/item class
					BoundingBox healthBound = h.getBound();
					if (healthBound.getBottom() < 0) {
						h.position.y -= healthBound.getBottom();
					}

					// Checks for any collisions
					if (p.getBound().intersects(healthBound)) {
						// Apply health to player
						p.setHealth(h.getHealthValue());
						displayNotificationWithoutBanner(InGameText.generateCollectedHealthText(getActivePlayer().getName(), h.getHealthValue()));
						// Remove from list
						h.setActive(false);
					}
				}
				if(p.position == mBottomOfScreen){
					Log.v("PlayerDeath", p.getName() + " died in water. Much tragedy. Wow.");
					p.setHealth(0);
					p.killByWater();
				}
				
				if(p.getBound().intersects(getActivePlayer().getProjectile().getBound())){
					p.doDamage(getActivePlayer().getProjectile().getDamage());
					
				}
				
				else if(p.getHealth() <= 0){
					p.kill();
				}
			}
			
			if (mShootButton.isActivated()) {
				mTeamManager.getActivePlayer().getProjectile().loadProjectile();
				getGame().getAssetManager().getSound("Bullet_SFX").play();
				//DM TODO - Testing the deform circle method
			//	mTerrain.deformCircle(mTeamManager.getActivePlayer().getPlayerTarget().getX(),
			//  mTeamManager.getActivePlayer().getPlayerTarget().getY(), 20);
				
			}
			
			// Checking the weapon menu bitmaps for a touch event and logging
			// the type of bitmap (weapon) that was selected
			for (int j = 0; j < mWeaponSelection.size(); j++) {
				if (mWeaponSelection.get(j).isActivated()
						&& mWeaponSelection.get(j) == mWeaponSelection.get(0)) {
					Log.v("WEAPON CLICK", "GUN");
				} else if (mWeaponSelection.get(j).isActivated()
						&& mWeaponSelection.get(j) == mWeaponSelection.get(1)) {
					Log.v("WEAPON CLICK", "GRENADE");
				} else if (mWeaponSelection.get(j).isActivated()
						&& mWeaponSelection.get(j) == mWeaponSelection.get(2)) {
					Log.v("WEAPON CLICK", "ROCKET");
				} else if (mWeaponSelection.get(j).isActivated()
						&& mWeaponSelection.get(j) == mWeaponSelection.get(3)) {
					Log.v("WEAPON CLICK", "BAT");
				}
			}
		getActivePlayer().update(elapsedTime, 
				mMoveLeftButton.isActivated(),
				mMoveRightButton.isActivated(),
				mJumpLeftButton.isActivated(),
				mJumpRightButton.isActivated(),
				mAimUpButton.isActivated(),
				mAimDownButton.isActivated(),
				mWeaponSelect.isActivated(), mTerrain);
		
		for (Healthkit h : healthPacks) {
			h.update(elapsedTime, mTerrain);

			// Ensure healthpacks cannot leave the confines of the world
			// TODO this should be applied to sprite/item class
			BoundingBox healthBound = h.getBound();
			if (healthBound.getBottom() < 0) {
				h.position.y -= healthBound.getBottom();
			}

			// Checks for any collisions
			if (mTeamManager.getActivePlayer().getBound().intersects(healthBound)) {
				// Apply health to player
				mTeamManager.getActivePlayer().setHealth(h.getHealthValue());
				// Remove from list
				h.setActive(false);
			}
		}

		// TODO DM - More work needed to collect used up items
		// Garbage Collection to remove objects from screen not being used
		Iterator<Healthkit> healthpackList = healthPacks.listIterator();
		while (healthpackList.hasNext()) {
			// If the healthkit is NOT active
			if (!healthpackList.next().isActive()) {
				healthpackList.remove(); // Remove
			}
		}
		try {
			mDashboardTimer.updateText(Long.toString(mCountDownTimer.getCountDownInSeconds()));
			mDashboardTimer.update(elapsedTime);
			int c = 0;
			if(mTeamHealthText != null && mTeamHealthText.size() > 0){
				for(OnScreenText t : mTeamHealthText){
					String str = mTeamManager.getTeam(c).getTeamName();
					String buffer = "";
					if(str.length() > 20){
						str = str.substring(0, 19);
					}
					else if(str.length() < 20) {
						int difference =  (20 - str.length());
						for(int i = 0; i < (difference); i ++){
							buffer += " ";
						}
					}
					StringBuilder sb = new StringBuilder();
					sb.append(str);
					sb.append(buffer);
					if(mTeamManager.getTeam(c).getCollectiveHealth() > 0){
						sb.append(" " + Integer.toString(mTeamManager.getTeam(c).getCollectiveHealth()));
					}
					else {
						sb.append(" OUT");
					}
					t.updateText(sb.toString());
					t.update(elapsedTime);
					c++;
				}
			}
		}
		catch (Exception e){
			Log.e("Text Error", "Game screen timer error : " + e);
		}
	} else {
		displayNotificationWithBanner(InGameText.generateWinText(mTeamManager.getWinningTeam().getTeamName()));
		if(mMainMenu.isActivated()){
			getGame().getAssetManager().getSound("ButtonClick").play();
			mGame.getScreenManager().removeScreen(this.getName());
			//AnimalWarzPlayScreen AnimalWarzPlayScreen = new AnimalWarzPlayScreen(mGame);
			MenuScreen menuScreen = new MenuScreen(mGame);
			// As it's the only added screen it will become active.
			mGame.getScreenManager().addScreen(menuScreen);
		}
	}

	}

	private void gameOver(boolean surrender) {
		Log.v("PlayerDeath", "Game over!");
		if (!surrender) {
			displayNotificationWithBanner(InGameText
					.generateWinText(mTeamManager.getWinningTeam()
							.getTeamName()));
		}
		mGameOver = true;
	}

	/**
	 * Overrides the draw method from GameScreen class Draws all gameobjects on
	 * the game screen
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 * @param graphics2D
	 *            Graphics instance
	 */
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

		// Draw the background first of all
		mBackground.draw(elapsedTime, graphics2D, mBackgroundViewport,
				mScreenViewport);
		mTerrain.draw(elapsedTime, graphics2D, mBackgroundViewport,
				mScreenViewport);

		for (Player p : mTeamManager.getAllNotActivePlayers()) {
			// for(Player p : mPlayers){
			p.draw(elapsedTime, graphics2D, mBackgroundViewport,
					mScreenViewport, false);

		}
		mTeamManager.getActivePlayer().draw(elapsedTime, graphics2D,
				mBackgroundViewport, mScreenViewport, true);

		// mProjectile.draw(elapsedTime, graphics2D, mBackgroundViewport,
		// mScreenViewport);
		// mPlayer.draw(elapsedTime, graphics2D, mBackgroundViewport,
		// mScreenViewport);
		for (Healthkit h : healthPacks) {
			h.draw(elapsedTime, graphics2D, mBackgroundViewport,
					mScreenViewport);

			// }
		}
		// mGun.draw(elapsedTime, graphics2D, mBackgroundViewport,
		// mScreenViewport);

		// Draw the controls last so they appear at the top
		for (Control c : mControls) {
			c.draw(elapsedTime, graphics2D, mDashboardViewport, mScreenViewport);
		}
		if (!mGameOver) {
			if (mWeaponSelect.isTouched()) {
				for (Control n : mWeaponSelection) {
					n.draw(elapsedTime, graphics2D, mDashboardViewport,
							mScreenViewport);
				}

				// mWeaponsList.setPosition(getGame().getScreenWidth()/2,
				// getGame().getScreenHeight()/2);
			}

			mDashboardTimer.draw(elapsedTime, graphics2D, mDashboardViewport,
					mScreenViewport);

		}
		if (mTeamHealthText != null && mTeamHealthText.size() > 0) {
			for (OnScreenText t : mTeamHealthText) {
				t.draw(elapsedTime, graphics2D, mDashboardViewport,
						mScreenViewport);
			}
		}
		mNotification.position = new Vector2((getGame().getScreenWidth() /2) - (mNotification.getBitmap().getWidth()),
				mNotification.position.y);
		mNotification.draw(elapsedTime, graphics2D, mDashboardViewport,
				mScreenViewport);
		
		mNonBannerText.position = new Vector2((getGame().getScreenWidth() /2) - (mNonBannerText.getBitmap().getWidth()),
				mNonBannerText.position.y);
		mNonBannerText.draw(elapsedTime, graphics2D, mDashboardViewport,
				mScreenViewport);
		if (mGameOver) {
			mMainMenu.draw(elapsedTime, graphics2D, mDashboardViewport,
					mScreenViewport);
		}

	}
}
