package com.threeml.awu.world.gameobject.map;

/**
 * Map Object that holds all the properties of a map
 * 
 * !Important Uses MapHelper to set properties
 * 
 * @author Dean
 */
import com.threeml.awu.Game;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;

public class Map {
	
            
	private String name;
	private Vector2[] SpawnLocations;
	private Terrain MapTerrainObj;
	private Background MapBackgroundObj;

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
		//Create the Terrain Object
		this.MapTerrainObj = new Terrain(LEVEL_WIDTH / 2.0f,
				LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, mGame
				.getAssetManager().getBitmap("TerrainImage"), mGameScreen);
		//Create Background Object
		this.MapBackgroundObj = new Background(LEVEL_WIDTH / 2.0f,
						LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, mGame
								.getAssetManager().getBitmap("TerrainBackground"), mGameScreen);		
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
	public Vector2 getAndRemoveSpawnLocation(){
		
		for(Vector2 SpawnLocation : SpawnLocations){
			if(!SpawnLocation.isZero()){
			 	//If its the last spawn location don't set the location to 0
				if(SpawnLocation == SpawnLocations[SpawnLocations.length-1]){
					SpawnLocation.set(0, 0);
				}
				return SpawnLocation;
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
            
}
