package com.threeml.awu.game.singlePlayer;

import java.util.ArrayList;
import java.util.List;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.threeml.awu.Game;
import com.threeml.awu.engine.AssetStore;
import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.world.GameObject;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.dashboardobject.Control;
import com.threeml.awu.world.gameobject.droppable.Healthkit;
import com.threeml.awu.world.gameobject.map.Terrain;
import com.threeml.awu.world.gameobject.player.Player;
import com.threeml.awu.world.gameobject.weapon.Gun;

/**
 * Simple steering game world 
 * 
 * @version 1.0
 */
public class SinglePlayerGameScreen extends GameScreen {

	// /////////////////////////////////////////////////////////////////////////
	// Properties
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * Width and height of the level (Changed later by width/height of backgroundimage)
	 */
	private float LEVEL_WIDTH = 0.0f;
	private float LEVEL_HEIGHT = 0.0f;

	/**
	 * Define viewports for this layer and the associated screen projection
	 */
	private ScreenViewport mScreenViewport;
	private LayerViewport mTerrainViewport;
	private LayerViewport mBackgroundViewport; 
	private LayerViewport mDashboardViewport;
	/**
	 * Define game objects used within game
	 */
	private GameObject mBackground;
	private Terrain mTerrain;
	private Player mPlayer;
	private Player mPlayer2;
	private Healthkit healthPack;

	private Gun mGun;
	private List<Player> mPlayers = new ArrayList<Player>();
	
	
	/**
	 * Create touch controls for player input
	 */
	private Control mLeft, mRight, mJump, mWeapon, mShoot;
	private List<Control> mControls = new ArrayList<Control>();
	
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
		
		// Load in the assets used by this layer
		AssetStore assetManager = mGame.getAssetManager();
		//assetManager.loadAndAddBitmap("Player", "img/player/mark.png");
		assetManager.loadAndAddBitmap("Player", "img/player/worm_walk_left.png");
		assetManager.loadAndAddBitmap("Terrain", "img/terrain/castles.png");
		//assetManager.loadAndAddBitmap("Terrain", "img/terrain/EarthRise.png");
		assetManager.loadAndAddBitmap("Background", "img/background/lostKingdom.png");
		assetManager.loadAndAddBitmap("Health", "img/gameObject/healthpack.png");
		assetManager.loadAndAddBitmap("Gun", "img/gun.png");
		assetManager.loadAndAddBitmap("RightArrow", "img/rightarrow.png");
		assetManager.loadAndAddBitmap("LeftArrow", "img/leftarrow.png");
		assetManager.loadAndAddBitmap("JumpArrow", "img/jumparrow.png");
		assetManager.loadAndAddBitmap("Weapons", "img/weapons.png");
		assetManager.loadAndAddBitmap("Shoot", "img/shoot.png");
		assetManager.loadAndAddBitmap("Font", "img/fonts/bitmapfont-VCR-OSD-Mono.png");
		
		//Get Camera/Screen Width and Height
		int screenWidth = game.getScreenWidth();
		int screenHeight = game.getScreenHeight();
		
		//Set the level width and height to that of the background image
		LEVEL_WIDTH = getGame().getAssetManager().getBitmap("Terrain").getWidth()/2;
		LEVEL_HEIGHT = getGame().getAssetManager().getBitmap("Terrain").getHeight()/2;
				
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
		//TextView tViewTime;
		//tViewTime.setText("00:02:00");
		//final CounterClass Timer= new CounterClass(180000,1000);
		
		//public class CounterClass extends CountDownTimer {

			//public CounterClass(long millisInFuture, long countDownInterval) {
				//super(millisInFuture, countDownInterval);
				// TODO Auto-generated constructor stub
			//}

			@Override
			//public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				
			//}

