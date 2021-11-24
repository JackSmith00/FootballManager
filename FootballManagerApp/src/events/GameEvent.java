package events;

import java.io.Serializable;

import enums.EventType;
import leagueComponents.Player;

/**
 * Represents an event in a game of football.
 * @author Jack
 *
 */
public class GameEvent implements Serializable {

	// Attributes
	private EventType type; // Type of event that occurred
	private	Player player;  // Player involved in the event
	private int gameMinute; // Minutes into the game that the event occurred
	
	/**
	 * Allows a game event to be created
	 * @param type : The type of event that occurred
	 * @param player : The player involved with the event
	 * @param gameMinute : Minutes into the game in which the event occurred
	 */
	public GameEvent(EventType type, Player player, int gameMinute) {
		this.type = type;
		this.player = player;
		this.gameMinute = gameMinute;
	}	
	
	// Methods
	// Getters
	/**
	 * @return The type of event that occurred
	 */
	public EventType getType() {
		return type;
	}
	
	/**
	 * @return The player involved with the event
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return The minute the event occurred in the game
	 */
	public int getGameMinute() {
		return gameMinute;
	}
	
}
