package com.threeml.awu.game;

import java.util.ArrayList;
import java.util.Iterator;
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
public class AnimalWarzPlayScreen extends GameScreen {

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
		//-Updated to array, we could have an array of healthpacks
		//-after certain goes, we then can "para" in some more
	private List<Healthkit> healthPacks = new ArrayList<Healthkit>();
	//TODO MP - there shouldn't be one global gun object, probably.
			//- need more work on weapon management
	private Gun mGun;
	//TODO MJ - Player management 
	/*private List<Player> mPlayers = new ArrayList<Player>();
	private Player mPlayer1;
	private Player mPlayer2;*/
	private TeamManager mTeamManager;
	
	
	
	/*
	 * Create touch controls for player input
	 */
	private Control mMoveLeftButton, mMoveRightButton, mJumpLeftButton, 
			mJumpRightButton, mWeaponsCrateButton, mShootButton, mAimUpButton, mAimDownButton;
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
	public AnimalWarzPlayScreen(Game game) {
		super("AnimalWarzPlayScreen", game);
		
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
		
		CreateGameObjects(screenHeight, screenWidth);
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
	private void CreateGameObjects(int screenHeight, int screenWidth) {
		// Create the terrain and background for the game
		mTerrain = new Terrain(LEVEL_WIDTH / 2.0f,
				LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
						.getAssetManager().getBitmap("Terrain"), this);	
		mBackground = new Background(LEVEL_WIDTH / 2.0f,
						LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
								.getAssetManager().getBitmap("Background"), this);

				// Create the objects
				
				createPlayersAndTeams();

		
		//TODO - Add recursive loop to add players setting first player active
		// to coincide with
		//TODO - Add random spawn locations
		// Create the objects
		/*mPlayer1 = new Player(700, 400, 14, 1, getGame().getAssetManager().getBitmap("Player"), this, 0);
		mPlayer1.setActive(true);
		mPlayers.add(mPlayer1);
		
		mPlayer2 = new Player(600, 400, 14, 1, getGame().getAssetManager().getBitmap("Player"), this, 1);
		mPlayers.add(mPlayer2);*/
		
		healthPacks.add(new Healthkit(50, 750, 300, getGame().getAssetManager().getBitmap("Health"),this));
		
		//Create Controls for game
		float x=0,y=0,width=0,height=0;
		float screenWidthCell = (screenWidth/100);
		float screenHeightCell = (screenHeight/100);
		
		height = screenWidthCell*6f;
		width = height*2f;
		
		x = screenWidthCell*10;
		y = (screenHeight - (screenHeightCell*21));
		mMoveLeftButton = new Control(x,y,width,height, "MoveLeft", this);
		mControls.add(mMoveLeftButton);
		
		x = screenWidthCell*22.05f;
		mMoveRightButton = new Control(x,y,width,height, "MoveRight", this);
		mControls.add(mMoveRightButton);
		
		
		width = screenWidthCell*8.5f;
		height = width;
		
		x = screenWidthCell*16.05f;
		y = (screenHeight - (screenHeightCell*38.2f));
		mAimUpButton = new Control(x,y,width,height, "AimUp", this);
		mControls.add(mAimUpButton);
		
		y = (screenHeight - (screenHeightCell*8.6f));
		mAimDownButton = new Control(x,y,width,height, "AimDown", this);
		mControls.add(mAimDownButton);	x=0;y=0;width=0;height=0;
		
		
		height = screenWidthCell*12f;
		width = height*0.95f;
		
		x = screenWidthCell*40;
		y = (screenHeight - (screenHeightCell*15f));
		
		mWeaponsCrateButton = new Control(x,y,width,height, "WeaponsCrate", this);
		mControls.add(mWeaponsCrateButton);
		
		x = screenWidthCell*80;
		mShootButton = new Control(x,y,width,height, "Fireeee", this);
		mControls.add(mShootButton);	
		
		x = screenWidthCell*95;
		mJumpRightButton = new Control(x,y,width,height, "JumpRight", this);
		mControls.add(mJumpRightButton);	
		
		y = (screenHeight - (screenHeightCell*38.2f));
		mJumpLeftButton = new Control(x,y,width,height, "JumpLeft", this);
		mControls.add(mJumpLeftButton);

	}
	//TODO MJ - TEMPORARY SOLUTION UNTIL SETUP SCREEN IS CREATED
	public void createPlayersAndTeams(){
		try{
			List<Player> players = new ArrayList<Player>();
			List<Player> players2 = new ArrayList<Player>();
			int count = 0;
			for(int i = 0; i < 2; i++){
				players.add(new Player((count * 100), 400, 1, 15, getGame().getAssetManager().getBitmap("PlayerWalk"), this, count));
				Log.v("PlayerManagement", players.get(i).getId() +"");
				count++;
			}
			Team t1 = new Team(players, "Team1");
			
			for(int i = 0; i < 2; i++){
				players2.add(new Player((count * 100), 400, 1, 15, getGame().getAssetManager().getBitmap("PlayerWalk"), this, count));
				Log.v("PlayerManagement", players.get(i).getId() +"");
				count++;
			}
			Team t2 = new Team(players, "Team2");
		mTeamManager = new TeamManager(t1, t2);
		} catch(RuntimeException e){
			Log.v("Major Error", e + "AnimalWarzPlayScreen createPlayersAndTeams");
		}

	}
	/**
	 * Loads image assets to game
	 */
	public void loadAssets(){
		// Load in the assets used by this layer

		AssetStore assetManager = mGame.getAssetManager();
		assetManager.loadAndAddBitmap("Player", "img/player/worm_walk_left.png");
		assetManager.loadAndAddBitmap("PlayerBackFlip", "img/player/wbackflp.png");
		assetManager.loadAndAddBitmap("PlayerWalk", "img/player/wwalk.png");
		assetManager.loadAndAddBitmap("PlayerWin", "img/player/wwinner.png");
		assetManager.loadAndAddBitmap("PlayerUp", "img/player/wflyup.png");
		assetManager.loadAndAddBitmap("PlayerFall", "img/player/wfall.png");
		
		assetManager.loadAndAddBitmap("Terrain", "img/terrain/castles.png");
		assetManager.loadAndAddBitmap("Background", "img/background/lostKingdom.png");
		assetManager.loadAndAddBitmap("Health", "img/gameObject/healthpack.png");
		assetManager.loadAndAddBitmap("Gun", "img/gun.png");
		assetManager.loadAndAddBitmap("Font", "img/fonts/bitmapfont-VCR-OSD-Mono.png");

		//DashboardControls
		assetManager.loadAndAddBitmap("MoveLeft","img/dashControls/MoveLeft.png");
		assetManager.loadAndAddBitmap("MoveRight","img/dashControls/MoveRight.png");
		assetManager.loadAndAddBitmap("JumpLeft","img/dashControls/JumpLeft.png");
		assetManager.loadAndAddBitmap("JumpRight","img/dashControls/JumpRight.png");
		assetManager.loadAndAddBitmap("WeaponsCrate","img/dashControls/WeaponsCrate.png");
		assetManager.loadAndAddBitmap("Fireeee","img/dashControls/Fireeee.png");
		assetManager.loadAndAddBitmap("AimUp","img/dashControls/AimUp.png");
		assetManager.loadAndAddBitmap("AimDown","img/dashControls/AimDown.png");
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
		/*for(Player p : mPlayers){
			if(p.getActive()){
				return p;
			}
		}
		return null;*/
	}
	
	//MJ ~
	private void changeActivePlayer(){
		
		//TODO MJ - This is really hacked together, I'm gonna fix it to be more scalable
		/*if(getActivePlayer().getId() == 0){
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
		}*/
		mTeamManager.changeActiveTeamAndPlayer();
		mCountDownTimer.cancel();
		mCountDownTimer.start();
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
			Log.v("Error", "Error occurred in AnimalWarzPlayScreen: update method. No active player");
		}

		
		//Until we have a paralex effect, lets position forground and background together
		mTerrainViewport.x = mBackgroundViewport.x;
		mTerrainViewport.y = mBackgroundViewport.y;
		
		
		for(Team t : mTeamManager.getAllTeams()){
			for(Player p : t.getPlayers()){
		//for(Player p : mPlayers){
			// Temporary solution to make the health pack appear
			// to be collected by the user
				if(p.getActive()){
					p.update(elapsedTime, mMoveLeftButton.isActivated(),
						mMoveRightButton.isActivated(), mJumpLeftButton.isActivated(), mTerrain);
				}
			else {
				p.update(elapsedTime, false,
						false, false, mTerrain);
			}
				
				
			Log.v("UpdateMethod", "Player ID : " + p.getId());
			
			/*if(mWeaponsCrateButton.isActivated()){
				mGun.setPosition(500, 120);
			}*/
			
			//Update Items
			for(Healthkit h : healthPacks){
				h.update(elapsedTime, mTerrain);
				
				// Ensure healthpacks cannot leave the confines of the world
				//TODO this should be applied to sprite/item class
				BoundingBox healthBound = h.getBound();
				if (healthBound.getBottom() < 0){
					h.position.y -= healthBound.getBottom();
				}
				
				//Checks for any collisions
				if(p.getBound().intersects(healthBound)){
					//Apply health to player
					p.setHealth(h.getHealthValue());
					//Remove from list
					h.setActive(false);
				}
			}
		}
		}

		
		//TODO DM - More work needed to collect used up items
		//Garbage Collection to remove objects from screen not being used
		Iterator<Healthkit> iter = healthPacks.listIterator();
		while(iter.hasNext()){
			//If the healthkit is NOT active
		    if(!iter.next().isActive()) {
		        iter.remove(); //Remove
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
		
		for(Team t : mTeamManager.getAllTeams()){
			for(Player p : t.getPlayers()){
		//for(Player p : mPlayers){
				p.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);
		
			}
		}
		
		//mPlayer.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);
		for(Healthkit h : healthPacks){
			h.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);
	
		//}
		}
		//mGun.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);

		// Draw the controls last so they appear at the top
		for (Control c : mControls){
			c.draw(elapsedTime, graphics2D, mDashboardViewport,
					mScreenViewport);
		}

	}
}
