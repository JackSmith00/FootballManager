package events;

import enums.EventType;
import leagueComponents.Player;

/**
 * Represents a substitution event in a football game
 * @author Jack
 *
 */
public class Substitution extends GameEvent {

	// Attributes
	private Player playerTakenOff;
	
	/**
	 * Allows a substitution event to be created
	 * @param player : The player coming onto the pitch
	 * @param playerTakenOff : The player leaving the pitch
	 * @param gameMinute : The number of minutes into the game that the substitution took place
	 */
	public Substitution(Player player, Player playerTakenOff, int gameMinute) {
		super(EventType.SUBSTITUTION, player, gameMinute); // The event type will only be a SUBSTITUTION so is not needed as a parameter
		this.playerTakenOff = playerTakenOff;
	}
	
	// Methods
	// Getters
	/**
	 * @return The player taken off in the substitution
	 */
	public Player getPlayerTakenOff() {
		return playerTakenOff;
	}

}
