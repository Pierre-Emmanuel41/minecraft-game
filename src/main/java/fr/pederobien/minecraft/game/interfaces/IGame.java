package fr.pederobien.minecraft.game.interfaces;

import fr.pederobien.utils.IPausable;

public interface IGame extends IPausable {

	/**
	 * {@inheritDoc}.
	 * 
	 * @throws IllegalStateException If the configuration associated to this game is null.
	 */
	@Override
	void start();

	/**
	 * @return The name of this game.
	 */
	String getName();

	/**
	 * @return The configuration associated to this game.
	 */
	IGameConfiguration getConfig();

	/**
	 * Set the configuration associated to this game.
	 * 
	 * @param configuration The new game configuration.
	 */
	void setConfig(IGameConfiguration configuration);
}
