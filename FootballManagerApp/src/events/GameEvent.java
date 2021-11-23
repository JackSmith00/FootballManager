package events;

import java.io.Serializable;

import enums.EventType;
import leagueComponents.Player;

public class GameEvent implements Serializable {

	// Attributes
	private EventType type;
	private	Player player;
	private int gameMinute;
	
	// Constructor
	public GameEvent(EventType type, Player player, int gameMinute) {
		super();
		this.type = type;
		this.player = player;
		this.gameMinute = gameMinute;
	}	
	
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
