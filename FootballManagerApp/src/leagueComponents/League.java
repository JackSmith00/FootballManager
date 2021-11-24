package leagueComponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;import java.io.OutputStream;
import java.io.Serializable;
import java.nio.CharBuffer;
import java.util.ArrayList;

import interfaces.Reader;
import interfaces.Saveable;

/**
 * A class to represent a football league, consisting of teams and their results
 * @author Jack
 *
 */
public class League implements Serializable, Saveable, Reader {

	// Attributes
	private String name; // Name of the football league
	private ArrayList<Team> teams = new ArrayList<Team>(); // Initialised as an empty array by default so teams can be added
	
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
	public ArrayList<Team> getTeams() {
		return teams;
	}
	
}
