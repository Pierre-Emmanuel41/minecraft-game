package fr.pederobien.minecraft.game.impl;

import org.bukkit.command.TabExecutor;

import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class GameConfiguration implements IGameConfiguration {
	private String name;
	private TabExecutor startTabExecutor, stopTabExecutor;
	private ITeamList teams;

	/**
	 * Creates a game configuration.
	 * 
	 * @param name             The configuration name.
	 * @param startTabExecutor The tab executor for specific treatment before starting the game.
	 * @param stopTabExecutor  The tab executor for specific treatment before stopping the game.
	 */
	public GameConfiguration(String name, TabExecutor startTabExecutor, TabExecutor stopTabExecutor) {
		this.name = name;
		this.startTabExecutor = startTabExecutor;
		this.stopTabExecutor = stopTabExecutor;

		teams = new TeamList(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ITeamList getTeams() {
		return teams;
	}

	@Override
	public TabExecutor getStartTabExecutor() {
		return startTabExecutor;
	}

	@Override
	public TabExecutor getStopTabExecutor() {
		return stopTabExecutor;
	}
}
