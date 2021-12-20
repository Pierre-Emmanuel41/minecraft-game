package fr.pederobien.minecraft.game.interfaces;

import java.util.List;

import org.bukkit.command.TabExecutor;

public interface IGameConfiguration {

	/**
	 * @return The name of this configuration.
	 */
	String getName();

	/**
	 * Append the given team to this configuration.
	 * 
	 * @param team The team to add.
	 */
	void add(ITeam team);

	/**
	 * Remove the given team from this configuration.
	 * 
	 * @param team The team to remove.
	 */
	void remove(ITeam team);

	/**
	 * Remove each registered team from this configuration. It also clear each registered teams.
	 */
	void clear();

	/**
	 * @return The list of registered team associated to this configuration. This list is unmodifiable.
	 */
	List<ITeam> getTeams();

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before starting the game.
	 */
	TabExecutor getStartTabExecutor();

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before stopping the game.
	 */
	TabExecutor getStopTabExecutor();
}
