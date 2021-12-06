package enums;

/**
 * Represents different employment statuses held by a person.
 * Can either be full time or part time
 * @author Jack
 *
 */
public enum EmploymentStatus {
	
	FULL_TIME,
	PART_TIME;
	
	@Override
	public String toString() {
		switch(this) {
		case FULL_TIME:
			return "Full-Time";
		case PART_TIME:
			return "Part-Time";
		}
		return "";
	}
}
