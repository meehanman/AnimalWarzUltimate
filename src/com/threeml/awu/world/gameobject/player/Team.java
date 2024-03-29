package com.threeml.awu.world.gameobject.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	/** Teams Colour **/
	private String mTeamColour;
	
	/** Possible Team Colours */
	private static String[] colours = {"Blue","Pink","Purple","Red","White"};

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
		mTeamColour = setRandomTeamColour();
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the value of all the health of all the players in the team
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
					if (getAlivePlayers() != null
							&& getAlivePlayers().size() > 0) {
						mLastActivePlayer = mPlayers.get(mPlayers
								.indexOf(mLastActivePlayer) + 1);
					} else {
						return null;
					}
				} else {
					mLastActivePlayer = mPlayers.get(0);
				}
			} catch (Exception e) {
				Log.e("TeamError", "Error in Team changeActivePlayer : " + e);
			}
		}
		return mLastActivePlayer;
	}

	/**
	 * Get all the Players whose Alive status is true
	 * 
	 * @return list of alive players
	 */
	public List<Player> getAlivePlayers() {
		List<Player> players = new ArrayList<Player>();
		for (Player p : mPlayers) {
			if (p.isAlive()) {
				players.add(p);
			}
		}
		return players;
	}

	/**
	 * Get if the team contains any Alive Players
	 * 
	 * @return if team has any Alive Players
	 */
	public boolean hasAlivePlayers() {
		return (getAlivePlayers().size() > 0);
	}

	/**
	 * Adds a new player to the team
	 * 
	 * @param p
	 *            Player
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

	/**
	 * @return Gets the team colour
	 * 
	 * @author Dean
	 */
	public String getTeamColour() {
		return mTeamColour;
	}
	
	/**
	 * Returns a random color
	 * @author Dean
	 */
	public static String setRandomTeamColour(){
		
		//Get a random item from array
		int idx = new Random().nextInt(colours.length);
		String randColour = (colours[idx]);
		//Loop
		while(true){
			//If the colours white, got for it
			if(randColour=="White"){
				break;
			//If the color is 
			}
			
			//If its blank get another colour
			if(randColour==""){
				idx = new Random().nextInt(colours.length);
				randColour = (colours[idx]);
			}else{
				colours[idx]="";
				break;
			}
		}
		//Return a random team colour
		return randColour;
		
	}
	public static void resetColours(){
		colours[0] = "Blue";
		colours[1] = "Pink";
		colours[2] = "Purple";
		colours[3] = "Red";
		colours[4] = "White";
	}

}
