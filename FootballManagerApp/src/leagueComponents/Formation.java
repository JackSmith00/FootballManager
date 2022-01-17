package leagueComponents;

import java.io.Serializable;

/**
 * A class to represent a football formation, with given number of defenders,
 * midfielders and strikers
 * 
 * @author Jack
 *
 */
public class Formation implements Serializable, Comparable<Formation> {

	// Attributes
	private int defenders; // Number of defenders in given formation
	private int midfielders; // Number of midfielders in given formation
	private int strikers; // Number of strikers in given formation
	
	/**
	 * Constructor for a team formation
	 * @param defenders : number of defenders in given formation
	 * @param midfielders : number of midfielders in given formation
	 * @param strikers : number of strikers in given formation
	 */
	public Formation(int defenders, int midfielders, int strikers) {
		this.defenders = defenders; // Set class variable `defenders` equal to input number of defenders
		this.midfielders = midfielders; // Set class variable `midfielders` equal to input number of midfielders
		this.strikers = strikers; // Set class variable `strikers` equal to input number of strikeres
	}
	
	// Methods
	
	@Override
	public String toString() {
		return defenders + "-" + midfielders + "-" + strikers; // Standard format for displaying formations
	}
	
	// Getters
	/**
	 * Returns an int of the number of defenders in the formation
	 * @return : An int of the number of defenders in the formation
	 */
	public int getDefenders() {
		return defenders;
	}
	
	/**
	 * Returns an int of the number of midfielders in the formation
	 * @return : An int of the number of midfielders in the formation
	 */
	public int getMidfielders() {
		return midfielders;
	}
	
	/**
	 * Returns an int of the number of strikers in the formation
	 * @return : An int of the number of strikers in the formation
	 */
	public int getStrikers() {
		return strikers;
	}

	@Override
	public int compareTo(Formation o) {
		// TODO Auto-generated method stub
		if(this.defenders != o.defenders) { // if formations have different number of defenders, sort on this first
			return this.defenders - o.defenders;	
		} else if(this.midfielders != o.midfielders) { // formations have same number of defenders, sort on midfielders if they are different
			return this.midfielders - o.midfielders;
		} else { // formations have same number of defenders and midfielders, sort on strikers
			return this.strikers - o.strikers;
		}
	}
}
