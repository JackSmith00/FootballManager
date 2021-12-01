package events;

import enums.EventType;
import leagueComponents.Player;

/**
 * Represents a game event involving two players in a football game
 * @author Jack
 *
 */
public class TwoPlayerGameEvent extends GameEvent {

	// Attributes
	private Player secondPlayer;
	
	/**
	 * Allows a two player event to be created
	 * @param player : Player involved in event, for a substitution the player substituted on, for a goal, the goal scorer
	 * @param secondPlayer : Second player involved in event, for a substitution the player substituted off, for a goal the player who assisted
	 * @param gameMinute : The number of minutes into the game that the substitution took place
	 */
	public TwoPlayerGameEvent(EventType type, Player player, Player secondPlayer, int gameMinute) {
		super(type, player, gameMinute); 
		this.secondPlayer = secondPlayer;
	}
	
	// Methods
	// Getters
	/**
	 * @return The second player in the event
	 */
	public Player getSecondPlayer() {
		return secondPlayer;
	}

}
