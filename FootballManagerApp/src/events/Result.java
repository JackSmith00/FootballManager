package events;

import java.io.Serializable;
import java.util.Date;

import leagueComponents.Player;
import leagueComponents.Team;

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
	
	// Constructor
	public Result(Team homeTeam, int homeScore, Team awayTeam, int awayScore, Date datePlayed,
			GameEvent[] events, Player[] homePlayers, Player[] awayPlayers) {
		super();
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
	// Getters
	public Team getHomeTeam() {
		return homeTeam;
	}
	public int getHomeScore() {
		return homeScore;
	}
	public Team getAwayTeam() {
		return awayTeam;
	}
	public int getAwayScore() {
		return awayScore;
	}
	public Date getDatePlayed() {
		return datePlayed;
	}
	public GameEvent[] getEvents() {
		return events;
	}
	public Player[] getHomePlayers() {
		return homePlayers;
	}
	public Player[] getAwayPlayers() {
		return awayPlayers;
	}
	
}
