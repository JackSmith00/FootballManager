package leagueComponents;

import enums.EmploymentStatus;

public abstract class PersonWithFormationPreference extends Person {

	// Attributes
	private Formation preferredFormation;
	
	// Constructor
	public PersonWithFormationPreference(String name, EmploymentStatus employmentStatus, int payPerYear) {
		super(name, employmentStatus, payPerYear);
	}

	// Methods
	// Getter	
	public Formation getPreferredFormation() {
		return preferredFormation;
	}
	
	// Setter
	public void setPreferredFormation(Formation preferredFormation) {
		this.preferredFormation = preferredFormation;
	}
	
}
