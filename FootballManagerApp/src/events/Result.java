package events;

import java.io.Serializable;
import java.util.Date;

import leagueComponents.Player;
import leagueComponents.Team;

/**
 * Represents the results of a game of football.
 * 
 * Implements Serializable so it can be stored for future access.
 * 
 * @author Jack
 *
 */
public class Result implements Serializable {

	// Attributes
	private Team homeTeam; // the team that played at home
	private int homeScore; // the home team score
	private Team awayTeam; // the team that played away
	private int awayScore; // the away team score
	private Date datePlayed; // the date the game was played
	private GameEvent[] events; // an array of all events that took place during the game
	private Player[] homePlayers; // all the players who played for the home team
	private Player[] awayPlayers; // all the players who played for the away team
	
	/**
	 * Allows a result to be recorded
	 * 
	 * @param homeTeam the team playing at home
	 * @param homeScore number of goals scored by the home team
	 * @param awayTeam the team playing away
	 * @param awayScore number of goals scored by the away team
	 * @param datePlayed date the game took place
	 * @param events an array of events that occurred in the game
	 * @param homePlayers an array of players that appeared for the home team
	 * @param awayPlayers an array of players that appeared for the away team
	 */
	public Result(Team homeTeam, int homeScore, Team awayTeam, int awayScore, Date datePlayed,
			GameEvent[] events, Player[] homePlayers, Player[] awayPlayers) {
		this.homeTeam = homeTeam;
		this.homeScore = homeScore;
		this.awayTeam = awayTeam;
		this.awayScore = awayScore;
		this.datePlayed = datePlayed;
		this.events = events;
		this.homePlayers = homePlayers;
		this.awayPlayers = awayPlayers;
	}
	
	// Methods
	/**
	 * Formats the score of the result in the format 'homeScore-awayScore'.
	 * 
	 * @return a string representation of the score
	 */
	public String scoreString() {
		return homeScore + "-" + awayScore;
	}
	
	// Getters
	/**
	 * @return the home team in the game
	 */
	public Team getHomeTeam() {
		return homeTeam;
	}
	
	/**
	 * @return number of goals scored by the home team
	 */
	public int getHomeScore() {
		return homeScore;
	}
	
	/**
	 * @return the away team in the game
	 */
	public Team getAwayTeam() {
		return awayTeam;
	}
	
	/**
	 * @return number of goals scored by the away team
	 */
	public int getAwayScore() {
		return awayScore;
	}
	
	/**
	 * @return the date the game took place
	 */
	public Date getDatePlayed() {
		return datePlayed;
	}
	
	/**
	 * @return an array of events that took place in the game
	 */
	public GameEvent[] getEvents() {
		return events;
	}
	
	/**
	 * @return an array of players that played for the home team in the game
	 */
	public Player[] getHomePlayers() {
		return homePlayers;
	}
	
	/**
	 * @return an array of players that played for the away team in the game
	 */
	public Player[] getAwayPlayers() {
		return awayPlayers;
	}
	
	/**
	 * Calculates and returns the team that won the game,
	 * returns null if the game was a draw
	 * 
	 * @return the winning team or null if game was a draw
	 */
	public Team getWinner() {
		if(homeScore > awayScore) { // home win
			return homeTeam;
		} else if(awayScore > homeScore) { // away win
			return awayTeam;
		} else { // draw
			return null;
		}
	}
	
}
