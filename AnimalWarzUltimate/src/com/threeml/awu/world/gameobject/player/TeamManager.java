package com.threeml.awu.world.gameobject.player;

import java.util.ArrayList;
import java.util.List;

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
	
	/** First team */
	private Team mTeamOne;
	/** Second team */
	private Team mTeamTwo;
	/** Winning team id */
	private int mWinningTeamId = -1;
	/** List containing both teams */
	private List<Team> mTeams;
	
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
		mTeamOne = teamOne;
		mTeamOne.setId(1);
		mTeamTwo = teamTwo;
		mTeamTwo.setId(2);
		
		mTeams = new ArrayList<Team>();
		mTeams.add(mTeamOne);
		mTeams.add(mTeamTwo);
		
		setFirstActivePlayer();
	}
	
	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * Gets the currently active team
	 * 
	 * @return active team
	 */
	public Team getActiveTeam(){
		try{
			if(mTeamOne.isActive()){
				return mTeamOne;
			}else if(mTeamTwo.isActive()){
				return mTeamTwo;
			}
		}catch(Exception e){
			Log.v("Error", e + "Error in TeamManager getActiveTeam");
			mTeamOne.setActive(true);
			mTeamTwo.setActive(false);
		}
		return mTeamOne;
	}
	
	/**
	 * Changes the currently active team to be the currently inactive team
	 */
	public void changeActiveTeam(){
		try{
			switch(getActiveTeam().getId()){
			case 1:
				mTeamOne.setActive(false);
				mTeamTwo.setActive(true);
				break;
			case 2:
				mTeamOne.setActive(true);
				mTeamTwo.setActive(false);
				break;
			default:
				mTeamOne.setActive(true);
				mTeamTwo.setActive(false);
			}
		}catch(Exception e){
			Log.v("Error", e + "Error in TeamManager changeActiveTeam");
		}
	}
	
	public Player getActivePlayerFromCurrentActiveTeam(){
		return getActiveTeam().getActivePlayer();
	}

	/**
	 * Checks if the game is over by checking the teams' collective health
	 * Selects a winning team by whichever doesn't have 0 health or chooses a draw
	 * if both teams have 0 health
	 * @return game over
	 */
	public boolean checkIfGameOverAndFindWinningTeam(){
		if(mTeamOne.getCollectiveHealth() <= 0 && mTeamTwo.getCollectiveHealth() > 0){
			mWinningTeamId = mTeamOne.getId();
			return true;
		} else if(mTeamTwo.getCollectiveHealth() <= 0 && mTeamOne.getCollectiveHealth() > 0){
			mWinningTeamId = mTeamOne.getId();
			return true;
		} else if (mTeamOne.getCollectiveHealth() <= 0 && mTeamTwo.getCollectiveHealth() <= 0){
			mWinningTeamId = 0;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Changes the active team to the other team and select the next
	 * active player
	 */
	public void changeActiveTeamAndPlayer(){
		//Log.v("changeActiveTeamAndPlayer", "TeamManager changeActiveTeamAndPlayer called");
		//Log.v("changeActiveTeamAndPlayer", "Active Team : " + getActiveTeam().getTeamName() + " Active Player " + getActiveTeam().getActivePlayer().getId());
		changeActiveTeam();
		getActiveTeam().nextActivePlayer();
		//Log.v("changeActiveTeamAndPlayer", "TeamManager changeActiveTeamAndPlayer completed");
	}
	
	/**
	 * Gets the winning team by id, if 0 returned then game was a draw
	 * @return
	 */
	public int getWinningTeamId(){
		if(mWinningTeamId > 0){
			return getTeamById(mWinningTeamId).getId();
		} else {
			return mWinningTeamId;
		}
	}
	
	/**
	 * Gets a team by it's id - id should be 1 or 2. If team doesn't exist,
	 * null is returned
	 * @param id
	 * @return id or null if team does not exist
	 */
	public Team getTeamById(int id){
		if(id == mTeamOne.getId()){
			return mTeamOne;
		} else if(id == mTeamOne.getId()){
			return mTeamTwo;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a list containing both teams
	 * 
	 * @return teams
	 */
	public List<Team> getAllTeams(){
		return mTeams;
	}
	
	public List<Player> getAllPlayers(){
		List<Player> players = new ArrayList<Player>();
		for(Team t : getAllTeams()){
			players.addAll(t.getPlayers());
		}
		return players;
	}
	
	/**
	 * Get the first team
	 * 
	 * @return team 1
	 */
	public Team getTeamOne() {
		return mTeamOne;
	}
	/**
	 * Set the first team
	 * 
	 * @param teamOne
	 */
	public void setTeamOne(Team teamOne) {
		mTeamOne = teamOne;
	}
	/**
	 * Get the second team
	 * 
	 * @return team 2
	 */
	public Team getTeamTwo() {
		return mTeamTwo;
	}
	/**
	 * Set the second team
	 * 
	 * @param teamTwo
	 */
	public void setTeamTwo(Team teamTwo) {
		mTeamTwo = teamTwo;
	}
	
	public void setFirstActivePlayer(){
		mTeamOne.setActivePlayerByIndex(0);
	}
	
	public void createNewPlayerAndAddToTeam(int teamId, Player p){
		if(mTeamOne.getId() == teamId){
			mTeamOne.addNewPlayer(p);
		}
	}
}
