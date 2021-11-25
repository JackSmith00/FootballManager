package leagueComponents;

import enums.EmploymentStatus;

/**
 * Represents a football player in a team
 * 
 * @author Jack
 *
 */
public class Player extends PersonWithFormationPreference {

	// Attributes
	private String position;
	private int goalsScored = 0; // How many goals have been scored by this player, initially 0
	private int assistsMade = 0; // How many assists have been made by this player, initially 0
	private int cardsGiven = 0; // How many cards have been given to this player, initially 0
	
	/**
	 * Allows a player to be created
	 * @param name : Name of the player
	 * @param position : A string representing the position the player plays
	 * @param employmentStatus : Employment status of the player
	 * @param payPerYear : How much the player gets payed per year in GBP
	 */
	public Player(String name, String position, EmploymentStatus employmentStatus, int payPerYear) {
		super(name, employmentStatus, payPerYear);
		this.position = position;
	}
	
	// Methods
	
	/**
	 * When a player scores a goal, increase their goalsScored value by 1
	 */
	
	public void goalScored() {
		goalsScored += 1;
	}
	
	/**
	 * When a player makes an assist, increase their assitsMade value by 1
	 */
	public void assistMade() {
		assistsMade += 1;
	}
	
	/**
	 * When a player receives a card, increase their cardsGiven value by 1
	 */
	public void cardGiven() {
		cardsGiven += 1;
	}
	
	// Getter
	/**
	 * @return The position played by the player
	 */
	public String getPosition() {
		return position;
	}
	
	/**
	 * @return The number of goals scored by the player
	 */
	public int getGoalsScored() {
		return goalsScored;
	}
	
	/**
	 * @return The number of assists made by the player
	 */
	public int getAssistsMade() {
		return assistsMade;
	}
	
	/**
	 * @return The number of cards given to the player
	 */
	public int getCardsGiven() {
		return cardsGiven;
	}
	
	// Setter
	/**
	 * Allows the player to change to a new position
	 * @param position : The new position the player plays in
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
}
