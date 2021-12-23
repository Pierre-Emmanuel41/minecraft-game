package fr.pederobien.minecraft.game.interfaces;

import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.game.impl.EGameState;
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
	 * @return The list of teams for this configuration.
	 */
	ITeamList getTeams();

	/**
	 * @return The list of features for this configuration.
	 */
	IFeatureList getFeatures();

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before starting the game.
	 */
	TabExecutor getStartTabExecutor();

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before stopping the game.
	 */
	TabExecutor getStopTabExecutor();

	/**
	 * @return The state in which this game is.
	 */
	EGameState getState();
}
