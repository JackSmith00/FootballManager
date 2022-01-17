package leagueComponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import events.Result;
import interfaces.HasResults;
import interfaces.StatisticsCalculator;

/**
 * A class to represent a football team in a league
 * 
 * @author Jack
 */
public class Team implements Serializable, StatisticsCalculator, HasResults, Comparable<Team> {
	
	// Attributes
	private String teamName;
	private Stadium homeGround;
	private LinkedList<Result> results = new LinkedList<Result>(); // Initially no games played
	private int gamesWon = 0; // Initially no games won
	private int gamesLost = 0; // Initially no games lost
	private int gamesDrew = 0; // Initially no games drew
	private int points = 0; // Initially no points
	private int goalsConceded = 0;
	private int cleanSheets = 0;
	private int leaguePosition;
	private ArrayList<Player> players = new ArrayList<Player>(16); // Initial empty ArrayList to hold players
	private ArrayList<CoachingStaffMember> coachingStaff = new ArrayList<CoachingStaffMember>(6); // Initial empty ArrayList to hold coaching staff
	private ArrayList<Referee> referees = new ArrayList<Referee>(4); // Initial empty ArrayList to hold referees
	
	/**
	 * Allows a new team to be created
	 * @param teamName : The name of the Team
	 * @param homeGround : The home stadium of the team
	 */
	public Team(String teamName, Stadium homeGround) {
		this.teamName = teamName;
		this.homeGround = homeGround;
	}
	
	// Methods
	/**
	 * Allows a player to be added to the team
	 * @param p : The player to add to the team
	 */
	public void addPlayer(Player p) {
		players.add(p); // add player to the team players list
		p.setTeam(this); // set players team to this team
	}
	
	/**
	 * Allows a player to be removed from the team
	 * @param p : The player to remove from the team
	 */
	public void removePlayer(Player p) {
		players.remove(p);
	}
	
	/**
	 * Allows a member of coaching staff to be added to the team
	 * @param cs : The coaching staff member to add to the team
	 */
	public void addCoachingStaffMember(CoachingStaffMember cs) {
		coachingStaff.add(cs); // add player to the team players list
		cs.setTeam(this); // set players team to this team
	}
	
	/**
	 * Allows a member of coaching staff to be removed from the team
	 * @param cs : The coaching staff member to removed from the team
	 */
	public void removeCoachingStaffMember(CoachingStaffMember cs) {
		coachingStaff.remove(cs);
	}
	
	/**
	 * Allows a referee to be added to the team
	 * @param r : The referee to add to the team
	 */
	public void addReferee(Referee r) {
		referees.add(r); // add player to the team players list
		r.setTeam(this); // set players team to this team
	}
	
	/**
	 * Allows a referee to be removed from the team
	 * @param r : The referee to removed from the team
	 */
	public void removeReferee(Referee r) {
		referees.remove(r);
	}
	
	/**
	 * Adds a new result to the teams ArrayList of results: `results`
	 * 
	 * @param r : The result to add to the `results` collection
	 */
	public void addResult(Result r) {
		results.add(r);
		if(r.getHomeTeam() == this) { // if this team is the home team, add away goals to this teams conceded
			addGoalsConceded(r.getAwayScore());
		} else { // this team is the away team so add home goals to this teams conceded
			addGoalsConceded(r.getHomeScore());
		}
		if(r.getWinner() == this) {
			addWin();
		} else if(r.getWinner() == null) {
			addDraw();
		} else {
			addLoss();
		}
	}
	
	/**
	 * Called when a game is won to increase the number
	 * of games won by 1 and to add 3 points to the team
	 */
	private void addWin() {
		gamesWon++;
		points += 3;
	}
	
	/**
	 * Called when a game is drew to increase the number
	 * of games drew by 1 and to add 1 point to the team
	 */
	private void addDraw() {
		gamesDrew++;
		points += 1;
	}
	
	/**
	 * Called when a game is lost to increase the number
	 * of games lost by 1
	 */
	private void addLoss() {
		gamesLost++;
	}
	
