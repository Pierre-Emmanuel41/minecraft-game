package fr.pederobien.minecraft.game.interfaces;

import org.bukkit.command.TabExecutor;

import fr.pederobien.utils.IPausable;

public interface IFeature extends IPausable {

	/**
	 * @return The game associated to this feature.
	 */
	IGame getGame();

	/**
	 * @return The name of this feature.
	 */
	String getName();

	/**
	 * @return If this feature is enabled.
	 */
	boolean isEnable();

	/**
	 * Enable or disable this feature.
	 * 
	 * @param isEnable True in order to enable, false to disable.
	 */
	void setEnabled(boolean isEnable);

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before starting the feature.
	 */
	TabExecutor getStartTabExecutor();
}
