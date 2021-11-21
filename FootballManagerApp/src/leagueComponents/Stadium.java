package leagueComponents;

import java.io.Serializable;

public class Stadium implements Serializable {
	
	// Attributes
	private String name;
	private int capacity;
	
	// Constructor
	public Stadium(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}
	
	// Methods
	// Getters
	public String getName() {
		return name;
	}
	
	public int getCapacity() {
		return capacity;
	}

}
