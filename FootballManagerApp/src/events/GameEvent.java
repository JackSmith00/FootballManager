package events;

import java.io.Serializable;

import enums.EventType;
import leagueComponents.Player;

/**
 * Represents an event in a game of football.
 * 
 * This class implements Serializable, so game events can be saved, and
 * Comparable so that events can be ordered based on the time they took
 * place in a game.
 * 
 * @author Jack
 *
 */
public class GameEvent implements Serializable, Comparable<GameEvent> {

	// Attributes
	private EventType type; // type of event that occurred
	private	Player player;  // player involved in the event
	private int gameMinute; // minutes into the game that the event occurred
	
	/**
	 * Allows a game event to be created
	 * 
	 * @param type the type of event that occurred
	 * @param player the player involved with the event
	 * @param gameMinute minutes into the game in which the event occurred
	 */
	public GameEvent(EventType type, Player player, int gameMinute) {
		this.type = type;
		this.player = player;
		this.gameMinute = gameMinute;
	}	
	
	// Methods
	// Getters
	/**
	 * @return the type of event that occurred
	 */
	public EventType getType() {
		return type;
	}
	
	/**
	 * @return the player involved with the event
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return the minute the event occurred in the game
	 */
	public int getGameMinute() {
		return gameMinute;
	}

	/*
	 * I used code found at the following resource to write the
	 * method below
	 * 
	 * HowToDoInJava, 2021. Java Collections sort(). [Online] 
	 * Available at: https://howtodoinjava.com/java/sort/collections-sort/
	 * [Accessed 5 December 2021].
	 * 
	 * I did not copy the code verbatim, I only used it to know find which
	 * interface I needed to implement and override which method to be able
	 * to sort this class by the `gameMinute`.
	 * 
	 */
	@Override
	public int compareTo(GameEvent o) {
		return gameMinute - o.gameMinute; // this will be negative when this GameEvent occurred before GameEvent `o`
	}
	
}
