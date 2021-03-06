package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamListEvent extends ProjectGameEvent {
	private ITeamList list;

	/**
	 * Creates a team list event.
	 * 
	 * @param list The list source involved in this event.
	 */
	public TeamListEvent(ITeamList list) {
		this.list = list;
	}

	/**
	 * @return The list involved in this event.
	 */
	public ITeamList getList() {
		return list;
	}
}
