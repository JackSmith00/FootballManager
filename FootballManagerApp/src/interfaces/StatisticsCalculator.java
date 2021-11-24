package interfaces;

import leagueComponents.Player;

/**
 * Allows a football collection object (league/team) to return common statistics
 * @author Jack
 *
 */
public interface StatisticsCalculator {

	/**
	 * Finds the top goal scorer of the league/team
	 * @return The player who scored the most goals in the league/team
	 */
	public Player topGoalScorer();
	
	/**
	 * Finds the top assister of the league/team
	 * @return The player who made the most assists in the league/team
	 */
	public Player topAssister();
	
	/**
	 * Calculates the total number of goals scored in a league/team
	 * @return The number of goals scored in the league/team
	 */
	public int totalGoalsScored();
	
	/**
	 * Calculates how many cards have been given in a league/team
	 * @return
	 */
	public int totalCardsGiven();
	
}
