package leagueComponents;

import enums.EmploymentStatus;

public class Player extends PersonWithFormationPreference {

	// Attributes
	private String position;
	
	// Constructor
	public Player(String name, String position, EmploymentStatus employmentStatus, int payPerYear) {
		super(name, employmentStatus, payPerYear);
		this.position = position;
	}
	
	// Methods
	// Getter
	public String getPosition() {
		return position;
	}
	
	// Setter
	public void setPosition(String position) {
		this.position = position;
	}
	
}
