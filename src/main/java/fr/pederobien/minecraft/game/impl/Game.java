package fr.pederobien.minecraft.game.impl;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.game.event.GameConfigurationChangePostEvent;
import fr.pederobien.minecraft.game.event.GamePausePostEvent;
import fr.pederobien.minecraft.game.event.GameResumePostEvent;
import fr.pederobien.minecraft.game.event.GameStartPostEvent;
import fr.pederobien.minecraft.game.event.GameStopPostEvent;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;
import fr.pederobien.utils.event.EventManager;

public class Game implements IGame {
	private String name;
	private Plugin plugin;
	private IGameConfiguration configuration;
	private EGameState state;

	/**
	 * Creates a game.
	 * 
	 * @param name          The game name.
	 * @param plugin        The plugin associated to this name.
	 * @param configuration The game configuration.
	 */
	public Game(String name, Plugin plugin, IGameConfiguration configuration) {
		this.name = name;
		this.configuration = configuration;
		state = EGameState.NOT_STARTED;
	}

	/**
	 * Creates a game.
	 * 
	 * @param name   The game name.
	 * @param plugin The plugin associated to this game.
	 */
	public Game(String name, Plugin plugin) {
		this(name, plugin, null);
	}

	@Override
	public void start() {
		if (configuration == null)
			throw new IllegalStateException("There is no configuration associated to this game");

		for (IFeature feature : configuration.getFeatures())
			feature.start();

		state = EGameState.STARTED;
		EventManager.callEvent(new GameStartPostEvent(this));
	}

	@Override
	public void stop() {
		for (IFeature feature : configuration.getFeatures())
			feature.stop();

		state = EGameState.NOT_STARTED;
		EventManager.callEvent(new GameStopPostEvent(this));
	}

	@Override
	public void pause() {
		for (IFeature feature : configuration.getFeatures())
			feature.pause();

		state = EGameState.PAUSED;
		EventManager.callEvent(new GamePausePostEvent(this));
	}

	@Override
	public void resume() {
		for (IFeature feature : configuration.getFeatures())
			feature.resume();

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
	public IGameConfiguration getConfig() {
		return configuration;
	}

	@Override
	public void setConfig(IGameConfiguration configuration) {
		if (this.configuration != null && this.configuration == configuration)
			return;

		IGameConfiguration oldConfiguration = this.configuration;
		this.configuration = configuration;
		EventManager.callEvent(new GameConfigurationChangePostEvent(this, oldConfiguration));
	}

	@Override
	public EGameState getState() {
		return state;
	}
}
