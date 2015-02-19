package com.threeml.awu.world.gameobject.player;

import java.util.Iterator;
import java.util.List;

/**
 * Team object is used to hold all the players in a team
 * Use the team ID to distinguish between teams and set if team is currently
 * active using Active variable
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
	/** If active is true then it is the team the user interacts with */
	private boolean mActive = false;
	/** Team ID used to distinguish between teams */
	private int mId;
	/** The last active Player's ID */
	private int mActivePlayerId;
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * Creates a new Team object for managing players
	 * 
	 * @param players
	 * 				List of players in this team
	 * @param teamName
	 * 				The team name that is displayed
	 */
	public Team(List<Player> players, String teamName){
		mPlayers = players;
		mTeamName = teamName;
	}
	
	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
	public void nextActivePlayer(){
		if(mPlayers.indexOf(getActivePlayer()) < mPlayers.size()){
			setActivePlayerByIndex(mPlayers.indexOf(getActivePlayer()) + 1);
		}else {
			setActivePlayerByIndex(0);
		}
	}
	
	/**
	 * Returns the value of the all health of all players in the team
	 * 
	 * @return collectiveHealth
	 */
	public int getCollectiveHealth(){
		int collectiveHealth = 0;
		for(Player p : mPlayers){
			collectiveHealth += p.getHealth();
		}
		return collectiveHealth;
	}
	
	/**
	 * Set the active player using the player's ID
	 * 
	 * @param id
	 */
	public void setActivePlayerById(int id){
		for(Player p : mPlayers){
			if(p.getId() == id){
				p.setActive(true);
				mActivePlayerId = p.getId();
			} else{
				p.setActive(false);
			}
		}
	}
	
	/**
	 * Set the active player by it's index in the player's list
	 * 
	 * @param index
	 */
	public void setActivePlayerByIndex(int index){
		for(Player p : mPlayers){
			p.setActive(false);
		}
		mPlayers.get(index).setActive(true);
	}
	
	/**
	 * Get the active player in this team
	 * 
	 * @return player
	 */
	public Player getActivePlayer(){
		for(Player p : mPlayers){
			if(p.getActive()){
				return p;
			} else{
				return null;
			}
		}
		return null;
	}
	/**
	 * Gets the size of the list of players
	 * 
	 * @return teamSize
	 */
	public int getTeamSize(){
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
	public void setTeamName(String teamName) {
		mTeamName = teamName;
	}
	/**
	 * Gets if the team is currently active
	 * 
	 * @return active
	 */
	public boolean isActive() {
		return mActive;
	}
	/**
	 * Set if team is currently active
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		mActive = active;
	}
	/**
	 * Get the team ID
	 * 
	 * @return id
	 */
	public int getId() {
		return mId;
	}
	/**
	 * Set the team ID
	 * 
	 * @param id
	 */
	public void setId(int id) {
		mId = id;
	}
	
	
}