			@Override
			//public void onFinish() {
				// TODO Auto-generated method stub
				//TextViewTime.setText("The Game is Complete");
			//}
		}
		

	}
	
	/**
	 * Creates the objects for the game, such as controls, items, players, etc.
	 */
	private void CreateGameObjects(int screenHeight) {
		// Create the background
				mBackground = new GameObject(LEVEL_WIDTH / 2.0f,
						LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
								.getAssetManager().getBitmap("Background"), this);
				mTerrain = new Terrain(LEVEL_WIDTH / 2.0f,
						LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
								.getAssetManager().getBitmap("Terrain"), this);
				


				// Create the objects
				mPlayer = new Player(700, 400, 14, 1, getGame().getAssetManager().getBitmap("Player"), this, 0);
				mPlayer.setActive(true);
				mPlayers.add(mPlayer);
				
				mPlayer2 = new Player(600, 400, 14, 1, getGame().getAssetManager().getBitmap("Player"), this, 1);
				mPlayers.add(mPlayer2);
				
				healthPack = new Healthkit(500, 300, getGame().getAssetManager().getBitmap("Health"),this); //So we can easily walk on it?
				
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
	
	
	// /////////////////////////////////////////////////////////////////////////
	// Support methods
	// /////////////////////////////////////////////////////////////////////////

	/**	
	 * Return the active player 
	 * 
	 * @return Player Active player object
	 */
	private Player getActivePlayer() {
		for(Player p : mPlayers){
			if(p.getActive()){
				return p;
			}
		}
		return null;
	}
	private void changeActivePlayer(){
		if(getActivePlayer().getId() < mPlayers.size()){
			mPlayers.get(getActivePlayer().getId()).setActive(false);
			mPlayers.get(getActivePlayer().getId() + 1).setActive(true);
		}
		else{
			mPlayers.get(getActivePlayer().getId()).setActive(false);
			mPlayers.get(0).setActive(true);
		}
	}
	
	
	// /////////////////////////////////////////////////////////////////////////
	// Update and Draw methods
	// /////////////////////////////////////////////////////////////////////////
		
	/*
	 * (non-Javadoc) fs
	 * 
	 * @see
	 * uk.ac.qub.eeecs.gage.world.GameScreen#update(uk.ac.qub.eeecs.gage.engine
	 * .ElapsedTime)
	 */
	/**
	 * Update method
	 */
	@Override
	public void update(ElapsedTime elapsedTime) {
		
		if(getActivePlayer() != null){
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
			
			
	
			// Update the player
			//getActivePlayer().update(elapsedTime, moveLeft.isActivated(),
				//moveRight.isActivated(), jumpUp.isActivated(), mTerrain);
			
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
			
			if(mWeapon.isActivated())
				mGun.setPosition(500, 120);
			
			if(p.getBound().intersects(healthBound))
			{
				healthPack.setPosition(-999, -999);
				p.setHealth(healthPack.getHealthValue());
				Log.v("Player Stats", "Health: " + p.getHealth());
			}
		}

		// Process any touch events occurring since the update
		/*Input input = mGame.getInput();
		
		List <TouchEvent> touchEvents = input.getTouchEvents();
		if (touchEvents.size() > 0) {
			// Determine the next movement along the x-axis and update the viewport
			float moveDistance = touchEvents.get(0).x
					- touchEvents.get(touchEvents.size() - 1).x;
			mBackgroundViewport.x += moveDistance;
			
			// Check we don't exceed our layer bounds
			if (mBackgroundViewport.x < mBackgroundViewport.halfWidth)
				mBackgroundViewport.x = mBackgroundViewport.halfWidth;
			if (mBackgroundViewport.x > mBackgroundViewport.halfWidth*2)
				mBackgroundViewport.x = mBackgroundViewport.halfWidth*2;
		}
		*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.qub.eeecs.gage.world.GameScreen#draw(uk.ac.qub.eeecs.gage.engine
	 * .ElapsedTime, uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D)
	 */
	/**
	 * draw method
	 */
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

		/*
		// Create the screen to black and define a clip based on the viewport
		graphics2D.clear(Color.BLACK);
		graphics2D.clipRect(mScreenViewport.toRect());
		 */
		
		// Draw the background first of all
		mBackground.draw(elapsedTime, graphics2D, mBackgroundViewport,	mScreenViewport);
		mTerrain.draw(elapsedTime, graphics2D, mBackgroundViewport,	mScreenViewport);
		
		for(Player p : mPlayers){
			p.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);
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
