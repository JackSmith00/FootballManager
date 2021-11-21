package events;

import java.util.ArrayList;
import java.util.Date;

import leagueComponents.Player;
import leagueComponents.Team;

public class Result {

	// Attributes
	private Team homeTeam;
	private int homeScore;
	private Team awayTeam;
	private int awayScore;
	private Date datePlayed;
	private ArrayList<GameEvent> goalsScored;
	private ArrayList<Substitution> substitutions;
	private ArrayList<GameEvent> cardsGiven;
	private Player[] homePlayers;
	private Player[] awayPlayers;
	public Team getHomeTeam() {
		return homeTeam;
	}
	
	// Methods
	// Getters
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
	public ArrayList<GameEvent> getGoalsScored() {
		return goalsScored;
	}
	public ArrayList<Substitution> getSubstitutions() {
		return substitutions;
	}
	public ArrayList<GameEvent> getCardsGiven() {
		return cardsGiven;
	}
	public Player[] getHomePlayers() {
		return homePlayers;
	}
	public Player[] getAwayPlayers() {
		return awayPlayers;
	}
	
}
