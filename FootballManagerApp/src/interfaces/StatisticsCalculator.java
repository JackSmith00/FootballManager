package interfaces;

import leagueComponents.Player;

public interface StatisticsCalculator {

	public Player topGoalScorer();
	
	public Player topAssister();
	
	public int totalGoalsScored();
	
	public int totalCardsGiven();
	
}
