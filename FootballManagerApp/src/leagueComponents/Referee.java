package leagueComponents;

import enums.EmploymentStatus;

/**
 * Represents a referee in a football team
 * 
 * @author Jack
 *
 */
public class Referee extends Person {

	/**
	 * Allows a referee to be created in a team
	 * 
	 * @param name : Name of the referee
	 * @param employmentStatus : Employment status of the referee
	 * @param payPerYear : How much the referee gets payed per year in GBP
	 */
	public Referee(String name, EmploymentStatus employmentStatus, int payPerYear) {
		super(name, employmentStatus, payPerYear);
	}

}
