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

public class League implements Serializable, Saveable, Reader {

	// Attributes
	private String name;
	private ArrayList<Team> teams = new ArrayList<Team>();
	
	// Constructors
	public League() { }
	
	public League(String name) {
		this.name = name;
	}
	
	// Methods
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
	public String getName() {
		return name;
	}
	
	public ArrayList<Team> getTeams() {
		return teams;
	}
	
}
