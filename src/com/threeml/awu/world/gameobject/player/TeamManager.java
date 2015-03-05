package com.threeml.awu.world.gameobject.player;

import java.util.ArrayList;
import java.util.List;

import com.threeml.awu.Game;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.gameobject.map.Map;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Class to handle Player Teams
 * Only built to handle two teams currently 
 * 
 * Could be enhanced to be scalable, but not currently a function
 * 
 * @author Mary-Jane
 *
 */
public class TeamManager {
	
	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////

	/** List containing both teams */
	private List<Team> mTeams;
	/** The current playing team */
	private Team mActiveTeam;
	/** The current Player the user interacts with */
	private Player mActivePlayer;
	
	
	//TESTING PURPOSES ONLY
	private String [] villains = new String [] {"Cyberman", "Dalek", "The Master", "Weeping Angel", "The Silence"};
	private String [] heroes = new String [] {"The Doctor", "Amy", "Jack", "Rose", "Clara"};
	private String [] threeml = new String [] {"MayJay", "Mark", "Dean", "Lisa", "Jade"};
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * Creates new Team Manager object
	 * 
	 * @param teamOne
	 * 				First team
	 * @param teamTwo
	 * 				Second team
	 */
	public TeamManager(Team teamOne, Team teamTwo){
		
		mTeams = new ArrayList<Team>();
		mTeams.add(teamOne);
		mTeams.add(teamTwo);
		
		//TODO MJ FIX THIS
		mActiveTeam = mTeams.get(0);
		mActivePlayer = mTeams.get(0).getPlayers().get(0);
	}
	
	/**
	 * Empty constructor
	 * 
	 * Initialises list of teams
	 */
	public TeamManager(){
		mTeams = new ArrayList<Team>();
	}
	
	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * Changes the currently active team to be the currently inactive team
	 * Should only be called within changeActivePlayer method
	 */
	private void changeActiveTeam(){
		try{
			if(mTeams.indexOf(mActiveTeam) < (mTeams.size() - 1)){
				mActiveTeam = mTeams.get(mTeams.indexOf(mActiveTeam) + 1);
			}
			else {
				mActiveTeam = mTeams.get(0);
			}
		}catch(Exception e){
			Log.e("TeamError", "Error in TeamManager changeActiveTeam : " + e);
			mActiveTeam = mTeams.get(0);
		}
	}
	
	/**
	 * Changes the currently active player
	 */
	public void changeActivePlayer(){
		try{
			changeActiveTeam();
			mActivePlayer = mActiveTeam.changeActivePlayer();
		}catch(Exception e){
			Log.e("TeamError", "Error in TeamManager changeActivePlayer : " + e);
		}
	}
	
	/**
	 * Creates a new team and adds to the team manager
	 * 
	 * @param p
	 * 			List of players in team
	 * @param n
	 * 			Name of team
	 */
	public void createNewTeam(List<Player> p, String n){
		mTeams.add(new Team(p, n));
	}
	
	/**
	 * Returns all players, except the current active player
	 * 
	 * @return
	 * 			All not active players
	 */
	public List<Player> getAllNotActivePlayers(){
		List<Player> players = new ArrayList<Player>();
		try {
			players.addAll(getAllPlayers());
			players.remove(mActivePlayer);
		}
		catch(Exception e){
			Log.e("TeamError", "Error in Team getAllNotActivePlayers : "
					+ e);
		}
		return players;
	}
	
	/**
	 * Returns all the players handled by the team manager
	 * 
	 * @return
	 * 			all players
	 */
	public List<Player> getAllPlayers(){
		List<Player> players = new ArrayList<Player>();
		try {
			for(int i = 0; i < mTeams.size(); i ++){
				players.addAll(mTeams.get(i).getPlayers());
			}
		}
		catch(Exception e){
			Log.e("TeamError", "Error in Team getAllPlayers : "
					+ e);
		}
		return players;
	}
	
