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
	 * @return The team the person is a member of
	 */
	public Team getTeam() {
		return team;
	}
	
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
	
	
	// Setters
	/**
	 * Allows the person to be reallocated to a different team
	 * @param team : The team the person should be moved to
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
	
	/**
	 * Allows the Persons name to be changed
	 * @param name the new name of the Person
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Allows the Persons employment status to be changed
	 * @param employmentStatus the new employment status of the Person
	 */
	public void setEmploymentStatus(EmploymentStatus employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	/**
	 * Allows the Persons pay per year to be changed
	 * @param payPerYear the new pay per year of the Person
	 */
	public void setPayPerYear(int payPerYear) {
		this.payPerYear = payPerYear;
	}

	@Override
	public String toString() {
		return name;
	}
}
