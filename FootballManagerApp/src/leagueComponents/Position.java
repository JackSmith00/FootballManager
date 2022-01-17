package leagueComponents;

import java.io.Serializable;

/**
 * Represents a position held by a player in a football team
 * 
 * @author Jack
 *
 */
public class Position implements Serializable, Comparable<Position> {
	
	private String positionTitle;
	private String positionAbbreviation;
	
	/**
	 * Allows a new Position to be created
	 * 
	 * @param title the full name of the position
	 * @param abbreviation a shortened version of the position name, typically 2-3 letters
	 */
	public Position(String title, String abbreviation) {
		this.positionTitle = title;
		this.positionAbbreviation = abbreviation;
	}
	
	/**
	 * @return the full name of the position
	 */
	public String getPositionTitle() {
		return positionTitle;
	}
	
	/**
	 * @return the shortened label of the position
	 */
	public String getPositionAbbreviation() {
		return positionAbbreviation;
	}
	
	/**
	 * Allows the full name of a position to be changed
	 * @param positionTitle the new title for the position
	 */
	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}
	
	/**
	 * Allows the abbreviation of a position to be changed
	 * @param positionAbbreviation the new abbreviation for the position
	 */
	public void setPositionAbbreviation(String positionAbbreviation) {
		this.positionAbbreviation = positionAbbreviation;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return positionAbbreviation;
	}

	@Override
	public int compareTo(Position o) {
		return positionAbbreviation.compareTo(o.positionAbbreviation);
	}

}
