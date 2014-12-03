package com.threeml.awu.world.InteractiveObject;

import com.threeml.awu.ai.SteeringBehaviours;
import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.input.Input;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

public class Player extends Sprite {
	
	/**
	 * Centre of the screen (used to determine the offset of touch events)
	 */
	private Vector2 screenCentre = new Vector2();
	
	/**
	 * Acceleration vector based on the player's touch input
	 */
	private Vector2 playerTouchAcceleration = new Vector2();
	
	//Constructor
	public Player(float startX, float startY, GameScreen gameScreen) {
		super(startX, startY, 50.0f, 50.0f, gameScreen.getGame()
				.getAssetManager().getBitmap("Player"), gameScreen);
		
		// Store the centre of the screen
		screenCentre.x = gameScreen.getGame().getScreenWidth() / 2;
		screenCentre.y = gameScreen.getGame().getScreenHeight() / 2;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.qub.eeecs.gage.world.Sprite#update(uk.ac.qub.eeecs.gage.engine.
	 * ElapsedTime)
	 */
	@Override
	public void update(ElapsedTime elapsedTime) {
		// DM - Player Currently cannot be moved by simply clicking on screen
		/*
		// Consider any touch events occurring since the update
		Input input = mGameScreen.getGame().getInput();

		if (input.existsTouch(0)) {
			// Get the primary touch event
			playerTouchAcceleration.x = (input.getTouchX(0) - screenCentre.x)
					/ screenCentre.x;
			playerTouchAcceleration.y = (screenCentre.y - input.getTouchY(0))
					/ screenCentre.y; // Invert the for y axis

			// Convert into an input acceleration
			acceleration.x = playerTouchAcceleration.x * maxAcceleration;
			acceleration.y = playerTouchAcceleration.y * maxAcceleration;
			
		}

		// Ensure that the ships points in the direction of movement
		angularAcceleration = SteeringBehaviours.alignWithMovement(this);

		// Dampen the linear and angular acceleration and velocity
		angularAcceleration *= 0.95f;
		angularVelocity *= 0.75f;
		acceleration.multiply(0.75f);
		velocity.multiply(0.95f);

		// Apply the determined accelerations
		super.update(elapsedTime);
		*/
	}
	
	public void setSpeed(float speedX, float speedY){
		playerTouchAcceleration.x = speedX;
		playerTouchAcceleration.y = speedY;
	}
	
	public float getSpeedX() {
		return playerTouchAcceleration.x;
	}
	
	public float getSpeedY() {
		return playerTouchAcceleration.y;
	}
}