	/**
	 * Used to add any goals scored by the opposing team
	 * to this teams conceded record.
	 * 
	 * Also will increase the number of clean sheets if
	 * no goals are conceded.
	 * 
	 * @param numberConceded the number of goals scored by the opposing team
	 */
	private void addGoalsConceded(int numberConceded) {
		if(numberConceded > 0) {
			goalsConceded += numberConceded;
		} else {
			cleanSheets++;
		}
	}
	
	// Getters
	
	/**
	 * @return the team name
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @return the home stadium of the team
	 */
	public Stadium getHomeGround() {
		return homeGround;
	}

	/**
	 * @return all results associated with this team
	 */
	public LinkedList<Result> getResults() {
		return results;
	}

	/**
	 * @return the number of games won by the team
	 */
	public int getGamesWon() {
		return gamesWon;
	}

	/**
	 * @return the number of games lost by the team
	 */
	public int getGamesLost() {
		return gamesLost;
	}

	/**
	 * @return the number of games drew by the team
	 */
	public int getGamesDrew() {
		return gamesDrew;
	}
	
	/**
	 * @return the number of points earned by the team
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * @return the number of goals conceded by the team
	 */
	public int getGoalsConceded() {
		return goalsConceded;
	}
	
	/**
	 * @return the number of games where the team did not concede any goals
	 */
	public int getCleanSheets() {
		return cleanSheets;
	}
	
	/**
	 * The difference between goals scored and goals conceded, where a
	 * negative number indicates more goals have been conceded than scored
	 * 
	 * @return Difference between goals scored and goals conceded
	 */
	public int getGoalDifference() {
		return totalGoalsScored() - goalsConceded;
	}

	/**
	 * @return Current position of the team within the league
	 */
	public int getLeaguePosition() {
		return leaguePosition;
	}

	/**
	 * @return An ArrayList of players who play for the team
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @return An ArrayList of coaching staff members who work for the team
	 */
	public ArrayList<CoachingStaffMember> getCoachingStaff() {
		return coachingStaff;
	}

	/**
	 * @return An ArrayList of referees who work for the team
	 */
	public ArrayList<Referee> getReferees() {
		return referees;
	}
	
	// Setters
	
	/**
	 * Allows the league position of the team to be updated
	 * @param leaguePosition the new position of the team in the league
	 */
	public void setLeaguePosition(int leaguePosition) {
		this.leaguePosition = leaguePosition;
	}

	@Override
	public Player topGoalScorer() {
		if(getPlayers().size() == 0) {			
			return null;
		} else {
			Player topGoalScorer = players.get(0);
			for(Player player: players) {
				if (player.getGoalsScored() > topGoalScorer.getGoalsScored()) {
					topGoalScorer = player;
				}
			}
			return topGoalScorer;
		}
	}

	@Override
	public Player topAssister() {
		if(getPlayers().size() == 0) {			
			return null;
		} else {
			Player topAssister = players.get(0);
			for(Player player: players) {
				if (player.getAssistsMade() > topAssister.getAssistsMade()) {
					topAssister = player;
				}
			}
			return topAssister;
		}
	}

	@Override
	public int totalGoalsScored() {
		int totalGoalsScored = 0;
		for(Player player : players) {
			totalGoalsScored += player.getGoalsScored();
		}
		return totalGoalsScored;
	}

	@Override
	public int totalCardsGiven() {
		int totalCardsGiven = 0;
		for(Player player : players) {
			totalCardsGiven += player.getCardsGiven();
		}
		return totalCardsGiven;
	}
	
	@Override
	public String toString() {
		return teamName;
	}

	@Override
	public int compareTo(Team o) {
		if(o.points != points) { // if points are different, sort on points
			return o.points - points;
		} else if(o.getGoalDifference() != getGoalDifference()) { // if points are the same, sort on goal difference
			return o.getGoalDifference() - getGoalDifference();
		} else { // if points and goal difference are equal, sort alphabetically
			return teamName.compareTo(o.teamName);
		}
	}
	
}
