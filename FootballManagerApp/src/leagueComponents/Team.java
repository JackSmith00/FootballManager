package leagueComponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import events.Result;

/**
 * A class to represent a football team in a league
 * 
 * @author Jack
 */
public class Team implements Serializable {
	
	// Attributes
	private String teamName;
	private Stadium homeGround;
	private LinkedList<Result> results = new LinkedList<Result>(); // Initially no games played
	private int gamesWon = 0; // Initially no games won
	private int gamesLost = 0; // Initially no games lost
	private int gamesDrew = 0; // Initially no games drew
	private int goalDifference = 0; // Initially no difference in goals scored/conceded
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
	 * Allows a member of coaching staff to be added to the team
	 * @param p : The coaching staff member to add to the team
	 */
	public void addCoachingStaffMember(CoachingStaffMember cs) {
		coachingStaff.add(cs); // add player to the team players list
		cs.setTeam(this); // set players team to this team
	}
	
	/**
	 * Allows a referee to be added to the team
	 * @param p : The referee to add to the team
	 */
	public void addReferee(Referee r) {
		referees.add(r); // add player to the team players list
		r.setTeam(this); // set players team to this team
	}
	
	/**
	 * Updates the league position value of the team
	 */
	public void updateLeaguePosition() {
		// unimplemented
	}
	
	/**
	 * Adds a new result to the teams ArrayList of results: `results`
	 * 
	 * @param r : The result to add to the `results` collection
	 */
	public void addResult(Result r) {
		results.add(r);
	}
	
	// Getters
	
	/**
	 * @return The team name
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @return The home stadium of the team
	 */
	public Stadium getHomeGround() {
		return homeGround;
	}

	/**
	 * @return All results associated with this team
	 */
	public LinkedList<Result> getResults() {
		return results;
	}

	/**
	 * @return Number of games won by the team
	 */
	public int getGamesWon() {
		return gamesWon;
	}

	/**
	 * @return Number of games lost by the team
	 */
	public int getGamesLost() {
		return gamesLost;
	}

	/**
	 * @return Number of games drew by the team
	 */
	public int getGamesDrew() {
		return gamesDrew;
	}
	
	/**
	 * The difference between goals scored and goals conceded, where a
	 * negative number indicates more goals have been conceded than scored
	 * 
	 * @return Difference between goals scored and goals conceded
	 */
	public int getGoalDifference() {
		return goalDifference;
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
	
}
