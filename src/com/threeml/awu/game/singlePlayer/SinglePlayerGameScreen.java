package com.threeml.awu.game.singlePlayer;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.threeml.awu.Game;
import com.threeml.awu.engine.AssetStore;
import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.GameCountDownTimer;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.dashboardobject.Control;
import com.threeml.awu.world.gameobject.droppable.Healthkit;
import com.threeml.awu.world.gameobject.map.Background;
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
public class SinglePlayerGameScreen extends GameScreen {

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////
	
	/** Width of the level (Changed later by width/height of backgroundimage) */
	private float LEVEL_WIDTH = 0.0f;
	/** Height of the level (Changed later by width/height of backgroundimage) */
	private float LEVEL_HEIGHT = 0.0f;

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
	
	
	/*
	 * Define game objects used within game
	 */
	/** Background image - not interactable */
	private Background mBackground;
	/** Terrain image, all game objects interact with this object */
	private Terrain mTerrain;
	//TODO MJ - Player management isn't complete
	/** players */
	//TODO MJ - remove this when completed team management
	/*private Player mPlayer;
	private Player mPlayer2;*/
	
	//TODO - should be a better way to add healthpacks to game
		 //- I don't think there should be just one global healthpack
	/** healthpacks */
	private Healthkit healthPack;
	//TODO MP - there shouldn't be one global gun object, probably.
			//- need more work on weapon management
	private Gun mGun;
	//TODO MJ - Player management 
	private List<Player> mPlayers = new ArrayList<Player>();
	private Player mPlayer1;
	private Player mPlayer2;
	//private TeamManager mTeamManager;
	
	
	
