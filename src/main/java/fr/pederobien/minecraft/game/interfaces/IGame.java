package fr.pederobien.minecraft.game.interfaces;

import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

import fr.pederobien.utils.IPausable;

public interface IGame extends IPausable {

	/**
	 * @return The name of this game.
	 */
	String getName();

	/**
	 * @return The plugin associated to this game.
	 */
	Plugin getPlugin();

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before starting the game.
	 */
	TabExecutor getStartTabExecutor();

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before stopping the game.
	 */
	TabExecutor getStopTabExecutor();
}
