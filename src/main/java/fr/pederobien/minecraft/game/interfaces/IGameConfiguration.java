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
	 * Get the team associated to the given name.
	 * 
	 * @param name The name of the team to return.
	 * 
	 * @return The team if the given name corresponds to a registered team, null otherwise.
	 */
	ITeam getTeam(String name);

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