	/*
	 * Create touch controls for player input
	 */
	/** Control objects - user interacts with Control objects */
	private Control mLeft, mRight, mJump, mWeapon, mShoot;
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
	public SinglePlayerGameScreen(Game game) {
		super("SinglePlayerGameScreen", game);
		
		//Loads image assets
		loadAssets();
		
		//Get Camera/Screen Width and Height
		int screenWidth = game.getScreenWidth();
		int screenHeight = game.getScreenHeight();
		
		//Change the scaling of the map on screen
		LEVEL_WIDTH = getGame().getAssetManager().getBitmap("Terrain").getWidth();
		LEVEL_HEIGHT = getGame().getAssetManager().getBitmap("Terrain").getHeight();
		
		//TODO apply scaling depending on screensize
		LEVEL_WIDTH /= 1.2f;
		LEVEL_HEIGHT /= 1.2f;
		
		// Create the screen viewport
		mScreenViewport = new ScreenViewport(0, 0, screenWidth,
				screenHeight);
		mDashboardViewport = new LayerViewport(0, 0, screenWidth,
				screenHeight);
		
		// Create the layer viewport, taking into account the orientation
		// and aspect ratio of the screen.
		if (mScreenViewport.width > mScreenViewport.height){
			mBackgroundViewport = new LayerViewport(240.0f, 240.0f
					* mScreenViewport.height / mScreenViewport.width, 240,
					240.0f * mScreenViewport.height / mScreenViewport.width);
			mTerrainViewport = new LayerViewport(240.0f, 240.0f
				* mScreenViewport.height / mScreenViewport.width, 240,
				240.0f * mScreenViewport.height / mScreenViewport.width);
		}else{
			mBackgroundViewport = new LayerViewport(240.0f * mScreenViewport.height
					/ mScreenViewport.width, 240.0f, 240.0f
					* mScreenViewport.height / mScreenViewport.width, 240);
			mTerrainViewport = new LayerViewport(240.0f * mScreenViewport.height
					/ mScreenViewport.width, 240.0f, 240.0f
					* mScreenViewport.height / mScreenViewport.width, 240);
		}
		
		CreateGameObjects(screenHeight);
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
	 * 				Height of the screen
	 */
	private void CreateGameObjects(int screenHeight) {
		// Create the terrain and background for the game
		mTerrain = new Terrain(LEVEL_WIDTH / 2.0f,
				LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
						.getAssetManager().getBitmap("Terrain"), this);	
		mBackground = new Background(LEVEL_WIDTH / 2.0f,
						LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
								.getAssetManager().getBitmap("Background"), this);

				// Create the objects
				
				//createPlayersAndTeams();

				
		// Create the objects
		mPlayer1 = new Player(700, 400, 14, 1, getGame().getAssetManager().getBitmap("Player"), this, 0);
		mPlayer1.setActive(true);
		mPlayers.add(mPlayer1);
		
		mPlayer2 = new Player(600, 400, 14, 1, getGame().getAssetManager().getBitmap("Player"), this, 1);
		mPlayers.add(mPlayer2);
		
		//So we can easily walk on it?
		healthPack = new Healthkit(50, 750, 300, getGame().getAssetManager().getBitmap("Health"),this); 
		
		//Create Controls for game
		mLeft = new Control(
				100.0f, (screenHeight - 100.0f), 100.0f, 100.0f, "LeftArrow", this);
		mControls.add(mLeft);
		
		mGun = new Gun(500.0f, 100.0f, getGame().getAssetManager().getBitmap("Gun"), this);

		mRight = new Control(
				250.0f, (screenHeight  - 100.0f), 100.0f, 100.0f, "RightArrow", this);
		mControls.add(mRight);
		
		mJump = new Control(
				175.5f, (screenHeight - 200.0f), 100.0f, 100.0f, "JumpArrow", this);
		mControls.add(mJump);
		
		mWeapon = new Control(
				650.0f, (screenHeight - 100.0f), 300.0f, 300.0f, "Weapons", this);
		mControls.add(mWeapon);
		
		mShoot = new Control(
				850.0f, (screenHeight - 100.0f), 300.0f, 300.0f, "Shoot", this);
		mControls.add(mShoot);

	}
	//TODO MJ - TEMPORARY SOLUTION UNTIL SETUP SCREEN IS CREATED
	public void createPlayersAndTeams(){
		try{
		List<Team> teams = new ArrayList<Team>();
		int count = 0;
		for(int c = 0; c < 2; c++){
			List<Player> players = new ArrayList<Player>();
			for(int i = 0; i < 2; i++){
				players.add(new Player(700, 400, 14, 1, getGame().getAssetManager().getBitmap("Player"), this, count));
				count++;
			}
			teams.get(c).setPlayers(players);
		}
		//mTeamManager = new TeamManager(teams.get(0), teams.get(1));
		} catch(RuntimeException e){
			Log.v("Major Error", e + "SinglePlayerGameScreen createPlayersAndTeams");
		}

	}
	/**
	 * Loads image assets to game
	 */
	public void loadAssets(){
		// Load in the assets used by this layer
				mGame.getAssetManager().loadAndAddBitmap("Player2", "img/player/mark.png");
				mGame.getAssetManager().loadAndAddBitmap("Player", "img/player/worm_walk_left.png");
				mGame.getAssetManager().loadAndAddBitmap("Terrain", "img/terrain/castles.png");
				//assetManager.loadAndAddBitmap("Terrain", "img/terrain/EarthRise.png");
				mGame.getAssetManager().loadAndAddBitmap("Background", "img/background/lostKingdom.png");
				mGame.getAssetManager().loadAndAddBitmap("Health", "img/gameObject/healthpack.png");
				mGame.getAssetManager().loadAndAddBitmap("Gun", "img/gun.png");
				mGame.getAssetManager().loadAndAddBitmap("RightArrow", "img/rightarrow.png");
				mGame.getAssetManager().loadAndAddBitmap("LeftArrow", "img/leftarrow.png");
				mGame.getAssetManager().loadAndAddBitmap("JumpArrow", "img/jumparrow.png");
				mGame.getAssetManager().loadAndAddBitmap("Weapons", "img/weapons.png");
				mGame.getAssetManager().loadAndAddBitmap("Shoot", "img/shoot.png");
				mGame.getAssetManager().loadAndAddBitmap("Font", "img/fonts/bitmapfont-VCR-OSD-Mono.png");

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
		//return mTeamManager.getActivePlayerFromCurrentActiveTeam();
		for(Player p : mPlayers){
			if(p.getActive()){
				return p;
			}
		}
		return null;
	}
	
	//MJ ~
	private void changeActivePlayer(){
		
		//TODO MJ - This is really hacked together, I'm gonna fix it to be more scalable
		if(getActivePlayer().getId() == 0){
			mPlayers.get(1).setActive(true);
			mPlayers.get(0).setActive(false);
			mCountDownTimer.cancel();
			mCountDownTimer.start();
		}
		else{
			mPlayers.get(0).setActive(true);
			mPlayers.get(1).setActive(false);
			mCountDownTimer.cancel();
			mCountDownTimer.start();
		}
		//mTeamManager.changeActiveTeamAndPlayer();
	}
	
	
	// /////////////////////////////////////////////////////////////////////////
	// Update and Draw methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Update method
	 * @param elapsedTime
	 * 				Elapsed time information
	 */
	@Override
	public void update(ElapsedTime elapsedTime) {
		
		if(getActivePlayer() != null){
			if(mCountDownTimer.hasFinished()){
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
		}
		else{
			Log.v("Error", "Error occurred in SinglePlayerGameScreen: update method. No active player");
		}
		// Ensure the healthpack cannot leave the confines of the world
		BoundingBox healthBound = healthPack.getBound();
		if (healthBound.getBottom() < 0){
			healthPack.position.y -= healthBound.getBottom();
		}
		//Until we have a paralex effect, lets position forground and background together
		mTerrainViewport.x = mBackgroundViewport.x;
		mTerrainViewport.y = mBackgroundViewport.y;
		
		//Update Items
		healthPack.update(elapsedTime, mTerrain);
		
		/*for(Team t : mTeamManager.getAllTeams()){
			for(Player p : t.getPlayers()){*/
		for(Player p : mPlayers){
			// Temporary solution to make the health pack appear
			// to be collected by the user
				if(p.getActive()){
					p.update(elapsedTime, mLeft.isActivated(),
						mRight.isActivated(), mJump.isActivated(), mTerrain);
				}
				else {
					p.update(elapsedTime, false,
							false, false, mTerrain);
				}
				Log.v("UpdateMethod", "Player ID : " + p.getId());
				
				//TODO MP - This was for testing purposes? Can you take it out if it's not needed anymore
				if(mWeapon.isActivated()){
					mGun.setPosition(500, 120);
				}
				
				if(p.getBound().intersects(healthBound))
				{
					//TODO - Should be a better way to handle healthpacks
					healthPack.setPosition(-999, -999);
					p.setHealth(healthPack.getHealthValue());
					Log.v("Player Stats", "Health: " + p.getHealth());
				}
			}
		
	}

	/**
	 * Overrides the draw method from GameScreen class
	 * 				Draws all gameobjects on the game screen
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 * @param graphics2D
	 *            Graphics instance
	 */
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
		
		// Draw the background first of all
		mBackground.draw(elapsedTime, graphics2D, mBackgroundViewport,	mScreenViewport);
		mTerrain.draw(elapsedTime, graphics2D, mBackgroundViewport,	mScreenViewport);
		
		/*for(Team t : mTeamManager.getAllTeams()){
			for(Player p : t.getPlayers()){*/
		for(Player p : mPlayers){
				p.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);
		
			//}
		}
		
		//mPlayer.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);
		healthPack.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);
		mGun.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);

		// Draw the controls last so they appear at the top
		for (Control c : mControls){
			c.draw(elapsedTime, graphics2D, mDashboardViewport,
					mScreenViewport);
		}

	}
}
