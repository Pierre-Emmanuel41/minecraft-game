package fr.pederobien.minecraft.game.interfaces;

import org.bukkit.command.TabExecutor;

public interface IGameConfiguration {

	/**
	 * @return The name of this configuration.
	 */
	String getName();

	/**
	 * @return The list of teams for this configuration.
	 */
	ITeamList getTeams();

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before starting the game.
	 */
	TabExecutor getStartTabExecutor();

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before stopping the game.
	 */
	TabExecutor getStopTabExecutor();
}
