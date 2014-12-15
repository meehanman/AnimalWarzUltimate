package com.threeml.awu.util;

import android.util.Log;

import com.threeml.awu.world.GameObject;
import com.threeml.awu.world.InteractiveObject.Player;

/**
 * Collision Detector Helper Library
 * 
 * @version 1.0
 */
public class CollisionDetector {

	/**
	 * Type of collision
	 */
	public enum CollisionType {
		None, Top, Bottom, Left, Right
	};

	/**
	 * Determine if the two specified bounding boxes are in collision
	 * 
	 * @param one
	 *            First bounding box
	 * @param two
	 *            Second bounding box
	 * @return boolean true if the boxes overlap, false otherwise
	 */
	public static boolean isCollision(BoundingBox one, BoundingBox two) {
		return (one.x - one.halfWidth < two.x + two.halfWidth
				&& one.x + one.halfWidth > two.x - two.halfWidth
				&& one.y - one.halfHeight < two.y + two.halfHeight && one.y
				+ one.halfHeight > two.y - two.halfHeight);
	}
	/**
	 * Determine the type of collision between the two bounding boxes.
	 * CollisionType.None is returned if there are no collisions.
	 * 
	 * @param one
	 *            First bounding box
	 * @param two
	 *            Second bounding box
	 * @return Collision type
	 */
	public static CollisionType determineCollisionType(BoundingBox one, BoundingBox two) {
		if (!isCollision(one, two)) {
			return CollisionType.None;
		} else {
			CollisionType collisionType = CollisionType.None;

			// Determine the side of *least intersection*
			float minOverlap = Float.MAX_VALUE;

			// Check the top side
			float tOverlap = (two.y + two.halfHeight)
					- (one.y - one.halfHeight);
			if (tOverlap > 0 && tOverlap < minOverlap) {
				collisionType = CollisionType.Top;
				minOverlap = tOverlap;
			}

			// Check the bottom side
			float bOverlap = (one.y + one.halfHeight)
					- (two.y - two.halfHeight);
			if (bOverlap > 0 && bOverlap < minOverlap) {
				collisionType = CollisionType.Bottom;
				minOverlap = bOverlap;
			}

			// Check the right overlap
			float rOverlap = (one.x + one.halfWidth) - (two.x - two.halfWidth);
			if (rOverlap > 0 && rOverlap < minOverlap) {
				collisionType = CollisionType.Right;
				minOverlap = rOverlap;
			}

			// Check the left overlap
			float lOverlap = (two.x + two.halfWidth) - (one.x - one.halfWidth);
			if (lOverlap > 0 && lOverlap < minOverlap) {
				collisionType = CollisionType.Left;
				minOverlap = lOverlap;
			}

			return collisionType;
		}
	}

	/**
	 * Determine the type of collision between the two game objects. If the two
	 * objects overlap, then they are separated. The first game object will be
	 * repositioned. The second game object will not be moved following a
	 * collision.
	 * 
	 * CollisionType.None is returned if there are no collisions.
	 * 
	 * @param gameObjectOne
	 *            First game object box
	 * @param gameObjectTwo
	 *            Second game object box
	 * @return Collision type
	 */
	public static CollisionType determineAndResolveCollision(
			BoundingBox gameBoundingBoxOne, BoundingBox gameBoundingBoxTwo) {
		CollisionType collisionType = CollisionType.None;

		BoundingBox one = gameBoundingBoxOne;
		BoundingBox two = gameBoundingBoxTwo;

		if (isCollision(one, two)) {
			// Determine the side of *least intersection*
			float collisionDepth = Float.MAX_VALUE;

			// Check the top side
			float tOverlap = (two.y + two.halfHeight)
					- (one.y - one.halfHeight);
			if (tOverlap > 0.0f && tOverlap < collisionDepth) {
				collisionType = CollisionType.Top;
				collisionDepth = tOverlap;
			}

			// Check the bottom side
			float bOverlap = (one.y + one.halfHeight)
					- (two.y - two.halfHeight);
			if (bOverlap > 0.0f && bOverlap < collisionDepth) {
				collisionType = CollisionType.Bottom;
				collisionDepth = bOverlap;
			}

			// Check the right overlap
			float rOverlap = (one.x + one.halfWidth) - (two.x - two.halfWidth);
			if (rOverlap > 0.0f && rOverlap < collisionDepth) {
				collisionType = CollisionType.Right;
				collisionDepth = rOverlap;
			}

			// Check the left overlap
			float lOverlap = (two.x + two.halfWidth) - (one.x - one.halfWidth);
			if (lOverlap > 0.0f && lOverlap < collisionDepth) {
				collisionType = CollisionType.Left;
				collisionDepth = lOverlap;
			}

			
			// Separate if needed
			switch (collisionType) {
			case Top:
				gameBoundingBoxOne.y += collisionDepth;
				break;
			case Bottom:
				gameBoundingBoxOne.y -= collisionDepth;
				break;
			case Right:
				gameBoundingBoxOne.x -= collisionDepth;
				break;
			case Left:
				gameBoundingBoxOne.x += collisionDepth;
				break;
			case None:
				break;
			}
		}
		
		Log.v("CollisionDetected","Type: " + collisionType.name());
		
		return collisionType;
	}	
	
	public static CollisionType determineAndResolveCollision(GameObject gameObjectOne, GameObject gameObjectTwo) {
			
		BoundingBox one = gameObjectOne.getBound();
		BoundingBox two = gameObjectTwo.getBound();
		
		return determineAndResolveCollision(one,two);
		
	}
	
	
	public static CollisionType determineAndResolveCollisionPlayerVsTerrain(Player P, BoundingBox T) {
		CollisionType collisionType = CollisionType.None;

		BoundingBox one = P.getBound();
		BoundingBox two = T;

		if (isCollision(one, two)) {
			// Determine the side of *least intersection*
			float collisionDepth = Float.MAX_VALUE;

			// Check the top side
			float tOverlap = (two.y + two.halfHeight)
					- (one.y - one.halfHeight);
			if (tOverlap > 0.0f && tOverlap < collisionDepth) {
				collisionType = CollisionType.Top;
				collisionDepth = tOverlap;
			}

			// Check the bottom side
			float bOverlap = (one.y + one.halfHeight)
					- (two.y - two.halfHeight);
			if (bOverlap > 0.0f && bOverlap < collisionDepth) {
				collisionType = CollisionType.Bottom;
				collisionDepth = bOverlap;
			}

			// Check the right overlap
			float rOverlap = (one.x + one.halfWidth) - (two.x - two.halfWidth);
			if (rOverlap > 0.0f && rOverlap < collisionDepth) {
				collisionType = CollisionType.Right;
				collisionDepth = rOverlap;
			}

			// Check the left overlap
			float lOverlap = (two.x + two.halfWidth) - (one.x - one.halfWidth);
			if (lOverlap > 0.0f && lOverlap < collisionDepth) {
				collisionType = CollisionType.Left;
				collisionDepth = lOverlap;
			}

			
			// Separate if needed
			switch (collisionType) {
			case Top:
				one.y += collisionDepth;
				P.velocity.x = 0f;
				break;
			case Bottom:
				one.y -= collisionDepth;
				P.velocity.x = 0f;
				break;
			case Right:
				one.x -= collisionDepth;
				break;
			case Left:
				one.x += collisionDepth;
				break;
			case None:
				break;
			}
		}
		
		//Log.v("CollisionDetected","Type: " + collisionType.name());
		
		return collisionType;
	}
	
	
}