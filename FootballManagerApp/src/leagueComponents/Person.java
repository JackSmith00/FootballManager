package leagueComponents;

import java.io.Serializable;

import enums.EmploymentStatus;

public abstract class Person implements Serializable {

	// Attributes
	private String name;
	private	EmploymentStatus employmentStatus;
	private int payPerYear;
	private Team team;
	
	// Constructor
	public Person(String name, EmploymentStatus employmentStatus, int payPerYear) {
		this.name = name;
		this.employmentStatus = employmentStatus;
		this.payPerYear = payPerYear;
	}
	
	// Methods
	// Getters
	public String getName() {
		return name;
	}
	public EmploymentStatus getEmploymentStatus() {
		return employmentStatus;
	}
	public int getPayPerYear() {
		return payPerYear;
	}
	public Team getTeam() {
		return team;
	}
	
	// Setters
	public void setTeam(Team team) {
		this.team = team;
	}
}
