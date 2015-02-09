package com.threeml.awu.world;
/**
 * Constants class is used for global declaration of constants, providing an easy, centralised way to manage constants
 * 
 * @author Mary-Jane
 *
 */
public class Constants {
	public enum PlayerSpec {
		/**
		 * Max Health the player can have
		 */
		MaxHealth(200.0f),
		/**
		 * Strength of gravity to apply along the y-axis
		 */
		Gravity(-800.0f),
		/**
		 * Acceleration with which the player can move along
		 * the x-axis
		 */
		RunAcceleration(150.0f),
		/**
		 * Maximum velocity of the player along the x-axis
		 */
		MaxXVelocity(200.0f),
		/**
		 * Scale factor that is applied to the x-velocity when
		 * the player is not moving left or right
		 */
		RunDecay(0.8f),
		/**
		 * Instantaneous velocity with which the player jumps up
		 */
		JumpVelocity(200.0f),
		/**
		 * Scale factor that is used to turn the x-velocity into
		 * an angular velocity to give the visual appearance
		 * that the sphere is rotating as the player moves.
		 */
		AngularVelocityScale(1.5f),
		
		;
		
		private float playerSpec;
		
		PlayerSpec(float _playerSpec){
			this.playerSpec = _playerSpec;
		}
		
		public float getValue() {
			return playerSpec;
		}
	}
}
