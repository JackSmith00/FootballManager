package leagueComponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

import events.GameEvent;
import events.Result;
import events.TwoPlayerGameEvent;
import interfaces.HasResults;
import interfaces.Reader;
import interfaces.Saveable;
import interfaces.StatisticsCalculator;

/**
 * A class to represent a football league, consisting of teams and their results
 * @author Jack
 *
 */
public class League implements Serializable, Saveable, Reader, StatisticsCalculator, HasResults {

	// Attributes
	private String name; // Name of the football league
	private TreeSet<Team> teams = new TreeSet<Team>(); // Initialised as an empty tree set by default so teams can be added
	private LinkedList<Result> results = new LinkedList<Result>();
	
	// Constructors
	/**
	 * An empty constructor to create a new league that can have values
	 * loaded to it from a saved file
	 */
	public League() { }
	
	/**
	 * Constructor for a new league for when there is no league saved
	 * @param name : The name of the football league
	 */
	public League(String name) {
		this.name = name; // Sets the league name to the name passed in
	}
	
	// Methods
	/**
	 * Adds a team to the ArrayList of teams in the league
	 * @param team : The team to be added to the league
	 */
	public void addTeam(Team team) {
		teams.add(team);
	}
	
	/**
	 * Adds a result to the ArrayList of results in the league
	 * @param team : The result to be added to the league
	 */
	public void addResult(Result result) {
		results.add(result);
		result.getHomeTeam().addResult(result);
		result.getAwayTeam().addResult(result);
		for(GameEvent event: result.getEvents()) {
			switch(event.getType()) {
			case GOAL:
				event.getPlayer().addGoal();
				((TwoPlayerGameEvent) event).getSecondPlayer().addAssist();
				break;
			case CARD:
				event.getPlayer().addCard();
				break;
			default:
				// do nothing, players keep no record of their substitutions
				break;
			}
		}
	}
	
	@Override
	public void save(String path) throws FileNotFoundException, IOException {
		FileOutputStream fileStream = new FileOutputStream(path);
		ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
		objectStream.writeObject(this);
		objectStream.close();
	}
	
	@Override
	public void load(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream fileStream = new FileInputStream(path);
		ObjectInputStream objectStream = new ObjectInputStream(fileStream);
		League tempLeague = (League) objectStream.readObject();
		objectStream.close();
		
		// Copy attributes
		this.name = tempLeague.getName();
		this.teams = tempLeague.getTeams();
		this.results = tempLeague.getResults();
	}
	
	// Getters
	/**
	 * @return The name of the football league
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return An ArrayList of teams in the league
	 */
	public TreeSet<Team> getTeams() {
		return teams;
	}
	
	/**
	 * @return An ArrayList of results in the league
	 */
	public LinkedList<Result> getResults() {
		return results;
	}

	@Override
	public Player topGoalScorer() {
		if(getTeams().size() == 0) { // check that there are teams before accessing their players		
			return null;
		} else { // there are teams in the league
			Player topGoalScorer = new Player("", null, null, null, 0); // initial blank Player
			for(Team team: teams) {
				if(team.getPlayers().isEmpty()) {
					continue; // skip any teams that have no players yet
				}
				if(team.topGoalScorer().getGoalsScored() >= topGoalScorer.getGoalsScored()) {
					topGoalScorer = team.topGoalScorer();
				}
			}
			return topGoalScorer;
		}
	}

	@Override
	public Player topAssister() {
		if(getTeams().size() == 0) {			
			return null;
		} else {
			Player topAssister = new Player("", null, null, null, 0); // initial blank Player
			for(Team team: teams) {
				if(team.getPlayers().isEmpty()) {
					continue; // skip any teams that have no players yet
				}
				if (team.topAssister().getAssistsMade() >= topAssister.getAssistsMade()) {
					topAssister = team.topAssister();
				}
			}
			return topAssister;
		}
	}

	@Override
	public int totalGoalsScored() {
		int totalGoalsScored = 0;
		for(Team team : teams) {
			totalGoalsScored += team.totalGoalsScored();
		}
		return totalGoalsScored;
	}

	@Override
	public int totalCardsGiven() {
		int totalCardsGiven = 0;
		for(Team team : teams) {
			totalCardsGiven += team.totalCardsGiven();
		}
		return totalCardsGiven;
	}
	
}
