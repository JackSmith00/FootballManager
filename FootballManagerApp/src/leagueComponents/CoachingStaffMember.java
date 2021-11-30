package leagueComponents;

import enums.EmploymentStatus;

/**
 * A class to represent a member of coaching staff in a football team,
 * this includes: managers, training staff, fitness coaches etc…
 * @author Jack
 *
 */
public class CoachingStaffMember extends PersonWithFormationPreference {

	// Attributes
	private String role; // The role the coaching staff plays (Manager, Assistant Coach etc)
	
	/**
	 * Constructor for a member of coaching staff
	 * 
	 * @param name : Name of the coaching staff member
	 * @param role : A String representing the role of the coaching staff member (Manager, Assistant Coach etc…)
	 * @param employmentStatus : Employment status of the coaching staff member, either full or part time
	 * @param payPerYear : An int representing how much the staff member is payed per year in GBP
	 */
	public CoachingStaffMember(String name, String role, Formation preferredFormation, EmploymentStatus employmentStatus, int payPerYear) {
		super(name, preferredFormation, employmentStatus, payPerYear);
		this.role = role;
	}
	
	// Methods
	// Getters
	/**
	 * Returns the role of the coaching staff member
	 * @return A string representing the role of the coaching staff member
	 */
	public String getRole() {
		return role;
	}

}
