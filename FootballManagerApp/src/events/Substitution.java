package events;

import enums.EventType;
import leagueComponents.Player;

public class Substitution extends GameEvent {

	// Attributes
	private Player playerTakenOff;
	
	// Constructor
	public Substitution(Player player, Player playerTakenOff, int gameMinute) {
		super(EventType.SUBSTITUTION, player, gameMinute);
		this.playerTakenOff = playerTakenOff;
	}
	
	// Methods
	// Getters
	public Player getPlayerTakenOff() {
		return playerTakenOff;
	}

}
