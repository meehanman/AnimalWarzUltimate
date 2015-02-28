package com.threeml.awu.world.gameobject.player;

import java.util.List;

import android.util.Log;

/**
 * Team object is used to hold all the players in a team Use the team ID to
 * distinguish between teams and set if team is currently active using Active
 * variable
 * 
 * @author Mary-Jane
 * 
 */
public class Team {
	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////

	/** List of players in this team */
	private List<Player> mPlayers;
	/** Team name that is displayed */
	private String mTeamName;
	/** Last player active in the team */
	private Player mLastActivePlayer;

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a new Team object for managing players
	 * 
	 * @param players
	 *            List of players in this team
	 * @param teamName
	 *            The team name that is displayed
	 */
	public Team(List<Player> players, String teamName) {
		mPlayers = players;
		mTeamName = teamName;
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the value of the all health of all players in the team
	 * 
	 * @return collectiveHealth
	 */
	public int getCollectiveHealth() {
		int collectiveHealth = 0;
		for (Player p : mPlayers) {
			collectiveHealth += p.getHealth();
		}
		return collectiveHealth;
	}

	/**
	 * Change the active player in the team
	 * 
	 * @return active player
	 */
	public Player changeActivePlayer() {
		if (mLastActivePlayer == null) {
			mLastActivePlayer = mPlayers.get(0);
		} else {
			try {
				if (mPlayers.indexOf(mLastActivePlayer) < (mPlayers.size() - 1)) {
					mLastActivePlayer = mPlayers.get(mPlayers
							.indexOf(mLastActivePlayer) + 1);
				} else {
					mLastActivePlayer = mPlayers.get(0);
				}
			} catch (Exception e) {
				Log.e("TeamError", "Error in Team changeActivePlayer : "
						+ e);
			}
		}
		return mLastActivePlayer;
	}
	
	/**
	 * Adds a new player to the team
	 * 
	 * @param p
	 * 			Player
	 */
	public void addNewPlayer(Player p) {
		mPlayers.add(p);
	}

	/**
	 * Gets the size of the list of players
	 * 
	 * @return teamSize
	 */
	public int size() {
		return mPlayers.size();
	}

	/**
	 * Get the list of players in this team
	 * 
	 * @return players
	 */
	public List<Player> getPlayers() {
		return mPlayers;
	}

	/**
	 * Set the list of players for this team
	 * 
	 * @param players
	 */
	public void setPlayers(List<Player> players) {
		mPlayers.addAll(players);
	}

	/**
	 * Get the team name
	 * 
	 * @return teamName
	 */
	public String getTeamName() {
		return mTeamName;
	}

	/**
	 * Set the team name
	 * 
	 * @param teamName
	 */
	public void setName(String teamName) {
		mTeamName = teamName;
	}

}
