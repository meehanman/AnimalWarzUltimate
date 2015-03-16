package com.threeml.awu.util;

import java.util.ArrayList;
import java.util.List;

import com.threeml.awu.Game;
import com.threeml.awu.util.Vector2;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.gameobject.map.Map;
import com.threeml.awu.world.gameobject.player.Player;
import com.threeml.awu.world.gameobject.player.Team;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Class to handle Player Teams Only built to handle two teams currently
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
	/** Stores the winning team, only initialised once the game has ended */
	private Team mWinningTeam;

	// TESTING PURPOSES ONLY
	private String[] villains = new String[] { "Cyberman", "Dalek",
			"The Master", "Weeping Angel", "The Silence", "Cyberman", "Dalek",
			"The Master", "Weeping Angel", "The Silence" };
	private String[] heroes = new String[] { "The Doctor", "Amy", "Jack",
			"Rose", "Clara", "The Doctor", "Amy", "Jack", "Rose", "Clara" };
	private String[] threeml = new String[] { "MayJay!", "Mark :D", "Dean :)",
			"Lisa *.*", "Jade", "MayJay", "Mark", "Dean", "Lisa", "Jade" };

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Creates new Team Manager object
	 * 
	 * @param teamOne
	 *            First team
	 * @param teamTwo
	 *            Second team
	 */
	public TeamManager(Team teamOne, Team teamTwo) {

		mTeams = new ArrayList<Team>();
		mTeams.add(teamOne);
		mTeams.add(teamTwo);
		mActiveTeam = mTeams.get(0);
		mActivePlayer = mTeams.get(0).getPlayers().get(0);
		mWinningTeam = null;
	}

	/**
	 * Empty constructor
	 * 
	 * Initialises list of teams
	 */
	public TeamManager() {
		mTeams = new ArrayList<Team>();
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Changes the currently active team to be the currently inactive team
	 * Should only be called within changeActivePlayer method
	 */
	private void changeActiveTeam() {
		try {
			if (mTeams.indexOf(mActiveTeam) < (mTeams.size() - 1)) {
				mActiveTeam = mTeams.get(mTeams.indexOf(mActiveTeam) + 1);
			} else {
				mActiveTeam = mTeams.get(0);
			}
		} catch (Exception e) {
			Log.e("TeamError", "Error in TeamManager changeActiveTeam : " + e);
			mActiveTeam = mTeams.get(0);
		}
	}

	/**
	 * Changes the currently active player
	 */
	public void changeActivePlayer() {
		try {
			changeActiveTeam();
			mActivePlayer = mActiveTeam.changeActivePlayer();
		} catch (Exception e) {
			Log.e("TeamError", "Error in TeamManager changeActivePlayer : " + e);
		}
	}

	/**
	 * Creates a new team and adds to the team manager
	 * 
	 * @param p
	 *            List of players in team
	 * @param n
	 *            Name of team
	 */
	public void createNewTeam(List<Player> p, String n) {
		mTeams.add(new Team(p, n));
	}

	/**
	 * Returns all players, except the current active player
	 * 
	 * @return All not active players
	 */
	public List<Player> getAllNotActivePlayers() {
		List<Player> players = new ArrayList<Player>();
		try {
			players.addAll(getAllPlayers());
			players.remove(mActivePlayer);
		} catch (Exception e) {
			Log.e("TeamError", "Error in Team getAllNotActivePlayers : " + e);
		}
		return players;
	}

	/**
	 * Returns all the players handled by the team manager
	 * 
	 * @return all players
	 */
	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<Player>();
		try {
			for (int i = 0; i < mTeams.size(); i++) {
				players.addAll(mTeams.get(i).getPlayers());
			}
		} catch (Exception e) {
			Log.e("TeamError", "Error in Team getAllPlayers : " + e);
		}
		return players;
	}

	/**
	 * Creates a new team, with players, and adds to the team manager
	 * 
	 * @param n
	 *            Name of team
	 * @param numPlayers
	 *            Number of players to create
	 */
	public void createNewTeam(String n, int numPlayers, Map map, Game game,
			GameScreen gameScreen) {
		try {
			List<Player> players = new ArrayList<Player>();
			for (int i = 0; i < numPlayers; i++) {
				Vector2 spawnLocation = map.getUniqueSpawnLocation();
				players.add(new Player(spawnLocation.x, spawnLocation.y, 1, 15,
						game.getAssetManager().getBitmap("PlayerWalk"),
						gameScreen, threeml[i]));
			}
			mTeams.add(new Team(players, n));
			mActiveTeam = mTeams.get(0);
			mActivePlayer = mTeams.get(0).getPlayers().get(0);
		} catch (Exception e) {
			Log.e("TeamError", "Error in TeamManager createNewTeam : " + e);
		}
	}

	/**
	 * Creates a new player using given arguments
	 * 
	 * @param teamIndex
	 *            index of the team in the team list
	 * @param startX
	 *            X location of the player
	 * @param startY
	 *            Y location of the player
	 * @param columns
	 *            No. of columns in the bitmap spritesheet
	 * @param rows
	 *            No. of rows in the bitmap spritesheet
	 * @param bitmap
	 *            Bitmap image used to represent the player
	 * @param gameScreen
	 *            Gamescreen to which player belongs
	 */
	public void createNewPlayer(int teamIndex, float startX, float startY,
			int columns, int rows, Bitmap bitmap, GameScreen gameScreen) {
		mTeams.get(teamIndex).addNewPlayer(
				new Player(startX, startY, columns, rows, bitmap, gameScreen,
						"Default"));
	}

	/**
	 * Gets the teams with Players with Alive status
	 * 
	 * @return playable teams
	 */
	public List<Team> getTeamsWithAlivePlayers() {
		List<Team> teams = new ArrayList<Team>();
		for (Team t : mTeams) {
			if (t.hasAlivePlayers()) {
				teams.add(t);
			}
		}
		return teams;
	}

	/**
	 * Determines if the game is over by counting the number of teams with alive
	 * players remaining
	 * 
	 * @return if the game contains playable teams
	 */
	public boolean hasPlayableTeams() {
		Log.v("PlayerDeath", "No. of playable teams : "
				+ getTeamsWithAlivePlayers().size() + "");
		if (getTeamsWithAlivePlayers().size() > 1) {
			return true;
		} else {
			mWinningTeam = getTeamsWithAlivePlayers().get(0);
			return false;
		}
	}

	/**
	 * Returns the winning team, if no winning team then returns null
	 * 
	 * @return winning team
	 */
	public Team getWinningTeam() {
		return mWinningTeam;
	}

	/**
	 * Returns the active player
	 * 
	 * @return Active Player
	 */
	public Player getActivePlayer() {
		return mActivePlayer;
	}

	/**
	 * Returns a team from the list of teams by its index, if it doesn't exist
	 * then method returns the first team in the list
	 * 
	 * @param index
	 *            Index of team in list of teams
	 * @return Team
	 */
	public Team getTeam(int index) {
		try {
			return mTeams.get(index);
		} catch (Exception e) {
			Log.e("Error", "TeamManager getTeam : " + e);
			return mTeams.get(0);
		}
	}

	/**
	 * Returns a list of all teams
	 * 
	 * @return list of teams
	 */
	public List<Team> getTeams() {
		return mTeams;
	}

	/**
	 * Returns the currently active team
	 * 
	 * @return active team
	 */
	public Team getActiveTeam() {
		return mActiveTeam;
	}
}