	/**
	 * Creates a new team, with players, and adds to the team manager
	 * 
	 * @param n
	 * 			Name of team
	 * @param numPlayers
	 * 			Number of players to create
	 */
	public void createNewTeam(String n, int numPlayers, Map map, Game game, GameScreen gameScreen){
		try {
			List<Player> players = new ArrayList<Player>();
			for(int i = 0; i < numPlayers; i++){
				Vector2 spawnLocation = map.getUniqueSpawnLocation();
				players.add(new Player(spawnLocation.x, spawnLocation.y, 1,
						15,
						game.getAssetManager().getBitmap("PlayerWalk"),
						gameScreen, heroes[i]));
			}
			mTeams.add(new Team(players, n));
			if(mActivePlayer == null){
				mActiveTeam = mTeams.get(0);
				mActivePlayer = mTeams.get(0).getPlayers().get(0);
			}
		}
		catch(Exception e){
			Log.e("TeamError", "Error in TeamManager createNewTeam : " + e);
		}
	}
	//TODO MJ - added name arrays for testing purposes, need to remove later
	public void createTestNewTeam(String n, int numPlayers, Map map, Game game, GameScreen gameScreen){
		try {
			List<Player> players = new ArrayList<Player>();
			for(int i = 0; i < numPlayers; i++){
				Vector2 spawnLocation = map.getUniqueSpawnLocation();
				players.add(new Player(spawnLocation.x, spawnLocation.y, 1,
						15,
						game.getAssetManager().getBitmap("PlayerWalk"),
						gameScreen, villains[i]));
			}
			mTeams.add(new Team(players, n));
			if(mActivePlayer == null){
				mActiveTeam = mTeams.get(0);
				mActivePlayer = mTeams.get(0).getPlayers().get(0);
			}
		}
		catch(Exception e){
			Log.e("TeamError", "Error in TeamManager createNewTeam : " + e);
		}
	}
	public void createTestNewTeam2(String n, int numPlayers, Map map, Game game, GameScreen gameScreen){
		try {
			List<Player> players = new ArrayList<Player>();
			for(int i = 0; i < numPlayers; i++){
				Vector2 spawnLocation = map.getUniqueSpawnLocation();
				players.add(new Player(spawnLocation.x, spawnLocation.y, 1,
						15,
						game.getAssetManager().getBitmap("PlayerWalk"),
						gameScreen, threeml[i]));
			}
			mTeams.add(new Team(players, n));
			if(mActivePlayer == null){
				mActiveTeam = mTeams.get(0);
				mActivePlayer = mTeams.get(0).getPlayers().get(0);
			}
		}
		catch(Exception e){
			Log.e("TeamError", "Error in TeamManager createNewTeam : " + e);
		}
	}
	
	/**
	 * Creates a new player using given arguments
	 * 
	 * @param teamIndex
	 * 				index of the team in the team list
	 * @param startX
	 *            	X location of the player
	 * @param startY
	 *            	Y location of the player
	 * @param columns
	 * 				No. of columns in the bitmap spritesheet
	 * @param rows
	 * 				No. of rows in the bitmap spritesheet
	 * @param bitmap
	 * 				Bitmap image used to represent the player
	 * @param gameScreen
	 *            Gamescreen to which player belongs
	 */
	public void createNewPlayer(int teamIndex, float startX, float startY, int columns, int rows, Bitmap bitmap, GameScreen gameScreen){
		mTeams.get(teamIndex).addNewPlayer(new Player(startX, startY, columns, rows, bitmap, gameScreen, "Default"));
	}
	
	/**
	 * Returns the active player
	 * 
	 * @return
	 * 			Active Player
	 */
	public Player getActivePlayer(){
		return mActivePlayer;
	}
	
	public Team getTeam(int index){
		try {
			return mTeams.get(index);
		} catch (Exception e){
			Log.e("Error", "TeamManager getTeam : " + e);
			return mTeams.get(0);
		}
	}
	public List<Team> getTeams(){
		return mTeams;
	}
}
