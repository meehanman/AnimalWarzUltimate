package com.threeml.awu.game.singlePlayer;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.threeml.awu.Game;
import com.threeml.awu.engine.AssetStore;
import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.util.CollisionDetector;
import com.threeml.awu.world.GameObject;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.BackgroundObject.Control;
import com.threeml.awu.world.BackgroundObject.Terrain;
import com.threeml.awu.world.InteractiveObject.Healthkit;
import com.threeml.awu.world.InteractiveObject.Item;
import com.threeml.awu.world.InteractiveObject.Player;

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
	private Healthkit healthPack;
	
	
	/**
	 * Create touch controls for player input
	 */
	private Control moveLeft, moveRight, jumpUp;
	private List<Control> mControls = new ArrayList<Control>();
	
	//private Rect movementButtonArea;	//MJ - touch area for arrow control
	
	/*
	private GameObject mBackground;

	private PlayerSpaceship mPlayer;

	private final int NUM_ASTEROIDS = 20;
	private List<Asteroid> mAsteroids;

	private final int NUM_SEEKERS = 5;
	private final int NUM_TURRETS = 5;
	private List<AISpaceship> mAISpaceships;
	*/
	
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
		assetManager.loadAndAddBitmap("Player", "img/player/mark.png");
		//assetManager.loadAndAddBitmap("Player", "img/player/worm_walk_left.png");
		assetManager.loadAndAddBitmap("Terrain", "img/terrain/castles.png");
		//assetManager.loadAndAddBitmap("Terrain", "img/terrain/EarthRise.png");
		assetManager.loadAndAddBitmap("Background", "img/background/lostKingdom.png");
		assetManager.loadAndAddBitmap("Health", "img/gameObject/healthpack.png");
		assetManager.loadAndAddBitmap("Arrow", "img/arrow.png");
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
		
		// Create the background
		mBackground = new GameObject(LEVEL_WIDTH / 2.0f,
				LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
						.getAssetManager().getBitmap("Background"), this);
		mTerrain = new Terrain(LEVEL_WIDTH / 2.0f,
				LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
						.getAssetManager().getBitmap("Terrain"), this);
		

		// Create the objects
		mPlayer = new Player(500, 400, 1, 1, this);
		healthPack = new Healthkit(500, 300, getGame().getAssetManager().getBitmap("Health"),this); //So we can easily walk on it?
		
		//Create Controls for game
		//TODO Create different directions for the controls
		moveLeft = new Control(
				100.0f, (screenHeight - 100.0f), 100.0f, 100.0f, "Arrow", this);
		mControls.add(moveLeft);

		moveRight = new Control(
				225.0f, (screenHeight - 100.0f), 100.0f, 100.0f, "Arrow", this);
		mControls.add(moveRight);

		jumpUp = new Control(
				(screenWidth - 125.0f), (screenHeight - 100.0f), 100.0f, 100.0f, "Arrow", this);
		mControls.add(jumpUp);
		
		
		/*
		// Create a number of randomly positioned asteroids
		Random random = new Random();
		mAsteroids = new ArrayList<Asteroid>(NUM_ASTEROIDS);
		for (int idx = 0; idx < NUM_ASTEROIDS; idx++)
			mAsteroids.add(new Asteroid(random.nextFloat() * LEVEL_WIDTH,
					random.nextFloat() * LEVEL_HEIGHT, this));

		// Create a number of randomly positioned AI controlled ships
		mAISpaceships = new ArrayList<AISpaceship>(NUM_SEEKERS + NUM_TURRETS);
		for (int idx = 0; idx < NUM_SEEKERS; idx++)
			mAISpaceships.add(new AISpaceship(random.nextFloat() * LEVEL_WIDTH,
					random.nextFloat() * LEVEL_HEIGHT,
					AISpaceship.ShipBehaviour.Seeker, this));
		for (int idx = 0; idx < NUM_TURRETS; idx++)
			mAISpaceships.add(new AISpaceship(random.nextFloat() * LEVEL_WIDTH,
					random.nextFloat() * LEVEL_HEIGHT,
					AISpaceship.ShipBehaviour.Turret, this));
		*/
	}
	
	
	// /////////////////////////////////////////////////////////////////////////
	// Support methods
	// /////////////////////////////////////////////////////////////////////////

	/**	
	 * Return the player 
	 * 
	 * @return Player spaceship
	 */
	public Player getPlayer() {
		return mPlayer;
	}

	/**
	 * Return a list of the AI spaceships in the level
	 * 
	 * @return List of AI controlled spaceships
	 
	public List<AISpaceship> getAISpaceships() {
		return mAISpaceships;
	}
*/
	/**
	 * Return a list of asteroids in the the level
	 * 
	 * @return List of asteroids in the level
	 
	public List<Asteroid> getAsteroids() {
		return mAsteroids;
	}
*/
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
	@Override
	public void update(ElapsedTime elapsedTime) {

		// Ensure the player cannot leave the confines of the world
		BoundingBox playerBound = mPlayer.getBound();
		if (playerBound.getLeft() < 0)
			mPlayer.position.x -= playerBound.getLeft();
		else if (playerBound.getRight() > LEVEL_WIDTH)
			mPlayer.position.x -= (playerBound.getRight() - LEVEL_WIDTH);

		if (playerBound.getBottom() < 0)
			mPlayer.position.y -= playerBound.getBottom();
		else if (playerBound.getTop() > LEVEL_HEIGHT)
			mPlayer.position.y -= (playerBound.getTop() - LEVEL_HEIGHT);
		
		// Ensure the healthpack cannot leave the confines of the world
		BoundingBox healthBound = healthPack.getBound();
		if (healthBound.getBottom() < 0)
			healthPack.position.y -= healthBound.getBottom();
		
		// Focus the layer viewport on the player
		mBackgroundViewport.x = mPlayer.position.x;
		mBackgroundViewport.y = mPlayer.position.y;

		// Ensure the viewport cannot leave the confines of the world
		if (mBackgroundViewport.getLeft() < 0)
			mBackgroundViewport.x -= mBackgroundViewport.getLeft();
		else if (mBackgroundViewport.getRight() > LEVEL_WIDTH)
			mBackgroundViewport.x -= (mBackgroundViewport.getRight() - LEVEL_WIDTH);

		if (mBackgroundViewport.getBottom() < 0)
			mBackgroundViewport.y -= mBackgroundViewport.getBottom();
		else if (mBackgroundViewport.getTop() > LEVEL_HEIGHT)
			mBackgroundViewport.y -= (mBackgroundViewport.getTop() - LEVEL_HEIGHT);
		
		//Until we have a paralex effect, lets position forground and background together
		mTerrainViewport.x = mBackgroundViewport.x;
		mTerrainViewport.y = mBackgroundViewport.y;

		//Update Items
		healthPack.update(elapsedTime, mTerrain);
		// Update the player
		mPlayer.update(elapsedTime, moveLeft.isActivated(),
			moveRight.isActivated(), jumpUp.isActivated(), mTerrain);
		
		// Temporary solution to make the health pack appear
		// to be collected by the user
		if(playerBound.intersects(healthBound))
		{
			healthPack.setPosition(-999, -999);
			mPlayer.setHealth(healthPack.getHealthValue());
			Log.v("Player Stats", "Health: " + mPlayer.getHealth());
		}
		
		 for (BoundingBox bb : mTerrain.getTerrainBlocks()) {
			
			CollisionDetector.determineAndResolveCollisionPlayerVsTerrain(mPlayer, bb);
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
					
					
		/*
		// Update each of the AI controlled spaceships
		for (AISpaceship aiSpaceship : mAISpaceships)
			aiSpaceship.update(elapsedTime);

		// Update each of the asteroids
		for (Asteroid asteroid : mAsteroids)
			asteroid.update(elapsedTime);
		*/
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

		/*
		// Create the screen to black and define a clip based on the viewport
		graphics2D.clear(Color.BLACK);
		graphics2D.clipRect(mScreenViewport.toRect());
		 */
		
		// Draw the background first of all
		mBackground.draw(elapsedTime, graphics2D, mBackgroundViewport,	mScreenViewport);
		mTerrain.draw(elapsedTime, graphics2D, mBackgroundViewport,	mScreenViewport);
		mTerrain.CreateTerrainPhysics();
		mPlayer.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);
		healthPack.draw(elapsedTime, graphics2D, mBackgroundViewport, mScreenViewport);
		
		// Draw the controls last so they appear at the top
		for (Control Control : mControls){
			Control.draw(elapsedTime, graphics2D, mDashboardViewport,
					mScreenViewport);
		}

		
		
		/*
		// Draw each of the asteroids
		for (Asteroid asteroid : mAsteroids)
			asteroid.draw(elapsedTime, graphics2D, mBackgroundViewport,
					mScreenViewport);

		// Draw each of the AI controlled spaceships
		for (AISpaceship aiSpaceship : mAISpaceships)
			aiSpaceship.draw(elapsedTime, graphics2D, mBackgroundViewport,
					mScreenViewport);
		*/

	}
}
