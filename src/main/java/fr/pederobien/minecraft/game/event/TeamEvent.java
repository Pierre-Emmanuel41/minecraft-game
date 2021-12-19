package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.ITeam;

public class TeamEvent extends GameEvent {
	private ITeam team;

	/**
	 * Creates a team event.
	 * 
	 * @param team The team source involved in this event.
	 */
	public TeamEvent(ITeam team) {
		this.team = team;
	}

	/**
	 * @return The team involved in this event.
	 */
	public ITeam getTeam() {
		return team;
	}
}
