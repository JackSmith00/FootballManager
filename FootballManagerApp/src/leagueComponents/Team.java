package leagueComponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A class to represent a football team in a league
 */
public class Team implements Serializable {
	
	// Attributes
	private String teamName;
	private Stadium homeGround;
	private LinkedList results;
	private int gamesWon;
	private int gamesLost;
	private int gamesDrew;
	private int leaguePosition;
	private ArrayList<Player> players = new ArrayList<Player>(16);
	private ArrayList<CoachingStaffMember> coachingStaff = new ArrayList<CoachingStaffMember>(6);
	private ArrayList<Referee> referees = new ArrayList<Referee>(4);
	
	// Constructor
	public Team(String teamName, Stadium homeGround) {
		this.teamName = teamName;
		this.homeGround = homeGround;
	}
	
	// Methods
	public void addPlayer(Player p) {
		players.add(p); // add player to the team players list
		p.setTeam(this); // set players team to this team
	}
	
	public void addCoachingStaffMember(CoachingStaffMember cs) {
		coachingStaff.add(cs); // add player to the team players list
		cs.setTeam(this); // set players team to this team
	}
	
	public void addReferee(Referee r) {
		referees.add(r); // add player to the team players list
		r.setTeam(this); // set players team to this team
	}
	
	public void updateLeaguePosition() {
		// unimplemented
	}
	
	// Getters
	
	public String getTeamName() {
		return teamName;
	}

	public Stadium getHomeGround() {
		return homeGround;
	}

	public LinkedList getResults() {
		return results;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public int getGamesLost() {
		return gamesLost;
	}

	public int getGamesDrew() {
		return gamesDrew;
	}

	public int getLeaguePosition() {
		return leaguePosition;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<CoachingStaffMember> getCoachingStaff() {
		return coachingStaff;
	}

	public ArrayList<Referee> getReferees() {
		return referees;
	}
	
}
