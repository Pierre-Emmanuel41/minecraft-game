package fr.pederobien.minecraft.game.impl;

import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.game.event.GameNameChangePostEvent;
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
	private PausableState state;

	/**
	 * Creates a game.
	 * 
	 * @param name             The game name.
	 * @param plugin           The plugin associated to this game.
	 * @param startTabExecutor The tab executor in order to run specific treatment according to argument line before starting the
	 *                         game.
	 * @param stopTabExecutor  The tab executor in order to run specific treatment according to argument line before stopping the
	 *                         game.
	 */
	public Game(String name, Plugin plugin, TabExecutor startTabExecutor, TabExecutor stopTabExecutor) {
		this.name = name;
		this.plugin = plugin;
		this.startTabExecutor = startTabExecutor;
		this.stopTabExecutor = stopTabExecutor;

		state = PausableState.NOT_STARTED;
	}

	/**
	 * Creates a game.
	 * 
	 * @param name   The game name.
	 * @param plugin The plugin associated to this game.
	 */
	public Game(String name, Plugin plugin) {
		this(name, plugin, plugin, plugin);
	}

	@Override
	public void start() {
		state = PausableState.STARTED;
		EventManager.callEvent(new GameStartPostEvent(this));
	}

	@Override
	public void stop() {
		state = PausableState.NOT_STARTED;
		EventManager.callEvent(new GameStopPostEvent(this));
	}

	@Override
	public void pause() {
		state = PausableState.PAUSED;
		EventManager.callEvent(new GamePausePostEvent(this));
	}

	@Override
	public void resume() {
		state = PausableState.STARTED;
		EventManager.callEvent(new GameResumePostEvent(this));
	}

	@Override
	public PausableState getState() {
		return state;
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

	/**
	 * Set the name of this game.
	 * 
	 * @param name The new game name.
	 */
	public void setName(String name) {
		if (this.name.equals(name))
			return;

		String oldName = this.name;
		this.name = name;
		EventManager.callEvent(new GameNameChangePostEvent(this, oldName));
	}
}
