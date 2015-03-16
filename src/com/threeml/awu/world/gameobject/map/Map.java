package com.threeml.awu.world.gameobject.map;

/**
 * Map Object that holds all the properties of a map
 * 
 * !Important Uses MapHelper to set properties
 * 
 * @author Dean
 */
import java.util.Random;

import android.graphics.Bitmap;
import android.util.Log;

import com.threeml.awu.Game;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;

public class Map {
	
            
	private String name;
	private Vector2[] SpawnLocations;
	private Terrain MapTerrainObj;
	private Background MapBackgroundObj;
	private Water MapWaterObject;

	/**
	 * Constructor
	 * @param name Map Name
	 * @param smallImage Map Image used for Map Selection
	 * @param terrainImage Map Imge used for Terrain Deform etc.
	 * @param spawnLocations Locations of Spawn Locations
	 */
	public Map(String name, float LEVEL_WIDTH, float LEVEL_HEIGHT, Game mGame, GameScreen mGameScreen) {
		
		//Map Helper Loads all approperate files
		MapHelper MapHelper = new MapHelper(name, mGame);
		//Set Map Name
		this.name = MapHelper.getName();
		//Set the Spawn Locations
		this.SpawnLocations = MapHelper.getSpawnLocations();
		//Shuffle the spawn locations
		shuffleSpawnLocations();
		//Create the Terrain Object
		this.MapTerrainObj = new Terrain(LEVEL_WIDTH / 2.0f,
				LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, mGame
				.getAssetManager().getBitmap("TerrainImage"), mGameScreen);
		//Create Background Object
		this.MapBackgroundObj = new Background(LEVEL_WIDTH / 2.0f,
						LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, mGame
								.getAssetManager().getBitmap("TerrainBackground"), mGameScreen);
		
		//Adding water
		Bitmap waterBitmap = mGame.getAssetManager().getBitmap("TerrainWater");
		
		this.MapWaterObject = new Water(LEVEL_WIDTH / 2.0f,0,waterBitmap.getHeight(),
				waterBitmap.getHeight()/12,waterBitmap, mGameScreen);	
		}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the spawnLocations
	 */
	public Vector2[] getSpawnLocations() {
		return this.SpawnLocations;
	}
	/**
	 * Returns a Spawn Location that has not been used yet
	 * then sets the value of that location to 0
	 * @author Dean
	 */
	public Vector2 getUniqueSpawnLocation(){
		
		for(int i = 0; i<SpawnLocations.length;i++){
			if(SpawnLocations[i] != null){
				Vector2 temp = SpawnLocations[i];
				//TODO DM - Temp Scale
				temp.x/=4;
				temp.y/=4;
				SpawnLocations[i] = null;
				Log.v("SpawnLocation",(i+1)+") Spawned Player: "+temp.x+" "+temp.y);
				return temp;
		 	}
		}	
		//If nothings returned, return the last spawn location
		return SpawnLocations[SpawnLocations.length-1];
	}
	
	/**
	 * @return the mapTerrainObj
	 */
	public Terrain getTerrain() {
		return MapTerrainObj;
	}
	/**
	 * @return the mapTerrainObj
	 */
	public Background getBackground() {
		return MapBackgroundObj;
	}
	
	/**
	 * @return the mapWaterObject
	 */
	public Water getWater() {
		return MapWaterObject;
	}

	/**
	 * Shuffle the spawn locations 
	 * so each games locations are different
	 * @param ar
	 * @author Dean
	 */
	  private void shuffleSpawnLocations()
	  {
	    Random rnd = new Random();
	    for (int i = SpawnLocations.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      Vector2 a = SpawnLocations[index];
	      SpawnLocations[index] = SpawnLocations[i];
	      SpawnLocations[i] = a;
	    }
	  }
            
}
