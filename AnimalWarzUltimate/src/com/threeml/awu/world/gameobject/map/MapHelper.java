package com.threeml.awu.world.gameobject.map;

import java.util.Random;

import android.util.Log;

import com.threeml.awu.Game;
import com.threeml.awu.util.AssetsHelper;
import com.threeml.awu.util.Vector2;

/**
 * Helps Generate the Map Object used pre-determined values for the maps
 * 
 * The Map Names to initialise the Maps are the imageName, a small and large
 * image must be placed in the TerrainImages/large and /small folder in assets
 * 
 * The Map Helper holds the spawn locations of all the maps on file
 * 
 * @author Dean
 * 
 */
public class MapHelper {

	/**
	 * When adding a new Map, images must be inserted in the folders
	 * assets/img/TerrainImages/ background, large, small
	 * 
	 * then add the map name below and add spawn points to getSpawnLocations()
	 */
	private static String[] MapNames = { "Castles", "FairyLand", "Pirates",
			"Ship", "Titanic" };
	/**
	 * The current map selected
	 */
	private String MapName;

	/**
	 * 
	 * @param selectedMap
	 */
	public MapHelper(String MapName, Game mGame) {

		// If its not a map name in the MapNamesArray
		if (isMapName(MapName)) {
			// Set Map Name
			this.MapName = MapName;
		} else {
			// Set as Random Map
			this.MapName = getRandomMapName();

		}
		// Load Assets into memory
		AssetsHelper.loadMapAssets(this.MapName, mGame);
	}

	// //////////////////////////////////////////////////////////////
	// Helper Constructors
	// //////////////////////////////////////////////////////////////
	/**
	 * Returns all possible maps
	 * 
	 * @return
	 * @author Dean
	 */
	public static String[] getMapNames() {
		return MapNames;
	}

	public static Boolean isMapName(String s) {
		for (String m : MapNames) {
			if (m == s) {
				return true;
			}
		}

		return false;

	}

	/**
	 * Returns a Random Map Name
	 * 
	 * @return Returns a Random Map Name
	 */
	private static String getRandomMapName() {

		Random randomMapLocation = new Random();
		int RandomMap = randomMapLocation.nextInt(MapNames.length - 1);
		Log.v("RandomMap", MapNames[RandomMap] + " map choosen.");
		return MapNames[RandomMap];
	}

	/**
	 * @return the mapName
	 */
	public String getName() {
		return this.MapName;
	}

