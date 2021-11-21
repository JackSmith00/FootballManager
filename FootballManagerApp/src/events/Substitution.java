package events;

import leagueComponents.Player;

public class Substitution extends GameEvent {

	// Attributes
	private Player playerTakenOff;
	
	// Methods
	// Getters
	public Player getPlayerTakenOff() {
		return playerTakenOff;
	}
}
