package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeatureListEvent extends ProjectGameEvent {
	private IFeatureList list;

	/**
	 * Creates a player list event.
	 * 
	 * @param list The list source involved in this event.
	 */
	public FeatureListEvent(IFeatureList list) {
		this.list = list;
	}

	/**
	 * @return The list involved in this event.
	 */
	public IFeatureList getList() {
		return list;
	}
}
