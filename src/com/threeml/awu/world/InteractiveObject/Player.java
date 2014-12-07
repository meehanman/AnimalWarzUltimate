package com.threeml.awu.world.InteractiveObject;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.util.CollisionDetector;
import com.threeml.awu.util.CollisionDetector.CollisionType;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;


/**
 * Player controlled sphere (that's not really a sphere)
 * 
 * @version 1.0
 */
public class Player extends Sprite {

	// /////////////////////////////////////////////////////////////////////////
	// Properties
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Strength of gravity to apply along the y-axis
	 */
	private float GRAVITY = -800.0f;
	
	/**
	 * Acceleration with which the player can move along
	 * the x-axis
	 */
	private float RUN_ACCELERATION = 150.0f;
	
	/**
	 * Maximum velocity of the player along the x-axis
	 */
	private float MAX_X_VELOCITY = 200.0f;
	
	/**
	 * Scale factor that is applied to the x-velocity when
	 * the player is not moving left or right
	 */
	private float RUN_DECAY = 0.95f;

	/**
	 * Instantaneous velocity with which the player jumps up
	 */
	private float JUMP_VELOCITY = 450.0f;
	
	/**
	 * Scale factor that is used to turn the x-velocity into
	 * an angular velocity to give the visual appearance
	 * that the sphere is rotating as the player moves.
	 */
	private float ANGULAR_VELOCITY_SCALE = 1.5f;

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Create the player's sphere
	 * 
	 * @param startX
	 *            x location of the sphere
	 * @param startY
	 *            y location of the sphere
	 * @param gameScreen
	 *            Gamescreen to which sphere belongs
	 */
	public Player(float startX, float startY, GameScreen gameScreen) {
		super(startX, startY, 50.0f, 50.0f, gameScreen.getGame()
				.getAssetManager().getBitmap("Player"), gameScreen);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Update the player
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 * @param moveLeft
	 *            True if the move left control is active
	 * @param moveRight
	 *            True if the move right control is active
	 * @param jumpUp
	 *            True if the jump up control is active
	 * @param platforms
	 *            Array of platforms in the world
	 */
	public void update(ElapsedTime elapsedTime, boolean moveLeft,
			boolean moveRight, boolean jumpUp, Sprite gameSprite) {

		// Apply gravity to the y-axis acceleration
		acceleration.y = GRAVITY;

		// Depending upon the left and right movement touch controls
		// set an appropriate x-acceleration. If the user does not
		// want to move left or right, then the x-acceleration is zero
		// and the velocity decays towards zero.
		if (moveLeft && !moveRight) {
			acceleration.x = -RUN_ACCELERATION;
		} else if (moveRight && !moveLeft) {
			acceleration.x = RUN_ACCELERATION;
		} else {
			acceleration.x = 0.0f;
			velocity.x *= RUN_DECAY;
		}

		// If the user wants to jump up then providing an immediate
		// boost to the y velocity.
		if (jumpUp && velocity.y == 0.0f) {
			velocity.y = JUMP_VELOCITY;
		}

		// We want the player's sphere to rotate to give the appearance
		// that the sphere is rolling as the player moves. The faster
		// the player is moving the faster the angular velocity.
		angularVelocity = ANGULAR_VELOCITY_SCALE * velocity.x;

		// Call the sprite's update method to apply the defined 
		// accelerations and velocities to provide a new position
		// and orientation.
		super.update(elapsedTime);

		// The player's sphere is constrained by a maximum x-velocity,
		// but not a y-velocity. Make sure we have not exceeded this.
		if (Math.abs(velocity.x) > MAX_X_VELOCITY)
			velocity.x = Math.signum(velocity.x) * MAX_X_VELOCITY;
		
		// Check that our new position has not collided by one of the
		// defined platforms. If so, then removing any overlap and
		// ensure a valid velocity.
		checkForAndResolveCollisions(gameSprite);		
	}

	/**
	 * Check for and then resolve any collision between the sphere and the
	 * platforms.
	 * 
	 * @param platforms
	 *            Array of platforms to test for collision against
	 */
	private void checkForAndResolveCollisions(Sprite map) {

		CollisionType collisionType;
		
		// Consider each platform for a collision
		collisionType = CollisionDetector.determineAndResolvePixelPerfectCollision(this, map);
			
			
			
			switch (collisionType) {
			case Top:
				velocity.y = 0.0f;
				break;
			case Bottom:
				velocity.y = 0.0f;
				break;
			case Left:
				 velocity.x = 0.0f;
				break;
			case Right:
				 velocity.x = 0.0f;
				break;
			case None:
				break;
			}
			
	}
}
