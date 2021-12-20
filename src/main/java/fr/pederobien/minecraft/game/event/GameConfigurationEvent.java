package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;

public class GameConfigurationEvent extends GameEvent {
	private IGameConfiguration configuration;

	/**
	 * Creates a configuration event.
	 * 
	 * @param configuration The configuration source involved in this event.
	 */
	public GameConfigurationEvent(IGameConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return The configuration involved in this event.
	 */
	public IGameConfiguration getConfiguration() {
		return configuration;
	}
}
