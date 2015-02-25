package com.threeml.awu.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;

import com.threeml.awu.Game;
import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.GameCountDownTimer;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.dashboardobject.Control;
import com.threeml.awu.world.gameobject.droppable.Healthkit;
import com.threeml.awu.world.gameobject.map.Background;
import com.threeml.awu.world.gameobject.map.Map;
import com.threeml.awu.world.gameobject.map.Terrain;
import com.threeml.awu.world.gameobject.player.Player;
import com.threeml.awu.world.gameobject.player.Team;
import com.threeml.awu.world.gameobject.player.TeamManager;
import com.threeml.awu.world.gameobject.weapon.Gun;

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

	/*
	 * Define viewports for this layer and the associated screen projection
	 */
	/** Viewport for device screen */
	private ScreenViewport mScreenViewport;
	/** Viewport for terrain - all game objects on this viewport */
	private LayerViewport mTerrainViewport;
	/** Viewport for background image - should only contain background */
	private LayerViewport mBackgroundViewport;
	/** Viewport for all user controls or information */
	private LayerViewport mDashboardViewport;
	/** Rectanlge used to display available weapons */

	/*
	 * Define game objects used within game
	 */
	private Map mCurrentMap;
	/** Background image - not interactable */
	private Background mBackground;
	/** Terrain image, all game objects interact with this object */
	private Terrain mTerrain;
	
	private Bitmap mBitmap;
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
	private Gun mGun;
	// TODO MJ - Player management
	/*
	 * private List<Player> mPlayers = new ArrayList<Player>(); private Player
	 * mPlayer1; private Player mPlayer2;
	 */
	private TeamManager mTeamManager;

	/*
	 * Create touch controls for player input
	 */
	private Control mMoveLeftButton, mMoveRightButton, mJumpLeftButton,
			mJumpRightButton, mWeaponSelect, mShootButton, mAimUpButton,
			mAimDownButton, mWeaponsList;
	/** List array to handle controls */
	private List<Control> mControls = new ArrayList<Control>();
	/** count down timer to change active user after 30 seconds */
	private GameCountDownTimer mCountDownTimer;

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Create a simple steering game world
	 * 
	 * @param game
	 *            Game to which this screen belongs
	 */
	public AnimalWarzPlayScreen(Game game, String MapName, int Teams,
			int Players) {
		super("AnimalWarzPlayScreen", game);

		// Get the current Map Ready
		mCurrentMap = new Map(MapName, LEVEL_WIDTH, LEVEL_HEIGHT, mGame, this);

		// Get Camera/Screen Width and Height
		int screenWidth = game.getScreenWidth();
		int screenHeight = game.getScreenHeight();

		// Create the terrain and background for the game
		mCurrentMap = new Map("Castles", LEVEL_WIDTH, LEVEL_HEIGHT, mGame, this);

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

		CreateGameObjects(screenHeight, screenWidth, Players, Teams);
		mCountDownTimer = game.getPlayerCountDown();
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
	private void CreateGameObjects(int screenHeight, int screenWidth,
			int Players, int Teams) {

		// Create the terrain and background for the game
		Map mCurrentMap = new Map("Castles", LEVEL_WIDTH, LEVEL_HEIGHT, mGame,
				this);

		// Set Terrain and Background Objects
		mTerrain = mCurrentMap.getTerrain();
		mBackground = mCurrentMap.getBackground();

		// Create the objects
		createPlayersAndTeams(Players, Teams);

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

		healthPacks.add(new Healthkit(50, 750, 300, getGame().getAssetManager()
				.getBitmap("Health"), this));

		// Create Controls for game
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

		y = (screenHeight - (screenHeightCell * 43.4f));
		mWeaponsList = new Control(getGame().getScreenWidth() / 2, getGame()
				.getScreenHeight() / 2, getGame().getScreenWidth() / 2,  getGame()
				.getScreenHeight() / 2, "WeaponArchive", this);
		// mControls.add(mWeaponsList);
	}

	// TODO MJ - TEMPORARY SOLUTION UNTIL SETUP SCREEN IS CREATED
	public void createPlayersAndTeams(int Players, int Teams) {

		try {
			List<Player> players = new ArrayList<Player>();
			List<Player> players2 = new ArrayList<Player>();
			int count = 0;

			for (int i = 0; i < Players; i++) {
				Vector2 spawnLocation = mCurrentMap.getUniqueSpawnLocation();
				players.add(new Player(spawnLocation.x, spawnLocation.y, 1, 15,
						getGame().getAssetManager().getBitmap("PlayerWalk"),
						this, count));
				Log.v("PlayerManagement", players.get(i).getId() + "");
				count++;
			}
			Team t1 = new Team(players, "Team1");

			for (int i = 0; i < 2; i++) {
				Vector2 spawnLocation = mCurrentMap.getUniqueSpawnLocation();
				players2.add(new Player(spawnLocation.x, spawnLocation.y, 1,
						15,
						getGame().getAssetManager().getBitmap("PlayerWalk"),
						this, count));
				Log.v("PlayerManagement", players.get(i).getId() + "");
				count++;
			}
			Team t2 = new Team(players, "Team2");
			mTeamManager = new TeamManager(t1, t2);
		} catch (RuntimeException e) {
			Log.v("Major Error", e
					+ "AnimalWarzPlayScreen createPlayersAndTeams");
		}

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
		return mTeamManager.getActivePlayerFromCurrentActiveTeam();
		/*
		 * for(Player p : mPlayers){ if(p.getActive()){ return p; } } return
		 * null;
		 */
	}

	// MJ ~
	private void changeActivePlayer() {

		// TODO MJ - This is really hacked together, I'm gonna fix it to be more
		// scalable
		/*
		 * if(getActivePlayer().getId() == 0){ mPlayers.get(1).setActive(true);
		 * mPlayers.get(0).setActive(false); mCountDownTimer.cancel();
		 * mCountDownTimer.start(); } else{ mPlayers.get(0).setActive(true);
		 * mPlayers.get(1).setActive(false); mCountDownTimer.cancel();
		 * mCountDownTimer.start(); }
		 */
		mTeamManager.changeActiveTeamAndPlayer();
		mCountDownTimer.cancel();
		mCountDownTimer.start();
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

		// Until we have a paralex effect, lets position forground and
		// background together
		mTerrainViewport.x = mBackgroundViewport.x;
		mTerrainViewport.y = mBackgroundViewport.y;

		for (Team t : mTeamManager.getAllTeams()) {
			for (Player p : t.getPlayers()) {
				// for(Player p : mPlayers){
				// Temporary solution to make the health pack appear
				// to be collected by the user
				if (p.getActive()) {
					p.update(elapsedTime, mMoveLeftButton.isActivated(),
							mMoveRightButton.isActivated(),
							mJumpLeftButton.isActivated(),
							mWeaponSelect.isActivated(), mTerrain);
				} else {
					p.update(elapsedTime, false, false, false, false, mTerrain);
				}

				// Log.v("UpdateMethod", "Player ID : " + p.getId());

				/*
				 * if(mWeaponsCrateButton.isActivated()){ mGun.setPosition(500,
				 * 120); }
				 */

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
						// Remove from list
						h.setActive(false);
					}
				}
			}
		}

		// TODO DM - More work needed to collect used up items
		// Garbage Collection to remove objects from screen not being used
		Iterator<Healthkit> iter = healthPacks.listIterator();
		while (iter.hasNext()) {
			// If the healthkit is NOT active
			if (!iter.next().isActive()) {
				iter.remove(); // Remove
			}
		}
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

		for (Team t : mTeamManager.getAllTeams()) {
			for (Player p : t.getPlayers()) {
				// for(Player p : mPlayers){
				p.draw(elapsedTime, graphics2D, mBackgroundViewport,
						mScreenViewport);

			}
		}

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
		mWeaponsList.quarterBitmap(getGame().getAssetManager().getBitmap("WeaponArchive"));
		if (mWeaponSelect.isActivated()) {
			mWeaponsList.draw(elapsedTime, graphics2D, mDashboardViewport,
					mScreenViewport);
		
		}
		
	}
	
}