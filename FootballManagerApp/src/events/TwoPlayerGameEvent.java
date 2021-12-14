package events;

import enums.EventType;
import leagueComponents.Player;

/**
 * Represents a game event involving two players in a football game.
 * 
 * This could be a goal where the secondary player is the player who
 * assisted in the goal, or a substitution where the secondary player
 * is the player who is substituted off.
 * 
 * @author Jack
 *
 */
public class TwoPlayerGameEvent extends GameEvent {

	// Attributes
	private Player secondPlayer; // the secondary player of the event
	
	/**
	 * Allows a two player event to be created
	 * @param player the main player involved in event (goal scorer/substituted on)
	 * @param secondPlayer the secondary player involved in event (goal assister/substituted off)
	 * @param gameMinute the number of minutes into the game that the event took place
	 */
	public TwoPlayerGameEvent(EventType type, Player player, Player secondPlayer, int gameMinute) {
		super(type, player, gameMinute); 
		this.secondPlayer = secondPlayer;
	}
	
	// Methods
	// Getters
	/**
	 * @return the secondary player in the event
	 */
	public Player getSecondPlayer() {
		return secondPlayer;
	}

}
