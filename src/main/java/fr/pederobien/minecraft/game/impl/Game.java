package fr.pederobien.minecraft.game.impl;

import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.game.event.GamePausePostEvent;
import fr.pederobien.minecraft.game.event.GameResumePostEvent;
import fr.pederobien.minecraft.game.event.GameStartPostEvent;
import fr.pederobien.minecraft.game.event.GameStopPostEvent;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.utils.event.EventManager;

public class Game implements IGame {
	private String name;
	private Plugin plugin;
	private TabExecutor startTabExecutor, stopTabExecutor;
	private EGameState state;

	/**
	 * Creates a game.
	 * 
	 * @param name   The game name.
	 * @param plugin The plugin associated to this game.
	 */
	public Game(String name, Plugin plugin) {
		this.name = name;
		state = EGameState.NOT_STARTED;
	}

	@Override
	public void start() {
		state = EGameState.STARTED;
		EventManager.callEvent(new GameStartPostEvent(this));
	}

	@Override
	public void stop() {
		state = EGameState.NOT_STARTED;
		EventManager.callEvent(new GameStopPostEvent(this));
	}

	@Override
	public void pause() {
		state = EGameState.PAUSED;
		EventManager.callEvent(new GamePausePostEvent(this));
	}

	@Override
	public void resume() {
		state = EGameState.STARTED;
		EventManager.callEvent(new GameResumePostEvent(this));
	}

	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public TabExecutor getStartTabExecutor() {
		return startTabExecutor;
	}

	@Override
	public TabExecutor getStopTabExecutor() {
		return stopTabExecutor;
	}

	@Override
	public EGameState getState() {
		return state;
	}
}
