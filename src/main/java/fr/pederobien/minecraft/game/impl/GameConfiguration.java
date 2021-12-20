package fr.pederobien.minecraft.game.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bukkit.command.TabExecutor;

import fr.pederobien.minecraft.game.event.ConfigurationTeamAddPostEvent;
import fr.pederobien.minecraft.game.event.ConfigurationTeamRemovePostEvent;
import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.utils.event.EventManager;

public class GameConfiguration implements IGameConfiguration {
	private String name;
	private TabExecutor startTabExecutor, stopTabExecutor;
	private List<ITeam> teams;
	private Lock lock;

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
		this.teams = new ArrayList<ITeam>();
		lock = new ReentrantLock(true);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void add(ITeam team) {
		addTeam(team);
		EventManager.callEvent(new ConfigurationTeamAddPostEvent(this, team));
	}

	@Override
	public void remove(ITeam team) {
		removeTeam(team);
		EventManager.callEvent(new ConfigurationTeamRemovePostEvent(this, team));
	}

	@Override
	public void clear() {
		lock.lock();
		try {
			int size = teams.size();
			for (int i = 0; i < size; i++) {
				ITeam team = teams.remove(0);
				EventManager.callEvent(new ConfigurationTeamRemovePostEvent(this, team));
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public List<ITeam> getTeams() {
		return Collections.unmodifiableList(teams);
	}

	@Override
	public TabExecutor getStartTabExecutor() {
		return startTabExecutor;
	}

	@Override
	public TabExecutor getStopTabExecutor() {
		return stopTabExecutor;
	}

	/**
	 * Thread safe operation that adds a team to the teams list.
	 * 
	 * @param team The team to add.
	 */
	private void addTeam(ITeam team) {
		lock.lock();
		try {
			teams.add(team);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thread safe operation that removes a teams from the teams list.
	 * 
	 * @param team The team to remove.
	 */
	private void removeTeam(ITeam team) {
		lock.lock();
		try {
			teams.remove(team);
		} finally {
			lock.unlock();
		}
	}
}
