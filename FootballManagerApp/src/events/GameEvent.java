package events;

import enums.EventType;
import leagueComponents.Player;

public class GameEvent {

	// Attributes
	private EventType type;
	private	Player player;
	private int gameMinute;
	
	// Methods
	// Getters
	public EventType getType() {
		return type;
	}
	public Player getPlayer() {
		return player;
	}
	public int getGameMinute() {
		return gameMinute;
	}
	
}
