package leagueComponents;

import enums.EmploymentStatus;

public class CoachingStaffMember extends PersonWithFormationPreference {

	// Attributes
	private String role;
	
	// Constructor
	public CoachingStaffMember(String name, String role, EmploymentStatus employmentStatus, int payPerYear) {
		super(name, employmentStatus, payPerYear);
		this.role = role;
	}	
	
	// Methods
	// Getters
	public String getRole() {
		return role;
	}

}
