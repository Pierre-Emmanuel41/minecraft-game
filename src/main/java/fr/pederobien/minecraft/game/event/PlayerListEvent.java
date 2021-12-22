package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.IPlayerList;

public class PlayerListEvent extends ProjectGameEvent {
	private IPlayerList list;

	/**
	 * Creates a player list event.
	 * 
	 * @param list The list source involved in this event.
	 */
	public PlayerListEvent(IPlayerList list) {
		this.list = list;
	}

	/**
	 * @return The list involved in this event.
	 */
	public IPlayerList getList() {
		return list;
	}
}
