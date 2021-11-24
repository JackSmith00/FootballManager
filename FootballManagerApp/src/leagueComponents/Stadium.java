package leagueComponents;

import java.io.Serializable;

/**
 * A class to represent a football stadium that can be used as
 * a homeground for football teams
 * 
 * @author Jack
 *
 */
public class Stadium implements Serializable {
	
	// Attributes
	private String name;
	private int capacity;
	
	/**
	 * Allows a new stadium to be created
	 * @param name : The name of the stadium
	 * @param capacity : How many people the stadium can hold
	 */
	public Stadium(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}
	
	// Methods
	// Getters
	
	/**
	 * @return The name of the stadium
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return How many people the stadium can hold
	 */
	public int getCapacity() {
		return capacity;
	}

}
