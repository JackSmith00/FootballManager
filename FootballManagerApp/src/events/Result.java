package events;

import java.io.Serializable;
import java.util.Date;

import leagueComponents.Player;
import leagueComponents.Team;

/**
 * Represents the results of a game of football
 * @author Jack
 *
 */
public class Result implements Serializable {

	// Attributes
	private Team homeTeam;
	private int homeScore;
	private Team awayTeam;
	private int awayScore;
	private Date datePlayed;
	private GameEvent[] events;
	private Player[] homePlayers;
	private Player[] awayPlayers;
	
	/**
	 * Allows a result to be recorded
	 * 
	 * @param homeTeam : The team playing at home
	 * @param homeScore : Number of goals scored by the home team
	 * @param awayTeam : The team playing away
	 * @param awayScore : Number of goals scored by the away team
	 * @param datePlayed : Date the game took place
	 * @param events : An array of events that occurred in the game
	 * @param homePlayers : An array of players that appeared for the home team
	 * @param awayPlayers : An array of players that appeared for the away team
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
	 * Gets a string representation of the score of the game
	 * @return The home team in the game
	 */
	public String scoreString() {
		return homeScore + "-" + awayScore;
	}
	
	// Getters
	/**
	 * @return The home team in the game
	 */
	public Team getHomeTeam() {
		return homeTeam;
	}
	
	/**
	 * @return Number of goals scored by the home team
	 */
	public int getHomeScore() {
		return homeScore;
	}
	
	/**
	 * @return The away team in the game
	 */
	public Team getAwayTeam() {
		return awayTeam;
	}
	
	/**
	 * @return Number of goals scored by the away team
	 */
	public int getAwayScore() {
		return awayScore;
	}
	
	/**
	 * @return The date the game took place
	 */
	public Date getDatePlayed() {
		return datePlayed;
	}
	
	/**
	 * @return An array of events that took place in the game
	 */
	public GameEvent[] getEvents() {
		return events;
	}
	
	/**
	 * @return An array of players that played for the home team in the game
	 */
	public Player[] getHomePlayers() {
		return homePlayers;
	}
	
	/**
	 * @return An array of players that played for the away team in the game
	 */
	public Player[] getAwayPlayers() {
		return awayPlayers;
	}
	
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
