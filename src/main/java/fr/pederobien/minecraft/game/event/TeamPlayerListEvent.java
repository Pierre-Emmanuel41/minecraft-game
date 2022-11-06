package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.ITeamPlayerList;

public class TeamPlayerListEvent extends ProjectGameEvent {
	private ITeamPlayerList list;

	/**
	 * Creates a player list event associated to a team.
	 * 
	 * @param list The list source involved in this event.
	 */
	public TeamPlayerListEvent(ITeamPlayerList list) {
		this.list = list;
	}

	/**
	 * @return The list involved in this event.
	 */
	public ITeamPlayerList getList() {
		return list;
	}
}
