package leagueComponents;

import enums.EmploymentStatus;

/**
 * Abstract class of a person in a team who will have a preference on formation
 * 
 * @author Jack
 *
 */
public abstract class PersonWithFormationPreference extends Person {

	// Attributes
	private Formation preferredFormation;
	
	/**
	 * To be used by child classes calling super() to set variables
	 * associated with a person who has a preference of formation
	 * 
	 * @param name : Name of the person
	 * @param employmentStatus : Employment status of the person within the team
	 * @param payPerYear : How much the person gets payed in a year in GBP
	 */
	public PersonWithFormationPreference(String name, Formation preferredFormation, EmploymentStatus employmentStatus, int payPerYear) {
		super(name, employmentStatus, payPerYear);
		this.preferredFormation = preferredFormation;
	}

	// Methods
	// Getter
	/**
	 * @return The preferred formation of the person
	 */
	public Formation getPreferredFormation() {
		return preferredFormation;
	}
	
	// Setter
	/**
	 * Allows the preferred formation to be changed as necessary
	 * @param preferredFormation : New preferred formation
	 */
	public void setPreferredFormation(Formation preferredFormation) {
		this.preferredFormation = preferredFormation;
	}
	
}
