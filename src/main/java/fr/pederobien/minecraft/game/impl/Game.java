package fr.pederobien.minecraft.game.impl;

import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;

public class Game implements IGame {
	private String name;
	private IGameConfiguration configuration;
	private EGameState state;

	/**
	 * Creates a game.
	 * 
	 * @param name          The game name.
	 * @param configuration The game configuration.
	 */
	public Game(String name, IGameConfiguration configuration) {
		this.name = name;
		this.configuration = configuration;
		state = EGameState.NOT_STARTED;
	}

	/**
	 * Creates a game.
	 * 
	 * @param name The game name.
	 */
	public Game(String name) {
		this.name = name;
		state = EGameState.NOT_STARTED;
	}

	@Override
	public void start() {
		if (configuration == null)
			throw new IllegalStateException("There is no configuration associated to this game");
		state = EGameState.STARTED;
	}

	@Override
	public void stop() {
		state = EGameState.NOT_STARTED;
	}

	@Override
	public void pause() {
		state = EGameState.PAUSED;
	}

	@Override
	public void resume() {
		state = EGameState.STARTED;
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
		this.configuration = configuration;
	}

	@Override
	public EGameState getState() {
		return state;
	}
}
