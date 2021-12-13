package leagueComponents;

import java.io.Serializable;

public class Position implements Serializable, Comparable<Position> {
	
	private String positionTitle;
	private String positionAbbreviation;
	
	public Position(String title, String abbreviation) {
		// TODO Auto-generated constructor stub
		this.positionTitle = title;
		this.positionAbbreviation = abbreviation;
	}
	
	public String getPositionTitle() {
		return positionTitle;
	}
	
	public String getPositionAbbreviation() {
		return positionAbbreviation;
	}
	
	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}
	
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