	/**
	 * @return Returns a vector2 array of all spawn locations
	 */
	public Vector2[] getSpawnLocations() {

		if (MapName == "Castles") {
			Vector2 SpawnLocations[] = {
					new Vector2(4283.071330850913, 1770.856404391271),
					new Vector2(4414.071330850911, 1886.856404391271),
					new Vector2(4649.071330850913, 2048.856404391271),
					new Vector2(4896.071330850913, 1333.856404391271),
					new Vector2(4657.071330850913, 1442.856404391271),
					new Vector2(4588.071330850913, 1624.856404391271),
					new Vector2(4838.071330850913, 1768.856404391271),
					new Vector2(4749.071330850913, 2221.856404391271),
					new Vector2(5046.071330850913, 2215.856404391271),
					new Vector2(4920.071330850913, 1672.856404391271),
					new Vector2(3925.0713308509135, 1431.856404391271),
					new Vector2(4018.0713308509135, 1286.856404391271),
					new Vector2(3763.0713308509135, 1676.856404391271),
					new Vector2(3681.0713308509135, 2073.856404391271),
					new Vector2(3877.0713308509135, 2055.856404391271),
					new Vector2(3309.0713308509135, 2044.856404391271),
					new Vector2(3502.0713308509135, 2177.856404391271),
					new Vector2(4167.071330850913, 1958.856404391271),
					new Vector2(2938.0713308509135, 1663.856404391271),
					new Vector2(2852.0713308509135, 2095.856404391271),
					new Vector2(2660.0713308509135, 2106.856404391271),
					new Vector2(2428.0713308509135, 2063.856404391271),
					new Vector2(2517.0713308509135, 1522.856404391271),
					new Vector2(2793.0713308509135, 1345.856404391271),
					new Vector2(2914.0713308509135, 1442.856404391271),
					new Vector2(3357.0713308509135, 1310.856404391271),
					new Vector2(3112.0713308509135, 1286.856404391271), };

			return SpawnLocations;
		} else if (MapName == "FairyLand") {

			Vector2 SpawnLocations[] = {
					new Vector2(4546.690106970014, 2074.3098930299857),
					new Vector2(3117.8659123194716, 1458.5519380287974),
					new Vector2(3413.8659123194716, 1431.5519380287974),
					new Vector2(3620.8659123194716, 1523.5519380287974),
					new Vector2(3592.8659123194716, 2120.5519380287974),
					new Vector2(3299.8659123194716, 1969.5519380287974),
					new Vector2(3954.8659123194716, 2063.5519380287974),
					new Vector2(4225.865912319472, 2055.5519380287974),
					new Vector2(4435.865912319472, 1788.5519380287974),
					new Vector2(4717.865912319472, 1756.5519380287974),
					new Vector2(5013.865912319472, 2288.5519380287974),
					new Vector2(2677.8659123194716, 2078.5519380287974),
					new Vector2(2907.8659123194716, 2054.5519380287974),
					new Vector2(2494.8659123194716, 2243.5519380287974),
					new Vector2(3458.3488516009033, 2071.095649019126),
					new Vector2(3711.3488516009033, 2038.095649019126),
					new Vector2(2560.3488516009033, 2174.095649019126),
					new Vector2(4829.348851600904, 2103.095649019126),
					new Vector2(5081.348851600904, 1837.095649019126),
					new Vector2(4950.348851600904, 1825.095649019126),
					new Vector2(3838.0478527182613, 2085.391330583362), };

			return SpawnLocations;
		} else if (MapName == "Titanic") {
			Vector2 SpawnLocations[] = {
					new Vector2(2837.874947626356, 2603.847317090144),
					new Vector2(2836.874947626356, 2298.847317090144),
					new Vector2(3167.874947626356, 2439.847317090144),
					new Vector2(4065.874947626356, 2845.847317090144),
					new Vector2(4391.8749476263565, 2906.847317090144),
					new Vector2(3995.874947626356, 2215.847317090144),
					new Vector2(3078.874947626356, 2136.847317090144),
					new Vector2(2541.874947626356, 1475.8473170901439),
					new Vector2(3068.874947626356, 1629.8473170901439),
					new Vector2(3435.874947626356, 1802.8473170901439),
					new Vector2(3134.874947626356, 2025.8473170901439),
					new Vector2(3983.874947626356, 1586.8473170901439),
					new Vector2(4202.8749476263565, 2070.847317090144),
					new Vector2(4517.8749476263565, 1824.8473170901439),
					new Vector2(4625.8749476263565, 2115.847317090144),
					new Vector2(5037.8749476263565, 2054.847317090144),
					new Vector2(4446.8749476263565, 2716.847317090144),
					new Vector2(5607.8749476263565, 2310.847317090144),
					new Vector2(6161.8749476263565, 2180.847317090144),
					new Vector2(3439.8749476263565, 2440.847317090144),
					new Vector2(4275.8749476263565, 1939.8473170901439),
					new Vector2(4759.8749476263565, 2079.847317090144),
					new Vector2(5342.8749476263565, 2402.847317090144),
					new Vector2(3627.8749476263565, 2906.847317090144),
					new Vector2(3402.8749476263565, 2547.847317090144), };
			return SpawnLocations;
		} else if (MapName == "Pirates") {

			Vector2 SpawnLocations[] = {
					new Vector2(2354.354715233633, 2618.472692554285),
					new Vector2(2464.354715233633, 2167.472692554285),
					new Vector2(2569.354715233633, 1700.472692554285),
					new Vector2(2991.354715233633, 1523.472692554285),
					new Vector2(3472.354715233633, 1446.472692554285),
					new Vector2(3350.354715233633, 1563.472692554285),
					new Vector2(3838.354715233633, 1737.472692554285),
					new Vector2(3708.354715233633, 1635.472692554285),
					new Vector2(4212.354715233633, 1812.472692554285),
					new Vector2(4041.354715233633, 2140.472692554285),
					new Vector2(3735.354715233633, 2289.472692554285),
					new Vector2(3474.354715233633, 2115.472692554285),
					new Vector2(3411.354715233633, 1877.472692554285),
					new Vector2(2830.354715233633, 2699.472692554285),
					new Vector2(3160.354715233633, 2814.472692554285),
					new Vector2(3334.354715233633, 2499.472692554285),
					new Vector2(3617.354715233633, 2728.472692554285),
					new Vector2(3846.354715233633, 2826.472692554285),
					new Vector2(4346.354715233633, 2542.472692554285),
					new Vector2(4494.354715233633, 2577.472692554285),
					new Vector2(4666.354715233633, 2275.472692554285),
					new Vector2(4837.354715233633, 2650.472692554285),
					new Vector2(4977.354715233633, 2916.472692554285),
					new Vector2(5096.354715233633, 2320.472692554285),
					new Vector2(4571.354715233633, 1886.472692554285),
					new Vector2(4741.354715233633, 1670.472692554285),
					new Vector2(4392.354715233633, 1478.472692554285),
					new Vector2(4183.354715233633, 1824.472692554285), };

			return SpawnLocations;
		} else if (MapName == "Ship") {
			Vector2 SpawnLocations[] = {
					new Vector2(3917.7393193496255, 1794.696499546532),
					new Vector2(4070.7393193496255, 1544.696499546532),
					new Vector2(4109.7393193496255, 1870.696499546532),
					new Vector2(4000.7393193496255, 1924.696499546532),
					new Vector2(4337.7393193496255, 1836.696499546532),
					new Vector2(4753.7393193496255, 1830.696499546532),
					new Vector2(4681.7393193496255, 1931.696499546532),
					new Vector2(4856.7393193496255, 1925.696499546532),
					new Vector2(4652.7393193496255, 1402.696499546532),
					new Vector2(4166.7393193496255, 1376.696499546532),
					new Vector2(5047.7393193496255, 1802.696499546532),
					new Vector2(5095.7393193496255, 2291.6964995465323),
					new Vector2(4890.7393193496255, 2286.6964995465323),
					new Vector2(4133.7393193496255, 1527.696499546532),
					new Vector2(3794.7393193496255, 1309.696499546532),
					new Vector2(3655.7393193496255, 1324.696499546532),
					new Vector2(3387.7393193496255, 1770.696499546532),
					new Vector2(3576.7393193496255, 1788.696499546532),
					new Vector2(3283.7393193496255, 1311.696499546532),
					new Vector2(3118.7393193496255, 1321.696499546532),
					new Vector2(2957.7393193496255, 1531.696499546532),
					new Vector2(2900.7393193496255, 1625.696499546532),
					new Vector2(2627.7393193496255, 1544.696499546532),
					new Vector2(2649.7393193496255, 1882.696499546532),
					new Vector2(2553.7393193496255, 1761.696499546532),
					new Vector2(2328.7393193496255, 1747.696499546532), };

			return SpawnLocations;
		} else {
			return null;
		}
	}
}