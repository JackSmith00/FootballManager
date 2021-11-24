package leagueComponents;

import enums.EmploymentStatus;

/**
 * Represents a football player in a team
 * 
 * @author Jack
 *
 */
public class Player extends PersonWithFormationPreference {

	// Attributes
	private String position;
	
	/**
	 * Allows a player to be created
	 * @param name : Name of the player
	 * @param position : A string representing the position the player plays
	 * @param employmentStatus : Employment status of the player
	 * @param payPerYear : How much the player gets payed per year in GBP
	 */
	public Player(String name, String position, EmploymentStatus employmentStatus, int payPerYear) {
		super(name, employmentStatus, payPerYear);
		this.position = position;
	}
	
	// Methods
	// Getter
	/**
	 * @return The position played by the player
	 */
	public String getPosition() {
		return position;
	}
	
	// Setter
	/**
	 * Allows the player to change to a new position
	 * @param position : The new position the player plays in
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
}
