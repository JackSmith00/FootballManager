package leagueComponents;

import java.io.Serializable;

import enums.EmploymentStatus;

/**
 * An abstract class representing a generic person in a football team
 * 
 * @author Jack
 *
 */
public abstract class Person implements Serializable {

	// Attributes
	private String name;
	private	EmploymentStatus employmentStatus;
	private int payPerYear;
	private Team team;
	
	/**
	 * To be used by child classes calling super() to set variables
	 * associated with a generic person
	 * 
	 * @param name : Name of the person
	 * @param employmentStatus : Employment status of the person
	 * @param payPerYear : How much the person gets payed in a year in GBP
	 */
	public Person(String name, EmploymentStatus employmentStatus, int payPerYear) {
		this.name = name;
		this.employmentStatus = employmentStatus;
		this.payPerYear = payPerYear;
	}
	
	// Methods
	// Getters
	
	/**
	 * @return The name of the person
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The person's employment status in the team
	 */
	public EmploymentStatus getEmploymentStatus() {
		return employmentStatus;
	}
	
	/**
	 * @return How much pay the person recieves per year in GBP
	 */
	public int getPayPerYear() {
		return payPerYear;
	}
	
	/**
	 * @return The team the person is a member of
	 */
	public Team getTeam() {
		return team;
	}
	
	// Setters
	/**
	 * Allows the person to be reallocated to a different team
	 * @param team : The team the person should be moved to
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
}
